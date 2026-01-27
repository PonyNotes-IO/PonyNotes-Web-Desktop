package com.ruoyi.web.model;


import com.ruoyi.system.domain.vo.PaymentOrderVo;
import com.ruoyi.xmbj.domain.AfSubscriptionAddons;
import com.ruoyi.xmbj.domain.AfSubscriptionPlans;

import java.util.List;

public class PaymentOrderDetailVo {
    private PaymentOrderVo paymentOrderVo;
    private AfSubscriptionPlans afSubscriptionPlans;
    private List<AfSubscriptionAddons> afSubscriptionAddonsList;

    public PaymentOrderVo getPaymentOrderVo() {
        return paymentOrderVo;
    }

    public void setPaymentOrderVo(PaymentOrderVo paymentOrderVo) {
        this.paymentOrderVo = paymentOrderVo;
    }

    public AfSubscriptionPlans getAfSubscriptionPlans() {
        return afSubscriptionPlans;
    }

    public void setAfSubscriptionPlans(AfSubscriptionPlans afSubscriptionPlans) {
        this.afSubscriptionPlans = afSubscriptionPlans;
    }

    public List<AfSubscriptionAddons> getAfSubscriptionAddonsList() {
        return afSubscriptionAddonsList;
    }

    public void setAfSubscriptionAddonsList(List<AfSubscriptionAddons> afSubscriptionAddonsList) {
        this.afSubscriptionAddonsList = afSubscriptionAddonsList;
    }
}
