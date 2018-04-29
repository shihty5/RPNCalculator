package com.operators.impl;

import com.memento.StackMemento;
import com.operators.MementorOperator;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * 加法运算器
 */
public class AddOperator extends MementorOperator {

    @Override
    protected Stack<BigDecimal> compute(StackMemento stackMemento, Stack<BigDecimal> workingStack) {
        workingStack.push(
                workingStack.pop().add(workingStack.pop())
        );

        return workingStack;
    }
}
