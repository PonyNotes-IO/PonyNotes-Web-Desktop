package com.ruoyi.web.controller.ponynotes;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.controller.ponynotes.PonynotesController;
import com.ruoyi.xmbj.domain.AfSubscriptionPlans;
import com.ruoyi.xmbj.service.SubscriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;

@RestController
@Api(value = "支持计划",tags = {"支持计划"})
@RequestMapping("/api/ponynotes/plans")
public class PlansController  extends BaseController {

    @Autowired
    private SubscriptionService subscriptionService;

//    @PreAuthorize("@ss.hasPermi('ponynotes:appversion:list')")
    @GetMapping("/list")
    @ApiOperation("查询所有计划")
    public TableDataInfo list() {
        startPage();
        List<AfSubscriptionPlans>  list =  subscriptionService.getSubscriptionPlansPaged();
        return getDataTable(list);
    }

    @GetMapping("/getplans/{id}")
    public AjaxResult get(@PathVariable Long id) {
        AfSubscriptionPlans plan = subscriptionService.getSubscriptionPlanById(id);
        return AjaxResult.success(plan);
    }
}
