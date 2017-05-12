
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/**
 *
 * @author X1 User
 */
public class CalendarModel {

    /**
     * Array of months
     */
    public static String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    /**
     * Array of days of the week (abbreviated)
     */
    public static String[] wDays = {"S", "M", "T", "W", "T", "F", "S"};

    /**
     * Array of days of week (full titles)
     */
    public static String[] wDaysFull = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    private GregorianCalendar gc = new GregorianCalendar();
    private ArrayList<ChangeListener> listeners = new ArrayList<>();
    private int mDays;
    private int currentDay;
    private ArrayList<Event> eventList;
    private GregorianCalendar temp = new GregorianCalendar();

    public CalendarModel() {
        mDays = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
        currentDay = gc.get(Calendar.DATE);
        eventList = new ArrayList<Event>();
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public void attach(ChangeListener l) {
        listeners.add(l);
    }

    public void update() {
        for (ChangeListener l : listeners) {
            l.stateChanged(new ChangeEvent(this));
        }
    }

    public void nextMonth() {
        gc.add(Calendar.MONTH, 1);
        mDays = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
        //monthChanged = true;
        update();
    }

    /**
     * Moves the calendar backward by one month.
     */
    public void updateMDays(){
                mDays = gc.getActualMaximum(Calendar.DAY_OF_MONTH);

    }
    
    public void prevMonth() {
        gc.add(Calendar.MONTH, -1);
        mDays = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
        //maxDays = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
        //monthChanged = true;
        update();
    }

    public GregorianCalendar getGC() {
        return gc;
    }

    public int getMDays() {
        return mDays;
    }

    public String getMonthName() {
        return months[gc.get(Calendar.MONTH)];
    }

    public String getYear() {
        return Integer.toString(gc.get(Calendar.YEAR));
    }

    public int getMonthInt() {
        return gc.get(Calendar.MONTH) + 1;
    }

    public int getYearInt() {
        return gc.get(Calendar.YEAR);
    }

    public int getDayInt() {
        return gc.get(Calendar.DATE);
    }

    public int getDayOfWeekOfFirst() {
        temp = (GregorianCalendar) gc.clone();
        temp.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), 1);
        return temp.get(Calendar.DAY_OF_WEEK);
    }
    
    public boolean hasEvent(int i){
        for (Event e : eventList){
            if (e.getYear() == getYearInt() && e.getMonth() == getMonthInt() && e.getDay() == i){
                
                return true;
            }
        }
        return false;
    }
}
