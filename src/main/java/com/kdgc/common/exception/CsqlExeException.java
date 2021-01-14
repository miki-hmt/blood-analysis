package com.kdgc.common.exception;

/**
 * @author jczhou
 * @description
 * @date 2020/7/23
 **/
public class CsqlExeException extends RuntimeException{

    public CsqlExeException(String message) {
        super(message);
    }

    public CsqlExeException(String message, Throwable cause) {
        super(message, cause);
    }
}
