package com.newcen.newcen.common.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BoardType {
    NOTICE,
    QUESTION,
    FAQ;
   @JsonCreator
    public static BoardType from(String s){
       return BoardType.valueOf(s.toUpperCase());
   }
}
