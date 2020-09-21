package org.caohh.common.model;

import lombok.Data;

@Data
public class Result {
    private Integer code;
    private String message;
    private Object data;

    public Result() {

    }

    public Result(Code code, String message) {
        this(code, message, null);
    }

    public Result(Code code, Object data) {
        this(code, "", data);
    }

    public Result(Code code, String message, Object data) {
        this.code = code.getValue();
        this.message = message;
        this.data = data;
    }
}
