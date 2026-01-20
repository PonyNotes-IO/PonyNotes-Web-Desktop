package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;
import java.util.List;

/**
 * 日志响应基类
 */
public class LogsResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code; // 状态码
    private String message; // 返回消息
    private List<?> data; // 日志数据列表
    private Integer total; // 总条数
    private Integer pageNo; // 当前页码
    private Integer pageSize; // 每页大小

    public LogsResponse() {
    }

    public LogsResponse(Integer code, String message, List<?> data, Integer total, Integer pageNo, Integer pageSize) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.total = total;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "LogsResponse{" +
                "code=" + code +
                ", total=" + total +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
