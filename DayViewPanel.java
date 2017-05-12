

import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/**
 *
 * @author X1 User
 */
public class DayViewPanel extends JPanel implements ChangeListener {

    private CalendarModel cm;
    private int mDays;
//    public JLabel monthLabel = new JLabel();
//    private JLabel weekLabel = new JLabel();
    //private JPanel topPanel = new JPanel();
    private JTextArea dayText = new JTextArea(20, 15);
    private JPanel dayPanel = new JPanel();
    private String MMDDYY;
    private boolean timeConflict;
    private ArrayList<Event> sameDayEvents;
    private JList<Event> dayJList;
    private JScrollPane eventPane = new JScrollPane();
    private DefaultListModel listModel = new DefaultListModel<>();

    @Override
    public void stateChanged(ChangeEvent e) {
        //createDayView();
        createDayPanel();
        //createList();

        //eventPane = createList();
        //eventPane.repaint();
        //dayPanel.repaint();
        //eventPane.revalidate();
        //dayPanel.revalidate();
        revalidate();
        //eventPane.repaint();
        //dayPanel.repaint();
        repaint();
        //System.out.println("changed");
    }

    public JScrollPane createList() {
        cm.updateMDays();
        if (cm.getMonthInt() < 10 && cm.getDayInt() < 10) {
            MMDDYY = "0" + cm.getMonthInt() + "/" + "0" + cm.getDayInt() + "/" + cm.getYear();

        } else if (cm.getMonthInt() < 10) {
            MMDDYY = "0" + cm.getMonthInt() + "/" + cm.getDayInt() + "/" + cm.getYear();

        } else if (cm.getDayInt() < 10) {
            MMDDYY = cm.getMonthInt() + "/" + "0" + cm.getDayInt() + "/" + cm.getYear();

        } else {
            MMDDYY = cm.getMonthInt() + "/" + cm.getDayInt() + "/" + cm.getYear();
        }

        listModel.clear();
        listModel.add(0,MMDDYY);

        int i =1; 
        for (Event e : cm.getEventList()) {
            // System.out.println(cm.getYearInt() + cm.getMonthInt() + cm.getDayInt());

            if (cm.getYearInt() == e.getYear() && cm.getMonthInt() == e.getMonth() && cm.getDayInt() == e.getDay()) {
                //listModel.add(i, e);
                listModel.addElement(e);
                //System.out.println(e + "added");

            }
            i++;
        }
        //eventPane.removeAll();

        dayJList = new JList(listModel);
        //dayJList.setVisible(true);
        //eventPane = new JScrollPane(dayJList);
        //JScrollPane scroll = new JScrollPane(dayJList);
        eventPane = new JScrollPane(dayJList);

        //eventPane.add(dayJList);
        //scroll.add(new JLabel("MMDDYY"));
        eventPane.setVisible(true);
        //eventPane = scroll;
        //scroll.setVisible(true);
        //return scroll;
        return eventPane;

//        dayText.setText("");
//        dayText.setText(MMDDYY);
//        for (Event e : cm.getEventList()) {
//            //System.out.println(e.toString());
//            //System.out.println(e.getYear() + " " + e.getMonth() + " " + e.getDay());
//            //System.out.println(cm.getYearInt() + " " + cm.getMonthInt() + " " + cm.getDayInt());
//            if (cm.getYearInt() == e.getYear() && cm.getMonthInt() == e.getMonth() && cm.getDayInt() == e.getDay()) {
//                dayText.append("\n" + e.toString());
//                // System.out.println("correct day");
//            }
//        }
//        return dayText;
    }

    public JTextArea createDayView() {
        if (cm.getMonthInt() < 10 && cm.getDayInt() < 10) {
            MMDDYY = "0" + cm.getMonthInt() + "/" + "0" + cm.getDayInt() + "/" + cm.getYear();

        } else if (cm.getMonthInt() < 10) {
            MMDDYY = "0" + cm.getMonthInt() + "/" + cm.getDayInt() + "/" + cm.getYear();

        } else if (cm.getDayInt() < 10) {
            MMDDYY = cm.getMonthInt() + "/" + "0" + cm.getDayInt() + "/" + cm.getYear();

        } else {
            MMDDYY = cm.getMonthInt() + "/" + cm.getDayInt() + "/" + cm.getYear();
        }
        dayText.setText("");
        dayText.setText(MMDDYY);
        for (Event e : cm.getEventList()) {
            //System.out.println(e.toString());
            //System.out.println(e.getYear() + " " + e.getMonth() + " " + e.getDay());
            //System.out.println(cm.getYearInt() + " " + cm.getMonthInt() + " " + cm.getDayInt());
            if (cm.getYearInt() == e.getYear() && cm.getMonthInt() == e.getMonth() && cm.getDayInt() == e.getDay()) {
                dayText.append("\n" + e.toString());
                // System.out.println("correct day");
            }
        }
        return dayText;
    }

    public DayViewPanel(CalendarModel cm) {
        this.cm = cm;
        readEventsFromTextFile();
        setLayout(new FlowLayout());
        add(createDayPanel());
    }

