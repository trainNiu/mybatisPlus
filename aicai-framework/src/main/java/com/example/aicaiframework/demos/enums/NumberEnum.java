package com.example.aicaiframework.demos.enums;

/**
 * 数字常量枚举
 */
public enum NumberEnum {

    ZERO(0L, 0,"0"), ONE(1L, 1,"1"), TWO(2L, 2,"2"),
    THREE(3L, 3,"3"), FOUR(4L, 4,"4"), FIVE(5L, 5,"5"),
    SIX(6L, 6,"6"), SEVEN(7L, 7,"7"), EIGHT(8L, 8,"8"),
    NINE(9L, 9,"9"), TEN(10L, 10,"10"), ELEVEN(11L, 11,"11"),
    TWELVE(12L, 12,"12"), THIRTEEN(13L, 13,"13"), FOURTEEN(14L, 14,"14"),
    FIFTEEN(15L, 15,"15"), SIXTEEN(16L, 16,"16"), SEVENTEEN(17L, 17,"17"),
    EIGHTEEN(18L, 18,"18"), NINETEEN(19L, 19,"19"), TWENTY(20L, 20,"20"),
    TWENTY_ONE(21L, 21,"21"),TWENTY_TWO(22L, 22,"22"),TWENTY_THREE(23L, 23,"23"),
    TWENTY_FOUR(24L, 24,"24"),
    TWENTY_FIVE(25L,25,"25"),
    TWENTY_EIGHT(28L,28,"28"),
    THIRTY(30L, 30,"30"),
    FORTY(40L, 40,"40"), FORTY_FIVE(45L, 45,"45"),
    ONE_HUNDRED(100L, 100,"100"),
    TWO_HUNDRED(200L, 200,"200"),
    THREE_HUNDRED(300L, 300,"300"),
    FOUR_HUNDRED(400L, 400,"400"),
    FIVE_HUNDRED(500L, 500,"500"),
    ONE_THOUSAND(1000L, 1000,"1000"),
    TWO_THOUSAND(2000L, 2000,"2000"),
    FIVE_THOUSAND(5000L, 5000,"5000"),
    TEN_THOUSAND(10000L, 10000,"10000");

    private NumberEnum(long longCode, int intCode, String val) {
        this.longCode = longCode;
        this.intCode = intCode;
        this.strCode = val;
    }

    private final long longCode;
    private final int intCode;
    private final String strCode;

    public long longValue() {
        return longCode;
    }

    public int intValue() {
        return intCode;
    }

    public String strValue() {
        return strCode;
    }

    public String val() {
        return strCode;
    }

}
