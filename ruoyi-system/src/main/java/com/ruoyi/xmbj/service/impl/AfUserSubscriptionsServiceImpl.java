package com.ruoyi.xmbj.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.xmbj.domain.AfUserSubscriptions;
import com.ruoyi.xmbj.mapper.AfUserSubscriptionsMapper;
import com.ruoyi.xmbj.service.IAfUserSubscriptionsService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AfUserSubscriptionsServiceImpl implements IAfUserSubscriptionsService {

    @Autowired
    private AfUserSubscriptionsMapper afUserSubscriptionsMapper;
    @Override
    public void updateProductSubscriptionsPlan() {


        List<AfUserSubscriptions> lists = afUserSubscriptionsMapper.queryToUpdateSubscriptions();

        lists.forEach(this::processEndDatePlan);

    }

    private void processEndDatePlan(AfUserSubscriptions afUserSubscriptions) {
        afUserSubscriptions.setUpdatedAt(new Date());
        afUserSubscriptions.setStatus("expired");
        afUserSubscriptionsMapper.updateUserSubscriptions(afUserSubscriptions);
        startNewPlan(afUserSubscriptions.getUid());


    }


    void startNewPlan(Long userId) {
        Date now = new Date();
        // 取消之前的订阅（如果存在）
        AfUserSubscriptions currentSubscriptions = afUserSubscriptionsMapper.selectLastPausedList(userId);
        AfUserSubscriptions target = null;
        // 先找到匹配当前订阅类型的数据,
        // 没有则修改所有数据重置为过期或者挂起待生效
        // 有则将非当前的数据挂起,单独生效最新操作的版本
        if(currentSubscriptions != null) {
            Date newEnd = DateUtils.addDays(currentSubscriptions.getEndDate(),
                    DateUtils.differentDaysByMillisecond(currentSubscriptions.getStartDate(),currentSubscriptions.getEndDate()));
            currentSubscriptions.setStatus("active");
            currentSubscriptions.setStartDate(currentSubscriptions.getEndDate());
            currentSubscriptions.setEndDate(newEnd);
            afUserSubscriptionsMapper.updateUserSubscriptions(currentSubscriptions);
        }
    }
}
