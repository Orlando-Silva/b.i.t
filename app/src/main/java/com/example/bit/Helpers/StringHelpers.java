package com.example.bit.Helpers;

import android.util.Log;

public class StringHelpers {

    public static boolean isNullEmptyOrWhitespace(String text) {
        return text == null || text.matches("^[ \\t\\r\\n]*$");
    }
}
