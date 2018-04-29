package com.operators.impl;

import com.memento.StackMemento;
import com.operators.MementorOperator;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * 开平方根运算器
 */
public class SqrtOperator extends MementorOperator {

    @Override
    protected Stack<BigDecimal> compute(StackMemento stackMemento, Stack<BigDecimal> workingStack) {
        BigDecimal operand = workingStack.pop();

        if (operand.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Warning: 进行开平方根运算时，被操作数必须大于等于0！");

            //异常不需要保存现场，恢复到之前状态
            workingStack.push(operand);
            needSave = false;

            return workingStack;
        }

        workingStack.push(new BigDecimal(Math.sqrt(operand.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE).doubleValue())));

        return workingStack;
    }
}
