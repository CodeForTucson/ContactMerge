package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

public class LastNameProperty implements IContactProperty<String> {
    private String lastName;

    public LastNameProperty(String lastName)
    {
        this.lastName = lastName;
    }

    public AnswerType isMatch(String otherProperty) {
        // TODO: Add code to return appropriate response
        return null;
    }

    public String getValue()
    {
        return lastName;
    }

    public String toString() {
        return lastName;
    }

}
