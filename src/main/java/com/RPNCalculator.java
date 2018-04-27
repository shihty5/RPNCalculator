package com;

import java.util.Scanner;

public class RPNCalculator {

    private static Scanner scanner = new Scanner(System.in);
    private BasicCalculator calculator = new BasicCalculator();

    public static void main(String[] args) {
        new RPNCalculator().process();

    }

    public void process() {
        while (true) {
            String nextLine = scanner.nextLine();

            if ("".equals(nextLine)) {
                scanner.nextLine();
                continue;
            }

            String[] elements = nextLine.split(" ");
            for (String input : elements) {
                int result = calculator.action(input);
                if (result != 0) {
                    if (result == -1) {
                        System.out.println(String.format("该操作符 [%s] 无效.. 忽略", input));
                    }

                    if (result == -2) {
                        System.out.println(String.format("operator %s (position: ): insufficient parameters", input));
                        break;
                    }
                }
            }

            calculator.showStackInfo();

        }

    }
}
