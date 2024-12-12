package com.zev.studentmanager.exception;


import com.zev.studentmanager.enums.MessageCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFound extends RuntimeException{
    private MessageCode messageCode;
    public ResourceNotFound(MessageCode messageCode) {
        super(messageCode.getMessage());
        this.messageCode = messageCode;
    }

}
