/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reminders2;
import javax.swing.table.*;
/**
 *
 * @author gregoryhanford
 */
public class GroupsTableModel extends AbstractTableModel{
    private Manager manager;
    public GroupsTableModel(Manager manager) {
        this.manager = manager;
    }
    @Override
    public Object getValueAt(int r, int c) {
        if (manager.getGroupAtIndex(r) != null) {
            return manager.getGroupAtIndex(r).getName();
        } else {
            return manager.getGroupAtIndex(0).getName();
        }
    }
    public void setManager(Manager m) {
        this.manager = m;
    }
    @Override
    public int getColumnCount() {
        return 1;
    }
    @Override
    public int getRowCount() {
        return manager.getGroupsSize();
    }
    @Override
    public String getColumnName(int c) {
        return "Groups";
    }
    @Override
    public boolean isCellEditable(int r, int c) {
        if (r > 0) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public void setValueAt(Object o, int r, int c) {
        manager.getGroupAtIndex(r).setName((String)o);
        System.out.println((String)o);
        refresh();
    }
    public void refresh() {
        fireTableDataChanged();
    }
}
