//@@author matthewng1996

package wallet.logic.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import wallet.model.Wallet;
import wallet.model.record.Budget;
import wallet.model.record.Category;
import wallet.model.record.Expense;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetBudgetTest {

    private static Wallet testWallet = new Wallet();

    /**
     * This method will add relevant expense and budget to the test wallet.
     */
    @BeforeAll
    public static void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date1 = "10/01/2019";
        String date2 = "15/02/2019";
        LocalDate localDate1 = LocalDate.parse(date1, formatter);
        LocalDate localDate2 = LocalDate.parse(date2, formatter);

        testWallet.getExpenseList().addExpense(new Expense("Lunch", localDate1, 3, Category.FOOD, false, null));
        testWallet.getExpenseList().addExpense(new Expense("Dinner", localDate2, 5, Category.FOOD, false, null));
        testWallet.getBudgetList().addBudget(new Budget(100, 10, 2019));
    }

    /**
     * Test for addition of budget with negative amount.
     */
    @Test
    public void executeAddBudgetFail() {
        Budget budget = new Budget(-10, 3, 2019);
        SetBudgetCommand budgetCommand = new SetBudgetCommand(budget);
        budgetCommand.execute(testWallet);
        assertEquals(3, testWallet.getBudgetList().getBudgetList().size());
    }

    /**
     * Test for budget with proper parameters.
     */
    @Test
    public void executeAddBudgetSuccess() {
        Budget budget = new Budget(50, 3, 2019);
        SetBudgetCommand budgetCommand = new SetBudgetCommand(budget);
        budgetCommand.execute(testWallet);
        assertEquals(3, testWallet.getBudgetList().getBudgetList().size());
    }

    /**
     * Test for removing budget.
     */
    @Test
    public void executeRemoveBudget() {
        Budget budget = new Budget(0, 3, 2019);
        SetBudgetCommand budgetCommand = new SetBudgetCommand(budget);
        budgetCommand.execute(testWallet);
        assertEquals(1, testWallet.getBudgetList().getBudgetList().size());

    }

    /**
     * Test for editing existing budget.
     */
    @Test
    public void executeEditBudget() {
        Budget budget = new Budget(150, 10, 2019);
        SetBudgetCommand budgetCommand = new SetBudgetCommand(budget);
        budgetCommand.execute(testWallet);
        assertEquals(150, budget.getAmount());
    }

    /**
     * Test for adding expenses after having an existing budget.
     */
    @Test
    public void executeReduceBudgetFromExistingExpenses() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date1 = "10/10/2019";
        LocalDate localDate1 = LocalDate.parse(date1, formatter);
        Expense expense = new Expense("Breakfast", localDate1, 10, Category.FOOD, false, null);
        AddCommand addCommand = new AddCommand(expense);
        addCommand.execute(testWallet);
        for (Budget b : testWallet.getBudgetList().getBudgetList()) {
            if (b.getMonth() == expense.getDate().getMonthValue() && b.getYear() == expense.getDate().getYear()) {
                assertEquals(90, b.getAmount());
            }
        }
    }

    /**
     * Test for adding budget after existing expenses, with user input as "Yes".
     */
    @Test
    public void executeAddBudgetWithExistingExpensesWithUserInputYes() {
        Budget budget = new Budget(100, 1, 2019);
        SetBudgetCommand budgetCommand = new SetBudgetCommand(budget);

        String input = "yes";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        double remainingBudget = budget.getAmount()
                - testWallet.getExpenseList().getMonthExpenses(budget.getMonth(), budget.getYear());
        budgetCommand.execute(testWallet);
        assertEquals(100, budget.getAmount());
        assertEquals(97, remainingBudget);
    }

    /**
     * Test for adding budget after existing expenses, with user input as "No".
     */
    @Test
    public void executeAddBudgetWithExistingExpensesWithUserInputNo() {
        Budget budget = new Budget(100, 1, 2019);
        SetBudgetCommand budgetCommand = new SetBudgetCommand(budget);

        String input = "no";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        budgetCommand.execute(testWallet);
        assertEquals(100, budget.getAmount());
    }
}
