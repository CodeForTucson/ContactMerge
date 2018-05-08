package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

public class NameProperty  implements IContactProperty<NameProperty>
{
    private FirstNameProperty firstName;
    private LastNameProperty lastName;

    public NameProperty(String lastName, String firstName)
    {
        this.lastName = new LastNameProperty(lastName);
        this.firstName = new FirstNameProperty(firstName);
    }

    public AnswerType isMatch(NameProperty otherProperty) {
        // TODO: Add code to return appropriate response
        return null;
    }

    public NameProperty getValue()
    {
        return this;
    }

    public String toString() {
        return lastName + ", " + firstName;
    }

    public String getFirstName()
    {
        return firstName.getValue();
    }

    public String getLastName()
    {
        return lastName.getValue();
    }
}
