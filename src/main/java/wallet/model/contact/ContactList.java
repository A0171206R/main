package wallet.model.contact;

import java.util.ArrayList;

public class ContactList {
    private boolean isModified = false;
    /**
     * Stores the current list of contacts of the user.
     */
    private ArrayList<Contact> contactList;

    /**
     * Constructs a new ContactList object.
     */
    public ContactList() {
        this.contactList = new ArrayList<Contact>();
    }

    public ContactList(ArrayList<Contact> contactList) {
        this.contactList = contactList;
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
     * Add the given contact into the contactList.
     *
     * @param contact The contact to be added.
     */
    public void addContact(Contact contact) {
        contact.setId(getLargestId(this.contactList) + 1);
        contactList.add(contact);
    }

    /**
     * Retrieve the contact at the given index of the contactList.
     *
     * @param id The id of the contact in the contactList.
     * @return The contact object with the specified id.
     */
    public Contact getContact(int id) {
        return contactList.get(id);
    }

    /**
     * Modify the value of the contact at the given index in the contactList.
     *
     * @param id      The id of the contact in the list.
     * @param contact The contact with modified values.
     */
    public void editContact(int id, Contact contact) {
        contactList.set(id, contact);
    }

    /**
     * Removes the contact at the given index of the contactList.
     *
     * @param id The id of the contact in the list.
     */
    public void deleteContact(int id) {
        contactList.remove(id);
    }

    /**
     * Get the current number of contacts in the contactList.
     *
     * @return The number of contacts in the list.
     */
    public int getContactListSize() {
        return contactList.size();
    }

    /**
     * Returns the list of contacts in the contactList.
     *
     * @return The list of contacts.
     */
    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    /**
     * Returns the largest id.
     *
     * @param contactList The list of contact.
     * @return The largest id.
     */
    public int getLargestId(ArrayList<Contact> contactList) {
        int max = 0;
        for (Contact contact : contactList) {
            if (contact.getId() > max) {
                max = contact.getId();
            }
        }
        return max;
    }

    /**
     * Creates Contact object.
     *
     * @param name     Name of the contact.
     * @param detail   Details of the contact.
     * @param phoneNum Phone Number of the contact.
     * @return The Contact Object.
     */
    public Contact createContact(String name, String detail, String phoneNum) {
        return new Contact(name, detail, phoneNum);
    }
}


