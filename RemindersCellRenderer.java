/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reminders2;
import javax.swing.table.*;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author gregoryhanford
 */
public class RemindersCellRenderer extends DefaultTableCellRenderer{
    private boolean temp = false;
    private JCheckBox jcb;
    
    @Override
    public Component getTableCellRendererComponent(JTable jtab, Object o, boolean selected, boolean focus, int r, int c) {
        if (c == 0) {
            jcb = new JCheckBox();
            jcb.setSelected(((Boolean)o).booleanValue()) ;
            return jcb;
        } else {
            return null;
        }
    }
}
