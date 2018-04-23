package com.cft.contactmerge;

public class CompareContactParts {
    private Contact existingContact;
    private Contact newContact;

    public CompareContactParts(Contact existingContact, Contact newContact)
    {
        this.existingContact = existingContact;
        this.newContact = newContact;
    }

    public AnswerType doNamesMatch()
    {
        return AnswerType.no;
    }

    public AnswerType doAddressesMatch()
    {
        return AnswerType.no;
    }

    public AnswerType doCitiesMatch()
    {
        return AnswerType.no;
    }

    public AnswerType doStatesMatch()
    {
        return AnswerType.no;
    }

    public AnswerType doPhoneNumbersMatch()
    {
        return AnswerType.no;
    }

    public AnswerType doEmailsMatch()
    {
        return AnswerType.no;
    }
}
