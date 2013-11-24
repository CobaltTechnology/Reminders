/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reminders2;
import java.util.*;
import java.text.*;
import javax.swing.*;
import java.io.*;
/**
 *
 * @author gregoryhanford
 */
public class CalendarManager implements Serializable{
    private GregorianCalendar currentCal;
    private GregorianCalendar cal;
    private String[] daysOfWeek = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
    private int year;
    private SimpleDateFormat sdf;
    private JFrame jfrm;
    public CalendarManager(JFrame jfrm) {
        this.jfrm = jfrm;
        currentCal = new GregorianCalendar();
        year = currentCal.get(GregorianCalendar.YEAR);
    }
    public int getCurrentYear() {
       return currentCal.get(GregorianCalendar.YEAR);
    }
    public String[] getYears() {
        String[] years = new String[10];
        for (int i = 0; i < 10; i++) {
            years[i] = String.valueOf(2013 + i);
        }
        return years;
    }
    public int getCurrentMonth() {
        return currentCal.get(GregorianCalendar.MONTH);
    }
    public int getCurrentDay() {
        return currentCal.get(GregorianCalendar.DATE);
    }
    
    public void setYear(int year) {
        this.year = year;
        cal.set(GregorianCalendar.YEAR, year);
    }
    public int getYear() {
        return year;
    }
     
    
    public int getOffsetDaysInMonth(int year, int month) {
        return getFirstDayOfMonth(year, month) + getDaysInMonth(year, month) -1;
    }
    public int getDaysInMonth(int month) {
        cal = new GregorianCalendar(year, month, 1);
        int numDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        return numDays;
    }
    public int getMonthAsInteger(String month) {
        switch(month) {
            case "January":
                return 1;
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "October":
                return 10;
            case "November":
                return 11;
            case "December":
                return 12;
            default:
                return 0;
        }
    }
    public int getDaysInMonth(int year, int month) {
        cal = new GregorianCalendar(year, month, 1);
        int numDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return numDays;
    }
    public String[] getDaysInSheet(int year, int month) {
        String[] daysInSheet = new String[42];
        int startWrite = getFirstDayOfMonth(year, month);
        int endWrite = getOffsetDaysInMonth(year, month);
        for (int i = 0; i < 42; i++) {
            
            if ((i >= startWrite && i <= endWrite)) {
                daysInSheet[i] = String.valueOf((i+1) - startWrite);
            }
        }
        return daysInSheet;
    }

    public int getToday() {
        if (year == currentCal.get(Calendar.YEAR)) {
             return getDayOfSheet(currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH)+1, currentCal.get(Calendar.DATE));
        } return 0;
    }
    public int getDayOfSheet(int year, int month, int day) {
        return getFirstDayOfMonth(year, month) + day - 1;
    }
    public int getFirstDayOfMonth(int year, int month) {
        sdf = new SimpleDateFormat("EEEEEEEE");
        cal = new GregorianCalendar(year, month, 1);
        switch (sdf.format(cal.getTime())) {
            case "Sunday":
                return 0;
            case "Monday":
                return 1;
            case "Tuesday":
                return 2;
            case "Wednesday":
                return 3;
            case "Thursday":
                return 4;
            case "Friday":
                return 5;
            case "Saturday":
                return 6;
            default:
                return 0;
        }
    }
    
    
    public JFrame getJFrame() {
        return jfrm;
    }
}
