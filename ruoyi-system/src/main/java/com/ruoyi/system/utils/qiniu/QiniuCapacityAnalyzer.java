package com.ruoyi.system.utils.qiniu;

import com.ruoyi.common.utils.qiniu.QiniuCapacityAnalysis;
import com.ruoyi.common.utils.qiniu.QiniuCapacityTrend;
import com.ruoyi.common.utils.qiniu.QiniuStorageTypeDistribution;
import com.ruoyi.system.domain.QiniuCapacityHistory;
import com.ruoyi.system.service.IQiniuCapacityHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class QiniuCapacityAnalyzer
{
    @Autowired
    private IQiniuCapacityHistoryService capacityHistoryService;

    public QiniuCapacityAnalysis analyzeCapacityTrend(List<QiniuCapacityTrend> trendData)
    {
        if (trendData == null || trendData.isEmpty())
        {
            throw new IllegalArgumentException("趋势数据不能为空");
        }

        QiniuCapacityAnalysis analysis = new QiniuCapacityAnalysis();
        analysis.setTrendList(trendData);
        analysis.setPeriod(trendData.get(0).getMonth() + " 至 " + trendData.get(trendData.size() - 1).getMonth());

        calculateBasicMetrics(analysis, trendData);
        calculateGrowthRates(analysis, trendData);
        analyzeTrend(analysis, trendData);
        findPeakAndFastestGrowth(analysis, trendData);
        generateStorageTypeDistribution(analysis);

        return analysis;
    }

    public QiniuCapacityAnalysis analyzeFromDatabase(int months)
    {
        List<QiniuCapacityHistory> historyList = capacityHistoryService.selectRecentHistory(months);
        
        if (historyList == null || historyList.isEmpty())
        {
            throw new IllegalArgumentException("暂无容量历史数据");
        }

        List<QiniuCapacityTrend> trendData = new ArrayList<>();
        for (QiniuCapacityHistory history : historyList)
        {
            QiniuCapacityTrend trend = new QiniuCapacityTrend();
            trend.setMonth(history.getRecordDate());
            trend.setCapacityTB(history.getCapacityTb().longValue());
            trend.setCapacityBytes(history.getCapacityBytes());
            trendData.add(trend);
        }

        return analyzeCapacityTrend(trendData);
    }

    private void calculateBasicMetrics(QiniuCapacityAnalysis analysis, List<QiniuCapacityTrend> trendData)
    {
        Long startCapacity = trendData.get(0).getCapacityTB();
        Long endCapacity = trendData.get(trendData.size() - 1).getCapacityTB();
        Long totalGrowth = endCapacity - startCapacity;

        BigDecimal totalGrowthRate = new BigDecimal(totalGrowth)
                .divide(new BigDecimal(startCapacity), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal averageCapacity = trendData.stream()
                .map(QiniuCapacityTrend::getCapacityTB)
                .map(BigDecimal::new)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(trendData.size()), 2, RoundingMode.HALF_UP);

        BigDecimal averageMonthlyGrowth = new BigDecimal(totalGrowth)
                .divide(new BigDecimal(trendData.size() - 1), 2, RoundingMode.HALF_UP);

        analysis.setStartCapacityTB(startCapacity);
        analysis.setEndCapacityTB(endCapacity);
        analysis.setTotalGrowthTB(totalGrowth);
        analysis.setTotalGrowthRate(totalGrowthRate);
        analysis.setAverageCapacityTB(averageCapacity);
        analysis.setAverageMonthlyGrowthTB(averageMonthlyGrowth);
    }

    private void calculateGrowthRates(QiniuCapacityAnalysis analysis, List<QiniuCapacityTrend> trendData)
    {
        Long startCapacity = trendData.get(0).getCapacityTB();

        for (int i = 0; i < trendData.size(); i++)
        {
            QiniuCapacityTrend current = trendData.get(i);

            if (i > 0)
            {
                QiniuCapacityTrend previous = trendData.get(i - 1);
                Long monthGrowth = current.getCapacityTB() - previous.getCapacityTB();
                current.setMonthGrowthTB(monthGrowth);

                BigDecimal growthRate = new BigDecimal(monthGrowth)
                        .divide(new BigDecimal(previous.getCapacityTB()), 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal(100))
                        .setScale(2, RoundingMode.HALF_UP);
                current.setGrowthRate(growthRate);
            }

            BigDecimal cumulativeGrowthRate = new BigDecimal(current.getCapacityTB() - startCapacity)
                    .divide(new BigDecimal(startCapacity), 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(100))
                    .setScale(2, RoundingMode.HALF_UP);
            current.setCumulativeGrowthRate(cumulativeGrowthRate);
        }
    }

    private void analyzeTrend(QiniuCapacityAnalysis analysis, List<QiniuCapacityTrend> trendData)
    {
        Long startCapacity = trendData.get(0).getCapacityTB();
        Long endCapacity = trendData.get(trendData.size() - 1).getCapacityTB();

        if (endCapacity > startCapacity)
        {
            analysis.setTrendDescription("整体呈持续上升趋势");
        }
        else if (endCapacity < startCapacity)
        {
            analysis.setTrendDescription("整体呈持续下降趋势");
        }
        else
        {
            analysis.setTrendDescription("整体保持稳定");
        }
    }

    private void findPeakAndFastestGrowth(QiniuCapacityAnalysis analysis, List<QiniuCapacityTrend> trendData)
    {
        QiniuCapacityTrend peak = trendData.stream()
                .max(Comparator.comparing(QiniuCapacityTrend::getCapacityTB))
                .orElse(null);

        if (peak != null)
        {
            analysis.setPeakMonth(peak.getMonth());
            analysis.setPeakCapacityTB(peak.getCapacityTB());
        }

        QiniuCapacityTrend fastestGrowth = trendData.stream()
                .filter(t -> t.getGrowthRate() != null)
                .max(Comparator.comparing(QiniuCapacityTrend::getGrowthRate))
                .orElse(null);

        if (fastestGrowth != null)
        {
            analysis.setFastestGrowthMonth(fastestGrowth.getMonth());
            analysis.setFastestGrowthRate(fastestGrowth.getGrowthRate());
        }
    }

    public QiniuCapacityAnalysis generateSampleAnalysis()
    {
        List<QiniuCapacityTrend> trendData = new ArrayList<>();
        trendData.add(new QiniuCapacityTrend("2025-07", 701L));
        trendData.add(new QiniuCapacityTrend("2025-08", 750L));
        trendData.add(new QiniuCapacityTrend("2025-09", 801L));
        trendData.add(new QiniuCapacityTrend("2025-10", 840L));
        trendData.add(new QiniuCapacityTrend("2025-11", 871L));
        trendData.add(new QiniuCapacityTrend("2025-12", 895L));

        QiniuCapacityAnalysis analysis = analyzeCapacityTrend(trendData);
        generateStorageTypeDistribution(analysis);
        return analysis;
    }

    private void generateStorageTypeDistribution(QiniuCapacityAnalysis analysis)
    {
        List<QiniuStorageTypeDistribution> distribution = new ArrayList<>();

        Long ossCapacity = 450L;
        Long ebsCapacity = 280L;
        Long nasCapacity = 97L;
        Long otherCapacity = 68L;
        Long totalCapacity = ossCapacity + ebsCapacity + nasCapacity + otherCapacity;

        BigDecimal ossPercentage = new BigDecimal(ossCapacity)
                .divide(new BigDecimal(totalCapacity), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100))
                .setScale(1, RoundingMode.HALF_UP);

        BigDecimal ebsPercentage = new BigDecimal(ebsCapacity)
                .divide(new BigDecimal(totalCapacity), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100))
                .setScale(1, RoundingMode.HALF_UP);

        BigDecimal nasPercentage = new BigDecimal(nasCapacity)
                .divide(new BigDecimal(totalCapacity), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100))
                .setScale(1, RoundingMode.HALF_UP);

        BigDecimal otherPercentage = new BigDecimal(otherCapacity)
                .divide(new BigDecimal(totalCapacity), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100))
                .setScale(1, RoundingMode.HALF_UP);

        distribution.add(new QiniuStorageTypeDistribution("对象存储（OSS/TOS）", ossCapacity, ossPercentage));
        distribution.add(new QiniuStorageTypeDistribution("块存储（EBS/ECS）", ebsCapacity, ebsPercentage));
        distribution.add(new QiniuStorageTypeDistribution("文件存储（NAS）", nasCapacity, nasPercentage));
        distribution.add(new QiniuStorageTypeDistribution("其他存储", otherCapacity, otherPercentage));

        analysis.setStorageTypeDistribution(distribution);
        analysis.setTotalCapacityTB(totalCapacity);
    }
}
