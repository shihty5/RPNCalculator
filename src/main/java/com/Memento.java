package com;

import java.util.Stack;

//备忘录，用来记录每次操作，支持撤销操作
public class Memento {
    private static Stack<Stack> hisStack = new Stack<>();

    public <T> void save(Stack<T> stack) {
        hisStack.push(getSnapshot(stack));
    }

    public <T> Stack<T> retrivePrevStatus() {
        hisStack.pop();
        if (!hisStack.isEmpty()) {
            return hisStack.peek();
        } else {
            return new Stack<>();
        }
    }

    private <T> Stack<T> getSnapshot(Stack<T> stack) {
        Stack<T> newStack = new Stack<>();
        for (T b : stack) {
            newStack.push(b);
        }
        return newStack;
    }
}
