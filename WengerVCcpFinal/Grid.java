// Max Wenger
// APCS
// Period 1
// VisiCalc

import java.util.ArrayList;
import java.util.Collections;

public class Grid {

    private Cell[][] spreadsheet;

    public Grid() {
        initSpreadsheet();
    }

    //region Manipulate Spreadsheet

    public void initSpreadsheet() {

        spreadsheet = new Cell[10][7];

        for (Cell r[] : spreadsheet) {
            for (int i = 0; i < r.length; i++) {
                r[i] = new Cell();
            }
        }

    }

    public void setCell(CellLocation location, String value) {
        if(spreadsheet.length > location.getX() && spreadsheet[0].length > location.getY()){
            setTypeSpecificCell(location, value);
        }
    }

    private void setTypeSpecificCell(CellLocation location, String value) {
        if (DateCell.isValidCellType(value)) { spreadsheet[location.getX()][location.getY()] = new DateCell(value); }
        else if(DoubleCell.isValidCellType(value)) { spreadsheet[location.getX()][location.getY()] = new DoubleCell(MathWrapper.stringToDouble(value)); }
        else if(FunctionCell.isValidCellType(value)) { spreadsheet[location.getX()][location.getY()] = new FunctionCell(value); }
        else if(FormulaCell.isValidCellType(value)) { spreadsheet[location.getX()][location.getY()] = new FormulaCell(value); }
        else { spreadsheet[location.getX()][location.getY()] = new Cell(value); }
    }

    public Cell getCell(CellLocation location){
        return spreadsheet[location.getX()][location.getY()];
    }

    public void sortAscendingByRange(String startRange, String endRange) {
        sortAscendingByRange(Cell.getRangeStartEndIndex(startRange, endRange));
    }

    public void sortAscendingByRange(CellLocation[] range) {
        ArrayList<Cell> toSort = Cell.getCellRange(range, spreadsheet);
        Collections.sort(toSort);

        injectIntoSpreadsheet(range, toSort);
        
    }

    public void sortDescendingByRange(String startRange, String endRange) {
        sortDescendingByRange(Cell.getRangeStartEndIndex(startRange, endRange));
    }

    public void sortDescendingByRange(CellLocation[] range) {
        ArrayList<Cell> toSort = Cell.getCellRange(range, spreadsheet);
        Collections.sort(toSort, Collections.reverseOrder());

        injectIntoSpreadsheet(range, toSort);
    }

    private void injectIntoSpreadsheet(CellLocation[] range, ArrayList<Cell> toSort) {
        int sortedIndex = 0;
        for (int x = 0; x < spreadsheet.length; x++) {
            for (int y = 0; y < spreadsheet[x].length; y++) {
                if(isInRange(range, x, y)) {
                    spreadsheet[x][y] = toSort.get(sortedIndex++);
                }
            }
        }
    }

    private boolean isInRange(CellLocation[] range, int x, int y) {
        return range[0].getX() <= x && x <= range[1].getX()
                && range[0].getY() <= y && y <= range[1].getY();
    }


    //endregion

    //region Print Grid

    public void print() {

        int lengthOfLine = 75;

        System.out.println(createFirstLine(lengthOfLine));

        for (int row = 0; row < spreadsheet.length; row++) {
            System.out.println(createHorizontalLine(lengthOfLine));
            createRow(row);
        }
        System.out.println(createHorizontalLine(lengthOfLine) + "\n");
    }

    private String createFirstLine(int lengthOfLine) {
        String line = "";
        int column = 0;

        for (int i = 0; i <= lengthOfLine; i++) {
            if(i % 10 == 0 && i > 0) {
                line += CellLocation.letterFromNumber(column);
                column++;
            }
            else if(i % 10 == 4) { line += "|"; }
            else { line += " "; }
        }
        
        return line;
    }

    private String createHorizontalLine(int lengthOfLine) {
        String line = "";

        for (int i = 1; i <= lengthOfLine; i++) {
            if(i % 10 == 5) { line += "+"; }
                else { line += "-"; }
        }

        return line;
    }

    private String createRow(int lineNumber) {
        // TODO: Make it return the row as a string instead of printing it

        String row = "";
        System.out.printf("%4d|", lineNumber + 1);

        for (Cell c : spreadsheet[lineNumber]) {
            String cellText;

            cellText = getCellValue(c);

            if(c.toString().length() >= 10) { cellText = c.toString().substring(1, 10); }

            System.out.printf("%9s|", cellText);
        }

        System.out.println();
        return row;
    }

    private String getCellValue(Cell c) {
        String cellText;
        if(c instanceof FormulaCell) {
            ((FormulaCell) c).getCurrentValue(spreadsheet);
            cellText = ((FormulaCell) c).getLastValue();
        } else {
            cellText = c.toString();
        }
        return cellText;
    }

    //endregion

}
