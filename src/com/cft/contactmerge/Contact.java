package com.cft.contactmerge;

public class Contact {
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

    public String getFirstName()
    {
        return firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public AnswerType IsMatch(Contact compareContact)
    {
        // TODO: Add real code
        return AnswerType.no;
    }
}
