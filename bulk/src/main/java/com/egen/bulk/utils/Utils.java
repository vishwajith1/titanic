package com.egen.bulk.utils;

import java.sql.Timestamp;

public class Utils {
    public static Timestamp getCurrentTimeInCST() {
        return new Timestamp(System.currentTimeMillis() - 21600000);
    }
}
