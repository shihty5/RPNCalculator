package com.operators.impl;

import com.memento.StackMemento;
import com.operators.MementorOperator;

import java.math.BigDecimal;
import java.util.Stack;

public class SubOperator extends MementorOperator {

    @Override
    protected Stack<BigDecimal> compute(StackMemento stackMemento, Stack<BigDecimal> workingStack) {
        BigDecimal operand1 = workingStack.pop();
        BigDecimal operand2 = workingStack.pop();

        workingStack.push(
                operand2.subtract(operand1)
        );

        return workingStack;
    }
}
