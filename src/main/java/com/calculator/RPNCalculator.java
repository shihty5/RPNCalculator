package com.calculator;

import com.CalcException;
import com.domain.OperatorEnum;
import com.memento.StackMemento;
import com.operators.OperatorFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Stack;

public class RPNCalculator extends Calculator {
    private Stack<BigDecimal> workingStack;
    private StackMemento stackMemento;
    private OperatorFactory operatorFactory;
    private int position;
    private DecimalFormat decimalFormat;

    private final String SUPPORTED_OPERATORS = "+, -, *, /, sqrt, clear, undo";
    private final String QUIT = "quit";
    private final String HELP = "help";

    public RPNCalculator(Stack<BigDecimal> workingStack, StackMemento stackMemento, OperatorFactory operatorFactory) {
        this.workingStack = workingStack;
        this.stackMemento = stackMemento;
        this.operatorFactory = operatorFactory;
        this.decimalFormat = new DecimalFormat("0.##########");
        this.decimalFormat.setRoundingMode(RoundingMode.DOWN);

        System.out.println("**************  欢迎使用RPN Calculator  **********");
        System.out.println(String.format("您可以输入任意数字和如下操作符进行运算:[%s]\n输入help 获取更多帮助, quit退出", SUPPORTED_OPERATORS));
    }

    /**
     * 处理每一个输入
     *
     * @param input 待处理的输入，接受数字和操作符
     */
    @Override
    public void process(String input) {
        if (QUIT.equals(input)) {
            System.out.println("感谢使用RPN Calculator, 再见！");
            System.exit(0);
        }

        if (HELP.equals(input)) {
            printHelpMessage();
            return;
        }

        if (isNumber(input)) {
            workingStack.push(new BigDecimal(input));
            stackMemento.save(workingStack);
            return;
        }

        if (!operatorFactory.isSupportedOperator(input)) {
            throw new CalcException(String.format("该操作符[%s]无效，目前支持如下：[%s]", input, SUPPORTED_OPERATORS));
        }

        if (!OperatorEnum.operable(input, workingStack.size(), stackMemento.size())) {
            throw new CalcException(String.format("operator %s (position: %s): insufficient parameters", input, position));
        }

        workingStack = operatorFactory.getOperatorHandler(input).process(stackMemento, workingStack);

    }

    /**
     * 打印当前工作栈信息
     */
    void print() {
        System.out.print("stack: ");
        for (BigDecimal b : workingStack) {
            System.out.print(decimalFormat.format(b) + " ");
        }
        System.out.println();
    }

    @Override
    public void handle(InputStream is) {
        Scanner scanner = new Scanner(is);

        while (true) {
            String nextLine = scanner.nextLine();

            //过滤多余的回车空格
            while ("".equals(nextLine)) {
                nextLine = scanner.nextLine();
            }

            position = -1;

            String[] elements = nextLine.split("\\s");
            for (String input : elements) {
                position += 2;
                //过滤输入行中的空格
                if ("".equals(input)) {
                    position--;
                    continue;
                }

                try {
                    process(input);
                } catch (CalcException e) {
                    System.out.println(e.getMessage());
                    break;
                }
            }

            print();

        }
    }

    private boolean isNumber(String input) {
        try {
            Double.valueOf(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void printHelpMessage() {
        System.out.println(String.format("[1]目前支持如下操作符:[%s]", SUPPORTED_OPERATORS));
        System.out.println("[2]+ - * / 对应加减乘除运算，注意进行除法运算时，除数不能为0");
        System.out.println("[3]sqrt 返回正的平方根, 被计算数字必须大于等于0");
        System.out.println("[4]clear 清除所有数据，可以通过undo撤销");
        System.out.println("[5]undo 撤销上次成功的操作");
    }
}
