package com.github.rabitarochan.deeta.util;

public class StringUtils {

    private StringUtils() {}

    public static String repeat(String s, int count) {
        return new String(new char[count]).replace("\0", s);
    }

    public static String right(String s, int length) {
        return s.substring(s.length() - length);
    }

    public static int count(String s, String target) {
        int count = 0;
        int startIndex = 0;
        while (true) {
            int res = s.indexOf(target, startIndex);
            if (res == -1) {
                break;
            }

            startIndex = res + target.length();
            count++;
        }
        return count;
    }

}
