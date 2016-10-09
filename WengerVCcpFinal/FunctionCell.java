// Max Wenger
// APCS
// Period 1
// VisiCalc

import java.util.ArrayList;

public class FunctionCell extends FormulaCell implements Comparable<Cell> {

    private static final String FUNCTION_KEYWORDS[] = {"AVG", "SUM"};

    public FunctionCell(String value) {
        super(value);
        super.setRawValue(value);
    }

    public String getCurrentValue(Cell spreadsheet[][]) {
        String ret = "NaN";

        ArrayList<String> function = super.stringToElementList(getRawValue());

        if(isAllElementsValid(function)) {
            String functionKeyword = function.get(0);

            // function = { AVG, A1, -, A2 }
            ArrayList<Cell> cellsInRange = super.getCellRange(function.get(1), function.get(3), spreadsheet);

            unknownValuesToNumbers(spreadsheet, cellsInRange);

            ret = calculateFunction(ret, functionKeyword, cellsInRange);
        }

        setLastValue(ret);
        return ret;
    }

    private void unknownValuesToNumbers(Cell[][] spreadsheet, ArrayList<Cell> cellsInRange) {
        for (int i = 0; i < cellsInRange.size(); i++) {
            Cell c = cellsInRange.get(i);
            if(c instanceof FormulaCell) {
                ((FormulaCell) c).getCurrentValue(spreadsheet);
                double lastVal = MathWrapper.stringToDouble(((FormulaCell) c).getLastValue());
                cellsInRange.set(i, new DoubleCell(lastVal));
            }
        }
    }

    private String calculateFunction(String ret, String func, ArrayList<Cell> cellsInRange) {
        if(MathWrapper.isAllNumbers(cellsInRange)) {
            if (func.equalsIgnoreCase("AVG")) {
                ret = MathWrapper.findAverage(cellsInRange) + "";
            } else if (func.equalsIgnoreCase("SUM")) {
                ret = MathWrapper.findSum(cellsInRange) + "";
            }
        }
        return ret;
    }

    //region Validation
    public static boolean isValidCellType(String value) {
        return FormulaCell.isValidCellType(value)
                && isAllElementsValid(stringToElementList(value));
    }

    private static boolean isAllElementsValid(ArrayList<String> formula) {
        // Valid function input
        // A1 = ( AVG A1 - C1 )

        boolean ret = false;

        for (String s: FUNCTION_KEYWORDS) {
            if(formula.get(0).equalsIgnoreCase(s)){
                ret = true;
            }
        }
        return ret;
    }
    //endregion

}
