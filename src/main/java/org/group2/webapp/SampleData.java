
package org.group2.webapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.group2.webapp.entity.Assessment;
import org.group2.webapp.entity.Authority;
import org.group2.webapp.entity.Circumstance;
import org.group2.webapp.entity.Claim;
import org.group2.webapp.entity.Faculty;
import org.group2.webapp.entity.Item;
import org.group2.webapp.entity.User;
import org.group2.webapp.repository.AssessmentRepository;
import org.group2.webapp.repository.AuthorityRepository;
import org.group2.webapp.repository.CircumstanceRepository;
import org.group2.webapp.repository.ClaimRepository;
import org.group2.webapp.repository.FacultyRepository;
import org.group2.webapp.repository.ItemRepository;
import org.group2.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Dam Cao Son
 * @Contact kunedo1104@gmail.com
 */
@Component
public class SampleData {
    private static final Logger logger = Logger.getLogger(SampleData.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private CircumstanceRepository circumstanceRepository;
    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
    private PasswordEncoder passmwordEncoder;

	@Autowired

	public SampleData() {
		super();
	}

	public void run() {
		if (claimRepository.findAll().size() > 0) {
			return;
		} else {
			claimRepository.deleteAll();
			itemRepository.deleteAll();
			circumstanceRepository.deleteAll();
			userRepository.deleteAll();
			assessmentRepository.deleteAll();
			facultyRepository.deleteAll();
			authorityRepository.deleteAll();
		}

		logger.info("Initializing sample data...");

		Authority roleAdmin = new Authority("ROLE_ADMIN");
		Authority roleManagere = new Authority("ROLE_MANAGER");
		Authority roleCoordinator = new Authority("ROLE_COORDINATOR");
		Authority roleStudent = new Authority("ROLE_STUDENT");

		authorityRepository.save(roleAdmin);
		authorityRepository.save(roleManagere);
		authorityRepository.save(roleCoordinator);
		authorityRepository.save(roleStudent);

		Faculty f1 = new Faculty("Application of Computer Science");
		Faculty f2 = new Faculty("Database and Web Developement");
		Faculty f3 = new Faculty("Human Computer Interactive");
		Faculty f4 = new Faculty("Business Management");
		Faculty f5 = new Faculty("Graphic Design");

		facultyRepository.save(f1);
		facultyRepository.save(f2);
		facultyRepository.save(f3);
		facultyRepository.save(f4);
		facultyRepository.save(f5);

                //admin role sample data
		User admin = new User("admin", passmwordEncoder.encode("1234"), "Ai",
				"Nguyen", "ainvgc00704@fpt.edu.vn");
		admin.getAuthorities().add(roleAdmin);
		admin.getAuthorities().add(roleManagere);
                //manager role sample data
		User manager = new User("manager", passmwordEncoder.encode("1234"),
				"Tung",
				"Tien", "tungptgc00676@fpt.edu.vn");
		manager.getAuthorities().add(roleManagere);
                //coordinator role sample data
		User coordinator1 = new User("kunedo", passmwordEncoder.encode("1234"),
				"Kunedo", "Caspio", "kunedo1104@gmail.com",
				f1);
		coordinator1.getAuthorities().add(roleCoordinator);
		User coordinator2 = new User("nhocKaka", passmwordEncoder.encode("1234"),
				"Nhoc", "Kaka", "caubenuoc1995@gmail.com",
				f2);
		coordinator2.getAuthorities().add(roleCoordinator);
                //student role sample data
		User s1 = new User("sondcgc00681", passmwordEncoder.encode("1234"), "Son",
				"Dam",
				"sondcgc00681@fpt.edu.vn", f1);
		s1.getAuthorities().add(roleStudent);
		User s2 = new User("namnhgc00683", passmwordEncoder.encode("1234"), "Nam",
				"Nguyen",
				"namnhgc00683@fpt.edu.vn", f1);
		s2.getAuthorities().add(roleStudent);
		User s3 = new User("anhndgc00893", passmwordEncoder.encode("1234"), "Anh",
				"Duc",
				"anhndgc00893@fpt.edu.vn", f4);
		s3.getAuthorities().add(roleStudent);
		User s4 = new User("dfChicken", passmwordEncoder.encode("1234"), "Nam",
				"Hai",
				"hainam.4795@gmail.com", f2);
		s4.getAuthorities().add(roleStudent);

		User s5 = new User("s5", passmwordEncoder.encode("1234"), "s5firstname",
				"s5LastName",
				"email9@fpt.edu.vn", f5);
		s5.getAuthorities().add(roleStudent);

		userRepository.save(admin);
		userRepository.save(manager);
		userRepository.save(coordinator1);
		userRepository.save(coordinator2);
		userRepository.save(s1);
		userRepository.save(s2);
		userRepository.save(s3);
		userRepository.save(s4);

		Assessment ass1 = new Assessment("COMP-1108", "Project", f1);
		Assessment ass2 = new Assessment("COMP-1639", "Database Engineering", f2);
		Assessment ass3 = new Assessment("COMP-1640", "Enterprise Web Software Dev", f2);
		Assessment ass4 = new Assessment("COMP-1661", "Application Dev for Mobile Dev", f1);
		Assessment ass5 = new Assessment("COMP-1649", "Interaction Design", f3);
		Assessment ass6 = new Assessment("COMP-1714", "Software Engineeing Mgmnt", f3);
		Assessment ass7 = new Assessment("COMP-1648", "Dev Framework & Methods", f1);

		assessmentRepository.save(ass1);
		assessmentRepository.save(ass2);
		assessmentRepository.save(ass3);
		assessmentRepository.save(ass4);
		assessmentRepository.save(ass5);
		assessmentRepository.save(ass6);
		assessmentRepository.save(ass7);

		Item item1 = new Item("23718", ass1, "COMP 1108 Demonstration");
		Item item2 = new Item("23717", ass1, "COMP 1108 Final Report");
		Item item3 = new Item("24761", ass2, "COMP 1639 Exam");
		Item item4 = new Item("24760", ass2, "COMP 1639 Practical Coursework");
		Item item5 = new Item("24767", ass3, "COMP 1640 Coureswork");
		Item item6 = new Item("25042", ass7, "COMP 1648 Coursework");
		Item item7 = new Item("25045", ass5, "COMP 1649 Coursework");
		Item item8 = new Item("25066", ass4, "COMP 1661 Coursework");
		Item item9 = new Item("25067", ass4, "COMP 1661 Logbook");
		Item item10 = new Item("25391", ass6, "COMP 1714 Coursework");
		Item item11 = new Item("25392", ass6, "COMP 1714 Exam");

		itemRepository.save(item1);
		itemRepository.save(item2);
		itemRepository.save(item3);
		itemRepository.save(item4);
		itemRepository.save(item5);
		itemRepository.save(item6);
		itemRepository.save(item7);
		itemRepository.save(item8);
		itemRepository.save(item9);
		itemRepository.save(item10);
		itemRepository.save(item11);

		Circumstance cir1 = new Circumstance("Accident");
		Circumstance cir2 = new Circumstance("Bereavement");
		Circumstance cir3 = new Circumstance("Haritemment or Assault");
		Circumstance cir4 = new Circumstance("Jury Service");
		Circumstance cir5 = new Circumstance("Medical");
		Circumstance cir6 = new Circumstance("Organisational maladministration");
		Circumstance cir7 = new Circumstance("Unexpected personal or family difficulties");
		Circumstance cir8 = new Circumstance("Work (part-time and placement studends only)");
		Circumstance cir9 = new Circumstance("Other");

		circumstanceRepository.save(cir1);
		circumstanceRepository.save(cir2);
		circumstanceRepository.save(cir3);
		circumstanceRepository.save(cir4);
		circumstanceRepository.save(cir5);
		circumstanceRepository.save(cir6);
		circumstanceRepository.save(cir7);
		circumstanceRepository.save(cir8);
		circumstanceRepository.save(cir9);

		Claim claimTmp = new Claim();
		claimTmp.setItem(item1);
		claimTmp.getCircumstances().add(cir1);
		claimTmp.setUser(s2);
		claimTmp.setProcessed_time(new Date());
		claimTmp.setContent("hello world");

		claimRepository.save(claimTmp);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		try {
			claimTmp = new Claim("", ""
                                + "What is Lorem Ipsum?\n" +
"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
                                + "", new Date(), new Date(), 1, s2, new Date());
			claimTmp.clearCircumstances();
			claimTmp.addCircumstance(cir1);
			claimTmp.addCircumstance(cir2);
			claimTmp.addCircumstance(cir3);
			claimTmp.setItem(item1);
			claimTmp.setUser(s2);
			claimRepository.save(claimTmp);

			claimTmp = new Claim("", ""
                                + "Why do we use it?\n" +
"It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)."
                                + "", new Date(), null, 1, s2, formatter.parse("2017-05-05"));
			claimTmp.clearCircumstances();
			claimTmp.addCircumstance(cir2);
			claimTmp.addCircumstance(cir3);
			claimTmp.addCircumstance(cir4);
			claimTmp.setItem(item3);
			claimTmp.setUser(s2);
			claimRepository.save(claimTmp);

			claimTmp = new Claim("", ""
                                + "Where does it come from?\n" +
"Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32."
                                + "", formatter.parse("2016-05-05"), null, 1, s2, new Date());
			claimTmp.clearCircumstances();
			claimTmp.addCircumstance(cir4);
			claimTmp.addCircumstance(cir5);
			claimTmp.setItem(item4);
			claimTmp.setUser(s2);
			claimRepository.save(claimTmp);

			claimTmp = new Claim("", ""
                                + "Where can I get some?\n" +
"There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc."
                                + "", new Date(), new Date(), 1, s4, formatter.parse("2017-04-28"));
			claimTmp.clearCircumstances();
			claimTmp.addCircumstance(cir5);
			claimTmp.addCircumstance(cir6);
			claimTmp.setItem(item5);
			claimTmp.setUser(s2);
			claimRepository.save(claimTmp);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		logger.info("Sample data ready!");
	}

	/**
	 * ${tags}
	 */
	public void test() {
		// List<Assessment> assessments = assessmentRepository.findAll();
		// System.out.println("assessments: " + assessments.size());
		// for (Assessment ass : assessments) {
		// System.out.println("Item of assessment(" + ass.getTitle() + "): " +
		// ass.getItems().size());
		// }
	}

	public static void main(String[] args) {
		
	}
}
