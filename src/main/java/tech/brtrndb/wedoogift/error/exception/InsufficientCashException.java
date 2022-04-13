package tech.brtrndb.wedoogift.error.exception;

import java.io.Serial;

public class InsufficientCashException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5380298271593059175L;

    //

    public InsufficientCashException(String msg) {
        super(msg);
    }

}
