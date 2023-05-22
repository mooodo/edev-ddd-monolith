package com.edev.support.utils;

public class NameUtils {
    private NameUtils() {}
    /**
     * convert the name to came case style
     * @param name the name
     * @return came-case styled name
     */
    public static String convertToCamelCase(String name) {
        while (name.contains("_")) {
            name = underlineChange(name);
        }
        return name;
    }

    private static String underlineChange(String name) {
        int index = name.indexOf("_");
        String prefix = name.substring(0, index);
        String first = name.substring(index+1, index+2).toUpperCase();
        String suffix = name.substring(index+2);
        return prefix + first + suffix;
    }

    /**
     * convert the name to underline style
     * @param name the name
     * @return underline styled name
     */
    public static String convertToUnderline(String name) {
        StringBuilder result = new StringBuilder();
        char[] chars = name.toCharArray();
        for(char c : chars)
            if(c>='A'&&c<='Z') result.append("_").append(c);
            else result.append(c);
        return result.toString().toLowerCase();
    }

    /**
     * convert the name to the first letter upper case
     * @param name the name
     * @return first-letter-upper name
     */
    public static String convertToFirstUpperCase(String name) {
        char[] chars = name.toCharArray();
        chars[0] -= 32;
        return String.valueOf(chars);
    }
}
