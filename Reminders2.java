 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reminders2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author gregoryhanford
 */
public class Reminders2 extends JFrame implements ActionListener, TableModelListener, ListSelectionListener, MouseListener, ChangeListener {
    private GroupsTableModel gtm;
    private CalendarManager cm;
    private JTable jtabGroups;
    private JScrollPane jscrlpGroups;
    private RemindersTableModel rtm;
    private JTable jtabReminders;
    private JScrollPane jscrlpReminders;
    private JTextField jtfSearch;
    private JPanel westPanel;
    private JPanel centerPanel;
    private JPanel calendarPanel;
    private JLabel jlabGroup;
    private JToggleButton jbtnShowCal;
    private JMenuBar jmb;
    private JMenu jmReminders;
    private JMenu jmGroups;
    private JMenu jmSort;
    private JMenuItem jmiAddGroup, jmiDeleteGroup, jmiAddReminder, jmiDeleteReminder, jmiMoveCompleted, jmiSortByPriority, jmiSortByDate;
    private Manager manager;
    private ReminderDialog rdialog;
    private JButton[] daysButtons;
    private JTabbedPane jtpMonths;
    private JPanel[] monthsPanels;
    private JComboBox jcbYears, jcbMonths;
    private String[] monthsStr = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private String[] daysOfWeek = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
    private JPanel southPanel;
    public Reminders2() {
        super("Reminders 2");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        manager = new Manager();
        cm = new CalendarManager(this);
        initMenus();
        initWestPanel();
        initCenterPanel();
        initCalendarPanel();
        add(westPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        load();
        fillCurrentMonth();
        setVisible(true);
        
    }
    public void stateChanged(ChangeEvent ce) {
        if (ce.getSource().equals(jtpMonths)) {
            fillCurrentMonth();
        }
    }
    public void mouseExited(MouseEvent me) {
        
    }
    public void mouseEntered(MouseEvent me) {
        
    }
    public void mouseReleased(MouseEvent me) {
        
    }
    public void mousePressed(MouseEvent me) {
        
    }
    public void mouseClicked(MouseEvent me) {
        if (rtm.getDisplaySearch()) {
            if (rdialog == null && manager.getSearchGroup().getReminderAtIndex(jtabReminders.getSelectedRow()) != null) {
                rdialog = new ReminderDialog(this, manager, manager.getSearchGroup().getReminderAtIndex(jtabReminders.getSelectedRow()), rtm, cm);
            } else if (rdialog != null && !rdialog.isVisible() && jtabReminders.getSelectedRow() != -1) {
                rdialog = new ReminderDialog(this, manager, manager.getSearchGroup().getReminderAtIndex(jtabReminders.getSelectedRow()), rtm, cm);
            }
        } else {
            if (rdialog == null && manager.getGroupAtIndex(jtabGroups.getSelectedRow()).getReminderAtIndex(jtabReminders.getSelectedRow()) != null) {
                rdialog = new ReminderDialog(this, manager, manager.getGroupAtIndex(jtabGroups.getSelectedRow()).getReminderAtIndex(jtabReminders.getSelectedRow()), rtm, cm);
            } else if (rdialog != null && !rdialog.isVisible() && jtabReminders.getSelectedRow() != -1) {
                rdialog = new ReminderDialog(this, manager, manager.getGroupAtIndex(jtabGroups.getSelectedRow()).getReminderAtIndex(jtabReminders.getSelectedRow()), rtm, cm);
            }
        }
    }
    public void tableChanged(TableModelEvent tme) {
         
        if (tme.getSource().equals(gtm)) {
            if (jtabGroups.getSelectedRow() != -1) {
                jlabGroup.setText("<html><h1>" + (String)gtm.getValueAt(jtabGroups.getSelectedRow(), jtabGroups.getSelectedColumn()) + "</h1></html>");
                rtm.setGroupIndex(jtabGroups.getSelectedRow());
                rtm.refresh();
                
            }
        } else if (tme.getSource().equals(rtm)) {
            if (rdialog != null) {
                rdialog.refresh();
            }
        }
        fillCurrentMonth();
        save();
        
    }
    public void valueChanged(ListSelectionEvent lse) {
        if (lse.getSource().equals(jtabGroups.getSelectionModel())) {
            if (jtabGroups.getSelectedRow() != -1) {
                rtm.setDisplaySearch(false);
                jlabGroup.setText("<html><h1>" + (String)gtm.getValueAt(jtabGroups.getSelectedRow(), 0)+ "</h1></html>");
                rtm.setGroupIndex(jtabGroups.getSelectedRow());
                rtm.refresh();
                calendarPanel.removeAll();
                calendarPanel.add(jcbMonths);
                calendarPanel.add(monthsPanels[jcbMonths.getSelectedIndex()]);
                setVisible(true);
            }
        }
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(jtfSearch)) {
            manager.addRemindersToSearchGroup(manager.findRemindersByName(jtfSearch.getText()));
            jlabGroup.setText("<html><h1>Search</h1></html>");
            rtm.setDisplaySearch(true);
            rtm.refresh();
            calendarPanel.removeAll();
            calendarPanel.add(jcbMonths);
            calendarPanel.add(monthsPanels[jcbMonths.getSelectedIndex()]);
            setVisible(true);
        } else if (ae.getSource() instanceof JButton && !ae.getSource().equals(jbtnShowCal)) {
            JButton sourceButton = (JButton)ae.getSource();
            if (sourceButton.getText() != null) {
                int day = Integer.parseInt(sourceButton.getText());
                int month = jcbMonths.getSelectedIndex();
                int year = Integer.parseInt((String)jcbYears.getSelectedItem());
                ArrayList <Reminder> selectedReminders = manager.findRemindersByDueDate(year, month, day);
                manager.addRemindersToSearchGroup(selectedReminders);
                rtm.setDisplaySearch(true);
                rtm.refresh();
                jlabGroup.setText("<html><h1>Search</h1></html>");
                setVisible(true);
            }
        } else if (ae.getSource().equals(jcbYears)) {
            cm.setYear(Integer.parseInt((String)jcbYears.getSelectedItem()));
            fillCurrentMonth();
            setVisible(true);
        } else if (ae.getSource().equals(jcbMonths)) {
            calendarPanel.removeAll();
            fillCurrentMonth();
            calendarPanel.add(jcbMonths);
            calendarPanel.add(monthsPanels[jcbMonths.getSelectedIndex()]);
            
            calendarPanel.repaint();
            this.setVisible(true);
        } else {
            switch (ae.getActionCommand()) {
                case "Add Reminder":
                    if (!rtm.getDisplaySearch()) {
                        manager.addReminderToGroup(jtabGroups.getSelectedRow());
                        rtm.refresh();
                        save();
                    }
                    fillCurrentMonth();
                    break;
                case "Delete Reminder":
                    if (!rtm.getDisplaySearch()) {
                        if (jtabGroups.getSelectedRow() != -1 && jtabReminders.getSelectedRow() != -1) {
                            if (manager.getGroupAtIndex(jtabGroups.getSelectedRow()).getReminderAtIndex(jtabReminders.getSelectedRow()).equals(rdialog.getReminder())) {
                                rdialog.setVisible(false);
                                rdialog = null;
                            }
                        }
                        manager.deleteReminderFromGroup(jtabGroups.getSelectedRow(), jtabReminders.getSelectedRow());
                        rtm.refresh();
                        fillCurrentMonth();
                        save();
                    }
                    break;
                case "Move Completed":
                    manager.moveCompleted();
                    rtm.refresh();
                    save();
                    break;
                case "Add Group":
                    manager.addReminderGroup();
                    gtm.refresh();
                    save();
                    break;
                case "Delete Group":
                    manager.deleteReminderGroup(jtabGroups.getSelectedRow());
                    gtm.refresh();
                    save();
                    break;
                case "Show Calendar":

                    if (jbtnShowCal.isSelected()) {
                        showCalendar();
                    } else {
                        hideCalendar();
                    }
                    break;
                case "Sort By Priority":
                    manager.sortByPriority(jtabGroups.getSelectedRow());
                    rtm.refresh();
                    save();
                    break;
                case "Sort By Date":
                    manager.sortByDate(jtabGroups.getSelectedRow());
                    rtm.refresh();
                    save();
                    break;
            }
        }
    }
    public void initMenus() {
        jmb = new JMenuBar();
        this.setJMenuBar(jmb);
        jmGroups = new JMenu("Groups");
        jmiAddGroup = new JMenuItem("Add Group");
        jmiAddGroup.addActionListener(this);
        jmiDeleteGroup = new JMenuItem("Delete Group");
        jmiDeleteGroup.addActionListener(this);
        jmGroups.add(jmiAddGroup);
        jmGroups.add(jmiDeleteGroup);
        jmb.add(jmGroups);
        jmReminders = new JMenu("Reminders");
        jmiAddReminder = new JMenuItem("Add Reminder");
        jmiAddReminder.addActionListener(this);
        jmiDeleteReminder = new JMenuItem("Delete Reminder");
        jmiDeleteReminder.addActionListener(this);
        jmiMoveCompleted = new JMenuItem("Move Completed");
        jmiMoveCompleted.addActionListener(this);
        
        jmReminders.add(jmiAddReminder);
        jmReminders.add(jmiDeleteReminder);
        jmReminders.add(jmiMoveCompleted);
        jmb.add(jmReminders);
        jmSort = new JMenu("Sort");
        jmiSortByPriority = new JMenuItem("Sort By Priority");
        jmiSortByPriority.addActionListener(this);
        jmiSortByDate = new JMenuItem("Sort By Date");
        jmiSortByDate.addActionListener(this);
        jmSort.add(jmiSortByPriority);
        jmSort.add(jmiSortByDate);
        jmb.add(jmSort);
    }
    public void save() {
        try {
            File f = new File("Reminders.txt");
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(manager);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void load() {
        try {
            File f = new File("Reminders.txt");
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            manager = (Manager)ois.readObject();
            if (manager != null) {
                gtm.setManager(manager);
                rtm.setManager(manager);
                for (int i = 0; i < manager.getGroupsSize(); i++) {
                    manager.getGroupAtIndex(i).setManager(manager);
                }
            }
            gtm.refresh();
            rtm.refresh();
            ois.close();
            fis.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found");
        }
    }
    public void showCalendar() {
        southPanel.setPreferredSize(new Dimension(120, 300));
        southPanel.add(calendarPanel, 0);
        
        setVisible(true);
    }
    public void hideCalendar() {
        southPanel.remove(calendarPanel);
        southPanel.setPreferredSize(new Dimension(120, 70));
        setVisible(true);
    }
    public void initCalendarPanel() {
        calendarPanel = new JPanel();
        calendarPanel.setPreferredSize(new Dimension(160, 200));
        monthsPanels = new JPanel[12];
        jcbMonths = new JComboBox(monthsStr);
        jcbMonths.addActionListener(this);
        jtpMonths = new JTabbedPane();
        jtpMonths.setPreferredSize(new Dimension(160, 200));
        jtpMonths.addChangeListener(this);
        for (int i = 0; i < 12; i++) {
            monthsPanels[i] = new JPanel();
            jtpMonths.addTab(monthsStr[i], monthsPanels[i]);
        }
        jtpMonths.setSelectedIndex(cm.getCurrentMonth());
        jcbMonths.setSelectedIndex(cm.getCurrentMonth());
        fillCurrentMonth();
        calendarPanel.add(jcbMonths);
        calendarPanel.add(monthsPanels[cm.getCurrentMonth()]);
    }
    public void fillCurrentMonth() {
        int currentMonth = jcbMonths.getSelectedIndex();
        daysButtons = new JButton[42];
        JLabel[] daysLabels = new JLabel[7];
        JPanel currentMonthPanel = monthsPanels[currentMonth];
        currentMonthPanel.removeAll();
        GridLayout gl = new GridLayout(7, 7);
        currentMonthPanel.setLayout(gl);
        String days[] = cm.getDaysInSheet((Integer.parseInt((String)jcbYears.getSelectedItem())), currentMonth);
        for (int i = 0; i < 7; i++) {
            daysLabels[i] = new JLabel(daysOfWeek[i]);
            currentMonthPanel.add(daysLabels[i]);
        }
        for (int i = 0; i < 42; i++) {
            daysButtons[i] = new JButton();
            daysButtons[i].addActionListener(this);
            
            daysButtons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            daysButtons[i].setText(days[i]);
            if (daysButtons[i].getText() != null) {
                int year = Integer.parseInt((String)jcbYears.getSelectedItem());
                int month = jcbMonths.getSelectedIndex();
                int day = Integer.parseInt(daysButtons[i].getText());
                if (manager.findRemindersByDueDate(year, month, day).size() > 0) {
                    daysButtons[i].setBackground(Color.red);
                    daysButtons[i].setOpaque(true);
                }
            }
            currentMonthPanel.add(daysButtons[i]);
            
        }
    }
    public void initCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEtchedBorder());
        centerPanel.setLayout(new BorderLayout());
        jlabGroup = new JLabel("<html><h1>Reminder Group</h1></html>");
        rtm = new RemindersTableModel(manager);
        jtabReminders = new JTable(rtm);
        rtm.addTableModelListener(this);
        jtabReminders.setDefaultRenderer(Component.class, new RemindersCellRenderer());
        jtabReminders.getColumnModel().getColumn(0).setPreferredWidth(24);
        jtabReminders.getColumnModel().getColumn(1).setPreferredWidth(15);
        jtabReminders.addMouseListener(this);
        jscrlpReminders = new JScrollPane(jtabReminders);
        centerPanel.add(jlabGroup, BorderLayout.NORTH);
        centerPanel.add(jscrlpReminders, BorderLayout.CENTER);
    }
    public void initWestPanel() {
        westPanel = new JPanel();
        westPanel.setBorder(BorderFactory.createEtchedBorder());
        westPanel.setLayout(new BorderLayout());
        jtfSearch = new JTextField("Search", 5);
        jtfSearch.addActionListener(this);
        gtm = new GroupsTableModel(manager);
        jtabGroups = new JTable(gtm);
        jtabGroups.getModel().addTableModelListener(this);
        jtabGroups.getSelectionModel().addListSelectionListener(this);
        jscrlpGroups = new JScrollPane(jtabGroups);
        jscrlpGroups.setPreferredSize(new Dimension(160, 320));
        
        jbtnShowCal = new JToggleButton("Show Calendar");
        jbtnShowCal.addActionListener(this);
        jcbYears = new JComboBox(cm.getYears());
        jcbYears.addActionListener(this);
        westPanel.add(jtfSearch, BorderLayout.NORTH);
        westPanel.add(jscrlpGroups, BorderLayout.CENTER);
        southPanel = new JPanel();
        southPanel.setPreferredSize(new Dimension(160, 70));
        southPanel.setLayout(new FlowLayout());
        southPanel.add(jbtnShowCal);
        southPanel.add(jcbYears);
        westPanel.add(southPanel, BorderLayout.SOUTH);
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Reminders2();
            }
        });
    }
}
