package com.rincentral.test.error;

public enum  ErrorMessage {
    DATA_ERROR_INPUT("Input data wrong"), NO_VALUE_PRESENT("0 matches found");
    String text;

    ErrorMessage(String text) {
        this.text = text;
    }
}
