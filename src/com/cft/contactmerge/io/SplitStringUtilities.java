package com.cft.contactmerge.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import java.util.*;

public class SplitStringUtilities {

    private static List<String> removeWhiteSpace(List<String> input){
        List<String> output = new ArrayList<String>();

        //////////Remove white space before any character, and after the last character in the strings.

        for(ListIterator itb = input.listIterator();itb.hasNext();){
            String strToCheck = itb.next().toString();

            for(int j = 0;j<2;j++) {//runs through and removes white space, then flips the characters, then does the same process
                String wsf = "";
                boolean iwsf = true;


                for (int i = 0; i < strToCheck.length(); i++) {
                    if (strToCheck.charAt(i) == ' ') {

                    } else {
                        iwsf = false;
                    }

                    if (iwsf == false) {
                        wsf += strToCheck.charAt(i);
                    }
                }
                strToCheck = wsf;
                StringBuilder strb = new StringBuilder(strToCheck);

                strToCheck = strb.reverse().toString();
            }
            output.add(strToCheck);
        }


        return output;
    }

    public static List<String> splitTsvString(String input) {

        List<String> listout = new ArrayList<String>();
        String testString = input;
        String part = new String("");

        for(int i = 0;i< testString.length();i++){
            if(input.charAt(i)=='\t'){
                listout.add(part);
                part = "";
            }
            else {
                part = part+input.charAt(i);
            }
        }

        listout.add(part);
        listout = removeWhiteSpace(listout);

        return listout;
    }

    public static List<String> splitCsvString(String input) {
        List<String> x = new ArrayList<String>();

        String testString = input;

        boolean isQuote = false;
        boolean isComma = false;
        boolean isTextBefore = false;
        boolean isTextAfter = false;
        boolean isSpace = false;

        int index = 0;

        String part = new String("");


            while (index < testString.length()) {

                switch (testString.charAt(index)) {
                    case '\"'://then we are encapsulated
                        isQuote = true;
                        isTextAfter = true;
                        index++;
                        part = "";
                        break;
                    case ',':
                        isComma = true;
                        break;
                    case ' ':
                        isSpace = true;

                }

                if(isQuote==false&&isTextAfter==false&&isComma==false&&isSpace==false){
                    isTextBefore = true;
                }

                if(isQuote){
                    for(;index<testString.length()&&isQuote;index++){
                        if(testString.charAt(index)=='"'){
                            isQuote = false;
                            x.add(part);
                            part = "";
                        }
                        else{
                            part += testString.charAt(index);
                        }
                    }
                    for(;isTextAfter&&index<testString.length();index++){//burn up text after
                        if(testString.charAt(index)==','){
                            isTextAfter=false;
                        }
                    }
                }
                if(isTextBefore){
                    for(;index<testString.length()&&isTextBefore;){
                        if(testString.charAt(index)==','){
                            isTextBefore = false;
                            x.add(part);
                            part = "";

                        }
                        else{
                            part += testString.charAt(index);
                            index++;
                        }
                    }
                    //if part has stuff in it, then we broke out of the loop before adding it into the list
                    if(part.length()>0){
                        x.add(part);
                        part = "";
                    }
                }

                if(isComma){
                    if(part.length()==0){
                        x.add(part);
                    }
                }

                isQuote = false;
                isComma = false;
                isTextBefore = false;
                isTextAfter = false;
                isSpace = false;

                index++;

            }

            if(testString.length()==0){//edge cases
                x.add(part);
            }
            else{
                if(testString.charAt(testString.length()-1)==','){
                    x.add(part);
                }
            }

        x = removeWhiteSpace(x);

        return x;
    }
}
