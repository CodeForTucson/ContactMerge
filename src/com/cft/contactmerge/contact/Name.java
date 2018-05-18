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
    public Name(){}

    public Name(String firstName, String middleName, String lastName, String prefix, String suffix){
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
        setPrefix(prefix);
        setSuffix(suffix);
    }

    /*******************************************************************************************************************
     ************************************************* Get/Set Methods *************************************************
     *******************************************************************************************************************/
    public Name getValue()
    {
        return this;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public void setFirstName(String firstName){ this.firstName = firstName; }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public void setLastName(String lastName){ this.lastName = lastName; }

    public String getPrefix(){
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix(){
        return this.suffix;
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
        if (!getFirstName().isEmpty() && !getLastName().isEmpty()){
            System.out.println("found");
            return getLastName() + ", " + getFirstName();
        } else if (!getFirstName().isEmpty()){
            return getFirstName();
        } else if (!getLastName().isEmpty()){
            return getLastName();
        }
        return "";
    }
}