package com.operators.impl;

import com.memento.StackMemento;
import com.operators.MementorOperator;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * 除法运算器
 */
public class DivOperator extends MementorOperator {

    @Override
    protected Stack<BigDecimal> compute(StackMemento stackMemento, Stack<BigDecimal> workingStack) {
        BigDecimal operand1 = workingStack.pop();

        if (operand1.compareTo(BigDecimal.ZERO) == 0) {
            System.out.println("Warning: 0不能作为除数！请检查.");

            //异常不需要保存现场，恢复到之前状态
            workingStack.push(operand1);
            needSave = false;

            return workingStack;
        }

        BigDecimal operand2 = workingStack.pop();

        workingStack.push(
                operand2.divide(operand1, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE)
        );

        return workingStack;
    }
}
