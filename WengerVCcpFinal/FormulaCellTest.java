import junit.framework.TestCase;
import org.junit.Test;

public class FormulaCellTest extends TestCase {

    @Test
    public void testGetValue() throws Exception {
        Cell spreadsheet[][] = {
                /*       A1                   B1         */
                { new DoubleCell(5.25), new DoubleCell(3.75) },
                /*       A2                   B2           */
                { new DoubleCell(11), new DoubleCell(0.0) },
                /*       A3                   B3           */
                { new DoubleCell(486), new FormulaCell("( A1 - A2 )") },
        };

        simpleLiteralOperation(spreadsheet);

        simpleCellOperation(spreadsheet);

        PEMDAS(spreadsheet);

        numbersAndCellOperation(spreadsheet);

        expectedErrors(spreadsheet);
    }

    private void expectedErrors(Cell[][] spreadsheet) {
        assertEquals("NaN" ,new FormulaCell("( 5 / 0 )").getCurrentValue(spreadsheet));
        assertEquals("NaN" ,new FormulaCell("( Hello World )").getCurrentValue(spreadsheet));
    }

    private void numbersAndCellOperation(Cell[][] spreadsheet) {
        assertEquals("78.75" ,new FormulaCell("( 15 * A1 )").getCurrentValue(spreadsheet));
    }

    private void PEMDAS(Cell[][] spreadsheet) {
        assertEquals("1.0" ,new FormulaCell("( 4 / 2 + 9 - 2 * 5 )").getCurrentValue(spreadsheet));
    }

    private void simpleCellOperation(Cell[][] spreadsheet) {
        assertEquals("9.0", new FormulaCell("( A1 + B1 )").getCurrentValue(spreadsheet));
        assertEquals("1.5", new FormulaCell("( A1 - B1 )").getCurrentValue(spreadsheet));
        assertEquals("19.6875", new FormulaCell("( A1 * B1 )").getCurrentValue(spreadsheet));
        assertEquals("1.4", new FormulaCell("( A1 / B1 )").getCurrentValue(spreadsheet));
    }

    private void simpleLiteralOperation(Cell[][] spreadsheet) {
        assertEquals("4.0", new FormulaCell("( 1.5 + 2.5 )").getCurrentValue(spreadsheet));
        assertEquals("5.0", new FormulaCell("( 11 - 6 )").getCurrentValue(spreadsheet));
        assertEquals("10.0", new FormulaCell("( 100 * 0.1 )").getCurrentValue(spreadsheet));
        assertEquals("20.0", new FormulaCell("( 60 / 3 )").getCurrentValue(spreadsheet));
    }

    @Test
    public void testIsValidCellType() throws Exception {
        assertEquals(true, FormulaCell.isValidCellType("( A1 + 6 )"));
        assertEquals(false, FormulaCell.isValidCellType("Not a formula"));
    }
}