    public void readEventsFromTextFile() {
        try {
            FileReader fr = new FileReader("events.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String title, date;
            int start, end;
            while ((line = br.readLine()) != null) {
                String[] array = line.split("\\,");
                title = array[0];
                date = array[1];
                start = Integer.parseInt(array[2]);
                end = Integer.parseInt(array[3]);
                Event event = new Event(title, date, start, end);
                cm.getEventList().add(event);
                //System.out.println(event + " added");
            }

            br.close();
            fr.close();
            System.out.println("Events successfully loaded.");

        } catch (IOException x) {
            System.out.println("No file to load from. Create some events, then quit to save the file. Then try again.");

        }
    }

    public void saveEventsToTextFile() {

        try {
            FileWriter fw = new FileWriter("events.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (Event e : cm.getEventList()) {
                bw.write(e.returnFileInfo());
                bw.newLine();

            }

            System.out.println("Events Saved into events.txt");
            bw.close();
            fw.close();

        } catch (IOException poop) {
            System.out.println("io exception");
        }

    }

    public JPanel createDayPanel() {
        dayPanel.removeAll();
        dayPanel.setLayout(new BorderLayout());

        JButton quit = new JButton("Quit");
        quit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                saveEventsToTextFile();
                System.exit(0);
            }

        });

        JButton create = new JButton("Create");
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popUpDialog();
            }

        });

        JButton delete = new JButton("Delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = dayJList.getSelectedIndex();
                //System.out.println(index);
                if (index>= 0 && listModel.get(index).getClass() == Event.class) {
                    

                    for (int i = 0; i < cm.getEventList().size(); i++) {
                        if (cm.getEventList().get(i).equals(listModel.get(index))) {
                            cm.getEventList().remove(i);
cm.update();
                        }
                    }

                }
                

            }

        });

        dayPanel.add(createList(), BorderLayout.NORTH);
        //dayPanel.add(createDayView(), BorderLayout.NORTH);
        dayPanel.add(create, BorderLayout.CENTER);

        dayPanel.add(delete, BorderLayout.SOUTH);

        dayPanel.add(quit, BorderLayout.EAST);

        return dayPanel;
    }

    public void popUpDialog() {
        //MMDDYY = cm.getMonthInt() + "/" + cm.getDayInt() + "/" + cm.getYear();
        final JDialog create = new JDialog();
        create.setTitle("Create an Event");
        create.setLayout(new GridBagLayout());

        final JTextField title = new JTextField(30);
        final JTextField startTime = new JTextField(10);
        final JTextField endTime = new JTextField(10);

        JButton save = new JButton("Save");

        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int start = Integer.parseInt(startTime.getText());
                int end = Integer.parseInt(endTime.getText());
                if (!title.getText().isEmpty()) {

                    Event event = new Event(title.getText(), MMDDYY, start, end);
                    sameDayEvents = event.returnEventsOnSameDay(cm.getEventList());
                    timeConflict = false;

                    for (Event x : sameDayEvents) {
                        if (x.hasConflictWith(event)) {
                            System.out.println("Error: Time Conflict");
                            popUpTimeConflictDialog();
                            timeConflict = true;
                            break;
                        }

                    }

                    if (!timeConflict) {
                        cm.getEventList().add(event);
                        cm.update();
                        create.dispose();
                        
                    } 
                   
                }
 
            }

        });

        GridBagConstraints format = new GridBagConstraints();
        format.insets = new Insets(2, 2, 2, 2);
        format.gridx = 0;
        format.gridy = 0;
        create.add(new JLabel(MMDDYY), format);
        format.gridy = 1;
        format.weightx = 1.0;
        format.anchor = GridBagConstraints.LINE_START;
        create.add(new JLabel("Event"), format);
        format.gridy = 2;
        create.add(title, format);
        format.gridy = 3;
        format.weightx = 0.0;
        format.anchor = GridBagConstraints.LINE_START;
        create.add(new JLabel("Time Start"), format);
        format.anchor = GridBagConstraints.CENTER;
        create.add(new JLabel("           Time End (1730 for 5:30pm)"), format);
        format.gridy = 4;
        format.anchor = GridBagConstraints.LINE_START;
        create.add(startTime, format);
        format.anchor = GridBagConstraints.CENTER;
        create.add(endTime, format);
        format.anchor = GridBagConstraints.LINE_END;
        create.add(save, format);
        //create.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

//        create.add(new JLabel(MMDDYY), BorderLayout.WEST);
//        create.add(title, BorderLayout.NORTH);
//        create.add(startTime, BorderLayout.CENTER);
//        create.add(endTime,BorderLayout.EAST);
//        create.add(save, BorderLayout.SOUTH);
        create.pack();
        create.setVisible(true);
    }

    public void popUpTimeConflictDialog() {
        JDialog conflict = new JDialog();
        conflict.setLayout(new BorderLayout());
        conflict.add(new JLabel("WARNING: Time Conflict"), BorderLayout.NORTH);
        conflict.add(new JLabel("Cannot Create Event. \n Please Enter Valid Time."), BorderLayout.CENTER);
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                conflict.dispose();
            }
        });

        conflict.add(ok, BorderLayout.SOUTH);
        conflict.pack();
        conflict.setVisible(true);
    }
}
