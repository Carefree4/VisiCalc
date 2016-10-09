// Max Wenger
// APCS
// Period 1
// VisiCalc

/* -------EXTRA CREDIT-------
 * 1) Documentation (included in folder)
 * 2) Input validation
 * 3) Circular references
 * 4) Sorting text and date (text by length)
 * 5) Operator precedence (MDAS)
 * 6) Range sorting
 * 7) Range SUM and AVG
 */

import java.io.FileNotFoundException;
import java.util.Scanner;

public class VisiCalc {

    private static boolean isActive;
    private static Grid grid;

    /**
     * Stores a list of commands to save and load grids from a file.
     */
    private static String savedValues;

    public static void main(String[] args) throws FileNotFoundException {
        isActive = true;
        grid = new Grid();
        savedValues = "";

        Scanner console = new Scanner(System.in);

        do {
            System.out.print("Enter: ");
            String userInput = console.nextLine();

            System.out.println(processCommand(userInput));

        } while (isActive);
        console.close();
    }

    //region Command Execution
    private static String processCommand(String input) throws FileNotFoundException {
        String print;

        String inputPrefix = extractWord(input, 0);

        if (isNonArgumentativeCommand(input)) {
            print = executeNonArgumentativeCommand(input);
        } else if (isArgumentativeCommand(inputPrefix)) {
            print = executeArgumentativeCommand(input);
        } else {
            print = input;
        }

        return print;
    }

    private static String executeNonArgumentativeCommand(String input) {
        String print = "";

        if (input.equalsIgnoreCase("print")) {
            grid.print();
        } else if (input.equalsIgnoreCase("quit")) {
            isActive = false;
        } else if (input.equalsIgnoreCase("help")) {
            print = "To print the grid, type 'print' \n" +
                    "To quit, type 'quit' \n" +
                    "To load a file, type, you guessed it! 'load {file name/path}'";
        } else if (CellLocation.isCellLocation(input)) {
            print = input + " = " + grid.getCell(new CellLocation(input));
        } else if (input.equalsIgnoreCase("clear")) {
            grid.initSpreadsheet();
            savedValues = "";
        }
        return print;
    }

    private static String executeArgumentativeCommand(String input) throws FileNotFoundException {
        String print = "";
        String inputPrefix = extractWord(input, 0);
        String argument = extractWord(input, 1);

        if (inputPrefix.equalsIgnoreCase("load")) {
            savedValues = processFile(argument);
            loadCommands(savedValues);
        } else if (CellLocation.isCellLocation(inputPrefix)) {
            modifyCell(input);
        } else if (inputPrefix.equalsIgnoreCase("clear")) {
            modifyCell(argument, "");
            savedValues += input + "\n";
        } else if (inputPrefix.equalsIgnoreCase("save")) {
            saveFile(argument);
        } else if (inputPrefix.equalsIgnoreCase("sorta")) {
            // sorta A1 - C3
            grid.sortAscendingByRange(extractWord(input, 1), extractWord(input, 3));
        } else if (inputPrefix.equalsIgnoreCase("sortd")) {
            // sortd A1 - C3
            grid.sortDescendingByRange(extractWord(input, 1), extractWord(input, 3));
        }
        return print;
    }

    //endregion

    //region String Modification
    /**
     * Extracts word from line at specified zero-indexed index
     *
     * @param line
     * @param wordIndex
     * @return Returns the word at the specified index
     */
    private static String extractWord(String line, int wordIndex) {
        String result = "";
        Scanner parse = new Scanner(line);

        for (int i = 0; i <= wordIndex && parse.hasNext(); i++) {
            result = parse.next();
        }

        parse.close();
        return result;
    }

    private static int getNumberOfSpaces(String str) {
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                sum++;
            }
        }
        return sum;
    }
    //endregion

    //region Cell Modification
    private static void modifyCell(String userInput) {
        // Valid user input: [Cell Location] = [Value]; B3 = 5
        if (getNumberOfSpaces(userInput) >= 2) {
            Scanner parse = new Scanner(userInput);

            String location = parse.next();

            // To exclude the '=' symbol
            parse.next();

            String value = parse.nextLine();
            parse.close();

            if (CellLocation.isCellLocation(location)) {
                savedValues += userInput + "\n";
                modifyCell(location, value);
            }
        }
    }

    private static void modifyCell(String location, String value) {
        modifyCell(new CellLocation(location), value);
    }

    private static void modifyCell(CellLocation location, String value) {
    	
    	if(!value.equals("")){
    		value = value.substring(1, value.length());
    	}
    	grid.setCell(location, value);
    }
    //endregion

    //region File Management
    private static String processFile(String filename) throws FileNotFoundException {
        FileAccess fileAccess = new FileAccess(filename);
        return fileAccess.getFileContents();
    }

    private static void saveFile(String fileName) throws FileNotFoundException {
        FileAccess saveFile = new FileAccess(fileName);
        cleanSavedValues();
        saveFile.saveFile(savedValues);
    }

    private static void cleanSavedValues() {

        Scanner sc = new Scanner(savedValues);
        String save = "";
        String[] invalidCommandsForSave = { "save", "load" };
        while(sc.hasNextLine()) {
            String command = sc.nextLine();

            for (String s:invalidCommandsForSave) {
                if(!extractWord(command, 1).equalsIgnoreCase(s)) {
                    save += command + "\n";
                } else {
                    sc.nextLine();
                }
            }

        }
        savedValues = save;
    }

    /**
     * Takes a list of commands separated by carriage returns and runs them as if
     * the user inputted them from the console.
     *
     * @param values
     * @throws FileNotFoundException
     */
    private static void loadCommands(String values) throws FileNotFoundException {
        Scanner parse = new Scanner(values);
        while(parse.hasNextLine()) {
            processCommand(parse.nextLine());
        }
        parse.close();
    }
    //endregion

    //region Validation
    private static boolean isValidCommand(String inputPrefix, String[] validCommands) {
        boolean hasArgument = false;
        for (String command : validCommands) {
            if (inputPrefix.equalsIgnoreCase(command)) {
                hasArgument = true;
            }
        }
        return hasArgument;
    }

    private static boolean isArgumentativeCommand(String inputPrefix) {
        String commandsWithArguments[] = new String[]
                {
                        "load",
                        "clear",
                        "save",
                        "sorta",
                        "sortd"
                };

        return isValidCommand(inputPrefix, commandsWithArguments) || CellLocation.isCellLocation(inputPrefix);
    }

    private static boolean isNonArgumentativeCommand(String inputPrefix) {
        String commandsWithoutArguments[] = new String[]
                {
                        "print",
                        "quit",
                        "help",
                        "clear"
                };

        return isValidCommand(inputPrefix, commandsWithoutArguments) || (CellLocation.isCellLocation(inputPrefix) && !inputPrefix.contains("="));
    }

    //endregion

}
