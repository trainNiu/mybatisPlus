package com.example.aicaiframework.demos.constant;


import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * 日期 format
 */
public class DateFormat {

    public final static String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_TIME_SEPARATE = "yyyyMMddHHmmss";
    public final static String DATE = "yyyy-MM-dd";
    public final static String MONTH_DATE = "MM-dd";
    public final static String DATEV2 = "yyyy/MM/dd";
    public final static String DATEV3 = "yyyy/MM";
    public final static String DATE_SEPARATE = "yyyyMMdd";
    public final static String DATE_HOUR = "yyyyMMddHH";
    public final static String YEAR = "yyyy";
    public final static String YEAR_MONTH = "yyyyMM";
    public static final String YEAR_MONTH_PATTERN = "yyyy-MM";
    public final static String DATE_MINUTE = "yyyy-MM-dd HH:mm";

    /**
     * 线程不安全， 需要处理
     * yyyy-MM-dd HH:mm:ss
     */
    public final static ThreadLocal<SimpleDateFormat> SDF_DATE_TIME = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE_TIME);
        }
    };

    /**
     *  线程不安全， 需要处理
     * yyyyMMddHHmmss
     */
    public final static ThreadLocal<SimpleDateFormat> SDF_DATE_TIME_SEPARATE = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE_TIME_SEPARATE);
        }
    };

    /**
     *  线程不安全， 需要处理
     * yyyy-MM-dd
     */
    public final static ThreadLocal<SimpleDateFormat> SDF_DATE = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE);
        }
    };

    /**
     *  线程不安全， 需要处理
     * yyyy/MM/dd
     */
    public final static ThreadLocal<SimpleDateFormat> SDF_DATEV2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATEV2);
        }
    };

    /**
     *  线程不安全， 需要处理
     * yyyyMMdd
     */
    public final static ThreadLocal<SimpleDateFormat> SDF_DATE_SEPARATE = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE_SEPARATE);
        }
    };

    /**
     *  线程不安全， 需要处理
     * yyyyMMddHH
     */
    public final static ThreadLocal<SimpleDateFormat> SDF_DATE_HOUR = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE_HOUR);
        }
    };

    /**
     *  线程不安全， 需要处理
     * yyyy
     */
    public final static ThreadLocal<SimpleDateFormat> SDF_YEAR = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(YEAR);
        }
    };

    /**
     *  线程不安全， 需要处理
     * yyyyMM
     */
    public final static ThreadLocal<SimpleDateFormat> SDF_YEAR_MONTH = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(YEAR_MONTH);
        }
    };

    /**
     *  线程不安全， 需要处理
     * yyyy-MM
     */
    public final static ThreadLocal<SimpleDateFormat> SDF_YEAR_MONTH_PATTERN = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(YEAR_MONTH_PATTERN);
        }
    };


    /**
     * LocaDate，LocalDateTime 线程安全，不处理
     */
    public final static DateTimeFormatter DTF_DATE_TIME = DateTimeFormatter.ofPattern(DATE_TIME);
    public final static DateTimeFormatter DTF_DATE_TIME_SEPARATE = DateTimeFormatter.ofPattern(DATE_TIME_SEPARATE);
    public final static DateTimeFormatter DTF_DATE = DateTimeFormatter.ofPattern(DATE);
    public final static DateTimeFormatter DTF_DATEV2 = DateTimeFormatter.ofPattern(DATEV2);
    public final static DateTimeFormatter DTF_DATE_SEPARATE = DateTimeFormatter.ofPattern(DATE_SEPARATE);
    public final static DateTimeFormatter DTF_DATE_HOUR = DateTimeFormatter.ofPattern(DATE_HOUR);
    public final static DateTimeFormatter DTF_YEAR = DateTimeFormatter.ofPattern(YEAR);
    public final static DateTimeFormatter DTF_YEAR_MONTH = DateTimeFormatter.ofPattern(YEAR_MONTH);
    public final static DateTimeFormatter DTF_YEAR_MONTH_PATTERN = DateTimeFormatter.ofPattern(YEAR_MONTH_PATTERN);

}
