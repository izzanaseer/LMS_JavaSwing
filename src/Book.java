import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Book {
    private String title;
    private String author;
    private int year;
    private int popularityCount;

    public Book(String title, String author, int year, int popularityCount) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.popularityCount = popularityCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return year;
    }

    public void setPublicationYear(int year) {
        this.year = year;
    }

    public int getPopularityCount() {
        return popularityCount;
    }

    public void setPopularityCount(int popularityCount) {
        this.popularityCount = popularityCount;
    }

    @Override
    public String toString() {
        return title + "," + author + "," + year + "," + popularityCount;
    }

    public String getContent() {
        String fileName = getTitle() + ".txt"; // Assuming the book title is a valid file name
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: Could not read content.";
        }
    }
}
