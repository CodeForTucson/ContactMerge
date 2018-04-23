package com.cft.contactmerge;

public class ContactMatchResult {
    private ContactMatchType matchType;

    public ContactMatchResult(ContactMatchType matchType)
    {
        this.matchType = matchType;
    }

    public ContactMatchType getMatchType()
    {
        return matchType;
    }

    public AnswerType isMatchFound()
    {
        AnswerType answer;

        switch(matchType)
        {
            case Identical:
            case Match:
            case MatchButModifying:
                answer = AnswerType.yes;
                break;
            case PotentialMatch:
                answer = AnswerType.maybe;
                break;
            default:
                answer = AnswerType.no;
        }

        return answer;
    }

    public AnswerType isRelatedFound()
    {
        AnswerType answer;

        switch(matchType)
        {
            case Related:
                answer = AnswerType.yes;
                break;
            case PotentiallyRelated:
                answer = AnswerType.maybe;
                break;
            default:
                answer = AnswerType.no;
        }

        return answer;
    }


}
