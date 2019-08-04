package com.company.project.common.result;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求响应结构休
 * @author wangyonghao
 */
@Data
@NoArgsConstructor
public class Result {
    private int code;
    private String message;
    private Object data;

    private Result(int code,String message){
        this.code = code;
        this.message = message;
    }

    private Result(Object data){
        this.code = 0;
        this.data =data;
    }

    /**
     * 请求成功时，返回数据
     * @param data 数据
     * @return 成功的响应结果
     */
    public static Result success(Object data){
        return new Result(data);
    }

    /**
     * 发生错误时，返回错误信息
     * @return 发生错误时响应结果
     */
    public static Result error(String message){
        return new Result(500,message);
    }


    /**
     * 发生错误时，返回错误码与错误信息
     * @param code 错误码,必须大于0
     * @return 错误的响应结果
     */
    public static Result error(int code,String message){
        if(code <= 0){
            throw new IllegalArgumentException("code 必须大于0");
        }
        return new Result(code,message);
    }


}
