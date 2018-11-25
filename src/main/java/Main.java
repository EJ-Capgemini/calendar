import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Path path = Paths.get("output/calendars");
    private static final int minYear = 1970;
    private static final int maxYear = 2100;

    public static void main(String[] args) {
        int year = askYear();
        getCalendar(year);
    }

    private static int askYear(){
        System.out.println("Good day, for what year shall we generate a calendar?");
        Scanner scanner = new Scanner(System.in);
        int year = minYear;
        boolean isValidYear = false;

        while (!isValidYear){
            if (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.printf("\"%s\" is not a valid number.\n", input);
            } else {
                year = scanner.nextInt();
                if(year < minYear || year > maxYear){
                    System.out.printf(String.format("\"%%s\" is not a valid year. Please enter a number between %d and %d.\n", minYear, maxYear), year);
                } else {
                    isValidYear = true;
                }
            }
        }

        return year;
    }

    private static void getCalendar(int year){
        String filePath = path.toString() + "/" + year + ".txt";

//        NoSuchFileException voorkomen.
        if (Files.notExists(path)) {
            System.out.printf("New ../%s folder has been created, because it did not exist.%n", path.toString());
            new File(path.toString()).mkdirs();
        }

        List<String> lines = new Calendar(year).generateCalendar();
        Path file = Paths.get(filePath);

        try {
            //Indien het bestand al bestond wordt deze herschreven.
            Files.write(file, lines, Charset.forName("UTF-8"));
            openFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        Poging om gegenereerde file te openen.
     */
    private static void openFile(String filePath){
        File file = new File(filePath);

        //Indien bestand niet geopend kan worden melding weergaven waar de gebruiker het kan terugvinden.
        if(!Desktop.isDesktopSupported()){
            System.out.println("Calendar has been succesfully created. Check " + filePath + " for the result.");
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        if(file.exists()) {
            try {
                desktop.open(file);
                System.out.println("Calendar has been succesfully created. Till next time..");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
