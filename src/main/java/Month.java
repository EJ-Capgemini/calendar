import java.time.DayOfWeek;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Month {
    private YearMonth yearMonthObj;
    private String name;
    private int monthNumber;
    private int numberOfDays;
    private int year;
    private HashMap<DayOfWeek, List<Integer>> dayOfWeekList;
    private int firstWeek;
    private int lastWeek;

    public Month(int year, int number) {
        this.year = year;
        this.monthNumber = number;

        this.yearMonthObj = YearMonth.of(year, number);
        this.numberOfDays = yearMonthObj.lengthOfMonth();
        this.name = yearMonthObj.getMonth().name();

        this.firstWeek = (int)((yearMonthObj.atDay(1).getDayOfYear() -1) / 7) + 1;
        this.lastWeek = (int)((yearMonthObj.atDay(numberOfDays).getDayOfYear() -1) / 7) + 1;
//        System.out.println(number + " " + lastWeek);

        retrieveDaysOfWeek();
    }

    private void retrieveDaysOfWeek(){
        this.dayOfWeekList = new HashMap<>();

        List<Integer> mondays = new ArrayList<>();
        List<Integer> tuesdays = new ArrayList<>();
        List<Integer> wednesdays = new ArrayList<>();
        List<Integer> thursdays = new ArrayList<>();
        List<Integer> fridays = new ArrayList<>();
        List<Integer> saturdays = new ArrayList<>();
        List<Integer> sundays = new ArrayList<>();

        //Er rekening mee houden dat de 1e dag meestal niet op een maandag begint.
        //In zulke gevallen null vooraan plaatsen zodat deze later vervangen kan worden door lege string tijdens output.
        switch (yearMonthObj.atDay(1).getDayOfWeek()){
            case MONDAY:
                break;
            case TUESDAY:
                mondays.add(null);
                break;
            case WEDNESDAY:
                mondays.add(null);
                tuesdays.add(null);
                break;
            case THURSDAY:
                mondays.add(null);
                tuesdays.add(null);
                wednesdays.add(null);
                break;
            case FRIDAY:
                mondays.add(null);
                tuesdays.add(null);
                wednesdays.add(null);
                thursdays.add(null);
                break;
            case SATURDAY:
                mondays.add(null);
                tuesdays.add(null);
                wednesdays.add(null);
                thursdays.add(null);
                fridays.add(null);
                break;
            case SUNDAY:
                mondays.add(null);
                tuesdays.add(null);
                wednesdays.add(null);
                thursdays.add(null);
                fridays.add(null);
                saturdays.add(null);
                break;
        }

        for (int i = 1; i <= numberOfDays; i++) {
            switch(yearMonthObj.atDay(i).getDayOfWeek()){
                case MONDAY:
                    mondays.add(i);
                    break;
                case TUESDAY:
                    tuesdays.add(i);
                    break;
                case WEDNESDAY:
                    wednesdays.add(i);
                    break;
                case THURSDAY:
                    thursdays.add(i);
                    break;
                case FRIDAY:
                    fridays.add(i);
                    break;
                case SATURDAY:
                    saturdays.add(i);
                    break;
                case SUNDAY:
                    sundays.add(i);
                    break;
            }
        }

        dayOfWeekList.put(DayOfWeek.MONDAY, mondays);
        dayOfWeekList.put(DayOfWeek.TUESDAY, tuesdays);
        dayOfWeekList.put(DayOfWeek.WEDNESDAY, wednesdays);
        dayOfWeekList.put(DayOfWeek.THURSDAY, thursdays);
        dayOfWeekList.put(DayOfWeek.FRIDAY, fridays);
        dayOfWeekList.put(DayOfWeek.SATURDAY, saturdays);
        dayOfWeekList.put(DayOfWeek.SUNDAY, sundays);
    }

    public String getName() {
        return name;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public HashMap<DayOfWeek, List<Integer>> getDayOfWeekList() {
        return dayOfWeekList;
    }

    public int getFirstWeek() {
        return firstWeek;
    }

    public int getLastWeek() {
        return lastWeek;
    }
}
