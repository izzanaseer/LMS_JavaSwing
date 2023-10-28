import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddBookDialog extends JDialog {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField yearField;
    private JTable parentTable;
    private List<Book> books;
    private LMS parentLMS; // Reference to the parent LMS class

    public AddBookDialog(LMS parentLMS, JTable parentTable, List<Book> books) {
        super(parentLMS, "Add Book", true);

        this.parentTable = parentTable;
        this.books = books;
        this.parentLMS = parentLMS;

        // Initialize your dialog components here (text fields, buttons, etc.)
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
        JButton addButton = new JButton("Add");

        // Add action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String yearText = yearField.getText();

                // Validate the year input
                if (!yearText.isEmpty()) {
                    try {
                        int year = Integer.parseInt(yearText);

                        // Create a new book object.
                        Book book = new Book(title, author, year, 0);
                        DefaultTableModel tableModel = (DefaultTableModel) parentTable.getModel();
                        tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), year, "Read"});

                        // Update the books list and save the updated data to the file
                        books.add(book);
                        parentLMS.saveLibraryData(books);

                        dispose(); // Close the dialog
                    } catch (NumberFormatException ex) {
                        // Handle invalid year input (not a valid integer)
                        JOptionPane.showMessageDialog(AddBookDialog.this, "Invalid year. Please enter a valid number.");
                    }
                } else {
                    // Handle empty year input
                    JOptionPane.showMessageDialog(AddBookDialog.this, "Year cannot be empty. Please enter a year.");
                }
            }
        });

        // Set up your dialog layout
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);
        pack();
    }
}
