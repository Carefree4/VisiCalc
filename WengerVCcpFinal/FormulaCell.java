// Max Wenger
// APCS
// Period 1
// VisiCalc

import java.util.ArrayList;
import java.util.Scanner;

public class FormulaCell extends Cell implements Comparable<Cell>{

    private String lastValue;
    private Cell spreadsheet[][];

    //region Constructors
    public FormulaCell(String value) {
        super.setRawValue(value);
    }

    public FormulaCell(String value, Cell spreadsheet[][]) {
        super.setRawValue(value);
        this.spreadsheet = spreadsheet;
    }
    //endregion

    //region Field Encapsulation
    public void setSpreadsheet(Cell spreadsheet[][]) {
        this.spreadsheet = spreadsheet;
    }

    public String getLastValue() {
        return lastValue;
    }

    protected void setLastValue(String lastValue) {
        this.lastValue = lastValue;
    }
    //endregion

    //region Formula Value Evaluation
    public String toString() {
        return getLastValue();
    }

    public String getCurrentValue(Cell spreadsheet[][]) {
        setSpreadsheet(spreadsheet);
        return getCurrentValue();
    }

    public String getCurrentValue() {
        String res;

        ArrayList<String> formula = stringToElementList(getRawValue());
        if(isAllElementsValid(formula)) {
            formula = replaceCellLocationWithValue(formula);
            res = calculateFormula(formula);
        } else {
            res = "NaN";
        }

        setLastValue(res);
        return res;
    }

    private String calculateFormula(ArrayList<String> formula) {
        String c;

        if(MathWrapper.isSurroundingValuesNumbers(formula)) {

            MathWrapper.computeAllExpressionsByOpperand(formula, "*");
            MathWrapper.computeAllExpressionsByOpperand(formula, "/");
            MathWrapper.computeAllExpressionsByOpperand(formula, "+");
            MathWrapper.computeAllExpressionsByOpperand(formula, "-");

            c = formula.get(0);
        } else {
            c = "NaN";
        }
        return c;
    }
    //endregion

    //region Formula Manipulation
    protected static ArrayList<String> stringToElementList(String rawFormula) {
        ArrayList<String> formula = new ArrayList<>();
        Scanner split = new Scanner(rawFormula);

        while (split.hasNext()) {
            formula.add(split.next());
        }

        split.close();

        // Trims first and last ()'s
        formula.remove(0);
        formula.remove(formula.size() - 1);

        return formula;
    }

    private ArrayList<String> replaceCellLocationWithValue(ArrayList<String> formula) {
        for (int i = 0; i < formula.size(); i++) {
            String s = formula.get(i);
            if(CellLocation.isCellLocation(s)) {
                formula.set(i, locationToCellValue(new CellLocation(s), this.spreadsheet));
            } else {
                formula.set(i, s);
            }
        }
        return formula;
    }
    //endregion

    //region Validation
    private boolean isAllElementsValid(ArrayList<String> formula) {
        boolean res = true;

        String lastString = "";
        for (String s:formula) {
            if(!CellLocation.isCellLocation(s)
                    && !MathWrapper.isOperand(s)
                    && !DoubleCell.isValidCellType(s)) {
                res = false;
            }
            if(lastString.equals("/") && s.equals("0")) {
                res = false;
            }
            lastString = s;
        }
        return res;
    }

    public static boolean isValidCellType(String value) {
        // Valid input ( A1 - B3 )

        // Value comes in with a space every time as first char, will move later.

        return value.charAt(0) == '(' && value.charAt(value.length() - 1) == ')';
    }
    //endregion


}
