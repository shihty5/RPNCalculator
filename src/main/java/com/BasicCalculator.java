package com;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BasicCalculator extends Calculator {
    private static final Map<String, Operator> operatorMap = new HashMap<>();

    private static Stack<BigDecimal> stack = new Stack<>();
    private static Stack<Stack> hisStack = new Stack<>();

    static {
        operatorMap.put(OperatorEnum.ADD.getValue(), new Operator() {
            @Override
            public void compute() {
                stack.push(stack.pop().add(stack.pop()));
                hisStack.push(getSnapshot(stack));
            }
        });

        operatorMap.put(OperatorEnum.SUB.getValue(), new Operator() {
            @Override
            public void compute() {
                BigDecimal operand1 = stack.pop();
                BigDecimal operand2 = stack.pop();
                stack.push(operand2.subtract(operand1));
                hisStack.push(getSnapshot(stack));
            }
        });

        operatorMap.put(OperatorEnum.MUL.getValue(), new Operator() {
            @Override
            public void compute() {
                stack.push(stack.pop().multiply(stack.pop()));
                hisStack.push(getSnapshot(stack));
            }
        });

        operatorMap.put(OperatorEnum.DIV.getValue(), new Operator() {
            @Override
            public void compute() {
                BigDecimal operand1 = stack.pop();
                BigDecimal operand2 = stack.pop();
                stack.push(operand2.divide(operand1, 10, BigDecimal.ROUND_HALF_DOWN));
                hisStack.push(getSnapshot(stack));
            }
        });

        operatorMap.put(OperatorEnum.SQRT.getValue(), new Operator() {
            @Override
            public void compute() {
                stack.push(new BigDecimal(Math.sqrt(stack.pop().doubleValue())));
            }
        });

        operatorMap.put(OperatorEnum.UNDO.getValue(), new Operator() {
            @Override
            public void compute() {
                //撤销上一次操作
                hisStack.pop();
                if (!hisStack.empty()) {
                    stack = hisStack.peek();
                }
            }
        });

        operatorMap.put(OperatorEnum.CLEAR.getValue(), new Operator() {
            @Override
            public void compute() {
                stack.clear();
                hisStack.push(getSnapshot(stack));
            }
        });

    }

    public static Stack<BigDecimal> getSnapshot(Stack<BigDecimal> stack) {
        Stack<BigDecimal> newStack = new Stack<>();
        for (BigDecimal b : stack) {
            newStack.push(b);
        }
        return newStack;
    }

    public void push(String input) {
        stack.push(new BigDecimal(input));
    }

    @Override
    public int action(String input) {
        if (isNumber(input)) {
            stack.push(new BigDecimal(input));
            hisStack.push(getSnapshot(stack));
            return 0;
        }

        if (!operatorMap.containsKey(input)) {
            return -1;
        }

        if (!OperatorEnum.operable(input, stack.size(), hisStack.size())) {
            return -2;
        }

        operatorMap.get(input).compute();

        return 0;
    }

    public void showStackInfo() {
        System.out.print("stack: ");
        for (BigDecimal b : stack) {
            System.out.print(b + " ");
        }
        System.out.println();
    }

    public boolean isNumber(String input) {
        try {
            Double.valueOf(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
