package code.View;

import javax.swing.table.DefaultTableModel;

// custom table model to make cells uneditable to user
public class TableModel extends DefaultTableModel {
    DefaultTableModel tableModel;

    TableModel(Object[][] table, String[] columnNames) {
        this.tableModel = new DefaultTableModel(table, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public DefaultTableModel getTableModel() {
        return this.tableModel;
    }
}
