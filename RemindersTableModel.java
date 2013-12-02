/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reminders2;
import javax.swing.table.*;
import javax.swing.*;
import java.util.*;
/**
 *
 * @author gregoryhanford
 */
public class RemindersTableModel extends AbstractTableModel{
    private Manager manager;
    private int groupIndex;
    private boolean displaySearch;
    public RemindersTableModel(Manager manager) {
        super();
        this.manager = manager;
        groupIndex = 0;
    }
    public void setDisplaySearch(boolean displaySearch) {
        this.displaySearch = displaySearch;
    }
    public boolean getDisplaySearch() {
        return displaySearch;
    }
    @Override
    public Class getColumnClass(int c) {
        if (c == 0) {
            return Boolean.class;
        } else if (c == 1) {
            return Object.class;
        } else {
            return Object.class;
        }
    }
    public void setManager(Manager m) {
        this.manager = m;
    }
    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }
    @Override
    public Object getValueAt(int r, int c) {
        if (displaySearch) {
            
            if (c == 0) {
                return manager.getSearchGroup().getReminderAtIndex(r).getCompleted();
            } else if (c == 1) {
                return manager.getSearchGroup().getReminderAtIndex(r).getPriorityString();
            } else if (c == 2) {
                return manager.getSearchGroup().getReminderAtIndex(r).getName();
            } else if (c == 3) {
                return manager.getSearchGroup().getReminderAtIndex(r).getDueDateFormatted();
            } else {
                return null;
            }
        } else {
            if (c == 0) {
                return manager.getGroupAtIndex(groupIndex).getReminderAtIndex(r).getCompleted();
            } else if (c == 1){
                return manager.getGroupAtIndex(groupIndex).getReminderAtIndex(r).getPriorityString();
            } else if (c == 2) {
                return manager.getGroupAtIndex(groupIndex).getReminderAtIndex(r).getName();
            } else if (c == 3) {
                return manager.getGroupAtIndex(groupIndex).getReminderAtIndex(r).getDueDateFormatted();
            }else {
                return null;
            }
        }
        
    }
    @Override
    public int getColumnCount() {
        return 4;
    }
    @Override
    public int getRowCount() {
        if (displaySearch) {
            return manager.getSearchGroup().getReminderSize();
        } else {
            if (manager.getGroupsSize() > 1 && groupIndex < manager.getGroupsSize()) {
                return manager.getReminderSize(groupIndex);
            } else {
                return 0;
            }
        }
    }
    @Override
    public String getColumnName(int c) {
        switch (c) {
            case 0:
                return "Completed";
            case 1:
                return "Priority";
            case 2:
                return "Name";
            case 3:
                return "Due Date";
            default:
                return null;
        }
    }
    @Override
    public boolean isCellEditable(int r, int c) {
        if (c >= 0 && c <= 2) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void setValueAt(Object o, int r, int c) {
        if (displaySearch) {
            if (c == 0) {
                manager.getSearchGroup().getReminderAtIndex(r).setCompleted(((Boolean)o).booleanValue());
            } else if (c == 1) {
                manager.getSearchGroup().getReminderAtIndex(r).setPriorityString((String)o);
            } else if (c == 2) {
                manager.getSearchGroup().getReminderAtIndex(r).setName((String)o);
            }
        } else {
            if (c == 0) {
                manager.getGroupAtIndex(groupIndex).getReminderAtIndex(r).setCompleted(((Boolean)o).booleanValue());
            } else if (c == 1) {
                manager.getGroupAtIndex(groupIndex).getReminderAtIndex(r).setPriorityString((String)o);
            } else if (c == 2) {
                manager.getGroupAtIndex(groupIndex).getReminderAtIndex(r).setName((String)o);
            }
            refresh();
        }
    }
    public void refresh() {
        fireTableDataChanged();
    }
}
