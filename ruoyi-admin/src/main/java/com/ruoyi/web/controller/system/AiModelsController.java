package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.AiModels;
import com.ruoyi.system.service.IAiModelsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * AI模型管理Controller
 * 
 * @author ruoyi
 * @date 2026-01-25
 */
@RestController
@RequestMapping("/system/models")
public class AiModelsController extends BaseController
{
    @Autowired
    private IAiModelsService aiModelsService;

    /**
     * 查询AI模型管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:models:list')")
    @GetMapping("/list")
    public TableDataInfo list(AiModels aiModels)
    {
        startPage();
        List<AiModels> list = aiModelsService.selectAiModelsList(aiModels);
        return getDataTable(list);
    }

    /**
     * 导出AI模型管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:models:export')")
    @Log(title = "AI模型管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AiModels aiModels)
    {
        List<AiModels> list = aiModelsService.selectAiModelsList(aiModels);
        ExcelUtil<AiModels> util = new ExcelUtil<AiModels>(AiModels.class);
        util.exportExcel(response, list, "AI模型管理数据");
    }

    /**
     * 获取AI模型管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:models:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(aiModelsService.selectAiModelsById(id));
    }

    /**
     * 新增AI模型管理
     */
    @PreAuthorize("@ss.hasPermi('system:models:add')")
    @Log(title = "AI模型管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AiModels aiModels)
    {
        aiModels.setCreatedAt(new java.util.Date());
        aiModels.setOperator(String.valueOf(getUserId()));
        return toAjax(aiModelsService.insertAiModels(aiModels));
    }

    /**
     * 修改AI模型管理
     */
    @PreAuthorize("@ss.hasPermi('system:models:edit')")
    @Log(title = "AI模型管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AiModels aiModels)
    {
        return toAjax(aiModelsService.updateAiModels(aiModels));
    }

    /**
     * 删除AI模型管理
     */
    @PreAuthorize("@ss.hasPermi('system:models:remove')")
    @Log(title = "AI模型管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(aiModelsService.deleteAiModelsByIds(ids));
    }
}
