// Max Wenger
// APCS
// Period 1
// VisiCalc

public class DoubleCell extends Cell implements Comparable<Cell> {

    public DoubleCell(double doubleValue) { setRawValue(doubleValue); }

    //region Field Encapsulation
    public void setRawValue(double doubleValue) { super.setRawValue(doubleValue + ""); }

    /**
     * Takes rawValue from super and turns it into a double
     *
     * @return
     */
    public double getValue() {
        return MathWrapper.stringToDouble(super.getRawValue());
    }

    public String toString() {
        return getValue() + "";
    }
    //endregion

    /**
     * Checks each char in the String, returning false if any char in the String does not match '0..9' or '.'
     *
     * @param value String that will be checked if it is a double value
     * @return true if value is double
     */
    public static boolean isValidCellType(String value) {
        return MathWrapper.isDouble(value);
    }

    public int compareTo(DoubleCell c) {
        return (int)(this.getValue() - c.getValue());
    }

}
