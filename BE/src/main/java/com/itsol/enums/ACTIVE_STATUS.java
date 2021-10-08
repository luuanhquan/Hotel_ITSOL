package com.itsol.enums;

public enum ACTIVE_STATUS {

    INACTIVE(0),
    ACTIVE(1);

    public final int value;

    ACTIVE_STATUS(int value) {
        this.value = value;
    }

    public static ACTIVE_STATUS valueOf(Integer value) {
        if (value == null) {
            return null;
        }

        for (ACTIVE_STATUS val : ACTIVE_STATUS.values()) {
            if (val.value == value) {
                return val;
            }
        }

        return null;
    }

}
