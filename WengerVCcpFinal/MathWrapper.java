// Max Wenger
// APCS
// Period 1
// VisiCalc

import java.util.ArrayList;
import java.util.Scanner;

public final class MathWrapper {

    private static final char OPERANDS[] = {'(', ')', '+', '-', '*', '/', };

    //region Format
    /**
     * This is only in use to get around using the parse functions that throw errors.
     * Use this method only when you are absolutely sure that it is only a number and nothing but a number
     * If you input something that has more then just a simple number, it will return zero and passably
     * cause bugs.
     *
     * @param str
     * @return
     */
    public static double stringToDouble(String str) {
        double ret = 0.0;

        Scanner parse = new Scanner(str);
        if (isDouble(str) && parse.hasNextDouble()){
            ret = parse.nextDouble();
        }
        parse.close();
        return ret;
    }

    private static void removeSurroundingValues(ArrayList<String> formula, int index) {
        formula.remove(index + 1);
        formula.remove(index - 1);
    }
    //endregion

    //region Functions

    public static double findSum(ArrayList<Cell> cells) {
        int sum = 0;
        for(Cell c : cells){
            sum += stringToDouble(c.getRawValue());
        }
        return sum;
    }

    public static double findAverage(ArrayList<Cell> cells) {
        double sum = findSum(cells);
        sum /= cells.size();
        return sum;
    }

    //endregion

    //region Expression Calculation
    public static double computeSingleExpression(String first, String second, String operand) {
        double sum = 0.0;

        double a = stringToDouble(first);
        double b = stringToDouble(second);

        if (operand.equals("*")) { sum = a * b; }
        else if (operand.equals("/")) { sum = a / b; }
        else if (operand.equals("+")) { sum = a + b; }
        else if (operand.equals("-")) { sum = a - b; }

        return sum;
    }



    public static void computeAllExpressionsByOpperand(ArrayList<String> formula, String operand) {
        if(formula.contains(operand)) {
            int i = formula.indexOf(operand);

            double sum = MathWrapper.computeSingleExpression(formula.get(i - 1), formula.get(i + 1), operand);

            removeSurroundingValues(formula, i);

            formula.set(i-1, sum + "");

            // To understand recursion, see the following 2 lines
            computeAllExpressionsByOpperand(formula, operand);
            // To understand recursion, see the preceding 2 lines
        }
    }
    //endregion

    //region Validation
    public static boolean isDouble(String value) {
        char validChars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};

        boolean isValid = true;
        int numberOfDecimals = 0;

        for (int i = 0; i < value.length(); i++) {
            boolean isCurrentNumber = false;

            for (char c : validChars) {
                if(value.charAt(i) == c && numberOfDecimals <= 1) {
                    if(value.charAt(i) == '.') { numberOfDecimals++; }
                    isCurrentNumber = true;
                }
            }

            isValid = isCurrentNumber;
        }

        return isValid;
    }

    public static boolean isAllNumbers(ArrayList<Cell> cells) {
        boolean ret = true;

        for (Cell c:cells) {
            if(!DoubleCell.isValidCellType(c.getRawValue())) {
                ret = false;
            }
        }

        return ret;
    }
    public static boolean isSurroundingValuesNumbers(ArrayList<String> formula) {
        boolean res = true;

        for(int i = 0; i < formula.size(); i++) {
            if(isOperand(formula.get(i))) {
                isSurroundingValuesNumbers(formula, i);
            }
        }

        return res;
    }

    public static boolean isSurroundingValuesNumbers(ArrayList<String> formula, int index) {
        boolean result = false;

        if(formula.size() > 1) {
            if (index == 0) {
                result = DoubleCell.isValidCellType(formula.get(index + 1));
            } else if (index > formula.size()) {
                result = false;
            } else {
                result = index != formula.size()
                        && DoubleCell.isValidCellType(formula.get(index + 1))
                        && DoubleCell.isValidCellType(formula.get(index - 1));
            }
        }

        return result;
    }

    public static boolean isOperand(String s) {
        boolean res = false;
        for (char c:OPERANDS) {
            if(s.equals(c + "")) {
                res = true;
            }
        }
        return res;
    }
    //endregion
}
