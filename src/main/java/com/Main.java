package com;

import com.calculator.RPNCalculator;
import com.calculator.Calculator;
import com.memento.StackMemento;
import com.operators.OperatorFactory;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        Stack<BigDecimal> workingStack = new Stack<>();
        StackMemento memento = new StackMemento();
        Calculator calculator = new RPNCalculator(workingStack, memento, new OperatorFactory());

        Scanner scanner = new Scanner(System.in);
        String nextLine;

        while (true) {
            nextLine = scanner.nextLine();

            //过滤多余的回车空格
            while ("".equals(nextLine)) {
                nextLine = scanner.nextLine();
            }
            calculator.handle(nextLine);
        }

    }
}
