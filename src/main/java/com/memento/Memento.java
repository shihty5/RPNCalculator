package com.memento;

import java.math.BigDecimal;
import java.util.Stack;

public interface Memento {

    //保存现场
    void save(Stack<BigDecimal> stack);

    //退回到上次现场
    Stack<BigDecimal> getPrevStatus();
}
