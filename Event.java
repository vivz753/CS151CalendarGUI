

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

/**
 *
 * @author Vivian Leung
 */
public class Event implements Comparable<Event> {

    private String title;
    private String date;
    private int start;
    private int end;
    private GregorianCalendar c;

    /**
     * Constructor for Event to put into calendar
     *
     * @param theTitle the title of the event
     * @param theDate the date of the event in MM/DD/YYYY
     * @param theStart the start time of the event
     * @param theEnd the end time of the event
     */
    public Event(String theTitle, String theDate, int theStart, int theEnd) {
        title = theTitle;
        date = theDate;
        start = theStart;
        end = theEnd;
        GregorianCalendar c = new GregorianCalendar(this.getYear(), this.getMonth() - 1, this.getDay());
        this.c = c;

    }

    public String toString() {
        return title + " at " + start + " - " + end;
    }

    /**
     * return the calendar attached with the event
     *
     * @return GregorianCalendar the event's calendar
     */
    public GregorianCalendar getCalendar() {
        return c;
    }

    /**
     * return the title of the event
     *
     * @return String the title
     */
    public String getTitle() {
        return title;

    }

    /**
     * return the date input
     *
     * @return String the date in MM/DD/YYYY format
     */
    public String getDate() {
        return date;

    }

    /**
     * return the year of the event
     *
     * @return int the year
     */
    public int getYear() {
        String year = date.substring(6, 10);
        return Integer.parseInt(year);
    }

    /**
     * return the month number
     *
     * @return int the month number
     */
    public int getMonth() {
        String month = date.substring(0, 2);
        if (month.substring(0, 1).equals("0")) {
            month = date.substring(1, 2);
        }
        int x = Integer.parseInt(month);
        return x;
    }

    /**
     * return the day of the event
     *
     * @return int the day of the month of the event
     */
    public int getDay() {

        String day = date.substring(3, 5);
        if (day.substring(0, 1).equals("0")) {
            day = date.substring(4, 5);
        }
        int x = Integer.parseInt(day);
        return x;
    }

    /**
     * return the day of week of the event
     *
     * @return int the day of the week
     */
    public int getDayOfWeek() {
        return this.getCalendar().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * return the start time of the event
     *
     * @return int the start time
     */
    public int getStart() {
        return start;
    }

    /**
     * return the end time of the event
     *
     * @return int the end time
     */
    public int getEnd() {
        return end;
    }

    /**
     * main method to test out events
     *
     * @param args
     */
    public static void main(String[] args) {
        Event e0 = new Event("pooping", "03/22/2017", 8800, 800);
        Event e1 = new Event("pooping", "03/22/2017", 7700, 800);
        Event e2 = new Event("pooping", "03/22/2017", 1200, 800);
        Event e3 = new Event("pooping", "03/22/2017", 1100, 800);
        ArrayList<Event> list = new ArrayList<>();
        list.add(e0);
        list.add(e1);
        list.add(e2);
        list.add(e3);
        Collections.sort(list);
        for (Event e : list) {
            System.out.println(e.getDayOfWeek() + " " + e.getMonth() + " " + e.getDay() + " " + e.getYear() + " " + e.getTitle() + " " + e.getStart() + " " + e.getEnd());

        }

    }

    //compareTo method to sort the events in a Collection
    public int compareTo(Event that) {
        int num = 0;
        if (this.getYear() != that.getYear()) {
            num = this.getYear() - that.getYear();
        } else if (this.getMonth() != that.getMonth()) {
            num = this.getMonth() - that.getMonth();
        } else if (this.getDay() != that.getDay()) {
            num = this.getDay() - that.getDay();
        } else {
            num = this.getStart() - that.getStart();
        }

        return num;
    }

    public boolean hasConflictWith(Event e) {
        if ((e.getEnd() > this.getStart() && e.getEnd() <= this.getEnd()) || (e.getStart() < this.getEnd() && e.getStart() >= this.getStart())) {
            return true;
        } else {
            return false;
        }
    }
    
    public ArrayList<Event> returnEventsOnSameDay(ArrayList<Event> list){
        ArrayList<Event> newList = new ArrayList<>();
        for(Event sameDayEvent : list){
            if(sameDayEvent.date.equals(this.date)){
                newList.add(sameDayEvent);
                System.out.println(sameDayEvent);
            }
        }
        return newList;
    }
    
    public String returnFileInfo(){
                String info = (this.getTitle() + "," + this.getDate() + "," + this.getStart() + "," + this.getEnd());
return info;
    }
}
