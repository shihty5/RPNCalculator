package com;

import com.calculator.RPNCalculator;
import com.memento.StackMemento;
import com.operators.OperatorFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Stack;

/**
 * Unit test for RPNCalculator.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RPNCalculatorTest {
    RPNCalculator rpnCalculator;
    Stack<BigDecimal> workingStack;

    @Before
    public void init() {
        rpnCalculator = new RPNCalculator(new Stack<BigDecimal>(), new StackMemento(), new OperatorFactory());
        workingStack = getWorkingStack();
    }

    @Test
    public void testNumber() {
        rpnCalculator.process("3");
        Assert.assertEquals("3", getPeekValue());
    }

    @Test
    public void testAdd() {
        rpnCalculator.process("3");
        rpnCalculator.process("3");
        rpnCalculator.process("+");
        Assert.assertEquals("6", getPeekValue());
    }

    @Test(expected = CalcException.class)
    public void testAddWhenInsufficientParameters() {
        rpnCalculator.process("3");
        rpnCalculator.process("+");

    }

    @Test
    public void testSub() {
        rpnCalculator.process("9");
        rpnCalculator.process("18");
        rpnCalculator.process("-");
        Assert.assertEquals("-9", getPeekValue());
    }

    @Test
    public void testMul() {
        rpnCalculator.process("111");
        rpnCalculator.process("9");
        rpnCalculator.process("*");
        Assert.assertEquals("999", getPeekValue());
    }

    @Test
    public void testDivSuccess() {
        rpnCalculator.process("3");
        rpnCalculator.process("3");
        rpnCalculator.process("/");
        Assert.assertEquals("1", getPeekValue());
    }

    @Test
    public void testDivFailure() {
        rpnCalculator.process("3");
        rpnCalculator.process("0");
        rpnCalculator.process("/");

        //栈顶还是0，报警后没操作
        Assert.assertEquals("0", getPeekValue());
    }

    @Test
    public void testSqrtSuccess1() {
        rpnCalculator.process("9");
        rpnCalculator.process("sqrt");
        Assert.assertEquals("3", getPeekValue());
    }

    @Test
    public void testSqrtSuccess2() {
        rpnCalculator.process("2");
        rpnCalculator.process("sqrt");
        Assert.assertEquals("1.4142135623", getPeekValue());
    }

    @Test
    public void testSqrtFailure() {
        rpnCalculator.process("-5");
        rpnCalculator.process("sqrt");
        Assert.assertEquals("-5", getPeekValue());
    }

    @Test
    public void testClear() {
        rpnCalculator.process("9");
        rpnCalculator.process("sqrt");
        rpnCalculator.process("clear");

        Assert.assertEquals(0, workingStack.size());
    }

    @Test
    public void testUndo() {
        rpnCalculator.process("1");
        rpnCalculator.process("8");
        rpnCalculator.process("undo");

        Assert.assertEquals(BigDecimal.valueOf(1), getWorkingStack().peek());
    }

    @Test(expected = CalcException.class)
    public void testOtherCommands() {

        rpnCalculator.process("help");
        rpnCalculator.process("xxx");
        rpnCalculator.process("quit");

    }

    @Test
    public void testHandle() throws FileNotFoundException {

        String test1 = "clear 5 2";
        rpnCalculator.handle(test1);

        String test2 = "clear 2 sqrt clear 9 sqrt";
        rpnCalculator.handle(test2);

        String test3 = "clear 5 2 - 3 - clear";
        rpnCalculator.handle(test3);

        String test4 = "clear 5 4 3 2 undo undo * 5 * undo";
        rpnCalculator.handle(test4);

        String test5 = "clear 7 12 2 / * 4 /";
        rpnCalculator.handle(test5);

        String test6 = "clear 1 2 3 4 5 * clear 3 4 -";
        rpnCalculator.handle(test6);

        String test7 = "clear 1 2 3 4 5 * * * *";
        rpnCalculator.handle(test7);

        String test8 = "clear 1 2 3 * 5 + * * 6 5";
        rpnCalculator.handle(test8);

        String test9 = "xxx";
        rpnCalculator.handle(test9);
    }

    private String getPeekValue() {
        DecimalFormat decimalFormat = new DecimalFormat("0.##########");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);

        return decimalFormat.format(workingStack.peek());
    }

    private Stack<BigDecimal> getWorkingStack() {
        try {
            Field field = rpnCalculator.getClass().getDeclaredField("workingStack");
            field.setAccessible(true);
            return (Stack<BigDecimal>) field.get(rpnCalculator);
        } catch (Exception e) {
            System.err.println("Error Occurs!");
        }
        return null;
    }
}
