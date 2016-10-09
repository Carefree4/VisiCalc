// Max Wenger
// APCS
// Period 1
// VisiCalc

import java.util.ArrayList;

public class Cell implements Comparable<Cell> {

    /**
     * Stores the input the user set after the equals sign
     */
    private String rawValue;

    //region Constructors

    public Cell() { this.setRawValue(""); }

    public Cell(String rawValue) { this.setRawValue(rawValue); }

    //endregion

    //region Value Management

    public String getRawValue() { return rawValue; }

    public void setRawValue(String rawValue) { this.rawValue = rawValue; }

    /**
     * If it is a valid string, it will remove the quotes, if not it will return "NaN"
     * @return Formatted version of rawValue
     */
    public String toString() {
        String ret = "";
        if (rawValue.length() > 0) {
            if (rawValue.charAt(0) == '\"' && rawValue.charAt(rawValue.length() - 1) == '\"') {
                // rawValue will be "Something"
                // gets rid of the ""'s
                ret = rawValue;
                ret = ret.substring(1, ret.length() - 1);
            }
        }
        return ret;
    }

    protected String locationToCellValue(CellLocation location, Cell spreadsheet[][]) {
        String c = "";

        if(location.getX() >= 0 && location.getY() >= 0) {
            c = spreadsheet[location.getX()][location.getY()].getRawValue();
        }
        return c;
    }

    //endregion

    //region Get Cell Range

    public static ArrayList<Cell> getCellRange(String startIndex, String endIndex, Cell spreadsheet[][]) {
        CellLocation rangeIndex[] = getRangeStartEndIndex(startIndex, endIndex);
        return getCellRange(rangeIndex, spreadsheet);
    }

    public static ArrayList<Cell> getCellRange(CellLocation[] range, Cell spreadsheet[][]) {
        /*
        If they are on the same row, only get that row,
        If they are on the same column, only get that column
        If they are different, get every value in between them
         */

        ArrayList<Cell> ret;

        if(range[0].getX() == range[1].getX()) {
            ret = getRowOfCells(range, spreadsheet);
        } else if(range[0].getY() == range[1].getY()) {
            ret = getColumnOfCells(range, spreadsheet);
        } else {
            ret = getRectangleRangeOfCells(range, spreadsheet);
        }

        return ret;
    }

    private static ArrayList<Cell> getRectangleRangeOfCells(CellLocation[] range, Cell spreadsheet[][]) {
        CellLocation start = range[0];
        CellLocation end = range[1];

        ArrayList<Cell> ret = new ArrayList<Cell>();

        for (int x = 0; x < spreadsheet.length; x++) {
            if(start.getX() <= x && x <= end.getX()) {
                CellLocation[] currentColumn = {
                        new CellLocation(x, start.getY()),
                        new CellLocation(x, end.getY()),
                };
                ret.addAll(getRowOfCells(currentColumn, spreadsheet));
            }
        }
        return ret;
    }

    private static ArrayList<Cell> getColumnOfCells(CellLocation[] range, Cell spreadsheet[][]) {
        CellLocation start = range[0];
        CellLocation end = range[1];

        ArrayList<Cell> ret = new ArrayList<>();

        for (int x = 0; x < spreadsheet.length; x++) {
            if(start.getX() <= x && x <= end.getX() ) {
                ret.add(spreadsheet[x][start.getY()]);
            }
        }

        return ret;
    }

    private static ArrayList<Cell> getRowOfCells(CellLocation[] range, Cell spreadsheet[][]) {
        CellLocation start = range[0];
        CellLocation end = range[1];

        ArrayList<Cell> ret = new ArrayList<>();

        for (int y = 0; y < spreadsheet[start.getX()].length; y++) {
            if(start.getY() <= y && y <= end.getY() ) {
                ret.add(spreadsheet[start.getX()][y]);
            }
        }

        return ret;
    }

    public static CellLocation[] getRangeStartEndIndex(String start, String end) {

        CellLocation rangeIndex[] = new CellLocation[2];
        rangeIndex[0] = new CellLocation(start);
        rangeIndex[1] = new CellLocation(end);

        return rangeIndex;
    }

    //endregion

    //region Comparison


    /**
     * Compares the length of the two strings
     * @param c
     * @return Returns difference of rawValue length
     */
    public int compareTo(Cell c) {
        int ret = 0;

        if(c instanceof DoubleCell) {
            ret = ((DoubleCell) this).compareTo((DoubleCell) c);
        } else if(c instanceof FormulaCell) {
            ret = ((FormulaCell) this).compareTo((FormulaCell) c);
        } else if(c instanceof Cell) {
            int objValueLength = c.getRawValue().length();
            int thisValueLength = this.getRawValue().length();

            ret = thisValueLength - objValueLength;
        }


        return ret;
    }

    public boolean equals(Object o) {
        boolean res = false;

        if(o instanceof Cell) {
            res = this.getRawValue().equals(((Cell) o).getRawValue());
        }

        return res;
    }

    //endregion

}
