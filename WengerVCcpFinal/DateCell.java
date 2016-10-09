// Max Wenger
// APCS
// Period 1
// VisiCalc

import java.util.Scanner;

// Do not use this class after the year 2147483647
public class DateCell extends Cell implements Comparable<Cell> {

    private int month;
    private int day;
    private int year;

    public DateCell(String value) { setDate(value); }

    //region Field Encapsulation

    public int getMonth() { return month; }

    public void setMonth(int month) { this.month = month; }

    public int getDay() { return day; }

    public void setDay(int day) { this.day = day; }

    public int getYear() { return year; }

    public void setYear(int year) { this.year = year; }

    public String toString() {
        return this.month + "/" + this.day + "/" + this.year;
    }

    public void setDate(String value) {
        if(isValidCellType(value)) { parseStringToDate(value); }
    }

    //endregion

    /**
     * Parses through a string and sets the numeric month, day, and year, to this objects
     * corresponding member variables.
     *
     * @param date the string value of the date, represented as MM/DD/YYYY
     */
    private void parseStringToDate(String date) {
        if(isValidCellType(date)) {
            Scanner dateFormatter = new Scanner(replaceDelimiterWithSpace(date, '/'));
            this.month = dateFormatter.nextInt();
            this.day = dateFormatter.nextInt();
            this.year = dateFormatter.nextInt();
            dateFormatter.close();
        }
    }

    public static boolean isValidCellType(String value) {
        boolean result = true;

        if(value.length() == 10) {
            Scanner dateFormatter = new Scanner(replaceDelimiterWithSpace(value, '/'));

            if (dateFormatter.hasNextInt()) {
                dateFormatter.nextInt();
            } else {
                result = false;
            }

            if (dateFormatter.hasNextInt()) {
                dateFormatter.nextInt();
            } else {
                result = false;
            }

            if (dateFormatter.hasNextInt()) {
                dateFormatter.nextInt();
            } else {
                result = false;
            }

            dateFormatter.close();
        } else {
            result = false;
        }

        return result;
    }

    private static String replaceDelimiterWithSpace(String input, char delimiter) {
        String result = "";
        for (int i = 0; i < input.length(); i++) {
            if(input.charAt(i) != delimiter){ result += input.charAt(i); }
            else { result += " "; }
        }
        return result;
    }

    //region Comparison
    public int compareTo(DateCell o) {
        int result = -1;
        if(o instanceof DateCell) {
            result = isGreater(this.year, o.getYear());

            if (result == 0) {
                result = isGreater(this.month, o.getMonth());
                if (result == -1) {
                    result = isGreater(this.day, o.getDay());
                }
            }
        }

        return result;
    }

    /**
     * Similar to the compareTo method.
     *
     * @param i
     * @param j
     * @return if i is greater then j, return 1, if not, return -1. If equal, return 0.
     */
    private int isGreater(int i, int j) {
        int result = 0;
        if(i > j){ result = 1; }
        else if (i < j){ result = -1; }

        return result;
    }
    //endregion

}
