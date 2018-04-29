package com.operators.impl;

import com.memento.StackMemento;
import com.operators.MementorOperator;

import java.math.BigDecimal;
import java.util.Stack;

public class MulOperator extends MementorOperator {


    @Override
    protected Stack<BigDecimal> compute(StackMemento stackMemento, Stack<BigDecimal> workingStack) {
        workingStack.push(
                workingStack.pop().multiply(workingStack.pop())
        );
        return workingStack;
    }
}
