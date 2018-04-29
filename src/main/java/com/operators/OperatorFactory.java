package com.operators;

import com.domain.OperatorEnum;
import com.operators.impl.*;

import java.util.HashMap;
import java.util.Map;

public class OperatorFactory {
    private Map<String, MementorOperator> operatorMap;

    public OperatorFactory() {
        init();
    }

    private void init() {
        operatorMap = new HashMap<>();
        operatorMap.put(OperatorEnum.ADD.getValue(), new AddOperator());
        operatorMap.put(OperatorEnum.SUB.getValue(), new SubOperator());
        operatorMap.put(OperatorEnum.MUL.getValue(), new MulOperator());
        operatorMap.put(OperatorEnum.DIV.getValue(), new DivOperator());
        operatorMap.put(OperatorEnum.CLEAR.getValue(), new ClearOperator());
        operatorMap.put(OperatorEnum.UNDO.getValue(), new UndoOperator());
        operatorMap.put(OperatorEnum.SQRT.getValue(), new SqrtOperator());
    }

    public MementorOperator getOperatorHandler(String input) {
        return operatorMap.get(input);
    }

    public boolean isSupportedOperator(String input) {
        return operatorMap.containsKey(input);
    }
}
