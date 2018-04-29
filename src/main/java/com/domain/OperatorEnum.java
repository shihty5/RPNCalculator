package com.domain;

import java.util.HashMap;
import java.util.Map;

public enum OperatorEnum {
    ADD("+", "加", 2),
    SUB("-", "减", 2),
    MUL("*", "乘", 2),
    DIV("/", "除", 2),
    SQRT("sqrt", "开方", 1),
    UNDO("undo", "撤销上次操作", 1),
    CLEAR("clear", "清空数据", 0);

    private String value;
    private String desc;
    private int operandsNumber;

    private static Map<String, Integer> operandsNumMap;

    static {
        operandsNumMap = new HashMap<>();
        for (OperatorEnum operator : OperatorEnum.values()) {
            operandsNumMap.put(operator.getValue(), operator.getOperandsNumber());
        }
    }

    OperatorEnum(String value, String desc, int operandsNumber) {
        this.value = value;
        this.desc = desc;
        this.operandsNumber = operandsNumber;
    }

    public static boolean operable(String input, int stackSize, int mementoSize) {
        if (UNDO.value.equals(input)) {
            return mementoSize >= operandsNumMap.get(input);
        }

        return stackSize >= operandsNumMap.get(input);
    }

    public String getValue() {
        return value;
    }

    public int getOperandsNumber() {
        return operandsNumber;
    }


}
