package com.zev.studentmanager.exception;

import com.zev.studentmanager.enums.MessageCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException{
    public AppException(MessageCode messageCode) {
        super(messageCode.getMessage());
        this.messageCode = messageCode;
    }

    private MessageCode messageCode;

    public MessageCode getErrorCode() {
        return messageCode;
    }

    public void setErrorCode(MessageCode errorCode) {
        this.messageCode = errorCode;
    }
}
