package com.example.bit.DAL.Enums;

public enum Status {
    INACTIVE(0),
    ACTIVE (1);

    private final int VALUE;

    Status(int selectedValue){
        VALUE = selectedValue;
    }

    public int getVALUE(){
        return VALUE;
    }
}
