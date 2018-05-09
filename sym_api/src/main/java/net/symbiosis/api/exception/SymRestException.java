package net.symbiosis.api.exception;

/***************************************************************************
 * *
 * Created:     07 / 01 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

import java.io.Serializable;

public class SymRestException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;

    public SymRestException() {
        super();
    }

    public SymRestException(String message) {
        super(message);
    }

    public SymRestException(String message, Throwable cause) {
        super(message, cause);
    }

    public SymRestException(String message, Exception exception) {
        super(message, exception);
    }

    public SymRestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
