package com.program.fairBilling;

import com.program.model.EachRecord;
import com.program.model.UserReport;
import com.program.model.UserSession;
import com.program.service.FairBillingService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class FairBillingApplication {



	public static void main( String[] args ) {

		try {
			if (args.length < 1) {
				System.err.println("Wrong number of arguments " + args.length + "Please provide file name <path to file>");
			}

			String logFileName = args[0];

			// Convert records in the file into a list
			FairBillingService fairBilling = new FairBillingService();

			List<String> recordList = fairBilling.convertRecordToList(logFileName);
			if(recordList.isEmpty()){
				System.out.println("Message : No records in given log file !");
			}

			List<UserReport> results = fairBilling.fairBillingCalculator(recordList);

			// final output
			for (UserReport result : results) {
				System.out.println(result.getName() + " " + result.getNumberOfSessions() + " " + result.getBillableTimeInSeconds());
			}

		} catch(Exception e) {
			System.err.println("Error in calculating fairBill !  " + e);
		}
	}

}
