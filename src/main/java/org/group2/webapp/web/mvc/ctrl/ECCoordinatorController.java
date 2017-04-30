
package org.group2.webapp.web.mvc.ctrl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.group2.webapp.constraints.ClaimStatusContraints;
import org.group2.webapp.entity.Claim;
import org.group2.webapp.entity.User;
import org.group2.webapp.repository.ClaimRepository;
import org.group2.webapp.repository.UserRepository;
import org.group2.webapp.web.util.MailSender;
import org.group2.webapp.web.util.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Dam Cao Son
 * @Contact kunedo1104@gmail.com
 *
 */
@Controller
@RequestMapping("/eccoordinator")
public class ECCoordinatorController {
	private static final Logger logger = LoggerFactory.getLogger(ECCoordinatorController.class);

	@Autowired
	private ClaimRepository claimRepo;
	@Autowired
	private UserRepository userRepo;

	@GetMapping("/claim")
	public String index(HttpServletRequest req) {
		User currentUser = SessionUtils.getCurrentUserSession(userRepo).get();
		List<Claim> claims = claimRepo.findAllByFacultyId(currentUser.getFaculty().getId());
		Collections.sort(claims, new Comparator<Claim>() {

			@Override
			public int compare(Claim o1, Claim o2) {
				return o2.getCreated_time().compareTo(o1.getCreated_time());
			}
		});
		req.setAttribute("claims", claims);
		return "claim/claims";
	}

	@GetMapping("/claim/process")
	public String index(Long id, HttpServletRequest req) {
		// User currentUser =
		// SessionUtils.getCurrentUserSession(userRepo).get();
		Claim claim = claimRepo.findOne(id);
//		System.out.println("claim: " + claim);
		req.setAttribute("claim", claim);

		if (claim.getEvidence() != null && !"".equals(claim.getEvidence())) {
			String[] evidences = claim.getEvidence().split(";");
			System.out.println("Evidences: " + evidences.length);
			req.setAttribute("evidences", evidences);
		}

		return "claim/process";
	}

	@PostMapping("/claim/process")
	public String process(Long claimId, Integer status, String decision, HttpServletRequest req) {
		Claim claim = claimRepo.findOne(claimId);
		claim.setStatus(ClaimStatusContraints.DONE);
		claim.setDecision(decision);
		claim.setProcessed_time(new Date());
		claimRepo.save(claim);
		MailSender.informStudentThatTheClaimProcessed(claim);
		req.setAttribute("claimProcessed", true);
		return index(req);
	}
}
