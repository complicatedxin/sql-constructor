package com.zincyanide.sqlconstructor.internal;

public class StringUtil
{
    public static boolean isEmpty(String str)
    {
        return str == null || str.isEmpty();
    }

    public static void requireNonEmpty(String str)
    {
        requireNonEmpty(str, "a string is null or empty");
    }

    public static void requireNonEmpty(String str, String errorMsg)
    {
        if(isEmpty(str))
            throw new IllegalArgumentException(errorMsg);
    }

    public static boolean isWhite(String str)
    {
        return str == null || str.trim().isEmpty();
    }

    public static void requireNonWhite(String str)
    {
        requireNonEmpty(str, "a string is empty or filled with whitespace");
    }

    public static void requireNonWhite(String str, String errorMsg)
    {
        if(isWhite(str))
            throw new IllegalArgumentException(errorMsg);
    }
}
