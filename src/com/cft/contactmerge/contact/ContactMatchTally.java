package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

import java.util.ArrayList;
import java.util.Collection;

public class ContactMatchTally {
    private Collection<AnswerType> answerTypesFound = new ArrayList<AnswerType>();

    private int yesCount = 0;
    private int noCount = 0;
    private int maybeCount = 0;
    private int bothNullCount = 0;
    private int oneNullCount = 0;

    public void addComparison(IContactProperty source, IContactProperty target)
    {
        if (source != null && target != null) {
            switch(source.isMatch(target)) {
                case yes:
                    yesCount++;
                    break;
                case maybe:
                    maybeCount++;
                    break;
                default:
                    noCount++;
            }
        }
        else {
            if (source == null && target == null) {
                bothNullCount++;
            }
            else {
                oneNullCount++;
            }
        }
    }

    public int getYesCount() {
        return yesCount;
    }

    public int getNoCount() {
        return noCount;
    }

    public int getMaybeCount() {
        return maybeCount;
    }

    public int getBothNullCount() {
        return bothNullCount;
    }

    public int getOneNullCount() {
        return oneNullCount;
    }

    public int getTotalEvaluated() {
        return yesCount + noCount + maybeCount;
    }

    public int getTotalSubmitted() {
        return yesCount + noCount + maybeCount + bothNullCount + oneNullCount;
    }
}
