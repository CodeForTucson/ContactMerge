package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

public class FirstNameProperty implements IContactProperty<String> {

    private String firstName;

    public FirstNameProperty(String firstName)
    {
        this.firstName = firstName;
    }

    public AnswerType isMatch(String otherProperty) {
        // TODO: Add code to return appropriate response
        return null;
    }

    public String getValue()
    {
        return firstName;
    }

    public String toString() {
        return firstName;
    }
}
