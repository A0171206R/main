//@@author matthewng1996

package wallet.logic.parser;

import wallet.exception.WrongParameterFormat;
import wallet.logic.command.SetBudgetCommand;
import wallet.model.record.Budget;

public class SetBudgetParser implements Parser<SetBudgetCommand> {

    @Override
    public SetBudgetCommand parse(String input) throws NumberFormatException {
        String[] arguments = input.split(" ", 2);
        String[] amount = arguments[0].split("\\$", 2);

        Double budgetAmount = Double.parseDouble(amount[1].trim());
        String[] monthYear = arguments[1].split("/", 2);
        int month = Integer.parseInt(monthYear[0].trim());
        int year = Integer.parseInt(monthYear[1].trim());
        if (month < 0 || month > 12) {
            throw new WrongParameterFormat("Nice try but month only runs for 1 to 12 :)");
        } else if (year <= 0) {
            throw  new WrongParameterFormat("I too wonder if zero or negative year exists...");
        }
        Budget budget = new Budget(budgetAmount, month, year, false, 0);
        return new SetBudgetCommand(budget);
    }
}
