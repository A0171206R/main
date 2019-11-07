package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.ui.Ui;

import java.util.ArrayList;

/**
 * EditCommand Class deals with commands that involves
 * in editing a specific object
 * in the a specific list.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_USAGE = "Format for edit command:"
            + "\nedit expense <id> [/d <description>] [/t <date>] [/a <amount>] [/c <category>] [/r <recurrence rate>]"
            + "\nExample: " + COMMAND_WORD + " expense 2 /d Dinner /a 4.50 /c Food /t 10/10/2019 /r daily"
            + "\nExample: " + COMMAND_WORD + " expense 2 /d Lunch /a 9 /c Food /r no";
    public static final String MESSAGE_SUCCESS_EDIT_EXPENSE = "Successfully edited this expense:";
    public static final String MESSAGE_SUCCESS_EDIT_CONTACT = "Successfully edited this contact:";
    public static final String MESSAGE_SUCCESS_EDIT_LOAN = "Successfully edited this loan:";
    public static final String MESSAGE_ERROR_FORMAT = "Your format for edit command is wrong.";
    public static final String MESSAGE_ERROR_COMMAND = "An error encountered while executing command.";

    private Expense expense;
    private Contact contact;
    private Loan loan;

    /**
     * Constructs the EditCommand object with Expense object.
     *
     * @param expense The Expense Object.
     */
    public EditCommand(Expense expense) {
        this.expense = expense;
    }

    /**
     * Constructs the EditCommand object with Contact object.
     *
     * @param contact The Contact Object.
     */
    public EditCommand(Contact contact) {
        this.contact = contact;
    }

    /**
     * Constructs the EditCommand object with Loan object.
     *
     * @param loan The Loan Object.
     */
    public EditCommand(Loan loan) {
        this.loan = loan;
    }

    @Override
    public boolean execute(Wallet wallet) {
        if (expense != null) {
            //@@author kyang96
            int index = wallet.getExpenseList().findIndexWithId(expense.getId());
            Expense currentExpense = wallet.getExpenseList().getExpense(index);
            if (expense.getRecFrequency() == null || !expense.getRecFrequency().equals("")) {
                currentExpense.setRecurring(expense.isRecurring());
                currentExpense.setRecFrequency(expense.getRecFrequency());
            }
            if (expense.getDate() != null) {
                currentExpense.setDate(expense.getDate());
            }
            if (expense.getAmount() != 0.0) {
                currentExpense.setAmount(expense.getAmount());
            }
            if (expense.getCategory() != null) {
                currentExpense.setCategory(expense.getCategory());
            }
            if (expense.getDescription() != null) {
                currentExpense.setDescription(expense.getDescription());
            }
            wallet.getExpenseList().editExpense(index, currentExpense);
            wallet.getExpenseList().setModified(true);
            System.out.println(MESSAGE_SUCCESS_EDIT_EXPENSE);
            Ui.printExpense(currentExpense);
        } else if (contact != null) {
            //@@author Xdecosee
            int index = wallet.getContactList().findIndexWithId(contact.getId());
            if (index != -1) {
                Contact currentContact = wallet.getContactList().getContact(index);
                ArrayList<Loan> loanList = wallet.getLoanList().getLoanList();
                if (contact.getName() != null) {
                    currentContact.setName(contact.getName());
                }

                if ("".equals(contact.getDetail())) {
                    currentContact.setDetail(null);
                } else if (contact.getDetail() != null) {
                    currentContact.setDetail(contact.getDetail());
                }

                if ("".equals(contact.getPhoneNum())) {
                    currentContact.setPhoneNum(null);
                } else if (contact.getPhoneNum() != null) {
                    currentContact.setPhoneNum(contact.getPhoneNum());
                }
                wallet.getContactList().editContact(index, currentContact);
                wallet.getContactList().setModified(true);

                for (Loan l : loanList) {
                    if (l.getPerson().getId() == currentContact.getId()) {
                        int loanIndex = wallet.getLoanList().findIndexWithId(l.getId());
                        Loan toUpdate = wallet.getLoanList().getLoan(loanIndex);
                        toUpdate.setPerson(currentContact);
                        wallet.getLoanList().editLoan(loanIndex, toUpdate);
                        wallet.getLoanList().setModified(true);

                    }
                }

                System.out.println(MESSAGE_SUCCESS_EDIT_CONTACT);
                Ui.printContact(currentContact);
            } else {
                System.out.println(MESSAGE_ERROR_COMMAND);
            }
            //@@author
        } else if (loan != null) {
            //@@author A0171206R
            Loan updatedLoan = new Loan();
            updatedLoan.setId(loan.getId());
            updatedLoan.setDescription(loan.getDescription());
            updatedLoan.setIsSettled(loan.getIsSettled());
            updatedLoan.setAmount(loan.getAmount());
            updatedLoan.setPerson(loan.getPerson());
            updatedLoan.setIsLend(loan.getIsLend());
            updatedLoan.setDate(loan.getDate());

            int index = wallet.getLoanList().findIndexWithId(updatedLoan.getId());

            if (index != -1) {
                wallet.getLoanList().editLoan(index, updatedLoan);
                wallet.getLoanList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_EDIT_LOAN);
                Ui.printLoanTableHeaders();
                Ui.printLoanRow(updatedLoan);
                Ui.printLoanTableClose();
            } else {
                System.out.println(MESSAGE_ERROR_COMMAND);
            }
        }
        //@@author
        return false;
    }
}
