import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Calendar {
    private int year;
    private List<String> output;
    private YearMonth[] months;

    private static final int dayColumnWidth = 4;
    private static final int monthColumnWidth = 18;
    private static final String whitespace = " ";

    private int currentMonthsRow = 0;

    public Calendar(int year) {
        this.year = year;
        this.months = new YearMonth[12];

        for (int i = 0; i < 12; i++){
            months[i] = YearMonth.of(year, i + 1);

        }
    }

    List<String> generateCalendar(){
        output = new ArrayList<>();
        output.add(getNextMonths());

        return output;
    }

    private String getNextMonths(){
        String output = "";

        //met 4 spaties beginnen.
        output += getWhitespaces(dayColumnWidth);

        //4 maanden per rij
        int startingMonth = currentMonthsRow * 4;
        int lastMonth = startingMonth + 3;
        for (int i = startingMonth; i <= lastMonth; i++){
            output += months[i].getMonth().name();

            int whitespacesToAdd = monthColumnWidth - months[i].getMonth().name().length();
            if (whitespacesToAdd > monthColumnWidth){
                throw new Error("Lengte van String maand groter dan 22. Hier klopt iets niet!");
            } else {
                output += getWhitespaces(whitespacesToAdd);
            }
        }

        currentMonthsRow++;

        return output;
    }

    private String getWhitespaces(int number){
        return String.join("", Collections.nCopies(number, whitespace));
    }
}
