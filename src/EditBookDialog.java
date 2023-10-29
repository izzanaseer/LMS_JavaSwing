import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
public class EditBookDialog extends JDialog {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField yearField;
    private JTable parentTable;
    private int selectedRow;
    private List<Book> books;
    public EditBookDialog(JFrame parent, JTable parentTable, int selectedRow, List<Book> books) {
        super(parent, "Edit Book", true);

        this.parentTable = parentTable;
        this.selectedRow = selectedRow;
        this.books = books;

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Title:"));
        titleField = new JTextField();
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        authorField = new JTextField();
        panel.add(authorField);
        panel.add(new JLabel("Publication Year:"));
        yearField = new JTextField();
        panel.add(yearField);
        JButton saveButton = new JButton("Save");

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);

        loadBookDetails();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBookDetails();
                dispose();
            }
        });

        pack();
        setLocationRelativeTo(parent);
    }

    private void loadBookDetails() {
        DefaultTableModel tableModel = (DefaultTableModel) parentTable.getModel();
        titleField.setText(tableModel.getValueAt(selectedRow, 0).toString());
        authorField.setText(tableModel.getValueAt(selectedRow, 1).toString());
        yearField.setText(tableModel.getValueAt(selectedRow, 2).toString());
    }

    private void updateBookDetails() {
        DefaultTableModel tableModel = (DefaultTableModel) parentTable.getModel();
        tableModel.setValueAt(titleField.getText(), selectedRow, 0);
        tableModel.setValueAt(authorField.getText(), selectedRow, 1);
        tableModel.setValueAt(yearField.getText(), selectedRow, 2);

        Book book = books.get(selectedRow);
        book.setTitle(titleField.getText());
        book.setAuthor(authorField.getText());
        book.setPublicationYear(Integer.parseInt(yearField.getText()));

        saveLibraryData(books);
    }

    private void saveLibraryData(List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/izzanaseer/Documents/FAST/sem5/SCD/SCD Assignment3/LMS_Swing/src/data.txt"))) {
            for (Book book : books) {
                writer.write(book.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
