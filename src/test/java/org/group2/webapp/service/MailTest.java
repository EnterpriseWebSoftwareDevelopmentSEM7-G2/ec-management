/**
 * 
 */
package org.group2.webapp.service;

import javax.mail.MessagingException;

import org.group2.webapp.entity.User;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author Dam Cao Son
 * @Contact kunedo1104@gmail.com
 *
 */
public class MailTest extends TestCase {
	@Test
	public void testShouldSendEmailToCoordinator() throws MessagingException {
		User user = new User();
		user.setEmail("kunedo1104@gmail.com");
		// MailUtils.sendInformNewClaimForECCoordinator(user, null);
	}

	@Test
	public void testShouldSendEmailToAUser() {
//		MailUtils.sendMail("kunedo1104@gmail.com", "sondcgc00681@fpt.edu.vn", "Title", "<a href='#'>Click vao day</a>");
	}

}
