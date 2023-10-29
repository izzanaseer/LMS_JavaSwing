import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class LMS extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;
    private JButton addItemButton;
    private JButton editItemButton;
    private JButton deleteItemButton;
    private List<Book> books;
    private DefaultTableModel tableModel;

    public LMS() {
        super("GUI Example");

        tableModel = new DefaultTableModel(new String[]{"Title", "Author", "Publication Year", "Read Item"}, 0);
        table = new JTable(tableModel);

        scrollPane = new JScrollPane(table);
        buttonPanel = new JPanel();

        addItemButton = new JButton("Add Item");
        editItemButton = new JButton("Edit Item");
        deleteItemButton = new JButton("Delete Item");

        buttonPanel.add(addItemButton);
        buttonPanel.add(editItemButton);
        buttonPanel.add(deleteItemButton);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);

        addItemButton.addActionListener(e -> {
            AddBookDialog addBookDialog = new AddBookDialog(this, table, books);
            addBookDialog.setVisible(true);
        });


        editItemButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a book to edit.");
            } else {
                String title = tableModel.getValueAt(selectedRow, 0).toString();
                String author = tableModel.getValueAt(selectedRow, 1).toString();
                String year = tableModel.getValueAt(selectedRow, 2).toString();

                EditBookDialog editBookDialog = new EditBookDialog(this, table, selectedRow, books);
                editBookDialog.setVisible(true);
            }
        });



        deleteItemButton.addActionListener(e -> {
            // Show an input dialog to get the name of the item to delete
            String itemNameToDelete = JOptionPane.showInputDialog(this, "Enter the name of the item to delete:");

            if (itemNameToDelete != null && !itemNameToDelete.isEmpty()) {
                // Find and remove the item from the list of books
                Book bookToRemove = null;
                for (Book book : books) {
                    if (book.getTitle().equals(itemNameToDelete)) {
                        bookToRemove = book;
                        break;
                    }
                }

                if (bookToRemove != null) {
                    // Remove the book from the list
                    books.remove(bookToRemove);
                    // Remove the item from the table
                    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        if (tableModel.getValueAt(i, 0).equals(itemNameToDelete)) {
                            tableModel.removeRow(i);
                            break;
                        }
                    }
                    // Save the updated data to the text file
                    saveLibraryData(books);
                } else {
                    JOptionPane.showMessageDialog(this, "Item not found in the library.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid item name.");
            }
        });


        table.getColumnModel().getColumn(3).setCellRenderer(new TableRenderBtn());
        table.getColumnModel().getColumn(3).setCellEditor(new TableBtnEditor(new JTextField()));


        //Action listener for the "Read" button
        table.getColumnModel().getColumn(3).setCellRenderer(new TableRenderBtn());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = table.getColumnModel().getColumnIndex("Read Item");
                if (column != -1 && table.columnAtPoint(e.getPoint()) == column) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        Book selectedBook = books.get(row);
                        String bookContent = selectedBook.getContent();
                        ReadBookDialog readBookDialog = new ReadBookDialog(LMS.this, selectedBook.getTitle(), bookContent);
                        readBookDialog.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(LMS.this, "Please select a book to read.");
                    }
                }
            }
        });


        // Initialize the books list
        books = new ArrayList<>();
        loadLibraryData();
    }

    public void loadLibraryData() {

        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                Book book = new Book(values[0], values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]));
                books.add(book);
            }

            //DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            for (Book book : books) {
                tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getPublicationYear()});
            }
            table.getColumnModel().getColumn(3).setCellRenderer(new TableRenderBtn());
            table.getColumnModel().getColumn(3).setCellEditor(new TableBtnEditor(new JTextField()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveLibraryData(List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"))) {
            for (Book book : books) {
                writer.write(book.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            LMS lms = new LMS();
            lms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });

    }
}
