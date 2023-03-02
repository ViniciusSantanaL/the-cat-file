package br.com.viniciussls.thecatfiles.service;

import br.com.viniciussls.thecatfiles.infra.SSHConnection;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

public class FormatDateService {

    private static final ZoneId zoneId = ZoneId.of("Europe/London");

    public static String getTimeToday() {
        OffsetDateTime timeNow = OffsetDateTime.now(zoneId);
        return String.valueOf(timeNow.getYear()) +
                String.format("%02d", timeNow.getMonthValue())  +
                String.format("%02d",timeNow.getDayOfMonth());
    }

    public static String getTimeLast7DaysAgo() {
        OffsetDateTime timeNow = OffsetDateTime.now(zoneId).minusDays(7);
        return String.valueOf(timeNow.getYear()) +
                String.format("%02d", timeNow.getMonthValue())  +
                timeNow.getDayOfMonth();
    }

    public static List<String> filterStringsHaveTodayDate(List<String> listToFilter) {
        String today = getTimeToday();
        return listToFilter.stream().filter(item -> item.contains(today)).toList();
    }

}
