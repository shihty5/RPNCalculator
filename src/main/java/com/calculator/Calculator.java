package com.calculator;

import java.io.InputStream;

public abstract class Calculator {
    abstract int process(String input);
    abstract void print();
    public abstract void handle(InputStream is);
}
