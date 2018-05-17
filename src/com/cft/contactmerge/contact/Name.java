package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

public class Name implements IContactProperty<Name>
{
    private String firstName;
    private String lastName;
    private String middleName;
    private String prefix;
    private String suffix;

    /*******************************************************************************************************************
     *************************************************** Constructors **************************************************
     *******************************************************************************************************************/
    public Name(){
        setFirstName("");
        setMiddleName("");
        setLastName("");
        setPrefix("");
        setSuffix("");
    }

    public Name(String firstName, String middleName, String lastName, String prefix, String suffix){
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
        setPrefix(prefix);
        setSuffix(suffix);
    }

    /*******************************************************************************************************************
     *************************************************** Get Methods ***************************************************
     *******************************************************************************************************************/
    public Name getValue()
    {
        return this;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public String getPrefix(){
        return this.prefix;
    }

    public String getSuffix(){
        return this.suffix;
    }

    /*******************************************************************************************************************
     *************************************************** Set Methods ***************************************************
     *******************************************************************************************************************/
    public void setFirstName(String firstName){ this.firstName = firstName; }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName){ this.lastName = lastName; }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    public AnswerType isMatch(Name otherProperty) {
        // TODO: Add code to return appropriate response
        return null;
    }

    public String toString() {
        return getFirstName() + ", " + getMiddleName() + ", " + getPrefix() + ", " + getLastName() + ", " + getSuffix();
    }
}
