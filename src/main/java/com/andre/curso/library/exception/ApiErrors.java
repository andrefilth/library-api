package com.andre.curso.library.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 27/04/2020
 */
public class ApiErrors {

    private final List<String> errors;

    public ApiErrors(final BindingResult bindingResult) {
        this.errors = new ArrayList<>();
        addDefaultMessage(bindingResult);
    }

    public ApiErrors(final BusinessException ex) {
        this.errors = asList(ex.getMessage());
    }

    public List<String> getErrors() {
        return errors;
    }

    private void addDefaultMessage(final BindingResult bindingResult) {
        final List<ObjectError> allErrors = bindingResult.getAllErrors();
        allErrors.forEach(objectError -> this.errors.add(objectError.getDefaultMessage()));
    }

}
