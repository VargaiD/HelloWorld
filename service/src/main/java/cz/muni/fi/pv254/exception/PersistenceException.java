package cz.muni.fi.pv254.exception;

import org.springframework.dao.DataAccessException;

public class PersistenceException extends DataAccessException {

    public PersistenceException(String msg) {
        super(msg);
    }

    public PersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}