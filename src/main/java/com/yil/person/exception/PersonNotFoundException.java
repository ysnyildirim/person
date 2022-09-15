package com.yil.person.exception;

import com.yil.person.base.ApiException;
import com.yil.person.base.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@ApiException(code = ErrorCode.PersonNotFound)
public class PersonNotFoundException extends Exception {
}
