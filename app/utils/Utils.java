/*
 * (C) Copyright 2018 arman (collectors) and others.
 *
 * Utils.java
 * Created 9:44:40 AM
 */

package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.Logger;
import play.Play;
import play.data.validation.MaxSize;
import play.data.validation.Min;
import play.data.validation.MinSize;
import play.data.validation.Range;
import play.data.validation.Validation.Validator;
import play.db.jpa.Model;
import play.i18n.Messages;

// TODO: Auto-generated Javadoc
/**
 * The Class Utils.
 */
public class Utils {

    /** The Constant generalDateFormatter. */
    public static final SimpleDateFormat generalDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /** The Constant emailPattern. */
    public static final Pattern emailPattern = Pattern.compile(
            "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[a-zA-Z0-9](?:[\\w-]*[\\w])?");

    /**
     * Checks if is dev.
     *
     * @return true, if is dev
     */
    public static boolean isDev() {
        return (Play.mode.isDev() || (Play.id.equalsIgnoreCase("bongadev") || Play.id.equalsIgnoreCase("dev")));
    }

    /**
     * Checks if is empty string.
     *
     * @param s
     *            the s
     * @return true, if is empty string
     */
    public static boolean isEmptyString(String s) {
        return (s == null || s.isEmpty());
    }

    /**
     * Null safe string.
     *
     * @param string
     *            the String to be tested
     * @return the string
     */
    public static String nullSafeString(Object string) {
        String res = "";
        try {
            if (string != null)
                res = string.toString();
        } catch (Exception e) {
            Logger.error(e, "Utils.nullSafeString, %s", string.toString());
        }
        return res;
    }

    /**
     * To string or default.
     *
     * @param string
     *            the string
     * @param defaultString
     *            the default string
     * @return the string
     */
    public static String toStringOrDefault(Object string, String defaultString) {
        return (string == null || string.toString().isEmpty() ? defaultString : string.toString());
    }

    /**
     * Jalali to gregorian.
     *
     * @param s
     *            the s
     * @return the date
     */
    public static Date jalaliToGregorian(String s) {
        Date date = null;
        try {
            Pattern jalaliPattern = Pattern.compile("(\\d+)-(\\d+)-(\\d+)");
            Matcher jalaliMatcher = jalaliPattern.matcher(s);
            if (jalaliMatcher.matches()) {
                int d = Integer.valueOf(jalaliMatcher.group(1)).intValue();
                int m = Integer.valueOf(jalaliMatcher.group(2)).intValue();
                int y = Integer.valueOf(jalaliMatcher.group(3)).intValue();
                JalaliCalendar jc = new JalaliCalendar(y, m, d);
                date = jc.toGregorian().getTime();
            }
        } catch (Exception e) {
            Logger.error(e, "utils.jalaliToGregorian : %s", s);
        }
        return date;
    }

    /**
     * List to url param.
     *
     * @param list
     *            the list
     * @param name
     *            the name
     * @return the string
     */
    public static String listToUrlParam(List<String> list, String name) {
        String out = "";
        int i = 0;
        for (String l : list) {
            out += name + "[" + i + "]" + "=" + l + "&";
        }
        if (out.length() > 1)
            out = out.substring(0, out.length() - 1);
        return out;
    }

    /**
     * Format today as key.
     *
     * @return current date in yyMMdd format, e.g. 980313
     */
    public static String formatTodayAsKey() {
        return new SimpleDateFormat("yyMMdd").format(new Date());
    }

