package com.andre.curso.library.exception;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 17/05/2020
 */
public class BusinessException extends RuntimeException {

    public BusinessException(final String msg) {
        super((msg));
    }

}
