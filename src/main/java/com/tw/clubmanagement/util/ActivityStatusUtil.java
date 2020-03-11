package com.tw.clubmanagement.util;

import java.util.Date;

public class ActivityStatusUtil {

    public static Integer calculateStatus(Date joinEndTime, Date start, Date end, Integer numberLimitation, Integer numberJoined) {
        boolean isMatchedNumberLimitation = numberJoined.intValue() == numberLimitation.intValue();

        Date now = new Date();
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
