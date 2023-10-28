import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
    private static final String FILE_NAME = "data.txt";

    public LMS() {
        super("User Friendly GUI");

        // Create the JTable
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Title", "Author", "Publication Year", "Read Item"}, 0);


        // Create the JScrollPane
        scrollPane = new JScrollPane(table);

        // Create the JPanel to contain the buttons
        buttonPanel = new JPanel();

        // Create the buttons
        addItemButton = new JButton("Add Item");
        editItemButton = new JButton("Edit Item");
        deleteItemButton = new JButton("Delete Item");

        // Add the buttons to the JPanel
        buttonPanel.add(addItemButton);
        buttonPanel.add(editItemButton);
        buttonPanel.add(deleteItemButton);

        // Add the JScrollPane and JPanel to the JFrame window
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Pack the JFrame window and make it visible
        pack();
        setVisible(true);

        // Add listeners to the buttons
        addItemButton.addActionListener(e -> {
            // Add a new item to the table
        });

        editItemButton.addActionListener(e -> {
            // Edit the selected item in the table
        });

        deleteItemButton.addActionListener(e -> {
            // Delete the selected item from the table
        });
    }

    public void loadLibraryData() {
        List<Book> books = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/izzanaseer/Documents/FAST/sem5/SCD/SCD Assignment3/LMS_Swing/src/data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                Book book = new Book(values[0], values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]));
                books.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.books = books;
    }

    public void saveLibraryData(List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/izzanaseer/Documents/FAST/sem5/SCD/SCD Assignment3/LMS_Swing/src/data.txt"))) {
            for (Book book : books) {
                writer.write(book.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Create a new LMS object
        LMS lms = new LMS();

        // Load the library data from the file
        lms.loadLibraryData();
    }
}

