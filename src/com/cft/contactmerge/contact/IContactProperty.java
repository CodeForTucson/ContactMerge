package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

public interface IContactProperty<T> {
    public AnswerType isMatch(T otherProperty);
    public T getValue();
    public String toString();
}
