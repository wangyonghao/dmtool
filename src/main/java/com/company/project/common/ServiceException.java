package com.company.project.common;


import lombok.Getter;
import lombok.Setter;

/**
 * 服务（业务）异常如“ 账号或密码错误 ”，该异常只做INFO级别的日志记录 @see WebMvcConfigurer
 */

@Getter
@Setter
public class ServiceException extends RuntimeException {
    private String code;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String code,String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
