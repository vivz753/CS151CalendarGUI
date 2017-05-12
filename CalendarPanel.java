
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author X1 User
 */
public class CalendarPanel extends JPanel implements ChangeListener {

    private Border regular = new LineBorder(Color.BLACK, 0);
    private Border highlight = new LineBorder(Color.ORANGE, 1);
    private CalendarModel cm;
    private ArrayList<JButton> dayButtons = new ArrayList<>();
    private JPanel dayGridPanel = new JPanel();
    private JPanel monthPanel = new JPanel();
    private JTextPane dayPane = new JTextPane();
    private int mDays;
    public JLabel monthLabel = new JLabel();
    private JLabel weekLabel = new JLabel();
    private JPanel topPanel = new JPanel();
    private JButton previous = new JButton();
    private JButton next = new JButton();
//private int currentButton;

    public CalendarPanel(CalendarModel model) {
        super();
        this.cm = model;
        mDays = cm.getMDays();
createPreviousNextButtons();
        //dayPane.setPreferredSize(new Dimension(300, 150));
        //dayPane.setEditable(false);
        createDays();
        add(createMonthPanel());
        //add(createDayView());
        setLayout(new FlowLayout());
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //pack();
        //setVisible(true);

    }

    public void createPreviousNextButtons(){
          previous.setText("<<");
        next.setText(">>");

        previous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                cm.getGC().set(cm.getYearInt(), cm.getMonthInt() - 1, cm.getDayInt() - 1);
                cm.prevMonth();
            }
        });

        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                cm.getGC().set(cm.getYearInt(), cm.getMonthInt() - 1, cm.getDayInt() + 1);
                cm.nextMonth();
            }
        });
    }
    
    public JPanel createDayView() {
        JPanel dayView = new JPanel();
        dayView.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        JScrollPane scroll = new JScrollPane(dayView);
        scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        //dayView.add(scroll, c);

        return dayView;
    }

    public JPanel createMonthPanel() {

        dayButtons.clear();
        mDays = cm.getMDays();
        String title = cm.getMonthName() + " " + cm.getYear();
        monthLabel.setText("           " + title);
        weekLabel.setText("     S        M        T         W        T         F         S");

        topPanel.removeAll();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(monthLabel, BorderLayout.CENTER);
        topPanel.add(previous, BorderLayout.WEST);
        topPanel.add(next, BorderLayout.EAST);

        monthPanel.setLayout(new BorderLayout());
        monthPanel.add(topPanel, BorderLayout.NORTH);
        // monthPanel.add(monthLabel, BorderLayout.NORTH);
        monthPanel.add(weekLabel, BorderLayout.CENTER);
        // monthPanel.add(previous, BorderLayout.NORTH);
        //monthPanel.add(next, BorderLayout.NORTH);
        monthPanel.add(createDays(), BorderLayout.SOUTH);
        
        //monthPanel.add(dayGridPanel, BorderLayout.SOUTH);

        return monthPanel;
    }

    public JPanel createDays() {
        dayButtons.clear();
        dayGridPanel.removeAll();
        dayGridPanel.setLayout(new GridLayout(0, 7));

        for (int i = 1; i <= mDays; i++) {
            JButton day = new JButton(Integer.toString(i));
            final int date = i;
            day.setBackground(Color.WHITE);
dayButtons.add(day);
            day.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    highlightButton(date);
                    //System.out.println(cm.getYearInt() + " " + cm.getMonthInt() + " " + date);
                    cm.getGC().set(cm.getYearInt(), cm.getMonthInt() - 1, date);
                    cm.update();
                }
            });
            
        }

//add filler buttons
        for (int j = 1; j < cm.getDayOfWeekOfFirst(); j++) {
            JButton filler = new JButton();
            filler.setEnabled(false);
            dayGridPanel.add(filler);
        }

        //add day buttons
        for (JButton b : dayButtons) {
            int buttonNumber = Integer.parseInt(b.getText());       
                        
            if (buttonNumber == (cm.getDayInt())) {
                b.setBorder(highlight);
            }
            
            else if(cm.hasEvent(buttonNumber)){
            b.setBorder(new LineBorder(Color.BLUE, 1 ));
        }
            
            else {b.setBorder(regular);
            
            }
            dayGridPanel.add(b);
        }

        return dayGridPanel;
    }

    public void highlightButton(int i) {

        for (JButton b : dayButtons) {
            b.setBorder(regular);
        }
        dayButtons.get(i-1).setBorder(highlight);

    }

    @Override
    public void stateChanged(ChangeEvent e) {

        //monthPanel = createMonthPanel();
        //createDays();
        
        createMonthPanel();
        repaint();
        // monthPanel.repaint();

    }

}
