package com.cft.contactmerge.contact;

public class Email {
    private String emailType;
    private String emailAddress;

    /*******************************************************************************************************************
     *************************************************** Constructors **************************************************
     *******************************************************************************************************************/
    public Email(){
        setEmailType("");
        setEmailAddress("");
    }

    public Email(String emailType, String emailAddress){
        setEmailType(emailType);
        setEmailAddress(emailAddress);
    }

    /*******************************************************************************************************************
     *************************************************** Get Methods ***************************************************
     *******************************************************************************************************************/
    public String getEmailType() {
        return this.emailType;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    /*******************************************************************************************************************
     *************************************************** Set Methods ***************************************************
     *******************************************************************************************************************/
    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
