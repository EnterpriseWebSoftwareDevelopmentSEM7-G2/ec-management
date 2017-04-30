/**
 * 
 */
package org.group2.webapp;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.group2.webapp.entity.Claim;
import org.group2.webapp.entity.User;
import org.group2.webapp.repository.AssessmentRepository;
import org.group2.webapp.repository.AuthorityRepository;
import org.group2.webapp.repository.CircumstanceRepository;
import org.group2.webapp.repository.ClaimRepository;
import org.group2.webapp.repository.FacultyRepository;
import org.group2.webapp.repository.ItemRepository;
import org.group2.webapp.repository.UserRepository;
import org.group2.webapp.security.AuthoritiesConstants;
import org.group2.webapp.web.util.DateTimeUtils;
import org.group2.webapp.web.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Dam Cao Son
 * @Contact kunedo1104@gmail.com
 *
 */
@Component
public class ClaimChecker extends Thread {
	private static final Logger logger = Logger.getLogger(ClaimChecker.class);
	public static final int CHECK_FREQUENCY = 1000 * 3;
	public static final int SO_NGAY_HET_HAN_XU_LY = 14;
	public static final int SO_NGAY_NHAC_NHO_XY_LY = 10;
	public static final int SO_NGAY_HET_HAN_UPLOAD_EVIDENCE = 10;
	public static final int SO_NGAY_NHAC_NHO_UPLOAD_EVIDENCE = 7;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ClaimRepository claimRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				checkForAllClaim();
				Thread.sleep(CHECK_FREQUENCY);
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.warn("Claim checker thread have been disabled!");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#start()
	 */
	@Override
	public synchronized void start() {
		super.start();
		logger.info("ClaimChecker started");
	}

	/**
	 * ${tags}
	 */
	private void checkForAllClaim() {
		LocalDateTime today = LocalDateTime.now();

		// Process for claim did not processed
		List<Claim> claimsNotBeProcessed = claimRepository.findAllNotBeProcessed();
//		for (Claim claim : claimsNotBeProcessed) {
//			LocalDateTime submitDate = LocalDateTime.ofInstant(claim.getCreated_time().toInstant(),
//					ZoneId.systemDefault());
//			int code = 0;
//			long numberOfDay = submitDate.until(today, ChronoUnit.DAYS);
//			if (numberOfDay >= SO_NGAY_HET_HAN_XU_LY) {
//				code = 1;
//			} else if (numberOfDay >= SO_NGAY_NHAC_NHO_XY_LY) {
//				code = 2;
//			} else {
//				continue;
//			}
//
//			Date lastTimeRemindProcess = claim.getLastDateRemind();
//			if (lastTimeRemindProcess == null || !today.toLocalDate()
//					.equals(DateTimeUtils.dateToLocalDateTime(lastTimeRemindProcess).toLocalDate())) {
//				claim.setLastDateRemind(DateTimeUtils.localDateTimeToDate(today));
//				List<User> coordinators = userRepository.findAllUserByAuthorityAndFacultyId(
//						AuthoritiesConstants.COORDINATOR, claim.getUser().getFaculty().getId());
//
//				if (code == 1) {
//					claim.setOverDatelineProcess(true);
//					logger.debug("het han xu ly claim: " + claim.getId());
//					logger.debug("claim:"+claim);
//					MailSender.informCoordinatorClaimDeadline(claim, coordinators);
//				} else if (code == 2) {
//					logger.debug("Nhac nho mail sap het han: " + claim.getId());
//					MailSender.informCoordinatorClaimNearDeadline(claim, coordinators);
//				}
//				logger.debug("Saved claim after coordinator");
//				claimRepository.save(claim);
//			}
//		}

		// Remind for claim of student
		List<Claim> tatCaClaimChuaHetHanNopClaim = claimRepository.findAllNotBeOverUploadEvidence();
		logger.debug(claimsNotBeProcessed.size());
		logger.debug(claimsNotBeProcessed.get(0));
		
		logger.debug(tatCaClaimChuaHetHanNopClaim.size());
		logger.debug(tatCaClaimChuaHetHanNopClaim.get(0));
		
//		for (Claim claim : tatCaClaimChuaHetHanNopClaim) {
//			LocalDateTime submit = DateTimeUtils.dateToLocalDateTime(claim.getCreated_time());
//			int code = 0;
//			long numberOfDay = submit.until(today, ChronoUnit.DAYS);
//			if (numberOfDay >= SO_NGAY_HET_HAN_UPLOAD_EVIDENCE) {
//				code = 1;
//			} else if (numberOfDay >= SO_NGAY_NHAC_NHO_UPLOAD_EVIDENCE) {
//				code = 2;
//			} else {
//				continue;
//			}
//
//			if (code == 1) {
//				claim.setCanUploadMoreEvidence(false);
//				logger.debug("Het han nop evidence: " + claim.getId());
//				MailSender.informStudentOverDeadlineEvidence(claim);
//			} else if (code == 2) {
//				LocalDateTime lastRemindDate = DateTimeUtils.dateToLocalDateTime(claim.getLastRemindUploadDate());
//				if (lastRemindDate == null || !today.toLocalDate().equals(lastRemindDate.toLocalDate())) {
//					claim.setLastRemindUploadDate(DateTimeUtils.localDateTimeToDate(today));
//					logger.debug("Nhac nho nop evidence: " + claim.getId());
//					MailSender.informStudentNearDeadlineEvidence(claim);
//				}
//			}
//			claimRepository.save(claim);
//		}
		// logger.debug("tatCaClaimChuaHetHanNopClaim:" +
		// tatCaClaimChuaHetHanNopClaim.size());

	}

	public static void main(String[] args) {
		// LocalDateTime ldt=LocalDateTime.now();
		// ldt.plusDays(10);
		// System.out.println(ldt);
		// Calendar cal=Calendar.getInstance();
		// cal.setTime(new Date());
		// cal.add(Calendar.DAY_OF_MONTH, 5);
		// System.out.println(cal.getTime());

		System.out.println(DateTimeUtils.localDateTimeToDate(LocalDateTime.now()));
	}
}
