//@@author Xdecosee

package wallet.logic.parser;

import com.opencsv.CSVReader;
import wallet.logic.command.ImportCommand;
import wallet.model.contact.Contact;
import wallet.model.record.Category;
import wallet.model.record.Expense;
import wallet.model.record.ExpenseList;
import wallet.model.record.Loan;
import wallet.model.record.LoanList;
import wallet.model.record.RecurrenceRate;
import wallet.ui.Ui;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ImportCommandParser implements Parser<ImportCommand> {
    public static final String MESSAGE_ERROR_WRONG_FORMAT = "Wrong import command syntax!";
    public static final String MESSAGE_ERROR_READING_CSV = "Error in importing csv!";
    public static final String MESSAGE_ERROR_CSV_FORMAT = "CSV is formatted wrongly";

    /**
     * Returns a ImportCommand object.
     *
     * @param input User input of command.
     * @return ImportCommand Object.
     */
    @Override
    public ImportCommand parse(String input) {
        String[] arguments = input.split(" ", 2);
        if (arguments.length != 2) {
            Ui.printError(MESSAGE_ERROR_WRONG_FORMAT);
            return null;
        }
        arguments[0] = arguments[0].toLowerCase();
        if ("loan".equals(arguments[0])) {
            ArrayList<Loan> data = parseLoans(arguments[1]);
            if (data != null) {
                LoanList newList = new LoanList();
                newList.setLoanList(data);
                return new ImportCommand(newList);
            } else {
                return null;
            }
        } else if ("expense".equals(arguments[0])) {
            ArrayList<Expense> data = parseExpenses(arguments[1]);
            if (data != null) {
                ExpenseList newList = new ExpenseList();
                newList.setExpenseList(data);
                return new ImportCommand(newList);
            } else {
                return null;
            }
        }

        Ui.printError(MESSAGE_ERROR_WRONG_FORMAT);
        return null;
    }

    /**
     * Read and format loan entries from csv files.
     *
     * @param fileName File Name.
     * @return list of formatted data.
     */
    private ArrayList<Loan> parseLoans(String fileName) {
        ArrayList<Loan> data = new ArrayList<>();
        try {
            File current = new File(ImportCommand.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI().getPath());
            File csv = new File(current.getParentFile().getPath(), fileName);
            FileReader filereader = new FileReader(csv);
            CSVReader csvReader = new CSVReader(filereader);
            List<String[]> fileContent = csvReader.readAll();

            for (String[] s : fileContent) {
                //@@author Xdecosee-reused
                String description = s[0].trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate createdDate = LocalDate.parse(s[1].trim(), formatter);
                double amount = Double.parseDouble(s[2].trim());
                boolean isLend;
                boolean isSettled;
                String name;
                String details = null;
                String phoneNum = null;
                if (s[3].trim().equalsIgnoreCase("lend")) {
                    isLend = true;
                } else if (s[3].trim().equalsIgnoreCase("borrow")) {
                    isLend = false;
                } else {
                    Ui.printError(MESSAGE_ERROR_CSV_FORMAT);
                    return null;
                }

                if (s[4].trim().equalsIgnoreCase("yes")) {
                    isSettled = true;
                } else if (s[4].trim().equalsIgnoreCase("no")) {
                    isSettled = false;
                } else {
                    Ui.printError(MESSAGE_ERROR_CSV_FORMAT);
                    return null;
                }

                if (s[5].trim().isEmpty()) {
                    Ui.printError(MESSAGE_ERROR_CSV_FORMAT);
                    return null;
                } else {
                    name = s[5].trim();
                }

                if (s.length == 7) {
                    if (s[6].trim().isEmpty()) {
                        details = null;
                    } else {
                        details = s[6].trim();
                    }
                }

                if (s.length == 8) {
                    if (s[7].trim().isEmpty()) {
                        phoneNum = null;
                    } else {
                        phoneNum = s[7].trim();
                    }
                }

                Contact person = new Contact(name, details, phoneNum);
                Loan loan = new Loan(description, createdDate, amount, isLend, isSettled, person);
                data.add(loan);
            }

            csvReader.close();
            filereader.close();


        } catch (URISyntaxException | IOException | NullPointerException e) {
            Ui.printError(MESSAGE_ERROR_READING_CSV);
            return null;
        } catch (DateTimeParseException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            Ui.printError(MESSAGE_ERROR_CSV_FORMAT);
            return null;
        }
        return data;
    }

    /**
     * Read and format expense entries from csv files.
     *
     * @param fileName File Name.
     * @return list of formatted data.
     */
    private ArrayList<Expense> parseExpenses(String fileName) {
        ArrayList<Expense> data = new ArrayList<>();
        try {
            File current = new File(ImportCommand.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI().getPath());
            File csv = new File(current.getParentFile().getPath(), fileName);
            FileReader filereader = new FileReader(csv);
            CSVReader csvReader = new CSVReader(filereader);
            List<String[]> fileContent = csvReader.readAll();
            for (String[] s : fileContent) {
                //@@author Xdecosee-reused
                String desc = s[0].trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(s[1].trim(), formatter);

                Double amount = Double.parseDouble(s[2].trim());
                Category cat = Category.getCategory(s[3]);

                if (cat == null) {
                    Ui.printError(MESSAGE_ERROR_CSV_FORMAT);
                    return null;
                }
                boolean isRecurring = false;
                RecurrenceRate freq = RecurrenceRate.NO;
                if (s.length == 6 && s[4].trim().equalsIgnoreCase("yes")) {
                    isRecurring = true;
                    date = LocalDate.parse(s[1].trim(), formatter);
                    freq = RecurrenceRate.getRecurrence(s[5].trim());
                    if (freq == null) {
                        Ui.printError(MESSAGE_ERROR_CSV_FORMAT);
                        return null;
                    }
                }
                Expense expense = new Expense(desc, date, amount, cat, isRecurring, freq);
                data.add(expense);
            }
            csvReader.close();
            filereader.close();

        } catch (URISyntaxException | IOException | NullPointerException e) {
            Ui.printError(MESSAGE_ERROR_READING_CSV);
            return null;
        } catch (NumberFormatException | DateTimeParseException | ArrayIndexOutOfBoundsException e) {
            Ui.printError(MESSAGE_ERROR_CSV_FORMAT);
            return null;
        }
        return data;
    }

}
