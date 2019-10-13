package wallet.logic.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import wallet.model.Wallet;
import wallet.model.record.Expense;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditCommandTest {
    private static Wallet testWallet = new Wallet();

    @BeforeAll
    public static void setUp() {
        testWallet.getExpenseList().addExpense(new Expense("Lunch", LocalDate.now(), 3, "Food", false, null));
        testWallet.getExpenseList().addExpense(new Expense("Dinner", LocalDate.now(), 5, "Food", false, null));
    }

    @Test
    public void execute_validId_success() {
        Expense expense = testWallet.getExpenseList().findExpenseWithId(1);
        expense.setDescription("Supper");
        expense.setAmount(8);
        EditCommand command = new EditCommand(expense);
        command.execute(testWallet);
        Expense e = testWallet.getExpenseList().findExpenseWithId(1);
        assertAll("Expense should be updated with new values",
            () -> assertEquals(1, e.getId()),
            () -> assertEquals("Supper", e.getDescription()),
            () -> assertEquals(LocalDate.now(), e.getDate()),
            () -> assertEquals(8.0, e.getAmount()),
            () -> assertEquals("Food", e.getCategory()),
            () -> assertEquals(false, e.isRecurring()),
            () -> assertEquals(null, e.getRecFrequency())
        );
    }
}
