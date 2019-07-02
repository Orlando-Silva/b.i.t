package com.app.bit.Helpers;

public class StringHelpers {

    public static boolean isNullEmptyOrWhitespace(String text) {
        return text == null || text.matches("^[ \\t\\r\\n]*$");
    }
}
