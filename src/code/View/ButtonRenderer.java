package code.View;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

class ButtonRenderer extends JButton implements TableCellRenderer {

  public ButtonRenderer() {
    this.setOpaque(true);
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object obj, boolean selected, boolean focued, int rowm, int col) {
    if (obj == null) {
      setText("");
    } else {
      setText(obj.toString());
    }
    return this;
  }

}