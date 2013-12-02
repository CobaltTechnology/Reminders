/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reminders2;
import java.util.*;
import java.io.*;
/**
 *
 * @author gregoryhanford
 */
public class Group implements Serializable{
    private String name;
    private ArrayList <Reminder> reminders;
    private Manager manager;
    public Group(Manager manager) {
        name = "New Group";
        this.manager = manager;
        reminders = new ArrayList();
    }
    public void removeAll() {
        reminders.clear();
    }
    public void sortByDate() {
        Collections.sort(reminders, new DateComparator());
    }
    public void setManager(Manager m) {
        this.manager = m;
    }
    public void sortByPriority() {
        ArrayList <Reminder> newSort = new ArrayList();
        newSort.add(null);
        for (int i = 0; i < reminders.size(); i++) {
            if (reminders.get(i).getPriority() == Reminder.Priority.MEDIUM_PRIORITY) {
                newSort.add(newSort.size()-1, reminders.get(i));
            }
        }
        for (int i = 0; i < reminders.size(); i++) {
            if (reminders.get(i).getPriority() == Reminder.Priority.HIGH_PRIORITY) {
                newSort.add(0, reminders.get(i));
            }
        }
        for (int i = 0; i < reminders.size(); i++) {
            if (reminders.get(i).getPriority() == Reminder.Priority.LOW_PRIORITY) {
                newSort.add(newSort.size()-1, reminders.get(i));
            }
        }
        for (int i = 0; i < reminders.size(); i++) {
            if (reminders.get(i).getPriority() == Reminder.Priority.NO_PRIORITY) {
                newSort.add(newSort.size()-1, reminders.get(i));
            }
        }
        newSort.remove(null);
        reminders = newSort;
    }
    public Reminder getReminderAtIndex(int index) {
        if (reminders.size() > 0 && index != -1) {
            return reminders.get(index);
        } else {
            return null;
        }
    }
    public void addReminder() {
        Reminder r = new Reminder(name);
        reminders.add(r);
    }
    public void addReminder(Reminder r) {
        reminders.add(r);
    }
    public int getReminderSize() {
        return reminders.size();
    }
    public void deleteCompletedReminders() {
        ArrayList <Reminder> selectedReminders = new ArrayList();
        for (int i = 0; i < reminders.size(); i++) {
            if (reminders.get(i).getCompleted()) {
                manager.addReminderToCompleted(reminders.get(i));
                selectedReminders.add(reminders.get(i));
            }
        }
        for (int i = 0; i < selectedReminders.size(); i++) {
            reminders.remove(selectedReminders.get(i));
        }
    }
    public void moveCompletedRemindersToGroups() {
        ArrayList <Reminder> selectedReminders = new ArrayList();
        for (int i = 0; i < reminders.size(); i++) {
            if (!reminders.get(i).getCompleted()) {
                selectedReminders.add(reminders.get(i));
                for (int j = 0; j < manager.getGroupsSize(); j++) {
                    if (reminders.get(i).getGroup().equalsIgnoreCase(manager.getGroupAtIndex(j).getName())) {
                        manager.getGroupAtIndex(j).addReminder(reminders.get(i));
                    }
                }
            }
        }
        for (int i = 0; i < selectedReminders.size(); i++) {
            reminders.remove(selectedReminders.get(i));
        }
    }
    public void deleteReminder(int index) {
        if (index != -1) {  
            reminders.remove(index);
        }
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
        for (int i = 0; i < reminders.size(); i++) {
            reminders.get(i).setGroup(name);
        }
    }
}

class DateComparator implements Comparator <Reminder>{
    public int compare(Reminder r1, Reminder r2) {
        if (r1.getDueDate().compareTo(r2.getDueDate()) < 0) {
            return -1;
        } else if (r1.getDueDate().compareTo(r2.getDueDate()) == 0) {
            return 0;
        } else if (r1.getDueDate().compareTo(r2.getDueDate()) > 0) {
            return 1;
        }
        return 0;
    }
}
