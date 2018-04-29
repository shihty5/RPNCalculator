package com.operators.impl;

import com.memento.StackMemento;
import com.operators.MementorOperator;

import java.math.BigDecimal;
import java.util.Stack;

public class UndoOperator extends MementorOperator {

    @Override
    protected Stack<BigDecimal> compute(StackMemento stackMemento, Stack<BigDecimal> workingStack) {
        needSave = false;
        return stackMemento.getPrevStatus();
    }
}
