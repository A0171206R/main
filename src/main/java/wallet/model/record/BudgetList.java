package wallet.model.record;

import java.util.ArrayList;

public class BudgetList {

    private ArrayList<Budget> budgetList;

    /**
     * Constructs a new BudgetList object.
     */
    public BudgetList() {
        this.budgetList = new ArrayList<Budget>();
    }

    /**
     * Constructs a new BudgetList object with populated list.
     */
    public BudgetList(ArrayList<Budget> budgetList) {
        this.budgetList = budgetList;
    }

    /**
     * Returns the list of budgets.
     *
     * @return The list of budgets.
     */
    public ArrayList<Budget> getBudgetList() {
        return budgetList;
    }

    /**
     * Add the given budget into the expenseList.
     *
     * @param b The budget to be added.
     */
    public void addBudget(Budget b) {
        try {
            budgetList.add(b);
        } catch (NullPointerException e) {
            System.out.println("Budget addition failed. Please try again.");
        }
    }

    public void editBudget(int index, Budget budget) {
        budgetList.set(index, budget);
    }
}
