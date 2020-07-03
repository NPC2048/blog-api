package com.liangyuelong.blog.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yuelong.liang
 */
@Data
@AllArgsConstructor
public class Result {

    enum ResultState {
        // 成功
        SUCCESS(1),
        FAILED(0);

        int code;

        ResultState(int code) {
            this.code = code;
        }
    }

    /**
     * 默认成功
     */
    public static final Result SUCCESS = new Result(ResultState.SUCCESS.code, null, null);

    /**
     * 默认失败
     */
    public static final Result FAILED = new Result(ResultState.FAILED.code, null, null);

    /**
     * 状态
     */
    private Integer state;

    /**
     * 消息
     */
    private String msg;

    /**
     * 数据对象
     */
    private Object data;

    /**
     * 返回一个表示成功的对象，附带消息
     *
     * @param msg 消息
     * @return Result
     */
    public static Result success(String msg) {
        return new Result(ResultState.SUCCESS.code, msg, null);
    }

    /**
     * 返回一个表示成功的对象，附带对象
     *
     * @param data 对象
     * @return Result
     */
    public static Result success(Object data) {
        return new Result(ResultState.SUCCESS.code, null, data);
    }


    /**
     * 返回一个表示成功的对象
     *
     * @param msg 消息
     * @return Result
     */
    public static Result failed(String msg) {
        return new Result(ResultState.FAILED.code, msg, null);
    }

    /**
     * 返回一个表示成功的对象, 附带对象
     *
     * @param data 对象
     * @return Result
     */
    public static Result failed(Object data) {
        return new Result(ResultState.FAILED.code, null, data);
    }

    /**
     * 返回一个表示成功的对象, 附带异常参数的消息
     *
     * @param e 异常对象
     * @return Result
     */
    public static Result failed(Throwable e) {
        return new Result(ResultState.FAILED.code, e.getMessage(), null);
    }

}
