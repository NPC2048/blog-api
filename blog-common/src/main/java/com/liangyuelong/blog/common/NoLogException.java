package com.liangyuelong.blog.common;

/**
 * 不打印日志异常
 * 抛出异常由全局异常处理器处理, 日志不会打印此异常信息
 * @author yuelong.liang
 */
public class NoLogException extends BizException {

    public NoLogException() {
        super();
    }

    public NoLogException(String message) {
        super(message);
    }

    public NoLogException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoLogException(Throwable cause) {
        super(cause);
    }

    protected NoLogException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
