/**
 * 
 */
package org.group2.webapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Dam Cao Son
 * @Contact kunedo1104@gmail.com
 *
 */
public class Test {
	public static void main(String[] args) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime date = LocalDateTime.parse("2017-04-21T10:10:10");
		System.out.println(date);
		LocalDateTime future = date.plusDays(10);
		System.out.println(future);
		long gap = now.until(future, ChronoUnit.DAYS);
		System.out.println(gap+1);
	}

}
