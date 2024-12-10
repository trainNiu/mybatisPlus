package com.example.aicaiframework.demos.utils;

import com.example.aicaiframework.demos.constant.NumberFormat;
import com.example.aicaiframework.demos.enums.NumberEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: mengyu
 * @Description: 类型转换工具类
 */
public class ConvertUtil {

    protected static ThreadLocal<SimpleDateFormat> DATE_TIME = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue(){
           return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

    } ;
    protected static ThreadLocal<SimpleDateFormat> DATE = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue(){
            return new SimpleDateFormat("yyyy-MM-dd");
        }

    };


    /**
     * 根据分隔符，截取字符串转为 int 集合
     * @param obj
     * @param separator 分隔符
     * @return
     */
    public static Set<Integer> toIntSet(Object obj, String separator) {
        if (Objects.isNull(obj)) {
            return null;
        }
        Set<Integer> sets = Sets.newHashSet();
        String[] array = obj.toString().split(separator);
        if (array != null) {
            for (String str : array) {
                sets.add(toInteger(str, 0));
            }
        }
        return sets;
    }

    /**
     * 根据分隔符，截取字符串转为 int 集合
     * @param strs
     * @param separator 分隔符
     * @return
     */
    public static List<Integer> toIntList(String strs, String separator) {
        if (!StringUtils.hasLength(strs)){
            return null;
        }
        Set<Integer> sets = Sets.newHashSet();
        String[] array = strs.split(separator);
        if (array != null) {
            for (String str : array) {
                sets.add(toInteger(str, 0));
            }
        }
        return new ArrayList<Integer>(sets);
    }

    /**
     * 根据分隔符，截取字符串转为 long 集合
     * @param strs
     * @param separator 分隔符
     * @return
     */
    public static List<Long> toLongList(String strs, String separator) {
        if(!StringUtils.hasLength(strs)){
            return new ArrayList<>();
        }
        Set<Long> sets = Sets.newHashSet();
        String[] array = strs.split(separator);
        if (array != null) {
            for (String str : array) {
                sets.add(toLong(str, 0));
            }
        }
        return new ArrayList<Long>(sets);
    }

    /**
     * 根据分隔符，截取字符串转为 int 集合
     * @param strs
     * @param separator 分隔符
     * @return
     */
    public static List<String> toStringList(String strs, String separator) {
        if(!StringUtils.hasLength(strs)){
            return null;
        }
        List<String> list = Lists.newArrayList();
        String[] array = strs.split(separator);
        if (array != null) {
            for (String str : array) {
                if (StringUtils.hasLength(str)) {
                    list.add(str);
                }
            }
        }
        return list;
    }


    /**
     * 根据分隔符，截取字符串转为 String 数组
     * @param strs
     * @param separator 分隔符
     * @return 去重，排除空
     */
    public static String [] toStringArray(String strs, String separator) {
        if (StringUtils.isEmpty(strs)) {
            return null;
        }
        Set<String> list = new HashSet<String>();
        String [] array = strs.split(separator);
        if (array != null) {
            for (String str : array) {
                if (StringUtils.hasLength(str)) {
                    list.add(str);
                }
            }
        }

        return list.toArray(new String[list.size()]);
    }



    /**
     * 将对象转成字符串
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        if (!StringUtils.isEmpty(obj)) {
            return StringUtils.trimWhitespace(obj.toString());
        } else {
            return "";
        }
    }

    public static String toString(Object obj, String strDefault) {
        if (!StringUtils.isEmpty(obj)) {
            return StringUtils.trimWhitespace(obj.toString());
        } else {
            return strDefault;
        }
    }

    /**
     * 时间格式化成字符串
     * @param date
     * @param dateformat
     * @return
     */
    public static String toDateString(Date date, String dateformat) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
        return dateFormat.format(date);
    }

    /**
     * 时间格式化成字符串(yyyy-MM-dd)
     * @param date
     * @return
     */
    public static String toDateString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * 将对象转换为货币格式的字串
     * @param obj
     * @return
     */
    public static String toCurrency(Object obj) {
        if (StringUtils.isEmpty(obj)) {
            return null;
        }
        try {
            DecimalFormat df = new DecimalFormat(NumberFormat.NUMBER_2);
            return df.format(new BigDecimal(obj.toString()).setScale(2, BigDecimal.ROUND_HALF_UP));

        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 将对象转换为货币格式的字串
     * @param value
     * @return
     */
    public static String toCurrency(BigDecimal value) {
        value = value == null ? BigDecimal.ZERO : value;
        try {
            DecimalFormat df = new DecimalFormat(NumberFormat.NUMBER_2);
            return df.format(new BigDecimal(value.toString()).setScale(2, BigDecimal.ROUND_HALF_UP));

        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 对象转小写
     * @param val
     * @return
     */
    public static String toLower(String val) {
        if (!StringUtils.hasLength(val)) {
            return "";
        } else {
            return val.toLowerCase();
        }
    }

    /**
     * 对象转大写
     * @param val
     * @return
     */
    public static String toUpper(String val) {
        if (!StringUtils.hasLength(val)) {
            return "";
        } else {
            return val.toUpperCase();
        }
    }


    /**
     * 固定最长长度
     * @param str
     * @param length
     * @return
     */
    public static String toFixedLength(String str, int length) {
        if (!StringUtils.hasLength(str)) {
            return "";
        }

        int len = str.length();
        if (len > length) {
            return str.substring(0, length) + "...";
        } else {
            return str;
        }
    }

    /**
     * 去除千分位
     * @param str
     * @return
     */
    public static String toSafteNumber(String str) {
        if (!StringUtils.hasLength(str)) {
            return "";
        }

        return str.replaceAll(",", "");//千分符要去除
    }

    /**
     * 数字前面补0
     * @param number
     * @return
     */
    public static String beforeAppendZero(Integer number,int length) {
        String strNumber = toString(number);
        if (StringUtils.hasLength(strNumber)) {
            length = length - strNumber.length();
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(NumberEnum.ZERO.val());
        }
        return sb.append(number).toString();
    }


    /**
     * 对象转 Integer
     * @param obj
     * @return
     */
    public static Integer toInteger(Object obj) {
        if (StringUtils.isEmpty(obj)) {
            return null;
        }
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 对象转 Integer
     * @param obj
     * @param defaultValue
     * @return
     */
    public static int toInteger(Object obj, int defaultValue) {
        if (StringUtils.isEmpty(obj)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }


    /**
     * 字符串转 Long
     * @param obj
     * @return
     */
    public static long toLong(Object obj, long defaultValue) {
        if (Objects.isNull(obj)) { return defaultValue;}
        try {
            return Long.parseLong(obj.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 字符串转 Long
     * @param str
     * @return
     */
    public static Long toLong(String str) {
        if (StringUtils.isEmpty(str)) { return null;}
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * object 转 BigDecimal
     * @param obj
     * @return 4位小数，四舍五入
     */
    public static BigDecimal toDecimal(Object obj) {
        if (StringUtils.isEmpty(obj)) {
            return null;
        }
        try {
            return new BigDecimal(obj.toString()).setScale(4, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            return null;
        }
    }



    /**
     * object 转 BigDecimal
     * @param str
     * @return 4位小数，四舍五入
     */
    public static BigDecimal toDecimal(String str, BigDecimal defaultValue) {
        if (StringUtils.isEmpty(str)) {
            return defaultValue;
        }
        try {
            return new BigDecimal(str).setScale(4, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 字符串转 BigDecimal
     * @param obj
     * @param newScale     精度
     * @param roundingMode
     * @return
     */
    public static BigDecimal toDecimal(Object obj, int newScale, int roundingMode) {
        if (StringUtils.isEmpty(obj)) {
            return null;
        }
        try {
            return new BigDecimal(obj.toString()).setScale(newScale, roundingMode);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 字符串转 BigDecimal
     * @param obj
     * @param newScale     精度
     * @param roundingMode
     * @return
     */
    public static BigDecimal toDecimal(Object obj, int newScale, int roundingMode,BigDecimal defaultValue) {
        if (StringUtils.isEmpty(obj)) {
            return defaultValue;
        }
        try {
            return new BigDecimal(obj.toString()).setScale(newScale, roundingMode);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    /**
     * 对象转 DATE
     * @param obj
     * @param dateFormat
     * @return
     */
    public static Date toDate(Object obj, String dateFormat) {
        if (StringUtils.isEmpty(obj)) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(dateFormat);
            return df.parse(obj.toString());
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * 对象转 DATE
     * @param obj
     * @param dateFormat
     * @return
     */
    public static Date toDate(Object obj, ThreadLocal<SimpleDateFormat> dateFormat) {
        if (StringUtils.isEmpty(obj)) {
            return null;
        }
        try {
            return dateFormat.get().parse(obj.toString());
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * 对象转 DATE
     * @param obj
     * @param defaultValue
     * @param dateFormat
     * @return
     */
    public static Date toDate(Object obj, Date defaultValue,ThreadLocal<SimpleDateFormat> dateFormat) {
        if (StringUtils.isEmpty(obj)) {
            return defaultValue;
        }
        try {
            return dateFormat.get().parse(obj.toString());
        } catch (ParseException ex) {
            return defaultValue;
        }
    }

    /**
     * 对象转 DATE
     * @param str
     * @param dateFormat
     * @return
     */
    public static Date toDate(String str,ThreadLocal<SimpleDateFormat> dateFormat) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            return dateFormat.get().parse(str);
        } catch (ParseException ex) {
            return null;
        }
    }



    /**
     * 对象转为 DATE
     * @param obj  yyyy-MM-dd
     * @return
     */
    public static Date toDate(Object obj) {
        return toDate(obj, DATE);
    }

    /**
     * 对象转为 DATE
     * @param obj yyyy-MM-dd
     * @param defaultValue
     * @return
     */
    public static Date toDate(Object obj, Date defaultValue) {
        return toDate(obj, defaultValue, DATE);
    }



    /**
     * 对象转为 DateTime
     * @param obj yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date toDateTime(Object obj) {
        return toDate(obj, DATE_TIME);
    }

    /**
     * 对象转为 DateTime
     * @param obj yyyy-MM-dd HH:mm:ss
     * @param defaultValue
     * @return
     */
    public static Date toDateTime(Object obj, Date defaultValue) {
        return toDate(obj, defaultValue, DATE_TIME);
    }

}
