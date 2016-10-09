import junit.framework.TestCase;
import org.junit.Test;

public class DoubleCellTest extends TestCase {

    @Test
    public void testStringToDouble() throws Exception {
        assertEquals(5.25, new DoubleCell(5.25).getValue());
    }
}