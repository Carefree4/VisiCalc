import junit.framework.TestCase;

/**
 * Created by Max on 3/27/2016.
 */
public class DateCellTest extends TestCase {

    DateCell d = new DateCell("06/27/2000");

    public void testGetMonth() throws Exception {
        assertEquals(6, d.getMonth());
    }

    public void testGetDay() throws Exception {
        assertEquals(27, d.getDay());
    }

    public void testGetYear() throws Exception {
        assertEquals(2000, d.getYear());
    }

    public void testToString() throws Exception {
        assertEquals("6/27/2000", d.toString());
    }

    public void testIsValidCellType() throws Exception {
        assertEquals(true, DateCell.isValidCellType("06/27/2000"));
    }
}