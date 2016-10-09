// Max Wenger
// APCS
// Period 1
// VisiCalc

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class FileAccess {
    private File file;

    public FileAccess(String path) {
        file = new File(path);
    }

    public String getFileContents() throws FileNotFoundException {
        Scanner parseFile = new Scanner(file);

        String fileContents = "";
        while(parseFile.hasNext()) {
            fileContents += parseFile.nextLine() + "\n";
        }
        
        parseFile.close();
        return fileContents;
    }

    public void saveFile(String values) throws FileNotFoundException {
        PrintStream printStream = new PrintStream(file);
        printStream.print(values);
    }

}