    /**
     * Jvalidate from model properties.
     *
     * @param clazz
     *            the clazz
     * @param fieldName
     *            the field name
     * @param name
     *            the name
     * @return the string
     */
    public static String jvalidateFromModelProperties(Class<? extends Model> clazz, String fieldName, String name) {
        if (name == null || name.isEmpty())
            name = "object";
        StringBuilder sb = new StringBuilder();
        List<Validator> validators = play.data.validation.Validation.getValidators(clazz, fieldName, name);
        for (Validator v : validators) {
            switch (v.annotation.annotationType().getName().substring(21)) {
                case "Required":
                    sb.append("required ");
                    break;
                case "MinSize":
                    sb.append("minlength=" + ((MinSize) v.annotation).value() + " ");
                    break;
                case "MaxSize":
                    sb.append("maxlength=" + ((MaxSize) v.annotation).value() + " ");
                    break;
                case "Range":
                    sb.append("min=" + ((Range) v.annotation).min() + " max=" + ((Range) v.annotation).max() + " ");
                    break;
                case "Min":
                    sb.append("min=" + ((Min) v.annotation).value() + " ");
                    break;
                case "Email":
                    // validationtype="email";
                    break;
                case "URL":
                    // validationtype="url";
                    break;
                case "Equals":
                    break;
                case "Future":
                    break;
                case "Past":
                    break;
            }
        }

        return sb.toString();
    }

    /**
     * Jvalidate tags from validators.
     *
     * @param validators
     *            the validators
     * @return the string
     */
    public static String jvalidateTagsFromValidators(List<Validator> validators) {
        StringBuilder sb = new StringBuilder();
        for (Validator v : validators) {
            switch (v.annotation.annotationType().getName().substring(21)) {
                case "Required":
                    sb.append("required ");
                    break;
                case "MinSize":
                    sb.append("minlength=" + ((MinSize) v.annotation).value() + " ");
                    break;
                case "MaxSize":
                    sb.append("maxlength=" + ((MaxSize) v.annotation).value() + " ");
                    break;
                case "Range":
                    sb.append("min=" + ((Range) v.annotation).min() + " max=" + ((Range) v.annotation).max() + " ");
                    break;
                case "Min":
                    sb.append("min=" + ((Min) v.annotation).value() + " ");
                    break;
                case "Email":
                    // validationtype="email";
                    break;
                case "URL":
                    // validationtype="url";
                    break;
                case "Equals":
                    break;
                case "Future":
                    break;
                case "Past":
                    break;
            }
        }

        return sb.toString();
    }

    /**
     * Map to string.
     *
     * @param map
     *            the map
     * @return the string
     */
    public static String mapToString(Map<String, ? extends Object> map) {
        StringBuilder sb = new StringBuilder();
        for (String k : map.keySet()) {
            Object v = map.get(k);
            sb.append(k + " : " + v.toString() + "    ");
        }
        return sb.toString();
    }

    /**
     * Safe enum to class plus value key.
     *
     * @param e
     *            the e
     * @return the string
     */
    public static String safeEnumToClassPlusValueKey(Enum e) {
        if (e == null)
            return "null";
        String key = e.getClass().getSimpleName() + "." + e.name();
        return key;
    }

    /**
     * Safe enum to messages.
     *
     * @param e
     *            the e
     * @return the string
     */
    public static String safeEnumToMessages(Enum e) {
        return Messages.get(safeEnumToClassPlusValueKey(e));
    }

    /**
     * Creates the easy map.
     *
     * @return the easy args map
     */
    public static EasyArgsMap createEasyMap() {
        EasyArgsMap eam = new EasyArgsMap();
        return eam;
    }

    /**
     * The Class EasyArgsMap.
     */
    public static class EasyArgsMap {
        
        /** The map. */
        Map<String, Object> map = new HashMap<>();

        /**
         * Instantiates a new easy args map.
         */
        public EasyArgsMap() {
        }

        /**
         * Instantiates a new easy args map.
         *
         * @param k
         *            the k
         * @param v
         *            the v
         */
        public EasyArgsMap(String k, Object v) {
            map.put(k, v);
        }

        /**
         * Adds the.
         *
         * @param k
         *            the k
         * @param v
         *            the v
         * @return the easy args map
         */
        public EasyArgsMap add(String k, Object v) {
            map.put(k, v);
            return this;
        }

        /**
         * Gets the map.
         *
         * @return the map
         */
        public Map<String, Object> getMap() {
            return map;
        }
    }
}
