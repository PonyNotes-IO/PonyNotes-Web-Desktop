package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.QiniuCapacityHistoryMapper;
import com.ruoyi.system.domain.QiniuCapacityHistory;
import com.ruoyi.system.service.IQiniuCapacityHistoryService;

@Service
public class QiniuCapacityHistoryServiceImpl implements IQiniuCapacityHistoryService
{
    @Autowired
    private QiniuCapacityHistoryMapper qiniuCapacityHistoryMapper;

    @Override
    public QiniuCapacityHistory selectQiniuCapacityHistoryById(Long historyId)
    {
        return qiniuCapacityHistoryMapper.selectQiniuCapacityHistoryById(historyId);
    }

    @Override
    public List<QiniuCapacityHistory> selectQiniuCapacityHistoryList(QiniuCapacityHistory qiniuCapacityHistory)
    {
        return qiniuCapacityHistoryMapper.selectQiniuCapacityHistoryList(qiniuCapacityHistory);
    }

    @Override
    public int insertQiniuCapacityHistory(QiniuCapacityHistory qiniuCapacityHistory)
    {
        return qiniuCapacityHistoryMapper.insertQiniuCapacityHistory(qiniuCapacityHistory);
    }

    @Override
    public int updateQiniuCapacityHistory(QiniuCapacityHistory qiniuCapacityHistory)
    {
        return qiniuCapacityHistoryMapper.updateQiniuCapacityHistory(qiniuCapacityHistory);
    }

    @Override
    public int deleteQiniuCapacityHistoryById(Long historyId)
    {
        return qiniuCapacityHistoryMapper.deleteQiniuCapacityHistoryById(historyId);
    }

    @Override
    public int deleteQiniuCapacityHistoryByIds(Long[] historyIds)
    {
        return qiniuCapacityHistoryMapper.deleteQiniuCapacityHistoryByIds(historyIds);
    }

    @Override
    public QiniuCapacityHistory selectByRecordDate(String recordDate)
    {
        return qiniuCapacityHistoryMapper.selectByRecordDate(recordDate);
    }

    @Override
    public List<QiniuCapacityHistory> selectRecentHistory(int months)
    {
        return qiniuCapacityHistoryMapper.selectRecentHistory(months);
    }

    @Override
    public List<QiniuCapacityHistory> selectHistoryByDateRange(Date startDate, Date endDate)
    {
        return qiniuCapacityHistoryMapper.selectHistoryByDateRange(startDate, endDate);
    }

    @Override
    public boolean recordCapacityHistory(Long capacityBytes, Long fileCount, Double usageRate)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String recordDate = sdf.format(new Date());

            QiniuCapacityHistory history = new QiniuCapacityHistory();
            history.setRecordDate(recordDate);
            history.setCapacityBytes(capacityBytes);
            history.setFileCount(fileCount);
            history.setUsageRate(new BigDecimal(usageRate).setScale(2, RoundingMode.HALF_UP));

            BigDecimal capacityTb = new BigDecimal(capacityBytes)
                    .divide(new BigDecimal(1024 * 1024 * 1024 * 1024), 2, RoundingMode.HALF_UP);
            history.setCapacityTb(capacityTb);

            qiniuCapacityHistoryMapper.insertQiniuCapacityHistory(history);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
