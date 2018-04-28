package com;

public enum OperatorEnum {
    ADD("+", "加"),
    SUB("-", "减"),
    MUL("*", "乘"),
    DIV("/", "除"),
    SQRT("sqrt", "开方"),
    UNDO("undo", "撤销上次操作"),
    CLEAR("clear", "清空数据");

    private String value;
    private String desc;

    OperatorEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static boolean operable(String input, int stackSize) {
        if (CLEAR.getValue().equals(input)) {
            return true;
        }

        if (SQRT.getValue().equals(input)) {
            return stackSize >= 1;
        }

        return stackSize >= 2;
    }

    public String getValue() {
        return value;
    }

}
