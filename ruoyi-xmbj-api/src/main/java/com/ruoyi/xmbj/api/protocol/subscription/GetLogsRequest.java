package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;

/**
 * 获取日志请求
 */
public class GetLogsRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String logType; // 日志类型过滤：subscription/purchase/usage
    private Integer pageNo; // 分页页码（默认1）
    private Integer pageSize; // 每页大小（默认20）

    public GetLogsRequest() {
    }

    public GetLogsRequest(String logType, Integer pageNo, Integer pageSize) {
        this.logType = logType;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
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
        return "GetLogsRequest{" +
                "logType='" + logType + '\'' +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
