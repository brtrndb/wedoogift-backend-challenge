package tech.brtrndb.wedoogift.error.exception;

import java.io.Serial;

public class IllegalAmountException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6361551863713544835L;

    //

    public IllegalAmountException(String msg) {
        super(msg);
    }

}
