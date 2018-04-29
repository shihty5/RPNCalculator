package com.calculator;

import java.io.InputStream;

public abstract class Calculator {
    abstract void process(String input);
    abstract void print();
    public abstract void handle(InputStream is);
}
