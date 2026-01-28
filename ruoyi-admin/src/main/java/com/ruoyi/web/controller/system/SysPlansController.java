package com.ruoyi.web.controller.system;


import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysPlans;
import com.ruoyi.system.service.ISysPlansService;

/**
 * 套餐信息Controller
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/plans")
public class SysPlansController extends BaseController
{
    private String prefix = "system/plans";

    @Autowired
    private ISysPlansService sysPlansService;

    @PreAuthorize("@ss.hasPermi('system:plans:view')")
    @GetMapping()
    public String plans()
    {
        return prefix + "/plans";
    }

    /**
     * 查询套餐列表 - RESTful风格
     */
    @PreAuthorize("@ss.hasPermi('system:plans:list')")
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysPlans sysPlans)
    {
        startPage();
        List<SysPlans> list = sysPlansService.selectPlansList(sysPlans);
        return getDataTable(list);
    }

    /**
     * 查询套餐列表 - 兼容原有POST方式
     */
    @PreAuthorize("@ss.hasPermi('system:plans:list')")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo listPost(SysPlans sysPlans)
    {
        return list(sysPlans);
    }

    /**
     * 导出套餐列表
     */
    @Log(title = "套餐管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:plans:export')")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysPlans sysPlans)
    {
        List<SysPlans> list = sysPlansService.selectPlansList(sysPlans);
        ExcelUtil<SysPlans> util = new ExcelUtil<SysPlans>(SysPlans.class);
        return util.exportExcel(list, "套餐数据");
    }

    /**
     * 新增套餐页面
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增套餐 - RESTful风格
     */
    @Log(title = "套餐管理", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('system:plans:add')")
    @PostMapping()
    @ResponseBody
    public AjaxResult addSave(@Validated @RequestBody SysPlans sysPlans)
    {
        return toAjax(sysPlansService.insertPlans(sysPlans));
    }

    /**
     * 新增套餐 - 兼容原有方式
     */
    @Log(title = "套餐管理", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('system:plans:add')")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSaveLegacy(@Validated SysPlans sysPlans)
    {
        return addSave(sysPlans);
    }

    /**
     * 修改套餐页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        mmap.put("sysPlans", sysPlansService.selectPlansById(id));
        return prefix + "/edit";
    }

    /**
     * 修改套餐 - RESTful风格
     */
    @Log(title = "套餐管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('system:plans:edit')")
    @PutMapping()
    @ResponseBody
    public AjaxResult editSave(@Validated @RequestBody SysPlans sysPlans)
    {
        return toAjax(sysPlansService.updatePlans(sysPlans));
    }

    /**
     * 修改套餐 - 兼容原有方式
     */
    @Log(title = "套餐管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('system:plans:edit')")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSaveLegacy(@Validated SysPlans sysPlans)
    {
        return editSave(sysPlans);
    }

    /**
     * 删除套餐 - RESTful风格
     */
    @Log(title = "套餐管理", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('system:plans:remove')")
    @DeleteMapping("/{id}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("id") Long id)
    {
        return toAjax(sysPlansService.deletePlansById(id));
    }

    /**
     * 批量删除套餐 - RESTful风格
     */
    @Log(title = "套餐管理", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('system:plans:remove')")
    @DeleteMapping("/batch/{ids}")
    @ResponseBody
    public AjaxResult removeBatch(@PathVariable("ids") Long[] ids)
    {
        return toAjax(sysPlansService.deletePlansByIds(ids));
    }

    /**
     * 批量删除套餐 - 兼容原有方式
     */
    @Log(title = "套餐管理", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('system:plans:remove')")
    @PostMapping("/remove/batch")
    @ResponseBody
    public AjaxResult removeBatchLegacy(@RequestParam Long[] ids)
    {
        return removeBatch(ids);
    }

    /**
     * 查询套餐详细 - RESTful风格
     */
    @PreAuthorize("@ss.hasPermi('system:plans:query')")
    @GetMapping("/{id}")
    @ResponseBody
    public AjaxResult getPlans(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysPlansService.selectPlansById(id));
    }
}