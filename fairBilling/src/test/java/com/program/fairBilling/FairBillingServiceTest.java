package com.program.fairBilling;

import com.program.model.EachRecord;
import com.program.model.UserReport;
import com.program.service.FairBillingService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class FairBillingServiceTest {

	@InjectMocks
	public FairBillingService fairBillingService;


	@Test
	public void testValidRecord(){
		//OPERATE
		EachRecord eachRecord = fairBillingService.breakUpLine("14:02:03 ALICE99 Start");

		//CHECK
		assertTrue(eachRecord.isValid());
	}

	@Test
	public void testInValidRecord(){
		//OPERATE
		EachRecord eachRecord = fairBillingService.breakUpLine("14:02:03 ALICE99 ");

		//CHECK
		assertFalse(eachRecord.isValid());
	}

	@Test
	public void testRecordToList() throws Exception {
		//OPERATE
		List<String> recordList= fairBillingService.convertRecordToList("billingLogFile.log");

		//CHECK
		assertThat(recordList.size()).isEqualTo(11);
	}

	@Test
	public void testUserReport(){
		List<String> records = Arrays.asList("14:02:03 ALICE99 Start",
				"14:02:05 CHARLIE End",
				"14:02:34 ALICE99 End",
				"14:02:58 ALICE99 Start",
				"14:03:02 CHARLIE Start",
				"14:03:33 ALICE99 Start",
				"14:03:35 ALICE99 End",
				"14:03:37 CHARLIE End",
				"14:04:05 ALICE99 End",
				"14:04:23 ALICE99 End",
				"14:04:41 CHARLIE Start");
		List<UserReport> userReports = fairBillingService.fairBillingCalculator(records);
		List<UserReport> specificUserReports =  userReports.stream()
				.filter(ur -> ur.getName().equalsIgnoreCase("ALICE99"))
			 .collect(Collectors.toList());

		assertThat(specificUserReports.get(0).getNumberOfSessions()).isEqualTo(4);

	}
}
