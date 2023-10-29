import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
public class TableRenderBtn extends JButton implements TableCellRenderer {
    public TableRenderBtn()
    {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText("read");
        return this;
    }
}
