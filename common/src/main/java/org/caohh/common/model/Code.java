package org.caohh.common.model;

public enum Code {
    SUCCESS(10000),
    FAIL(10001);
    private final Integer value;

    Code(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
