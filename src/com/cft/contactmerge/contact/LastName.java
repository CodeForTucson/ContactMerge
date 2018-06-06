package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

public class LastName extends GeneralProperty {

    // TODO: This code assumes that names will be normalized before being compared. Normalization
    // should probably be part of construction. Things that should happen as part of normalization:
    // 1. Strip out punctuation and unnecessary spaces
    // 2. Normalize capitalization

    public LastName(String lastName)
    {
        super(lastName);
    }

    @Override
    public AnswerType isMatch(IContactProperty<String> otherLastName) {
        return PropertyMatchingHelpers.doNamePartsMatch(this.getValue(), otherLastName.getValue());
    }
}
