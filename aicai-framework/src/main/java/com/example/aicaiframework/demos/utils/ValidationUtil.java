package com.example.aicaiframework.demos.utils;


import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证
 * @author 小鱼
 */
public class ValidationUtil {
    private static final String RegInteger = "^(-)?(0|[0-9][0-9]*)$";
    private static final String RegIntegerSign = "^[+-]?[0-9]+$";
    private static final String RegDecimal = "^[-]?(0|[1-9][0-9]{0,2}(\\,?[0-9]{3})*)(\\.\\d+)?$";
    private static final String RegDecimalSign = "^[-]?(0|[1-9][0-9]{0,2}(\\,?[0-9]{3})*)(\\.\\d+)?$"; //等价于^[+-]?\d+[.]?\d+$
    /**
     *说明：已1开头的手机号
     * 验证号码 手机号 固话均可
     * 正则表达式:验证手机号
     */
    public static final String REGEX_MOBILE = "((^1\\d{10}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";

    /**
     * 比较是否相等
     * @param var1
     * @param var2
     * @return
     */
    public static  boolean equals(Object var1,Object var2) {
        return ConvertUtil.toString(var1).equals(ConvertUtil.toString(var2));
    }

    /**
     * 比较是否相等
     * @param var1
     * @param var2
     * @return
     */
    public static  boolean equalsIgnoreCase(Object var1,Object var2) {
        return ConvertUtil.toString(var1).equalsIgnoreCase(ConvertUtil.toString(var2));
    }


    public static boolean isEmpty(Collection coll) {
        return (coll == null || coll.isEmpty());
    }

    public static boolean isEmpty(String [] coll) {
        return (coll == null || coll.length==0);
    }

    public static boolean isNotEmpty(Collection coll) {
        return !isEmpty(coll);
    }

    public static boolean isEmpty(Map map) {
        return (map == null || map.isEmpty());
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }


    /**
     * 验证对象是否为空
     *
     * @param value
     * @return boolean
     */
    public static boolean isEmpty(Object value) {
        return Objects.isNull(value);
    }

    /**
     * integer
     * @param value
     * @return null or <1
     */
    public static boolean isIntEmpty(Integer value) {
        return StringUtils.isEmpty(value) || value < 1;
    }

    /**
     * integer
     * @param value
     * @return null or <1
     */
    public static boolean isLongEmpty(Long value) {
        return StringUtils.isEmpty(value) || value < 1L;
    }

    /**
     * 验证字符串是否为空
     *
     * @param value
     * @return boolean
     */
    public static boolean isEmpty(String value) {
        return !StringUtils.hasLength(value);
    }

    /**
     * 验证对象是否为整数型
     *
     * @param value
     * @return
     */
    public static boolean isInteger(String value) {
        Pattern pattern = Pattern.compile(RegInteger);
        Matcher matcher = pattern.matcher(StringUtils.trimWhitespace(value));
        return matcher.matches();
    }


    /**
     * 是否数字字符串，可带正负号
     * @param str
     * @return
     */
    public static boolean isIntegerSign(String str) {
        Pattern pattern = Pattern.compile(RegIntegerSign);
        Matcher matcher = pattern.matcher(StringUtils.trimWhitespace(str));
        return matcher.matches();
    }

    /**
     * 判断字符串是否是long类型
     * @param str
     * @return
     */
    public static boolean isLong (String str) {
        if(!StringUtils.hasLength(str)) { return false; }
        try {
            Long.parseLong(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 是否是小数
     *
     * @param str
     * @return
     */
    public static boolean isDecimal(String str) {
        Pattern pattern = Pattern.compile(RegDecimal);
        Matcher matcher = pattern.matcher(StringUtils.trimWhitespace(str));
        return matcher.matches();

    }


    /**
     * 是否是浮点数,可带正负号
     *
     * @param str
     * @return
     */
    public static boolean isDecimalSign(String str) {
        Pattern pattern = Pattern.compile(RegDecimalSign);
        Matcher matcher = pattern.matcher(StringUtils.trimWhitespace(str));
        return matcher.matches();

    }

    /**
     * 验证对象是否时间格式
     *
     * @param obj
     * @param dateFormat
     * @return
     */
    public static boolean isDateTime(Object obj, SimpleDateFormat dateFormat) {
        if (StringUtils.isEmpty(obj)) {
            return false;
        }
        try {
            String strdate = StringUtils.trimAllWhitespace(obj.toString());
            if (strdate.length() != dateFormat.toPattern().length()) {
                return false;
            }
            dateFormat.setLenient(false);
            dateFormat.parse(strdate);

        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * 验证对象是否时间格式 （年月日时分秒）
     *
     * @param obj
     * @return
     */
    public static boolean isDateTime(Object obj) {
        if (StringUtils.isEmpty(obj)) {
            return false;
        }
        try {
            String strdate = StringUtils.trimAllWhitespace(obj.toString().replace("-", "").replace("/", "").replace(":", ""));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
            if (strdate.length() != dateFormat.toPattern().length()) {
                return false;
            }
            dateFormat.setLenient(false);
            dateFormat.parse(strdate);
        } catch (Exception e) {
            // 如果解析方法失败则表示不是日期性数据
            return false;
        }
        return true;
    }

    /**
     * 验证对象是否日期格式 (年月日)
     *
     * @param obj
     * @return
     */
    public static boolean isDate(Object obj) {
        if (StringUtils.isEmpty(obj)) {
            return false;
        }
        try {
            String strdate = StringUtils.trimWhitespace(obj.toString().replace("-", "").replace("/", ""));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            if (strdate.length() != dateFormat.toPattern().length()) {
                return false;
            }
            dateFormat.setLenient(false);
            dateFormat.parse(strdate);
        } catch (Exception e) {
            // 如果解析方法失败则表示不是日期性数据
            return false;
        }
        return true;
    }

    /**
     * 判断是否是Windows系统
     *
     * @return Boolean
     * */
    public static boolean isWindows() {
        String os = System.getProperty("os.name");
        return os.toLowerCase().startsWith("win");
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }


    /**
     * 检查对象中所有的字段是否都为空（包括对象引用和基本类型的包装类）
     *
     * @param obj 要检查的对象
     * @return 如果所有字段都为空，则返回true；否则返回false
     */
    public static boolean isAllFieldsNull(Object obj) {
        if (obj == null) {
            return true;
        }
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            // 允许访问私有字段
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value != null) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("无法访问字段：" + field.getName(), e);
            }
        }
        return true;
    }
}
