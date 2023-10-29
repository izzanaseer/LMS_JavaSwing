import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
public class TableBtnEditor extends DefaultCellEditor {
    private final JButton button;
    private String label;
    private boolean isPushed;

    public TableBtnEditor(JTextField textField)
    {
        super(textField);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener((ActionEvent e) ->
        {
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

        @Override
        public Object getCellEditorValue()
        {
            if (isPushed)
            {
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing()
        {
            isPushed = false;
            return super.stopCellEditing();
        }
}
