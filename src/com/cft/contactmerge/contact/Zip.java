package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

public class Zip extends GeneralProperty {
    public Zip(String zip) {
        super(zip);
    }

    @Override
    public AnswerType isMatch(IContactProperty<String> otherZip) {
        if (PropertyMatchingHelpers.doPropertyPartsMatch(this.value, otherZip.getValue()) == AnswerType.yes) {
            return AnswerType.yes;
        }

        if (this.value.length() > 4 && otherZip.getValue().length() > 4 &&
                !PropertyMatchingHelpers.containsAlpha(this.value) && !PropertyMatchingHelpers.containsAlpha(otherZip.getValue())) {
            String mainPartSource = this.value.substring(0, 5);
            String mainPartTarget = otherZip.getValue().substring(0, 5);

            return (PropertyMatchingHelpers.doPropertyPartsMatch(mainPartSource, mainPartTarget));
        }

        return AnswerType.no;
    }
}
