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
public class Manager implements Serializable{
    private ArrayList <Group> groups;
    private Group searchGroup;
    private int startYear;
    public Manager() {
        groups = new ArrayList();
        addReminderGroup("Completed");
        searchGroup = new Group(this);
    }
    
    public Group getGroupAtIndex(int index) {
        if (index != -1 && index < groups.size()) {
            return groups.get(index);
        } else {
            return null;
        }
    }
    public void sortByPriority(int groupIndex) {
        groups.get(groupIndex).sortByPriority();
    }
    public void sortByDate(int groupIndex) {
        groups.get(groupIndex).sortByDate();
    }
    public void addReminderToCompleted(Reminder r) {
        groups.get(0).addReminder(r);
    }
    public void addReminderToGroup(int groupIndex) {
        if (groupIndex > 0) {
            groups.get(groupIndex).addReminder();
        }
    }
    public void addRemindersToSearchGroup(ArrayList <Reminder> selectedReminders) {
        searchGroup.removeAll();
        for (int i = 0; i < selectedReminders.size(); i++) {
            searchGroup.addReminder(selectedReminders.get(i));
        }
    }
    public Group getSearchGroup() {
        return searchGroup;
    }
    public void moveCompleted() {
        groups.get(0).moveCompletedRemindersToGroups();
        for (int i = 1; i < groups.size(); i++) {
            groups.get(i).deleteCompletedReminders();
        }
    }
    public void deleteReminderFromGroup(int groupIndex, int reminderIndex) {
        if (groupIndex != -1 && reminderIndex != -1) {
            groups.get(groupIndex).deleteReminder(reminderIndex);
        }
    }
    public int getReminderSize(int groupIndex) {
        return groups.get(groupIndex).getReminderSize();
    }
    public ArrayList <Reminder> findRemindersByDueDate(int year, int month, int day) {
        ArrayList <Reminder> selectedReminders = new ArrayList();
        for (int i = 1; i < groups.size(); i++) {
            for (int j = 0; j < groups.get(i).getReminderSize(); j++) {
                if (groups.get(i).getReminderAtIndex(j).checkIfHasDueDate(year, month, day)) {
                    selectedReminders.add(groups.get(i).getReminderAtIndex(j));
                }
            }
        }
        return selectedReminders;
    }
    public ArrayList <Reminder> findRemindersByName(String name) {
        ArrayList <Reminder> selectedReminders = new ArrayList();
        for (int i = 1; i < groups.size(); i++) {
            for (int j = 0; j < groups.get(i).getReminderSize(); j++) {
                if (groups.get(i).getReminderAtIndex(j).getName().toLowerCase().contains(name.toLowerCase())) {
                    selectedReminders.add(groups.get(i).getReminderAtIndex(j));
                }
            }
        }
        return selectedReminders;
    }
    public void addReminderGroup() {
        Group group = new Group(this);
        groups.add(group);
    }
    public void addReminderGroup(String name) {
        Group group = new Group(this);
        group.setName(name);
        groups.add(group);
    }
    public void deleteReminderGroup(int index) {
        if (index > 0) {
            groups.remove(index);
        }
    }
    public int getGroupsSize() {
        return groups.size();
    }
}
