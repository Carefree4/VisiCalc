import junit.framework.TestCase;

public class FunctionCellTest extends TestCase {

    public void testGetCurrentValue() throws Exception {
        Cell spreadsheet[][] = {
                /*       A1                   B1         */
                { new DoubleCell(5.25), new DoubleCell(3.75) },
                /*       A2                   B2           */
                { new DoubleCell(11), new DoubleCell(0.0) },
                /*       A3                   B3           */
                { new DoubleCell(486), new FormulaCell("( A1 - A2 )") },
        };


        sums(spreadsheet);
        average(spreadsheet);
    }

    public void testIsValidCellType() throws Exception {
        assertEquals(true, FunctionCell.isValidCellType("( AVG A1 - A9 )"));
        assertEquals(false, FunctionCell.isValidCellType("This should be false"));
    }

    private void sums(Cell[][] spreadsheet) {
        assertEquals("497.0", new FunctionCell("( SUM A2 - A3 )").getCurrentValue(spreadsheet));
        assertEquals("11.0", new FunctionCell("( SUM A2 - B2 )").getCurrentValue(spreadsheet));
        assertEquals("491.0", new FunctionCell("( SUM A2 - B3 )").getCurrentValue(spreadsheet));
    }

    private void average(Cell[][] spreadsheet) {
        assertEquals("248.5", new FunctionCell("( AVG A2 - A3 )").getCurrentValue(spreadsheet));
        assertEquals("5.5", new FunctionCell("( AVG A2 - B2 )").getCurrentValue(spreadsheet));
        assertEquals("122.75", new FunctionCell("( AVG A2 - B3 )").getCurrentValue(spreadsheet));
    }
}