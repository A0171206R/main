package wallet.logic.parser;

import org.junit.jupiter.api.Test;
import wallet.logic.LogicManager;
import wallet.model.Wallet;
import wallet.model.WalletList;
import wallet.model.contact.Contact;
import wallet.model.contact.ContactList;
import wallet.model.record.BudgetList;
import wallet.model.record.Category;
import wallet.model.record.Expense;
import wallet.model.record.ExpenseList;
import wallet.model.record.Loan;
import wallet.model.record.LoanList;
import wallet.model.record.RecordList;

import java.text.ParseException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddCommandParserTest {
    //@@author kyang96
    @Test
    public void parseExpense_validInput_success() {
        AddCommandParser parser = new AddCommandParser();
        String input = "Lunch $10 Food /on 11/11/2019";
        Expense expense = parser.parseExpense(input);
        assertAll("Expense should contain correct input values",
            () -> assertEquals("Lunch", expense.getDescription()),
            () -> assertEquals(LocalDate.parse("2019-11-11"), expense.getDate()),
            () -> assertEquals(10.0, expense.getAmount()),
            () -> assertEquals(Category.FOOD, expense.getCategory()),
            () -> assertEquals(false, expense.isRecurring()),
            () -> assertEquals(null, expense.getRecFrequency())
        );
    }

    @Test
    public void parseExpense_validRecurringInput_success() {
        AddCommandParser parser = new AddCommandParser();
        String input = "Phone Bill $49 Bills /on 05/10/2019 /r monthly";
        Expense expense = parser.parseExpense(input);
        assertAll("Expense should contain correct input values",
            () -> assertEquals("Phone Bill", expense.getDescription()),
            () -> assertEquals(LocalDate.parse("2019-10-05"), expense.getDate()),
            () -> assertEquals(49.0, expense.getAmount()),
            () -> assertEquals(Category.BILLS, expense.getCategory()),
            () -> assertEquals(true, expense.isRecurring()),
            () -> assertEquals("MONTHLY", expense.getRecFrequency())
        );
    }
    //@@author

    //@@author A0171206R
    @Test
    public void parseLoan_validInput_success() throws ParseException {
        AddCommandParser parser = new AddCommandParser();
        WalletList dummyWalletList = new WalletList();
        Wallet dummyWallet = new Wallet(new BudgetList(),
                new RecordList(),
                new ExpenseList(),
                new ContactList(),
                new LoanList());

        //dummy Contact object
        Contact person = new Contact("Mary", "Friend", "1234 5678");
        person.setId(1);

        ContactList contactList = dummyWallet.getContactList();
        contactList.addContact(person);

        dummyWalletList.getWalletList().add(dummyWallet);
        LogicManager.setWalletList(dummyWalletList);

        String input = "lunch $10 21/09/2019 /b /c 1";
        Loan loan = parser.parseLoan(input);
        assertAll("Loan should contain correct input values",
            () -> assertEquals("lunch", loan.getDescription()),
            () -> assertEquals(10.0, loan.getAmount()),
            () -> assertEquals(LocalDate.parse("2019-09-21"), loan.getDate()),
            () -> assertEquals(false, loan.getIsLend()),
            () -> assertEquals(false, loan.getIsSettled()),
            () -> assertEquals(contactList.getContactList().get(contactList.findIndexWithId(1)), loan.getPerson())
        );
    }
    //@@author
}
