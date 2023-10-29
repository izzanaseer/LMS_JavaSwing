import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class ReadBookDialog extends JDialog {
    private JTextArea textArea;
    public ReadBookDialog(JFrame parent, String bookTitle, String bookContent) {
        super(parent, "Read Book: " + bookTitle, true);

        textArea = new JTextArea(20, 60);
        textArea.setText(bookContent);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);
        JButton closeButton = new JButton("Close");

        closeButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "Do you want to exit reading the book?",
                    "Confirm Exit", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(ReadBookDialog.this,
                        "Do you want to exit reading the book?",
                        "Confirm Exit", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
    }
}
