package com.tw.clubmanagement.util;

import java.util.Date;

public class ActivityStatusUtil {

    public static Integer calculateStatus(Date joinEndTime, Date start, Date end, Integer numberLimitation, Integer numberJoined) {
        boolean isMatchedNumberLimitation = numberJoined.intValue() == numberLimitation.intValue();

        Date now = new Date();
        if (start != null && end != null && now.before(end) && now.after(start)) {
            return 2;
        }
        if ((start != null && now.before(start)) || isMatchedNumberLimitation) {
            return 1;
        }
        if (joinEndTime != null && now.before(joinEndTime)) {
            return 0;
        }

        return 3;
    }
}
