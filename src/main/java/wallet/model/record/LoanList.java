package wallet.model.record;

import wallet.logic.command.ListCommand;

import java.util.ArrayList;

public class LoanList {
    private boolean isModified = false;
    private ArrayList<Loan> loanList;

    public LoanList(ArrayList<Loan> loanList) {
        this.loanList = loanList;
    }

    public LoanList() {
        this.loanList = new ArrayList<Loan>();
    }

    /**
     * Returns the list of loans.
     *
     * @return The list of loans.
     */
    public ArrayList<Loan> getLoanList() {
        return this.loanList;
    }

    /**
     * Returns true if list is modified, else false.
     */
    public boolean getIsModified() {
        return isModified;
    }

    /**
     * Sets status of whether list is modified.
     */
    public void setModified(boolean modified) {
        isModified = modified;
    }

    /**
     * Sets a LoanList.
     *
     * @param loanList The list of loans.
     */
    public void setLoanList(ArrayList<Loan> loanList) {
        this.loanList = loanList;
    }

    /**
     * Adds a Loan object to the list of loans.
     *
     * @param loan The Loan object.
     */
    public void addLoan(Loan loan) {
        loan.setId(getLargestId(this.loanList) + 1);
        this.loanList.add(loan);
    }

    /**
     * Gets the Loan object based on index in list of loans.
     *
     * @param index The index of the Loan object in the list of loans.
     * @return
     */
    public Loan getLoan(int index) {
        return this.loanList.get(index);
    }

    /**
     * Edits the Loan object in the list of loans.
     *
     * @param index The index of the Loan object in the list of loans.
     * @param loan  The Loan object.
     */
    public void editLoan(int index, Loan loan) {
        this.loanList.set(index, loan);
    }

    /**
     * Returns the index of Loan object in the list of loans.
     *
     * @param loan The Loan object.
     * @return Index of Loan object in list of loans.
     */
    public int findLoanIndex(Loan loan) {
        return this.loanList.indexOf(loan);
    }

    /**
     * Returns size of the list of loans.
     *
     * @return Size of the list of loans.
     */
    public int getSize() {
        return this.loanList.size();
    }

    /**
     * Lists the list of loans.
     */
    public void listLoanList() {
        int counter = 1;
        System.out.println(ListCommand.MESSAGE_LIST_LOANS);
        for (Loan l : this.loanList) {
            System.out.println(counter + ". " + l.toString());
            counter++;
        }
    }

    /**
     * Returns the largest id.
     *
     * @param loanList The list of loans.
     * @return The largest id.
     */
    public int getLargestId(ArrayList<Loan> loanList) {
        int max = 0;
        for (Loan loan : loanList) {
            if (loan.getId() > max) {
                max = loan.getId();
            }
        }
        return max;
    }
}