package samples.demo;
import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

/**
 * JTable的例子
 */
public class Test {

    private JFrame frame = null;

    private JTable table = null;

    private Table_Model model = null;

    private JScrollPane s_pan = null;

    public Test() {
        frame = new JFrame("Test");
        model = new Table_Model(20);
        table = new JTable(model);
        String[] age = { "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" };
        JComboBox com = new JComboBox(age);
        TableColumnModel tcm = table.getColumnModel();
        tcm.getColumn(2).setCellEditor(new DefaultCellEditor(com)); // 设置某列采用JComboBox组件


        model.addRow("宋江", true, "30");
        model.addRow("孙二娘", false, "21");
        model.addRow("武松", true, "24");

        s_pan = new JScrollPane(table);

        frame.getContentPane().add(s_pan, BorderLayout.CENTER);

        frame.setSize(300, 200);
        frame.setVisible(true);

        //model.addRow(2); // 在某处插入一空行

        //table.updateUI(); // 刷新


    }

    public static void main(String args[]) {
        new Test();
    }

}

class Table_Model extends AbstractTableModel {
    private static final long serialVersionUID = -3094977414157589758L;

    private Vector content = null;

    private String[] title_name = { "姓名", "性别", "年龄" };

    public Table_Model() {
        content = new Vector();
    }

    public Table_Model(int count) {
        content = new Vector(count);
    }

    /**
     * 加入一空行
     * @param row 行号
     */
    public void addRow(int row) {
        Vector v = new Vector(3);
        v.add(0, null);
        v.add(1, null);
        v.add(2, null);
        content.add(row, v);
    }

    /**
     * 加入一行内容
     */
    public void addRow(String name, boolean b, String age) {
        Vector v = new Vector(3);
        v.add(0, name);
        v.add(1, new Boolean(b)); // JCheckBox是Boolean的默认显示组件，这里仅仅为了看效果，其实用JComboBox显示***更合适

        v.add(2, age); // 本列在前面已经设置成了JComboBox组件，这里随便输入什么字符串都没关系

        content.add(v);
    }

    public void removeRow(int row) {
        content.remove(row);
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(rowIndex == 2) {
            return false;
        }
        return true;
    }

    public void setValueAt(Object value, int row, int col) {
        ((Vector) content.get(row)).remove(col);
        ((Vector) content.get(row)).add(col, value);
        this.fireTableCellUpdated(row, col);
    }

    public String getColumnName(int col) {
        return title_name[col];
    }

    public int getColumnCount() {
        return title_name.length;
    }

    public int getRowCount() {
        return content.size();
    }

    public Object getValueAt(int row, int col) {
        return ((Vector) content.get(row)).get(col);
    }

    public Class getColumnClass(int col) {
        return getValueAt(0, col).getClass();
    }

}