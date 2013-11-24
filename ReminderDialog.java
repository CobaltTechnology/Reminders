/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reminders2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 *
 * @author gregoryhanford
 */
public class ReminderDialog extends JDialog implements ActionListener{
    private JButton jbtnDone;
    private Manager manager;
    private Reminder reminder;
    private RemindersTableModel rtm;
    private JTextField jtfName;
    private JComboBox jcbYear;
    private JComboBox jcbMonth;
    private JComboBox jcbDay;
    private JComboBox jcbPriority;
    private JTextArea jtaNote;
    private JScrollPane jscrlpNote;
    private String[] monthsStr = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private CalendarManager cm;
    private Reminders2 r2;
    public ReminderDialog(Reminders2 r2, Manager manager, Reminder reminder, RemindersTableModel rtm, CalendarManager cm) {
        super();
        this.r2 = r2;
        this.manager = manager;
        this.reminder = reminder;
        this.rtm = rtm;
        this.cm = cm;
        setTitle("Edit Reminder");
        setModal(false);
        setBounds(600, 0, 195, 450);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        jbtnDone = new JButton("Done");
        jbtnDone.addActionListener(this);
        jtfName = new JTextField(reminder.getName(), 15);
        jtfName.addActionListener(this);
        jcbYear = new JComboBox(cm.getYears());
        jcbYear.setSelectedItem(String.valueOf(reminder.getDueDate().get(GregorianCalendar.YEAR)));
        jcbYear.addActionListener(this);
        jcbMonth = new JComboBox(monthsStr);
        jcbMonth.setSelectedIndex(reminder.getDueDate().get(GregorianCalendar.MONTH));
        jcbMonth.addActionListener(this);
        jcbDay = new JComboBox();
        jcbDay.addActionListener(this);
        for (int i = 0; i < cm.getDaysInMonth(Integer.parseInt((String)jcbYear.getSelectedItem()), jcbMonth.getSelectedIndex()); i++) {
            jcbDay.addItem(String.valueOf(i+1));
        }
        jcbDay.setSelectedIndex(reminder.getDueDate().get(GregorianCalendar.DATE)-1);
        jcbPriority = new JComboBox(reminder.getPriorities());
        jcbPriority.setSelectedIndex(reminder.getPriorityInt());
        jcbPriority.addActionListener(this);
        jtaNote = new JTextArea(10, 10);
        
        jtaNote.setLineWrap(true);
        jtaNote.setWrapStyleWord(true);
        jtaNote.setText(reminder.getNote());
        jscrlpNote = new JScrollPane(jtaNote);
        add(jtfName);
        add(jcbYear);
        add(jcbMonth);
        add(jcbDay);
        add(jcbPriority);
        add(jscrlpNote);
        add(jbtnDone);
        setVisible(true);
    }
    public Reminder getReminder() {
        return reminder;
    }
    public void refresh() {
        jtfName.setText(reminder.getName());
        jcbPriority.setSelectedIndex(reminder.getPriorityInt());
        
    }
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Done") || ae.getSource().equals(jtfName)) {
            reminder.setName(jtfName.getText());
            reminder.setNote(jtaNote.getText());
            r2.fillCurrentMonth();
            dispose();
        } else if (ae.getSource().equals(jcbYear)) {
            reminder.setYear(jcbYear.getSelectedItem());
            jcbDay.removeAllItems();
            for (int i = 0; i < cm.getDaysInMonth(Integer.parseInt((String)jcbYear.getSelectedItem()), jcbMonth.getSelectedIndex()); i++) {
                jcbDay.addItem(String.valueOf(i+1));
            }
            rtm.refresh();
        } else if (ae.getSource().equals(jcbMonth)) {
            reminder.setMonth(jcbMonth.getSelectedIndex());
            jcbDay.removeAllItems();
            for (int i = 0; i < cm.getDaysInMonth(Integer.parseInt((String)jcbYear.getSelectedItem()), jcbMonth.getSelectedIndex()); i++) {
                jcbDay.addItem(String.valueOf(i+1));
            }
            rtm.refresh();
        } else if (ae.getSource().equals(jcbDay)) {
            if (this.isVisible()) {
                reminder.setDay(jcbDay.getSelectedItem());
            }
        } else if (ae.getSource().equals(jcbPriority)) {
            reminder.setPriorityInt(jcbPriority.getSelectedIndex());
            rtm.refresh();
        }
    }
}
