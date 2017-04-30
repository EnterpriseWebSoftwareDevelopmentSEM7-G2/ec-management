package org.group2.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class EcManagementApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(EcManagementApplication.class, args);
		SampleData sampleData = ctx.getBean(SampleData.class);
		ClaimChecker claimChecker = ctx.getBean(ClaimChecker.class);
		sampleData.run();
		sampleData.test();
		claimChecker.start();
	}
}
