package com.memento;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * 备忘录，用来记录每次操作，用以支持撤销操作
 */
public class StackMemento implements Memento {
    private static Stack<Stack> hisStack = new Stack<>();

    @Override
    public void save(Stack<BigDecimal> stack) {
        hisStack.push(getSnapshot(stack));
    }

    public Stack<BigDecimal> getPrevStatus() {
        hisStack.pop();
        if (!hisStack.isEmpty()) {
            return hisStack.peek();
        } else {
            return new Stack<>();
        }
    }

    private Stack<BigDecimal> getSnapshot(Stack<BigDecimal> stack) {
        Stack<BigDecimal> snapshot = new Stack<>();
        for (BigDecimal b : stack) {
            snapshot.push(b);
        }
        return snapshot;
    }

    public int size() {
        return hisStack.size();
    }
}
