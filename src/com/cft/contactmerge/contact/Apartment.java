package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

public class Apartment extends GeneralProperty {

    // TODO: This code assumes that apartment names will be normalized before being compared. Normalization
    // should probably be part of construction. Things that should happen as part of normalization:
    // 1. Strip out punctuation and unnecessary spaces
    // 2. Normalize capitalization
    // 3. Eliminate or use short form of apartment names (Apartment -> Apt; Suite -> Ste; Unit; #)

    public Apartment(String apartment)
    {
        super(apartment);
    }
}
