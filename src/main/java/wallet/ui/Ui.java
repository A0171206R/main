package wallet.ui;

import wallet.logic.LogicManager;
import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.thread.ChartThread;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    /**
     * Scanner object used for reading input from user.
     */
    private Scanner sc;

    /**
     * Constructs a new ui.Ui object.
     */
    public Ui() {
        sc = new Scanner(System.in);
    }

    //@@author matthewng1996

    /**
     * Prints the welcome message of the program.
     */
    public void welcomeMsg() {
        String logo = "__             __       _   _         \n"
                + "\\ \\    __     / /      | | | |        _\n"
                + " \\ \\  /  \\   / /___,__ | | | | ______| |_    \n"
                + "  \\ \\/ /\\  \\/ /  [] | || | | |  []_\\_  __|\n"
                + "   \\__/  \\___/ \\__,_|_||_| |_|\\___/  |_|\n";
        System.out.println("Hello from\n" + logo);

        printLine();
        System.out.println("Hello! Welcome to the WalletCLi Application!");
        System.out.println("What can I do for you?");
        printLine();
    }

    //@@author

    /**
     * Prints the goodbye message when the user exits the program.
     */
    public void byeMsg() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prompts the user for input.
     *
     * @return The input of the user.
     */
    public String readLine() {
        return sc.nextLine();
    }

    /**
     * Prints the separator lines for UI.
     */
    public void printLine() {
        System.out.println("_______________________________________________________________________________");
    }

    //@@author kyang96

    /**
     * Displays the expense list in table format.
     */
    public static void printExpenseTable(ArrayList<Expense> expenseList) {
        System.out.println("Here are the expenses in your list:");
        System.out.println("-----------------------------------------------------"
                + "-------------------------------------------------\n"
                + "|  ID  |              Description                 |"
                + "  Category  |    Date    |   Amount   | Recurring |\n"
                + "|---------------------------------------------------------------"
                + "-------------------------------------|");
        double total = 0;
        for (Expense e : expenseList) {
            if (e.isRecurring()) {
                System.out.printf("| %-4d | %-40s | %-10s | %-10s |  $%-7.2f  |  %-7s  |\n", e.getId(),
                        e.getDescription(), e.getCategory(), e.getDate(), e.getAmount(), e.getRecFrequency());
            } else {
                System.out.printf("| %-4d | %-40s | %-10s | %-10s |  $%-7.2f  |  %-7s  |\n",
                        e.getId(), e.getDescription(), e.getCategory(), e.getDate(), e.getAmount(), "No");

            }
            total += e.getAmount();
        }
        System.out.println("-----------------------------------------------------"
                + "-------------------------------------------------");
        System.out.printf("Total amount spent: $%.2f\n", total);
    }
    //@@author

    //@@author matthewng1996
    public void drawPieChart() {
        ChartThread chartThread = new ChartThread();
        System.out.println("Please wait while we draw the pie chart...");
    }
    //@@author

    //@@author Xdecosee

    /**
     * Displays the contact list in table format.
     */
    public static void printContactTable() {
        ArrayList<Contact> contactListCopy = LogicManager.getWallet().getContactList().getContactList();
        String dash = "-";
        String lineBreak = new String(new char[100]).replace("\0", dash);
        String headerBreak = new String(new char[98]).replace("\0", dash);
        System.out.println(lineBreak);
        System.out.printf("| %-4s | %-20s | %-20s | %-43s |\n", "ID", "Name", "Phone", "Detail");
        System.out.println("|" + headerBreak + "|");
        for (Contact c : contactListCopy) {
            String id = Integer.toString(c.getId()).trim();
            String name = c.getName();
            String phone = c.getPhoneNum();
            String detail = c.getDetail();

            if (phone == null) {
                phone = "";
            }

            if (detail == null) {
                detail = "";
            }

            System.out.printf("| %-4s | %-20s | %-20s | %-43s |\n", id, name, phone, detail);
        }
        System.out.println(lineBreak);
    }
    //@@author

    /**
     * Displays the loan list in table format.
     */
    public static void printLoanTable(ArrayList<Loan> loanList) {

        System.out.println("Here are the loans in your list:");
        printLoanTableHeaders();
        for (Loan loan : loanList) {
            printLoanRow(loan);
        }
        printLoanTableClose();
    }

    /**
     * Default headers for Loan table.
     */
    public static void printLoanTableHeaders() {
        System.out.println("--------------------------------------------------------"
                + "-------------------------------------------------------"
                + "-------------------------------------\n"
                + "|  ID  |  Settled  |              Description                 |  Amount  |    Date    |   "
                + "Borrow/Lend   |    Contact Name    |    Contact Number   |\n"
                + "|-----------------------------------------------------"
                + "---------------------------------------------------------"
                + "------------------------------------|");
    }

    /**
     * Prints the specified Loan object in table format.
     *
     * @param loan The Loan object.
     */
    public static void printLoanRow(Loan loan) {
        if (!loan.getIsLend() && !loan.getIsSettled()) {
            System.out.printf("| %-4d |  %-7s  | %-40s | $%-7.2f | %-10s |   %-11s   | %-18s | %-19s |\n",
                    loan.getId(), "No", loan.getDescription(), loan.getAmount(), loan.getDate(), "Borrow from",
                    loan.getPerson().getName(), loan.getPerson().getPhoneNum());
        } else if (!loan.getIsLend() && loan.getIsSettled()) {
            System.out.printf("| %-4d |  %-7s  | %-40s | $%-7.2f | %-10s |   %-11s   | %-18s | %-19s |\n",
                    loan.getId(), "Yes", loan.getDescription(), loan.getAmount(), loan.getDate(), "Borrow from",
                    loan.getPerson().getName(), loan.getPerson().getPhoneNum());
        } else if (loan.getIsLend() && !loan.getIsSettled()) {
            System.out.printf("| %-4d |  %-7s  | %-40s | $%-7.2f | %-10s |   %-11s   | %-18s | %-19s |\n",
                    loan.getId(), "No", loan.getDescription(), loan.getAmount(), loan.getDate(), "Lend to",
                    loan.getPerson().getName(), loan.getPerson().getPhoneNum());
        } else if (loan.getIsLend() && loan.getIsSettled()) {
            System.out.printf("| %-4d |  %-7s  | %-40s | $%-7.2f | %-10s |   %-11s   | %-18s | %-19s |\n",
                    loan.getId(), "Yes", loan.getDescription(), loan.getAmount(), loan.getDate(), "Lend to",
                    loan.getPerson().getName(), loan.getPerson().getPhoneNum());
        }
    }

    /**
     * Prints line to close of the Loans table.
     */
    public static void printLoanTableClose() {
        System.out.println("----------------------------------------"
                + "---------------------------------------------------"
                + "--------------------------------------------------------");
    }
}
