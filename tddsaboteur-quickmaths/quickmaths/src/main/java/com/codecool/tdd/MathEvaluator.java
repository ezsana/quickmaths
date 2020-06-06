package com.codecool.tdd;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * A basic math expression evaluator
 * <p>
 * "3"                      -> 3.0
 * "3 - 2.5"                -> 0.5
 * "3    -   2.5"           -> 0.5
 * "1 + 2 * 3"              -> 7
 * "4/3 + 5 - 1 * 2"        -> 4.33333
 * "3 - a"                  -> Exception
 * "34.fg3"                 -> Exception
 */
public class MathEvaluator {

    public double evaluate(String expression) throws Exception {
        checkExpressionIsNotNullOrEmpty(expression);

        expression = clearExpression(expression);

        List<String> exp = expressionToList(expression);

        Stack<String> numberStack = new Stack<>();
        Stack<String> operationStack = new Stack<>();

        for (int i = 0; i < exp.size(); i++) {
            if (exp.get(i).equals("+") || exp.get(i).equals("-")) {
                numberStack.push(exp.get(i-1));
                operationStack.push(exp.get(i));
            } else if (exp.get(i).equals("*")) {
                double d = Double.valueOf(exp.get(i-1)) * Double.valueOf(exp.get(i+1));
                numberStack.push(String.valueOf(d));
            } else if (exp.get(i).equals("/")) {
                double d = Double.valueOf(exp.get(i-1)) / Double.valueOf(exp.get(i+1));
                numberStack.push(String.valueOf(d));
            }
        }

        while (!operationStack.isEmpty()) {
            double d1 = Double.valueOf(numberStack.pop());
            String o = operationStack.pop();
            double d2 = Double.valueOf(numberStack.pop());
            if (o.equals("+")) {
                numberStack.push(String.valueOf(d1 + d2));
            } else if (o.equals("-")) {
                numberStack.push(String.valueOf(d2 - d1));
            }
        }

        return Double.valueOf(numberStack.pop());
    }

    private void checkExpressionIsNotNullOrEmpty(String expression) throws ParameterNullException, ParameterEmptyException {
        if (expression == null) {
            throw new ParameterNullException("Parameter is null, can't evaluate.");
        }
        if (expression.isEmpty()) {
            throw new ParameterEmptyException("Parameter is empty, nothing to evaluate.");
        }
    }

    private String clearExpression(String expression) throws ParameterInvalidException {
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '.') {
                try{
                    if (!String.valueOf(expression.charAt(i-1)).matches("\\d") || !String.valueOf(expression.charAt(i+1)).matches("\\d")) {
                        throw new ParameterInvalidException("Parameter is invalid");
                    }
                } catch(IndexOutOfBoundsException iob) {
                    throw new ParameterInvalidException("Parameter is invalid");
                }
            }
        }

        String regex = ".*[\\D&&[^\\s\\-+*/\\.]].*";
        if (expression.matches(regex)) throw new ParameterInvalidException("Parameter is invalid");

        String reg = "[\\D&&[^\\-+*/\\.]]";
        expression = expression.replaceAll(reg, "");

        return expression;
    }

    List<String> expressionToList(String expression) {
        List<String> exprList = new ArrayList<>();
        String num = "";

        for (int i = 0; i < expression.length(); i++) {
            if (String.valueOf(expression.charAt(i)).matches("\\d") || expression.charAt(i) == '.') {
                num += expression.charAt(i);
                if (i == expression.length()-1) {
                    exprList.add(num);
                }
            } else {
                exprList.add(num);
                exprList.add(String.valueOf(expression.charAt(i)));
                num = "";
            }
        }
        return exprList;
    }

}
