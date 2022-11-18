package com.zincyanide.sqlconstructor.internal;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    public static final SimpleDateFormat FORMATTER_DATETIME = new SimpleDateFormat(FORMAT_DATETIME);

    public static String format(Date date)
    {
        return FORMATTER_DATETIME.format(date);
    }

}
