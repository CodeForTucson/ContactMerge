package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

public class GeneralProperty implements IContactProperty<String> {
    protected String value;

    public GeneralProperty(String value)
    {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Property value is required");
        }

        this.value = value;
    }

    public AnswerType isMatch(IContactProperty<String> otherProperty) {
        return PropertyMatchingHelpers.doPropertyPartsMatch(this.value, otherProperty.getValue());
    }

    public String getValue()
    {
        return value;
    }

    public String toString() {
        return value;
    }

}
