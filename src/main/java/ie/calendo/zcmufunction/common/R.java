package ie.calendo.zcmufunction.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Calendo
 * @version 1.0
 * @description 统一结果集
 * @date 2023/9/11 13:58
 */
@Data
public class R<T> implements Serializable {
    /**
     * 状态码
     * 200: 成功
     * 400: 请求语法错误
     * 403: 服务器拒绝请求
     * 404: 服务器未响应
     * 500: 服务器内部错误
     */
    private Integer status;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回时间戳
     */
    private Date timestamp;
    /**
     * 返回的数据
     */
    private T data;

    /**
     * 响应成功
     *
     * @param status    状态码 200: 成功
     * @param object    任意对象
     * @param timestamp 时间戳
     * @param <T>       返回类型任意
     * @return R结果集
     */
    public static <T> R<T> success(Integer status, Date timestamp, T object) {
        R<T> r = new R<T>();
        r.status = status;
        r.timestamp = timestamp;
        r.data = object;
        return r;
    }

    /**
     * 响应成功
     *
     * @param status    状态码 200: 成功
     * @param msg       返回的消息
     * @param timestamp 时间戳
     * @param <T>       返回类型任意
     * @return R结果集
     */
    public static <T> R<T> success(Integer status, String msg, Date timestamp) {
        R<T> r = new R<T>();
        r.status = status;
        r.msg = msg;
        r.timestamp = timestamp;
        return r;
    }

    /**
     * 响应成功
     *
     * @param status    状态码 200: 成功
     * @param msg       返回的消息
     * @param timestamp 时间戳
     * @param object    返回的数据
     * @param <T>       返回类型任意
     * @return R结果集
     */
    public static <T> R<T> success(Integer status, String msg, Date timestamp, T object) {
        R<T> r = new R<T>();
        r.status = status;
        r.msg = msg;
        r.timestamp = timestamp;
        r.data = object;
        return r;
    }

    /**
     * 响应失败
     *
     * @param status    状态码 400: 请求语法错误 403: 服务器拒绝请求 404: 服务器未响应 500: 服务器内部错误
     * @param msg       返回给前端的错误信息
     * @param timestamp 时间戳
     * @param <T>       返回类型任意返回类型任意
     * @return R结果集
     */
    public static <T> R<T> error(Integer status, String msg, Date timestamp) {
        R<T> r = new R<>();
        r.status = status;
        r.msg = msg;
        r.timestamp = timestamp;
        return r;
    }

    /**
     * 响应失败
     *
     * @param status    状态码 400: 请求语法错误 403: 服务器拒绝请求 404: 服务器未响应 500: 服务器内部错误
     * @param msg       返回给前端的错误信息
     * @param timestamp 时间戳
     * @param object    任意对象
     * @param <T>       返回类型任意返回类型任意
     * @return R结果集
     */
    public static <T> R<T> error(Integer status, String msg, Date timestamp, T object) {
        R<T> r = new R<>();
        r.status = status;
        r.msg = msg;
        r.timestamp = timestamp;
        r.data = object;
        return r;
    }

}
