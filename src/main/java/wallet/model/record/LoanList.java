//@@author A0171206R

package wallet.model.record;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LoanList {
    private boolean isModified = false;
    private ArrayList<Loan> loanList;

    public LoanList(ArrayList<Loan> loanList) {
        this.loanList = loanList;
        checkUnsettledLoan();
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
     * Returns size of the list of loans.
     *
     * @return Size of the list of loans.
     */
    public int getSize() {
        return this.loanList.size();
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

    /**
     * Deletes an expense using its id.
     * @param id The id of the expense to delete.
     * @return
     */
    public Loan deleteLoan(int id) {
        int index = findIndexWithId(id);
        if (index >= 0) {
            Loan loan = getLoan(index);
            loanList.remove(index);
            return loan;
        }
        return null;
    }

    /**
     * Finds and returns expense index using its id.
     * @param id The id of the expense to find.
     * @return
     */
    public int findIndexWithId(int id) {
        int index = 0;
        for (Loan l : this.loanList) {
            if (l.getId() == id) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * Finds and returns the Loan object using its id.
     * @param id The id of the loan object.
     * @return The loan object.
     */
    public Loan findLoanWithId(int id) {
        for (Loan l : this.loanList) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }

    /**
     * Checks whether there are unsettled loans.
     *
     * @return false if there are no unsettled loans. True, otherwise.
     */
    public boolean checkUnsettledLoan() {
        for (Loan l: this.loanList) {
            if (!l.getIsSettled()) {
                return true;
            }
        }
        return false;
    }

    //@@author matthewng1996

    /**
     * Returns all loans sorted by date.
     * @return returns a sorted Arraylist of loans.
     */
    public ArrayList<Loan> sortByDate() {
        ArrayList<Loan> loanSortedByDate = new ArrayList<>();
        loanSortedByDate = this.loanList;
        Comparator<Loan> comparator = Comparator.comparing(Loan::getDate);
        Collections.sort(loanSortedByDate, comparator);
        return loanSortedByDate;
    }

    /**
     * Returns only loans (lend) sorted by date.
     * @return returns an arraylist of loans (lend) sorted by date
     */
    public ArrayList<Loan> sortByLend() {
        ArrayList<Loan> loanSortByLend = new ArrayList<>();
        for (Loan l : this.loanList) {
            if (l.getIsLend()) {
                loanSortByLend.add(l);
            }
        }
        Comparator<Loan> comparator = Comparator.comparing(Loan::getDate);
        Collections.sort(loanSortByLend, comparator);
        return loanSortByLend;
    }

    /**
     * Returns only loans (borrow) sorted by date.
     * @return returns an arraylist of loans (borrow) sorted by date
     */
    public ArrayList<Loan> sortByBorrow() {
        ArrayList<Loan> loanSortByBorrow = new ArrayList<>();
        for (Loan l : this.loanList) {
            if (!l.getIsLend()) {
                loanSortByBorrow.add(l);
            }
        }
        Comparator<Loan> comparator = Comparator.comparing(Loan::getDate);
        Collections.sort(loanSortByBorrow, comparator);
        return loanSortByBorrow;
    }

}