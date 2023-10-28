public class Book {
    private String title;
    public String author;
    public int year;
    private int popularityCount;
    Book(String t, String a, int y, int pCount) {
        this.title = t;
        this.author = a;
        this.year = y;
        this.popularityCount = pCount;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
