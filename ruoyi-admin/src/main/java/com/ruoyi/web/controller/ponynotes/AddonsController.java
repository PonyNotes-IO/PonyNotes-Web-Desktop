package com.ruoyi.web.controller.ponynotes;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xmbj.domain.AfSubscriptionAddons;
import com.ruoyi.xmbj.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;

@RestController
@RequestMapping("/api/ponynotes/addons")
public class AddonsController extends BaseController {

    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * 查询补充包列表（分页）
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:afSubscriptionAddon:list')")
    @GetMapping("/list")
    public TableDataInfo list(@RequestParam(required = false) String type) {
        startPage();
        List<AfSubscriptionAddons> list = (type == null) ? subscriptionService.getAllAddons()
                : subscriptionService.getAddonsByType(type);
        return getDataTable(list);
    }

    /**
     * 获取补充包详情（单个ID）
     */
    @GetMapping("/{id}")
    public AjaxResult get(@PathVariable Long id) {
        AfSubscriptionAddons addon = subscriptionService.getAddonById(id);
        return AjaxResult.success(addon);
    }

    /**
     * 新增补充包
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:afSubscriptionAddon:add')")
    @PostMapping
    public AjaxResult add(@RequestBody AfSubscriptionAddons addon) {
        int rows = subscriptionService.addAfSubscriptionAddons(addon);
        return rows > 0 ? AjaxResult.success("新增成功") : AjaxResult.error("新增失败");
    }

    /**
     * 修改补充包
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:afSubscriptionAddon:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody AfSubscriptionAddons addon) {
        int rows = subscriptionService.updateAfSubscriptionAddons(addon);
        return rows > 0 ? AjaxResult.success("修改成功") : AjaxResult.error("修改失败");
    }

    /**
     * 删除补充包
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:afSubscriptionAddon:remove')")
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        int rows = subscriptionService.deleteAfSubscriptionAddons(id);
        return rows > 0 ? AjaxResult.success("删除成功") : AjaxResult.error("删除失败");
    }

    /**
     * 套餐详情（批量查询，保留原有接口）
     */
    @PostMapping("/detail")
    public AjaxResult detail(@RequestParam List<Long> addonIds) {
        List<AfSubscriptionAddons> list = new ArrayList<>();
        for (Long addonId : addonIds) {
            AfSubscriptionAddons afSubscriptionAddons = subscriptionService.getAddonById(addonId);
            list.add(afSubscriptionAddons);
        }
        return AjaxResult.success(list);
    }

    /**
     * 套餐配置-年卡月卡（批量更新，保留原有接口）
     */
    @PostMapping("/editBatch")
    public AjaxResult editBatch(@RequestBody List<AfSubscriptionAddons> list) {
        for (AfSubscriptionAddons item : list) {
            int result = subscriptionService.updateAfSubscriptionAddons(item);
        }
        return AjaxResult.success("修改套餐成功");
    }
}
