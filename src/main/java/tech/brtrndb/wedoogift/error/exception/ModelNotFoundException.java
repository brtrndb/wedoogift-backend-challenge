package tech.brtrndb.wedoogift.error.exception;

import java.io.Serial;

public class ModelNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -183545989632272183L;

    //

    public ModelNotFoundException(String msg) {
        super(msg);
    }

}
