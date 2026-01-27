package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;

/**
 * 购买补充包请求
 */
public class PurchaseAddonRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long addonId; // 补充包ID（必填）
    private Integer quantity; // 购买数量（必填，默认1）

    public PurchaseAddonRequest() {
    }

    public PurchaseAddonRequest(Long addonId, Integer quantity) {
        this.addonId = addonId;
        this.quantity = quantity;
    }

    public Long getAddonId() {
        return addonId;
    }

    public void setAddonId(Long addonId) {
        this.addonId = addonId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "PurchaseAddonRequest{" +
                "addonId=" + addonId +
                ", quantity=" + quantity +
                '}';
    }
}
