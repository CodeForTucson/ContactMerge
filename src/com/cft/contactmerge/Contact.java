package com.cft.contactmerge;

public class Contact implements IContact {
    private String firstName;
    private String lastName;

    // TODO: Should support multiple addresses, phones, and emails for each contact
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String email;

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public String getFirstName() { return this.firstName; }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    public String getLastName() { return this.lastName; }

    public void setAddress(String address) { this.address = address; }
    public String getAddress() { return this.address; };

    public void setCity(String city) { this.city = city; };
    public String getCity() { return this.city; };

    public void setState(String state) { this.state = state; };
    public String getState() { return this.state; };

    public void setZip(String zip) { this.zip = zip; };
    public String getZip() {return this.zip; };

    public void setPhone(String phone) { this.phone = phone; };
    public String getPhone() { return this.phone; };

    public void setEmail(String email) { this.email = email; };
    public String getEmail() { return this.email; };

    public ContactMatchResult CompareTo(IContact compareContact)
    {
        // TODO: Need to add logic
        return new ContactMatchResult(ContactMatchType.NoMatch);
    }
}
