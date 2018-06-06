package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class Name implements IContactProperty<Name>
{
    private String firstName;
    private String middleName;
    private String lastName;
    private String prefix;
    private String suffix;
    private final String firstAndLastNameMatchHashKey = "isFirstAndLastNameMatch";
    private final String firstNameMatchHashKey = "isFirstNameMatch";
    private final String middleNameMatchHashKey = "isMiddleNameMatch";
    private final String lastNameMatchHashKey = "isLastNameMatch";
    private final String suffixMatchHashKey = "isSuffixMatch";

    /*******************************************************************************************************************
     *************************************************** Constructors **************************************************
     *******************************************************************************************************************/
    public Name(){}

    public Name(String firstName, String middleName, String lastName){
        setFullName(firstName, middleName, lastName);
    }

    public Name(String firstName, String middleName, String lastName, String prefix, String suffix){
        setFullName(firstName, middleName, lastName, prefix, suffix);
    }

    /*******************************************************************************************************************
     ************************************************* Get/Set Methods *************************************************
     *******************************************************************************************************************/
    public Name getValue()
    {
        return this;
    }

    public void setFullName(String firstName, String middleName, String lastName){
        setFirstName(firstName);
        setMiddleName(middleName);
        setParseSuffixAndPrefixInLastName(lastName);
    }

    public void setFullName(String firstName, String middleName, String lastName, String prefix, String suffix){
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
        setPrefix(prefix);
        setSuffix(suffix);
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

    private String getFirstAndLastNameMatchHashKey(){
        return this.firstAndLastNameMatchHashKey;
    }

    private String getFirstNameMatchHashKey(){
        return this.firstNameMatchHashKey;
    }

    private String getMiddleNameMatchHashKey(){
        return this.middleNameMatchHashKey;
    }

    private String getLastNameMatchHashKey(){
        return this.lastNameMatchHashKey;
    }

    private String getSuffixMatchHashKey(){
        return this.suffixMatchHashKey;
    }

    public void setNamePartsNull(){
        setFullName(null, null, null, null, null);
    }

    /*******************************************************************************************************************
     ************************************************** Match Methods **************************************************
     *******************************************************************************************************************/
    public AnswerType isMatch(IContactProperty<Name> otherName) {
        Hashtable<String, AnswerType> namePartsMatchResults = new Hashtable<>();

        // First and Last Name
        if (!(getFirstName() == null || getFirstName().isEmpty()) && !(getLastName() == null || getLastName().isEmpty()) &&
                !(otherName.getValue().getFirstName() == null || otherName.getValue().getFirstName().isEmpty()) &&
                !(otherName.getValue().getLastName() == null || otherName.getValue().getLastName().isEmpty())){
            namePartsMatchResults.put(getFirstAndLastNameMatchHashKey(),
                    isFirstAndLastNameMatch(getFirstName(), otherName.getValue().getFirstName(), getLastName(), otherName.getValue().getLastName()));
            if (namePartsMatchResults.get(getFirstAndLastNameMatchHashKey()).equals(AnswerType.no)){ // skip other matching if this result is no
                return AnswerType.no;
            }
        }

        // First Name Only
        if (!namePartsMatchResults.containsKey(getFirstAndLastNameMatchHashKey()) && !(getFirstName() == null || getFirstName().isEmpty()) &&
                !(otherName.getValue().getFirstName() == null || otherName.getValue().getFirstName().isEmpty())
                ){
            namePartsMatchResults.put(getFirstNameMatchHashKey(), isFirstNameMatch(getFirstName(), otherName.getValue().getFirstName()));
            if (namePartsMatchResults.get(getFirstNameMatchHashKey()).equals(AnswerType.no)){ // skip other matching if this result is no
                return AnswerType.no;
            }
        }

        // Last Name Only
        if (!namePartsMatchResults.containsKey(getFirstAndLastNameMatchHashKey()) && !(getLastName() == null || getLastName().isEmpty()) &&
                !(otherName.getValue().getLastName() == null || otherName.getValue().getLastName().isEmpty())
                ){
            namePartsMatchResults.put(getLastNameMatchHashKey(), isLastNameMatch(getLastName(), otherName.getValue().getLastName()));
            if (namePartsMatchResults.get(getLastNameMatchHashKey()).equals(AnswerType.no)){ // skip other matching if this result is no
                return AnswerType.no;
            }
        }

        // Middle Name
        if (!(getMiddleName() == null || getMiddleName().isEmpty()) &&
                !(otherName.getValue().getMiddleName() == null || otherName.getValue().getMiddleName().isEmpty())){
            namePartsMatchResults.put(getMiddleNameMatchHashKey(), isMiddleNameMatch(getMiddleName(), otherName.getValue().getMiddleName()));
            if (namePartsMatchResults.get(getMiddleNameMatchHashKey()).equals(AnswerType.no)){
                return AnswerType.no;
            }
        }

        // Suffix
        if (!(getSuffix() == null || getSuffix().isEmpty()) &&
                !(otherName.getValue().getSuffix() == null || otherName.getValue().getSuffix().isEmpty())){
            namePartsMatchResults.put(getSuffixMatchHashKey(), isSuffixMatch(getSuffix(), otherName.getValue().getSuffix()));
        }

        // Return Results
        if (namePartsMatchResults.containsValue(AnswerType.no)) {
            return AnswerType.no;
        } else if (namePartsMatchResults.containsValue(AnswerType.maybe)) {
            return AnswerType.maybe;
        } else if (namePartsMatchResults.containsValue(AnswerType.yes)) {
            return AnswerType.yes;
        }

        // Default Return (should never be reached)
        return AnswerType.no;
    }

    private AnswerType isFirstAndLastNameMatch(String fNameOne, String fNameTwo, String lNameOne, String lNameTwo){
        // edit names to go through simple matching algorithms
        ArrayList<String> normalizedFirstNamesOne = NameMatchLogic.setNormalizeName(fNameOne);
        ArrayList<String> normalizedLastNamesOne = NameMatchLogic.setNormalizeName(lNameOne);
        ArrayList<String> normalizedFirstNamesTwo = NameMatchLogic.setNormalizeName(fNameTwo);
        ArrayList<String> normalizedLastNamesTwo = NameMatchLogic.setNormalizeName(lNameTwo);

        // Note: Since first names are more common than last names, lets run last names match first.
        //       If they don't match, then we don't need to see if first names match, and can skip the first names completely during the matching calculations completely.
        AnswerType lNameMatchResult = NameMatchLogic.isNameMatch(normalizedLastNamesOne, normalizedLastNamesTwo);
        if (!lNameMatchResult.equals(AnswerType.no)){
            AnswerType fNameMatchResult = NameMatchLogic.isNameMatch_CheckInitial(normalizedFirstNamesOne, normalizedFirstNamesTwo);
            if (lNameMatchResult.equals(AnswerType.yes) && fNameMatchResult.equals(AnswerType.yes)){
                return AnswerType.yes;
            } else if (!fNameMatchResult.equals(AnswerType.no)){
                return AnswerType.maybe;
            }
        }

        return NameMatchLogic.getFirstLastSwapNameMatchResult(normalizedFirstNamesOne, normalizedLastNamesOne, normalizedFirstNamesTwo, normalizedLastNamesTwo);
    }

    private AnswerType isFirstNameMatch(String nameOne, String nameTwo){
        ArrayList<String> normalizedFirstNameOne = NameMatchLogic.setNormalizeName(nameOne);
        ArrayList<String> normalizedFirstNameTwo = NameMatchLogic.setNormalizeName(nameTwo);

        return NameMatchLogic.isNameMatch_CheckInitial(normalizedFirstNameOne, normalizedFirstNameTwo);
    }

    private AnswerType isMiddleNameMatch(String nameOne, String nameTwo){
        ArrayList<String> normalizedMiddleNameOne = NameMatchLogic.setNormalizeName(nameOne);
        ArrayList<String> normalizedMiddleNameTwo = NameMatchLogic.setNormalizeName(nameTwo);

        return NameMatchLogic.isNameMatch_CheckInitial(normalizedMiddleNameOne, normalizedMiddleNameTwo);
    }

    private AnswerType isLastNameMatch(String nameOne, String nameTwo){
        ArrayList<String> normalizedLastNameOne = NameMatchLogic.setNormalizeName(nameOne);
        ArrayList<String> normalizedLastNameTwo = NameMatchLogic.setNormalizeName(nameTwo);

        return NameMatchLogic.isNameMatch(normalizedLastNameOne, normalizedLastNameTwo);
    }

    private AnswerType isSuffixMatch(String suffixOne, String SuffixTwo){
        String normalizedSuffixOne = NameMatchLogic.setNormalizeSuffix(suffixOne);
        String normalizedSuffixTwo = NameMatchLogic.setNormalizeSuffix(SuffixTwo);
        if (NameMatchLogic.isSuffixComparisonMatch(normalizedSuffixOne, normalizedSuffixTwo)){
            return AnswerType.yes;
        }
        return AnswerType.no;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    @Override
    public String toString() {
        StringBuilder lastAndFirstName = new StringBuilder();

        if (!(getLastName() == null || getLastName().isEmpty())){
            lastAndFirstName.append(getLastName());
        }
        if (!lastAndFirstName.toString().isEmpty() && !(getFirstName() == null || getFirstName().isEmpty())){
            lastAndFirstName.append(", ").append(getFirstName());
        } else if (!(getFirstName() == null || getFirstName().isEmpty())){
            lastAndFirstName.append(getFirstName());
        }

        return lastAndFirstName.toString();
    }

    public void setParseSuffixAndPrefixInLastName(String lastName){
        ArrayList<String> lastNameParts = new ArrayList<>(Arrays.asList(lastName.trim().
                replaceAll("[^ a-zA-Z0-9!'’-]", " ").split("\\s+")));
        StringBuilder correctedLastName = new StringBuilder();

        // Set suffix and prefix from ArrayList
        for (int n = 0;n < lastNameParts.size(); n++){
            if (NameMatchLogic.getNamePrefixes().containsKey(lastNameParts.get(n).toLowerCase())){ // Prefix found
                setPrefix(NameMatchLogic.getNamePrefixes().get(lastNameParts.get(n).toLowerCase()));
                lastNameParts.remove(n);
                n--;
            } else if (NameMatchLogic.getNameSuffixes().containsKey(lastNameParts.get(n).toLowerCase())){ // Suffix found
                setSuffix(NameMatchLogic.getNameSuffixes().get(lastNameParts.get(n).toLowerCase()));
                lastNameParts.remove(n);
                n--;
            } else { // last name
                if (correctedLastName.toString().isEmpty()){
                    correctedLastName.append(lastNameParts.get(n).trim());
                    lastNameParts.remove(n);
                    n--;
                } else {
                    correctedLastName.append(" ").append(lastNameParts.get(n).trim());
                    lastNameParts.remove(n);
                    n--;
                }
            }
        }
        setLastName(correctedLastName.toString());
    }
}