import java.time.DayOfWeek;
import java.util.*;

public class Calendar {
    private int year;
    private List<String> output;
    private Month[] months;

    private static final int dayColumnWidth = 4;
    private static final int monthColumnWidth = 18;
    private static final String whitespace = " ";

    private int currentMonth = 0;
    private int monthsPerLine = 5;
    private int weekNumbers = 1;

    public Calendar(int year) {
        this.year = year;
        this.months = new Month[12];

        for (int i = 0; i < 12; i++){
            months[i] = new Month(year, i + 1);
        }
    }

    List<String> generateCalendar(){
        output = new ArrayList<>();
        for (int i = 0; i < 12; i = i + monthsPerLine) {
            output.addAll(getNextMonths());
        }
        return output;
    }

    private List<String> getNextMonths(){
        List<String> output = new ArrayList<>();

        //Maanden tonen
        String outputLine = getWhitespaces(dayColumnWidth);
        int lastMonth = currentMonth + monthsPerLine < 12 ? currentMonth + monthsPerLine : 12;
        for (int i = currentMonth; i < lastMonth; i++){
            outputLine += months[i].getName();

            int whitespacesToAdd = monthColumnWidth - months[i].getName().length();
            if (whitespacesToAdd > monthColumnWidth){
                throw new Error("Lengte van String maand groter dan " + monthColumnWidth + ". Hier klopt iets niet!");
            } else {
                outputLine += getWhitespaces(whitespacesToAdd);
            }
        }
        output.add(outputLine);

        //dagen tonen
        for (int i = 0; i < DayOfWeek.values().length; i++) {
            outputLine = DayOfWeek.of(i + 1).name().substring(0, 2);
            outputLine += getWhitespaces(2);
            for (int y = currentMonth; y < lastMonth; y++){
                outputLine += getDayNumbers(y, DayOfWeek.of(i + 1));
                int curLength = outputLine.length();
                int desiredLength = dayColumnWidth + monthColumnWidth * (y % monthsPerLine + 1);
                outputLine += getWhitespaces(desiredLength - curLength);
            }
            output.add(outputLine);
        }

        //weeknummers
        outputLine = getWhitespaces(dayColumnWidth);
        for (int y = currentMonth; y < lastMonth; y++){
            int start = months[y].getFirstWeek();
            int end = months[y].getLastWeek();
            for (int i = start; i <= end; i++) {
                if (i > 52){
                    outputLine += getWhitespaces(1) + 1;
                }
                else if((i + "").length() == 1){
                    outputLine += getWhitespaces(1) + i;
                } else {
                    outputLine += i;
                }
                outputLine += getWhitespaces(1);
            }
            int curLength = outputLine.length();
            int desiredLength = dayColumnWidth + monthColumnWidth * (y % monthsPerLine + 1);
            outputLine += getWhitespaces(desiredLength - curLength);
        }
        output.add(outputLine);
        output.add("");
        currentMonth += monthsPerLine;

        return output;
    }

    //voor parameter maand, en enum dag een lijst met array teruggeven.
    private String getDayNumbers(int monthNumber, DayOfWeek day){
        List<Integer> days = months[monthNumber].getDayOfWeekList().get(day);
        String outputLine = "";
        for (Integer integer : days) {
            if (integer == null){
                outputLine += getWhitespaces(2);
            } else if (integer.toString().length() == 1){
                outputLine += getWhitespaces(1) + integer;
            } else {
                outputLine += integer;
            }
            outputLine += getWhitespaces(1);
        }
        return outputLine;
    }

    //Altijd gebruik maken van deze functie wanneer lege spaties nodig zijn. Zelfs als het om 1 spatie gaat.
    private String getWhitespaces(int number){
        return String.join("", Collections.nCopies(number, whitespace));
    }
}
