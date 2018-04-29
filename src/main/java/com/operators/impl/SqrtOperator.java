package com.operators.impl;

import com.memento.StackMemento;
import com.operators.MementorOperator;

import java.math.BigDecimal;
import java.util.Stack;

public class SqrtOperator extends MementorOperator {

    @Override
    protected Stack<BigDecimal> compute(StackMemento stackMemento, Stack<BigDecimal> workingStack) {
        BigDecimal operand = workingStack.pop();

        if (operand.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("进行平方根运算时，数必须大于等于0！");
            workingStack.push(operand);
            return workingStack;
        }

        workingStack.push(new BigDecimal(Math.sqrt(operand.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE).doubleValue())));

        return workingStack;
    }
}
