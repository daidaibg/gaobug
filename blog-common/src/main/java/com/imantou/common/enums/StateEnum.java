package com.imantou.common.enums;


public enum StateEnum {
    /**
     * 逻辑删除
     */
    DELETE("-1","已删除"),
    NOT_DELETE("0","未删除"),
    /**
     * 用户状态
     */
    NORMAL("1","正常"),
    FORBIDDEN("0","禁用"),

    /**
     *
     */

    ;

    private String value;
    private String name;

    StateEnum( String value,String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
