import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class LibraryManagementSystem {

    // Book class
    public static class Book {
        private final int id;
        private final String title;
        private final String author;
        private final int yearPublished;
        private final String category;

        public Book(int id, String title, String author, int yearPublished, String category) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.yearPublished = yearPublished;
            this.category = category;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public int getYearPublished() {
            return yearPublished;
        }

        public String getCategory() {
            return category;
        }

        public void displayDetails() {
            System.out.println("Book ID: " + id);
            System.out.println("Title: " + title);
            System.out.println("Author: " + author);
            System.out.println("Year: " + yearPublished);
            System.out.println("Category: " + category);
        }
    }

    // eBook subclass
    public static class EBook extends Book {
        private final String fileFormat;

        public EBook(int id, String title, String author, int yearPublished, String category, String fileFormat) {
            super(id, title, author, yearPublished, category);
            this.fileFormat = fileFormat;
        }

        @Override
        public void displayDetails() {
            super.displayDetails();
            System.out.println("Format: " + fileFormat);
        }
    }

    // PrintedBook subclass
    public static class PrintedBook extends Book {
        private final int pages;

        public PrintedBook(int id, String title, String author, int yearPublished, String category, int pages) {
            super(id, title, author, yearPublished, category);
            this.pages = pages;
        }

        @Override
        public void displayDetails() {
            super.displayDetails();
            System.out.println("Pages: " + pages);
        }
    }

    // Library class
    public static class Library {
        private List<Book> books = new ArrayList<>();
        private final Stack<Book> undoStack = new Stack<>();
        private final Stack<Book> redoStack = new Stack<>();

        public void addBook(Book book) {
            books.add(book);
            undoStack.push(book);
            redoStack.clear();
        }

        public void removeBook(int id) {
            books.removeIf(book -> book.getId() == id);
        }

        public void undo() {
            if (!undoStack.isEmpty()) {
                Book lastBook = undoStack.pop();
                books.remove(lastBook);
                redoStack.push(lastBook);
                System.out.println("Undo: Removed " + lastBook.getTitle());
            } else {
                System.out.println("Nothing to undo.");
            }
        }

        public void redo() {
            if (!redoStack.isEmpty()) {
                Book book = redoStack.pop();
                books.add(book);
                undoStack.push(book);
                System.out.println("Redo: Re-added " + book.getTitle());
            } else {
                System.out.println("Nothing to redo.");
            }
        }

        public void displayBooks() {
            if (books.isEmpty()) {
                System.out.println("No books available.");
            } else {
                for (Book book : books) {
                    book.displayDetails();
                    System.out.println("---------");
                }
            }
        }

        // QuickSort
        public void quicksort() {
            books = quickSortHelper(new ArrayList<>(books));
            System.out.println("Books sorted using QuickSort.");
        }

        private List<Book> quickSortHelper(List<Book> list) {
            if (list.size() <= 1) return list;

            Book pivot = list.get(list.size() / 2);
            List<Book> left = new ArrayList<>();
            List<Book> right = new ArrayList<>();

            for (Book book : list) {
                if (book == pivot) continue;
                if (book.getTitle().compareToIgnoreCase(pivot.getTitle()) < 0)
                    left.add(book);
                else
                    right.add(book);
            }

            List<Book> sorted = new ArrayList<>(quickSortHelper(left));
            sorted.add(pivot);
            sorted.addAll(quickSortHelper(right));
            return sorted;
        }

        // MergeSort
        public void mergeSort() {
            books = mergeSortHelper(books);
            System.out.println("Books sorted using MergeSort.");
        }

        private List<Book> mergeSortHelper(List<Book> list) {
            if (list.size() <= 1) return list;

            int mid = list.size() / 2;
            List<Book> left = mergeSortHelper(new ArrayList<>(list.subList(0, mid)));
            List<Book> right = mergeSortHelper(new ArrayList<>(list.subList(mid, list.size())));

            return merge(left, right);
        }

        private List<Book> merge(List<Book> left, List<Book> right) {
            List<Book> result = new ArrayList<>();
            int i = 0, j = 0;

            while (i < left.size() && j < right.size()) {
                if (left.get(i).getTitle().compareToIgnoreCase(right.get(j).getTitle()) < 0)
                    result.add(left.get(i++));
                else
                    result.add(right.get(j++));
            }

            result.addAll(left.subList(i, left.size()));
            result.addAll(right.subList(j, right.size()));
            return result;
        }

        // Search
        public void searchBookByTitle(String title) {
            for (Book book : books) {
                if (book.getTitle().equalsIgnoreCase(title)) {
                    System.out.println("Book Found:");
                    book.displayDetails();
                    return;
                }
            }
            System.out.println("Book not found.");
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        while (true) {
            System.out.println("\n== Library Menu ==");
            System.out.println("1. Add Printed Book");
            System.out.println("2. Add eBook");
            System.out.println("3. Remove Book");
            System.out.println("4. Display Books");
            System.out.println("5. Undo");
            System.out.println("6. Redo");
            System.out.println("7. QuickSort");
            System.out.println("8. MergeSort");
            System.out.println("9. Search Book");
            System.out.println("10. Exit");

            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Title: ");
                    String title = scanner.nextLine();

                    System.out.print("Author: ");
                    String author = scanner.nextLine();

                    System.out.print("Year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Category: ");
                    String category = scanner.nextLine();

                    System.out.print("Pages: ");
                    int pages = scanner.nextInt();

                    library.addBook(new PrintedBook(id, title, author, year, category, pages));
                    break;

                case 2:
                    System.out.print("ID: ");
                    id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Title: ");
                    title = scanner.nextLine();

                    System.out.print("Author: ");
                    author = scanner.nextLine();

                    System.out.print("Year: ");
                    year = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Category: ");
                    category = scanner.nextLine();

                    System.out.print("Format: ");
                    String format = scanner.nextLine();

                    library.addBook(new EBook(id, title, author, year, category, format));
                    break;

                case 3:
                    System.out.print("Enter ID: ");
                    int removeId = scanner.nextInt();
                    library.removeBook(removeId);
                    break;

                case 4:
                    library.displayBooks();
                    break;

                case 5:
                    library.undo();
                    break;

                case 6:
                    library.redo();
                    break;

                case 7:
                    library.quicksort();
                    break;

                case 8:
                    library.mergeSort();
                    break;

                case 9:
                    System.out.print("Enter title: ");
                    String search = scanner.nextLine();
                    library.searchBookByTitle(search);
                    break;

                case 10:
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}