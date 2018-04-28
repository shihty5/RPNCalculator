package com;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BasicCalculator extends Calculator {
    private static final Map<String, Operator> operatorMap = new HashMap<>();
    private static Stack<BigDecimal> workingStack = new Stack<>();

    //除法运算精度
    private static final int DIV_SCALE = 10;

    private static Memento memento = new Memento();

    static {
        operatorMap.put(OperatorEnum.ADD.getValue(), new Operator() {
            @Override
            public void compute() {
                workingStack.push(workingStack.pop().add(workingStack.pop()));
                memento.save(workingStack);
            }
        });

        operatorMap.put(OperatorEnum.SUB.getValue(), new Operator() {
            @Override
            public void compute() {
                BigDecimal operand1 = workingStack.pop();
                BigDecimal operand2 = workingStack.pop();
                workingStack.push(operand2.subtract(operand1));
                memento.save(workingStack);
            }
        });

        operatorMap.put(OperatorEnum.MUL.getValue(), new Operator() {
            @Override
            public void compute() {
                workingStack.push(workingStack.pop().multiply(workingStack.pop()));
                memento.save(workingStack);
            }
        });

        operatorMap.put(OperatorEnum.DIV.getValue(), new Operator() {
            @Override
            public void compute() {
                BigDecimal operand1 = workingStack.pop();
                BigDecimal operand2 = workingStack.pop();
                workingStack.push(operand2.divide(operand1, DIV_SCALE, BigDecimal.ROUND_HALF_DOWN));
                memento.save(workingStack);
            }
        });

        operatorMap.put(OperatorEnum.SQRT.getValue(), new Operator() {
            @Override
            public void compute() {
                workingStack.push(new BigDecimal(Math.sqrt(workingStack.pop().doubleValue())));
                memento.save(workingStack);
            }
        });

        operatorMap.put(OperatorEnum.UNDO.getValue(), new Operator() {
            @Override
            public void compute() {
                //回滚到上一次操作
                workingStack = memento.retrivePrevStatus();
            }
        });

        operatorMap.put(OperatorEnum.CLEAR.getValue(), new Operator() {
            @Override
            public void compute() {
                workingStack.clear();
                memento.save(workingStack);
            }
        });

    }

    @Override
    public int action(String input) {
        if (isNumber(input)) {
            workingStack.push(new BigDecimal(input));
            memento.save(workingStack);
            return 0;
        }

        if (!operatorMap.containsKey(input)) {
            return -1;
        }

        if (!OperatorEnum.operable(input, workingStack.size())) {
            return -2;
        }

        operatorMap.get(input).compute();

        return 0;
    }

    public void showStackInfo() {
        System.out.print("stack: ");
        for (BigDecimal b : workingStack) {
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
