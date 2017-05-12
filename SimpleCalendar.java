

/**
 *
 * @author X1 User
 */
public class SimpleCalendar {
    
    public static void main(String[] args){
    CalendarModel cm = new CalendarModel();
    
    //Event e1 = new Event("running", "05/09/2017", 1200, 1300);
    //Event e2 = new Event("date with belle", "05/10/2017", 1200, 1400);
    //cm.getEventList().add(e1);
    //cm.getEventList().add(e2);
    
    DayViewPanel dvp = new DayViewPanel(cm);
    CalendarPanel cp = new CalendarPanel(cm);
    
    cm.attach(dvp);
    cm.attach(cp);
    
   
    CalendarFrame f = new CalendarFrame(cp, dvp);
    cm.attach(f);
            }
    
}
