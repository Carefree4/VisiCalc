// Max Wenger
// APCS
// Period 1
// VisiCalc

public class CellLocation {
    private int x;
    private int y;

    //region Constructors
    public CellLocation(String cellLocation) {
        if(isCellLocation(cellLocation)) {
            setLocationToCoordinates(cellLocation);
        } else {
            this.x = -1;
            this.y = -1;
        }
    }

    public CellLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //endregion

    //region Field Encapsulation
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    private void setLocationToCoordinates(String location) {
        this.x = new Integer(location.substring(1, location.length())) - 1;
        this.y = numberFromLetter(location.charAt(0));
    }
    //endregion

    //region Letter Number Conversion
    public static char letterFromNumber(int n) {
        n = Character.toLowerCase(n);
        if(n == 0) { return 'A'; }
        else if(n == 1) { return 'B'; }
        else if(n == 2) { return 'C'; }
        else if(n == 3) { return 'D'; }
        else if(n == 4) { return 'E'; }
        else if(n == 5) { return 'F'; }
        else if(n == 6) { return 'G'; }
        else { return '\u0000'; }
    }

    public static int numberFromLetter(char l) {
        l = Character.toUpperCase(l);
        if (l == 'A') { return 0; }
        else if (l == 'B') { return 1; }
        else if (l == 'C') { return 2; }
        else if (l == 'D') { return 3; }
        else if (l == 'E') { return 4; }
        else if (l == 'F') { return 5; }
        else if (l == 'G') { return 6;}
        else { return -1; }
    }
    //endregion

    //region Validation
    private static boolean isYCoordinate(char c) {
        return isYCoordinate(c + "");
    }

    private static boolean isYCoordinate(String str) {
        String[] n = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        boolean result = false;

        for (String s : n) {
            if (str.equalsIgnoreCase(s)) {
                result = true;
            }
        }

        return result;
    }

    public static boolean isCellLocation(String str) {
        // example of str: 'B3'
        boolean result = false;

        if (str.length() >= 2) {
            if (numberFromLetter(str.charAt(0)) != -1 && isYCoordinate(str.charAt(1))) {
                result = true;
            }
        }

        return result;
    }
    //endregion
}
