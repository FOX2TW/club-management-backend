package com.tw.clubmanagement.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class ActivityUtil {

    public static Integer calculateStatus(Date joinEndTime, Date start, Date end, Integer numberLimitation, Integer numberJoined) {
        boolean isMatchedNumberLimitation = numberLimitation.intValue() != 0 &&
                numberJoined.intValue() == numberLimitation.intValue();

        Date now = new Date();
        log.info("now: {}, join_end: {}, start: {}, end: {}, numberLimitation: {}, numberJoined: {}", now, joinEndTime, start, end, numberLimitation, numberLimitation);
        if (joinEndTime != null && now.before(joinEndTime) && !isMatchedNumberLimitation) {
            return 0;
        }
        if (joinEndTime != null && now.before(joinEndTime) && isMatchedNumberLimitation) {
            return 1;
        }

        if (start != null && joinEndTime != null && now.before(start) && now.after(joinEndTime)) {
            return 1;
        }
        if (start != null && end != null && now.before(end) && now.after(start)) {
            return 2;
        }


        return 3;
    }
}
