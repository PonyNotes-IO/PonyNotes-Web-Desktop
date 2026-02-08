package com.ruoyi.system.mapper;

import java.util.Date;
import java.util.List;
import com.ruoyi.system.domain.QiniuCapacityHistory;

public interface QiniuCapacityHistoryMapper
{
    public QiniuCapacityHistory selectQiniuCapacityHistoryById(Long historyId);

    public List<QiniuCapacityHistory> selectQiniuCapacityHistoryList(QiniuCapacityHistory qiniuCapacityHistory);

    public int insertQiniuCapacityHistory(QiniuCapacityHistory qiniuCapacityHistory);

    public int updateQiniuCapacityHistory(QiniuCapacityHistory qiniuCapacityHistory);

    public int deleteQiniuCapacityHistoryById(Long historyId);

    public int deleteQiniuCapacityHistoryByIds(Long[] historyIds);

    public QiniuCapacityHistory selectByRecordDate(String recordDate);

    public List<QiniuCapacityHistory> selectRecentHistory(int months);

    public List<QiniuCapacityHistory> selectHistoryByDateRange(Date startDate, Date endDate);
}
