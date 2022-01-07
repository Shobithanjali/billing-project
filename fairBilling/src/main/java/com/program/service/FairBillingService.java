package com.program.service;

import com.program.model.EachRecord;
import com.program.model.UserReport;
import com.program.model.UserSession;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FairBillingService {
    public static final String START_ACTION = "Start";

    public List<String> convertRecordToList(String logFileName) throws Exception {

        List<String> eachRecord;
        try {
            eachRecord = Files.readAllLines(Paths.get(logFileName), StandardCharsets.UTF_8);
        } catch (NoSuchFileException e) {
            throw new Exception("No file found with given name" + logFileName, e);
        } catch (Exception e) {
            throw new Exception("Other errors " + logFileName, e);
        }
        return eachRecord;
    }


    public List<EachRecord> getAllValidRecords(List<String> recordList) {

        List<EachRecord> validRecordList = new ArrayList<>();

        for (String line : recordList) {
            EachRecord validRecord = breakUpLine(line);

            if (validRecord.isValid()) {
                validRecordList.add(validRecord);
            }
        }

        return validRecordList;
    }


    public Map<String, List<UserSession>> processLines(List<EachRecord> lines) {

        Map<String, List<UserSession>> userSessionMap = new LinkedHashMap<>();

        for (EachRecord line : lines) {

            List<UserSession> userSessionList = userSessionMap.get(line.getName());

            userSessionList = processLine(line, userSessionList);
            userSessionMap.put(line.getName(), userSessionList);
        }

        return userSessionMap;
    }

    //logic
    public List<UserSession> processLine(EachRecord line, List<UserSession> userSessionList) {

        if (userSessionList == null) {
            userSessionList = new ArrayList<>();
        }

        //If START action, add a new session
        if (START_ACTION.equalsIgnoreCase(line.getAction()) ) {
            UserSession userSession = new UserSession(line.getName());
            userSession.setStartTime(line.getRecordTime());
            userSessionList.add(userSession);
            return userSessionList;
        }

        //If END, either update already present first occurring start session's end date or add a new session with current end Time
        for (UserSession userSession: userSessionList) {

            if (userSession.getEndTime() == null) {
                userSession.setEndTime(line.getRecordTime());
                return userSessionList;
            }
        }
        UserSession userSession = new UserSession(line.getName());
        userSession.setEndTime(line.getRecordTime());
        userSessionList.add(userSession);
        return userSessionList;
    }


    public EachRecord breakUpLine(String line) {

        EachRecord splitRecord = new EachRecord();
        splitRecord.setValid(false);

        if (line == null || line.isEmpty()) {
            return splitRecord;
        }

        //matches HH:MM:SS and any name , action Start or End
        String regularExpression = "^([01]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]) (.*) (Start|End|start|end)$";


        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(line);

        while(matcher.find()) {
            //silently ignored if invalid
            splitRecord.setValid(matcher.matches());

            splitRecord.setRecordTime(LocalTime.parse(matcher.group(1) +":" +matcher.group(2) + ":" + matcher.group(3)));
            splitRecord.setName( matcher.group(4) );
            splitRecord.setAction( matcher.group(5) );
        }
        return splitRecord;
    }

    public List<UserReport> fairBillingCalculator(List<String> recordList) {

        if (recordList == null || recordList.isEmpty()) {
            return new ArrayList<>();
        }

        List<EachRecord> splittedEachRecord = getAllValidRecords(recordList);

        //get all sessions for each user
        Map<String, List<UserSession>> map = processLines(splittedEachRecord);
        

        List<UserReport> results = new ArrayList<>();
        findTotalSessionsAndTime(results,map,splittedEachRecord);


        return results;
    }

    private void findTotalSessionsAndTime(List<UserReport> results, Map<String, List<UserSession>> map, List<EachRecord> splittedEachRecord) {
        LocalTime firstTime = null;
        LocalTime lastTime = null;
        if (splittedEachRecord.size() > 0) {
            firstTime =splittedEachRecord.get(0).getRecordTime();
            lastTime = splittedEachRecord.get(splittedEachRecord.size()-1).getRecordTime();
        }

        for (String name : map.keySet()) {
            int totalTime=0;
            int totalSessions=0;
            for (UserSession session : map.get(name)) {
                totalSessions++;
                if (session.getStartTime() == null) {
                    session.setStartTime(firstTime);
                }
                if (session.getEndTime() == null) {
                    session.setEndTime(lastTime);
                }
                totalTime +=  Duration.between(session.getStartTime(),session.getEndTime()).getSeconds();
            }
            results.add( new UserReport(name, totalSessions, totalTime) );
        }

    }
}
