package com.github.brijoe.broadcast;

enum DataType {
    DATA_TYPE_TXT(1),
    DATA_TYPE_FILE(2),
    DATA_TYPE_ASSERTS_FILE(3);

    private int value;

    private DataType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
