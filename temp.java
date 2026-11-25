import java.util.*;

class Expense {
    int id;
    String name;
    String category;
    double amount;
    String date;

    Expense(int id, String name, String category, double amount, String date) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String toString() {
        return "ID: " + id +
               " | Name: " + name +
               " | Category: " + category +
               " | Amount: Rs." + amount +
               " | Date: " + date;
    }
}

public class temp {

    static LinkedList<Expense> expenses = new LinkedList<>();
    static Stack<Integer> undoStack = new Stack<>();
    static int nextId = 1;
    static Scanner sc = new Scanner(System.in);

    // Add Expense using Linked List
    static void addExpense() {
        System.out.print("Enter Expense Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        System.out.print("Enter Amount: ");
        double amount = Double.parseDouble(sc.nextLine());

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        System.out.print("Enter Date (DD-MM-YYYY): ");
        String date = sc.nextLine();

        Expense e = new Expense(nextId++, name, category, amount, date);

        expenses.add(e);
        undoStack.push(e.id);

        System.out.println("Expense Added Successfully!");
    }

    // Display All Expenses
    static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No Expenses Found!");
            return;
        }

        System.out.println("\n----- All Expenses -----");

        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    // Sort Expenses using Bubble Sort
    static void sortExpenses() {
        if (expenses.size() < 2) {
            System.out.println("Not Enough Expenses to Sort.");
            return;
        }

        Expense[] arr = expenses.toArray(new Expense[0]);

        System.out.println("1. Sort by Amount");
        System.out.println("2. Sort by Expense ID");
        System.out.print("Choose Option: ");

        int choice = Integer.parseInt(sc.nextLine());

        if (choice != 1 && choice != 2) {
            System.out.println("Invalid Choice!");
            return;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {

                boolean swap;

                if (choice == 1) {
                    swap = arr[j].amount > arr[j + 1].amount;
                } else {
                    swap = arr[j].id > arr[j + 1].id;
                }

                if (swap) {
                    Expense temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        System.out.println("\n----- Sorted Expenses -----");

        for (Expense e : arr) {
            System.out.println(e);
        }
    }

    // Binary Search using Expense ID
    static void searchExpense() {
        if (expenses.isEmpty()) {
            System.out.println("No Expenses Available!");
            return;
        }

        Expense[] arr = expenses.toArray(new Expense[0]);

        // Sort by ID before Binary Search
        Arrays.sort(arr, Comparator.comparingInt(e -> e.id));

        System.out.print("Enter Expense ID to Search: ");
        int key = Integer.parseInt(sc.nextLine());

        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid].id == key) {
                System.out.println("Expense Found!");
                System.out.println(arr[mid]);
                return;
            } else if (arr[mid].id < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Expense Not Found!");
    }

    // Stack: Undo Last Added Expense
    static void undoExpense() {
        if (undoStack.isEmpty()) {
            System.out.println("No Expense Available to Undo!");
            return;
        }

        int id = undoStack.pop();

        Iterator<Expense> iterator = expenses.iterator();

        while (iterator.hasNext()) {
            Expense e = iterator.next();

            if (e.id == id) {
                iterator.remove();
                System.out.println("Last Added Expense Removed Successfully!");
                return;
            }
        }

        System.out.println("Expense Not Found!");
    }

    // Calculate Total Expenses
    static void totalExpenses() {
        double total = 0;

        for (Expense e : expenses) {
            total += e.amount;
        }

        System.out.println("Total Expenses: Rs." + total);
    }

    // Main Menu
    public static void main(String[] args) {

        while (true) {
            System.out.println("\n================================");
            System.out.println("       EXPENSE TRACKER");
            System.out.println("================================");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Sort Expenses");
            System.out.println("4. Search Expense");
            System.out.println("5. Undo Last Expense");
            System.out.println("6. Calculate Total Expenses");
            System.out.println("7. Exit");

            System.out.print("Enter Your Choice: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        addExpense();
                        break;

                    case 2:
                        viewExpenses();
                        break;

                    case 3:
                        sortExpenses();
                        break;

                    case 4:
                        searchExpense();
                        break;

                    case 5:
                        undoExpense();
                        break;

                    case 6:
                        totalExpenses();
                        break;

                    case 7:
                        System.out.println("Thank You for Using Expense Tracker!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid Choice! Try Again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please Enter a Valid Number!");
            }
        }
    }
}