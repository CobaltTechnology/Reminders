/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reminders2;
import java.util.*;
import java.io.*;
import java.text.*;
/**
 *
 * @author gregoryhanford
 */
public class Reminder implements Serializable{
    public static enum Priority {
        HIGH_PRIORITY, MEDIUM_PRIORITY, LOW_PRIORITY, NO_PRIORITY;
    }
    private boolean completed;
    private String name;
    private GregorianCalendar dueDate;
    private Priority remPriority = Priority.NO_PRIORITY;
    private String note;
    private String group;
    public Reminder(String group) {
        dueDate = new GregorianCalendar();
        this.group = group;
        completed = false;
        name = "";
        note = "";
    }
    public boolean checkIfHasDueDate(int year, int month, int day) {
        if (dueDate.get(GregorianCalendar.YEAR) == year && dueDate.get(GregorianCalendar.MONTH) == month && dueDate.get(GregorianCalendar.DATE) == day) {
            return true;
        }
        return false;
    }
    public String getGroup() {
        return group;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public boolean getCompleted() {
        return completed;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setDueDate(int year, int month, int day) {
        dueDate.set(year, month, day);
    }
    public void setYear(Object year) {
        dueDate.set(GregorianCalendar.YEAR, Integer.parseInt((String)year));
    }
    public void setMonth(int month) {
        dueDate.set(GregorianCalendar.MONTH, month);
    }
    public void setDay(Object day) {
        if (day != null) {
            dueDate.set(GregorianCalendar.DATE, Integer.parseInt((String)day));
        }
    }
   
    public GregorianCalendar getDueDate() {
        return dueDate;
    }
    public String getDueDateFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(dueDate.getTime());
    }
    public void setPriority(Priority p) {
        remPriority = p;
    }
    public Priority getPriority() {
        return remPriority;
    }
    public String[] getPriorities() {
        String[] priorities = {"No Priority", "Low Priority", "Medium Priority", "High Priority"};
        return priorities;
    }
    public String getPriorityString() {
        switch (remPriority) {
            case NO_PRIORITY:
                return "0";
            case LOW_PRIORITY:
                return "1"; 
            case MEDIUM_PRIORITY:
                return "2";
            case HIGH_PRIORITY:
                return "3";
            default:
                return "0";
        }
    }
    public int getPriorityInt() {
        switch (remPriority) {
            case NO_PRIORITY:
                return 0;
            case LOW_PRIORITY:
                return 1; 
            case MEDIUM_PRIORITY:
                return 2;
            case HIGH_PRIORITY:
                return 3;
            default:
                return 0;
        }
    }
    public void setPriorityString(String priority) {
        switch(priority) {
            case "0":
                remPriority = Priority.NO_PRIORITY;
                break;
            case "1":
                remPriority = Priority.LOW_PRIORITY;
                break;
            case "2":
                remPriority = Priority.MEDIUM_PRIORITY;
                break;
            case "3":
                remPriority = Priority.HIGH_PRIORITY;
                break;
        }
    }
    public void setPriorityInt(int priority) {
        switch(priority) {
            case 0:
                remPriority = Priority.NO_PRIORITY;
                break;
            case 1:
                remPriority = Priority.LOW_PRIORITY;
                break;
            case 2:
                remPriority = Priority.MEDIUM_PRIORITY;
                break;
            case 3:
                remPriority = Priority.HIGH_PRIORITY;
                break;
        }
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getNote() {
        return note;
    }
}
