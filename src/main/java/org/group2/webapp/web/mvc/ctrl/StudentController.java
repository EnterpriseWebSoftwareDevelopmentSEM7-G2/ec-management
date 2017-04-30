/**
 *
 */
package org.group2.webapp.web.mvc.ctrl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.group2.webapp.constraints.ClaimStatusContraints;
import org.group2.webapp.entity.Assessment;
import org.group2.webapp.entity.Circumstance;
import org.group2.webapp.entity.Claim;
import org.group2.webapp.entity.Item;
import org.group2.webapp.entity.User;
import org.group2.webapp.repository.AssessmentRepository;
import org.group2.webapp.repository.CircumstanceRepository;
import org.group2.webapp.repository.ClaimRepository;
import org.group2.webapp.repository.ItemRepository;
import org.group2.webapp.repository.UserRepository;
import org.group2.webapp.security.AuthoritiesConstants;
import org.group2.webapp.web.util.MailSender;
import org.group2.webapp.web.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Dam Cao Son
 * @Contact kunedo1104@gmail.com
 *
 */
@Controller
@RequestMapping("/student/claim")
public class StudentController {

	private static final Logger logger = Logger.getLogger(StudentController.class);
	@Autowired
	ServletContext servletContext;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AssessmentRepository assessmentRepo;
	@Autowired
	private ClaimRepository claimRepo;
	@Autowired
	private CircumstanceRepository circumRepo;
	@Autowired
	private ItemRepository itemRepo;

	@GetMapping("/view")
	public String viewClaim(HttpServletRequest req) {
		User currentUser = SessionUtils.getCurrentUserSession(userRepo).get();
		List<String> str = Collections.list(req.getSession().getAttributeNames());
		for (String s : str) {
			System.out.println("session name: " + s);
			System.out.println("value of: " + req.getSession().getAttribute(s));
		}
		List<Claim> claims = claimRepo.findAllByUserId(currentUser.getId());
		Collections.sort(claims, new Comparator<Claim>() {

			@Override
			public int compare(Claim o1, Claim o2) {
				return o2.getCreated_time().compareTo(o1.getCreated_time());
			}
		});

		req.setAttribute("claims", claims);
		return "claim/claims";
	}

	@GetMapping("/add")
	public String addClaim(HttpServletRequest req) {
		User currentUser = SessionUtils.getCurrentUserSession(userRepo).get();
		List<Assessment> suitableAssessment = assessmentRepo.findAll().stream()
				.filter(ass -> ass.getFaculty().getId() == currentUser.getFaculty().getId())
				.collect(Collectors.toList());
		for (Assessment ass : suitableAssessment) {
			for (Item item : ass.getItems()) {
				if (!item.isValid()) {
					ass.getItems().remove(item);
				}
			}
		}

		req.setAttribute("allAssessments", suitableAssessment);
		req.setAttribute("allCircumstances", circumRepo.findAll());
		logger.debug("suitableAssessment: " + suitableAssessment.size());
		return "claim/add";
	}

	@PostMapping(path = "/add", headers = "Content-Type=multipart/*", consumes = "application/x-www-form-urlencoded")
	public String addClaim(String[] itemCrns, Long[] circumstances, String content,
			HttpServletRequest req, @RequestParam("evidenceFiles") MultipartFile[] files) {
		if (itemCrns != null && circumstances != null && content != null) {
			List<Circumstance> myCirumstance = circumRepo.findAll(Arrays.asList(circumstances));
			User currentUser = SessionUtils.getCurrentUserSession(userRepo).get();
			for (String ass : itemCrns) {
				Item item = itemRepo.findOne(ass);
				Claim myClaim = new Claim();
				myClaim.setStatus(ClaimStatusContraints.PENDING);
				myClaim.setCanUploadMoreEvidence(true);
				myClaim.setUser(currentUser);
				// upload evidences
				String evidences = getEvidencesFromFileArray(files);
				logger.info(evidences);
				myClaim.setEvidence(evidences);

				myClaim.setContent(content);
				myClaim.setItem(item);
				myClaim.getCircumstances().addAll(myCirumstance);
				claimRepo.save(myClaim);
				MailSender.informCoordinatorNewClaim(myClaim,
						userRepo.findAllUserByAuthority(AuthoritiesConstants.COORDINATOR));
			}
			req.setAttribute("claimAdded", true);
			return viewClaim(req);
		} else {
			return addClaim(req);
		}
	}

	@GetMapping("/detail")
	public String detail(Long id, HttpServletRequest req) {
		Claim claim = claimRepo.findOne(id);
		System.out.println("claim: " + claim);
		req.setAttribute("claim", claim);

		if (claim.getEvidence() != null && !claim.getEvidence().equals("")) {
			String[] evidences = claim.getEvidence().split(";");
			System.out.println("Evidences: " + evidences.length);
			req.setAttribute("evidences", evidences);
		}

		return "claim/detail";
	}

	@PostMapping(path = "/update", headers = "Content-Type=multipart/*", consumes = "application/x-www-form-urlencoded")
	public String update(Long id, HttpServletRequest req, @RequestParam("evidenceFiles") MultipartFile[] files) {
		Claim claim = claimRepo.findOne(id);

		String evidences = claim.getEvidence() + ";" + getEvidencesFromFileArray(files);

		logger.info(evidences);

		claim.setEvidence(evidences);

		claimRepo.save(claim);
		req.setAttribute("updatedEvidence", true);
		return viewClaim(req);
	}

	public String getEvidencesFromFileArray(MultipartFile[] files) {
		int length = files.length;
		String evidences = "";

		for (int i = 0; i < length; i++) {
			String name;
			MultipartFile file = files[i];
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();

					long time = System.currentTimeMillis();
					java.sql.Timestamp timestmp = new java.sql.Timestamp(time);
					name = "evidence" + i + "_" + timestmp.getTime() + "."
							+ FilenameUtils.getExtension(file.getOriginalFilename());

					// Creating the directory to store file
					String actualPath = servletContext.getRealPath("");
					String fileLocation = actualPath + File.separator + "evidents";
					File dir = new File(fileLocation);
					if (!dir.exists()) {
						dir.mkdirs();
					}

					// Create the file on server
					File serverFile = new File(dir.getAbsolutePath()
							+ File.separator + name);

					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

					if (i == 0) {
						evidences = evidences + name;
					} else if (i > 0) {
						evidences = evidences + ";" + name;
					}

					logger.info("Saved to: "
							+ serverFile.getAbsolutePath());

				} catch (FileNotFoundException ex) {
					logger.info(ex.getMessage());
				} catch (IOException ex) {
					logger.info(ex.getMessage());
				}
			}
		}
		return evidences;
	}
}
