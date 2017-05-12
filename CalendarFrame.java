

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author X1 User
 */
public class CalendarFrame extends JFrame implements ChangeListener {
    private CalendarPanel cp;
    private DayViewPanel dvp;

    public CalendarFrame(CalendarPanel cp, DayViewPanel dvp){
        setLayout(new BorderLayout());
        
        this.cp = cp;
        this.dvp = dvp;
        add(cp, BorderLayout.CENTER);
        add(dvp, BorderLayout.EAST);
//        add(cp.monthLabel, BorderLayout.NORTH);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
                
cp.createDays();
repaint();
    }
        
        
}
