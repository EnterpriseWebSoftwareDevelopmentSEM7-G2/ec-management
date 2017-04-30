/**
 * 
 */
package org.group2.webapp.web.util;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author Dam Cao Son
 * @Contact kunedo1104@gmail.com
 *
 */
public class EmailPattern {
	private static final Logger logger = Logger.getLogger(EmailPattern.class);

	private String subject;
	private String content;

	/**
	 * @param subject
	 * @param content
	 */
	public EmailPattern(String subject, String content) {
		super();
		this.subject = subject;
		this.content = content;
	}

	/**
	 * 
	 */
	public EmailPattern() {
		super();
	}

	public void loadFromProperties(String url) {
//		Properties props = new Properties();
		try {
			File file = new File(getClass().getClassLoader().getResource(url).getPath());
			List<String> lines = Files.readAllLines(file.toPath());
			// props.load(getClass().getClassLoader().getResourceAsStream(url));
			// subject=props.getProperty("subject", "empty subject");
			// content=props.getProperty("content", "empty content");

			StringBuilder contentBuilder = new StringBuilder();
			for (int i = 1; i < lines.size(); i++)
			{
				contentBuilder.append(lines.get(i));
			}
			subject = lines.get(0);
			content = contentBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Cannot read email properties from: " + url);
		}
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Email [subject=" + subject + ", content=" + content + "]";
	}
}
