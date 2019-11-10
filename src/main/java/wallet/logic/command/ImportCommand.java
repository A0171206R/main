//@@author Xdecosee

package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.record.ExpenseList;
import wallet.model.record.LoanList;
import wallet.model.record.Expense;
import wallet.model.record.Loan;

import java.time.LocalDate;
import java.util.ArrayList;

public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    private static final String MESSAGE_SUCCESS_ADD_CONTACT = "Got it. I've added this contact:";
    private static final String MESSAGE_SUCCESS_ADD_EXPENSE = "Got it. I've added this expense:";
    private static final String MESSAGE_SUCCESS_ADD_LOAN = "Got it. I've added this loan:";
    private static final String MESSAGE_IMPORT_PROGRESS = "Importing records...";
    private static final String MESSAGE_IMPORT_FINISH = "Finish Import!";
    private LoanList loanList = null;
    private ExpenseList expenseList = null;

    /**
     * Constructs the ImportCommand object with LoanList object.
     *
     * @param newList list of new loan entries.
     */
    public ImportCommand(LoanList newList) {
        this.loanList = newList;
    }

    /**
     * Constructs the ImportCommand object with LoanList object.
     *
     * @param newList list of new expense entries.
     */
    public ImportCommand(ExpenseList newList) {
        this.expenseList = newList;
    }

    /**
     * Imports data into wallet.
     *
     * @param wallet The Wallet Object.
     * @return false.
     */
    @Override
    public boolean execute(Wallet wallet) {

        System.out.println(MESSAGE_IMPORT_PROGRESS + "\n");

        if (loanList != null) {

            ArrayList<Loan> loanData = loanList.getLoanList();

            for (Loan loan : loanData) {
                //@@author Xdecosee-reused
                int largestId = wallet.getContactList().getLargestId(wallet.getContactList().getContactList()) + 1;
                loan.getPerson().setId(largestId);
                wallet.getContactList().addContact(loan.getPerson());
                wallet.getContactList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_ADD_CONTACT);
                System.out.println(loan.getPerson().toString());

                wallet.getLoanList().addLoan(loan);
                wallet.getRecordList().addRecord(loan);
                wallet.getLoanList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_ADD_LOAN);
                System.out.println(loan.toString());
                System.out.println();

            }

        }

        if (expenseList != null) {

            //@@author Xdecosee-reused
            ArrayList<Expense> expenseData = expenseList.getExpenseList();
            for (Expense expense : expenseData) {
                wallet.getExpenseList().addExpense(expense);
                LocalDate date = expense.getDate();
                wallet.getRecordList().addRecord(expense);
                wallet.getExpenseList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_ADD_EXPENSE);
                System.out.println(expense.toString());
                System.out.println();
            }
        }

        System.out.println(MESSAGE_IMPORT_FINISH);
        return false;
    }
}
