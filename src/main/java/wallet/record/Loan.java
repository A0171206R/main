package wallet.record;

import wallet.contact.Contact;

public class Loan extends Record {
    private double amount;
    private Contact person;
    private boolean isLend;

    public Loan(double amount, Contact person, boolean isLend, boolean isSettled) {
        this.amount = amount;
        this.person = person;
        this.isLend = isLend;
        this.isSettled = isSettled;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Contact getPerson() {
        return person;
    }

    public void setPerson(Contact person) {
        this.person = person;
    }

    public boolean isLend() {
        return isLend;
    }

    public void setLend(boolean lend) {
        isLend = lend;
    }

    public boolean isSettled() {
        return isSettled;
    }

    public void setSettled(boolean settled) {
        isSettled = settled;
    }

    private boolean isSettled;


}
