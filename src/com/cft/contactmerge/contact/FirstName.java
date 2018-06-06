package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

public class FirstName extends GeneralProperty {

    // TODO: This code assumes that names will be normalized before being compared. Normalization
    // should probably be part of construction. Things that should happen as part of normalization:
    // 1. Strip out punctuation and unnecessary spaces
    // 2. Normalize capitalization

    public FirstName(String firstName)
    {
        super(firstName);
    }

    @Override
    public AnswerType isMatch(IContactProperty<String> otherFirstName) {
        return PropertyMatchingHelpers.doNamePartsMatch(this.getValue(), otherFirstName.getValue());
    }
}

