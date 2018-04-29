package com.operators;

import com.memento.StackMemento;

import java.math.BigDecimal;
import java.util.Stack;

public abstract class MementorOperator {
    //该操作符是否需要保存现场，默认为true, 只有undo操作时为false
    protected boolean needSave = true;
    //默认运算精度
    protected final int DEFAULT_SCALE = 15;
    protected final int DEFAULT_ROUNDING_MODE= BigDecimal.ROUND_HALF_EVEN;

    public Stack<BigDecimal> process(StackMemento stackMemento, Stack<BigDecimal> workingStack) {
        Stack<BigDecimal> curWokringStack = compute(stackMemento, workingStack);

        if (needSave) {
            stackMemento.save(curWokringStack);
        }

        return curWokringStack;
    }

    //各操作符需要完成各自具体逻辑
    abstract protected Stack<BigDecimal> compute(StackMemento stackMemento, Stack<BigDecimal> workingStack);

}
