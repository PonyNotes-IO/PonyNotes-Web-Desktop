package com.ruoyi.web.controller.ponynotes;

import java.util.List;

import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.xmbj.domain.AiModelConfig;
import com.ruoyi.xmbj.service.AiModelConfigService;

import javax.servlet.http.HttpServletResponse;

/**
 * AI模型配置信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/ponynotes/aiModelConfig")
public class AiModelConfigController extends BaseController {
    @Autowired
    private AiModelConfigService aiModelConfigService;

    /**
     * 获取AI模型配置列表
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:aimodelconfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(AiModelConfig aiModelConfig) {
        startPage();
        List<AiModelConfig> list = aiModelConfigService.selectAiModelConfigList(aiModelConfig);
        return getDataTable(list);
    }

    /**
     * 导出AI模型配置列表
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:aimodelconfig:export')")
    @Log(title = "AI模型配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AiModelConfig aiModelConfig) {
        List<AiModelConfig> list = aiModelConfigService.selectAiModelConfigList(aiModelConfig);
        ExcelUtil<AiModelConfig> util = new ExcelUtil<AiModelConfig>(AiModelConfig.class);
        util.exportExcel(response, list, "AI模型配置数据");
    }

    /**
     * 根据AI模型配置编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:aimodelconfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(aiModelConfigService.selectAiModelConfigById(id));
    }

    /**
     * 新增AI模型配置
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:aimodelconfig:add')")
    @Log(title = "AI模型配置", businessType = BusinessType.INSERT)
    @PostMapping(value = "/add")
    public AjaxResult add(@Validated @RequestBody AiModelConfig aiModelConfig) {
        return toAjax(aiModelConfigService.insertAiModelConfig(aiModelConfig));
    }

    /**
     * 修改AI模型配置
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:aimodelconfig:edit')")
    @Log(title = "AI模型配置", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/edit")
    public AjaxResult edit(@Validated @RequestBody AiModelConfig aiModelConfig) {
        return toAjax(aiModelConfigService.updateAiModelConfig(aiModelConfig));
    }

    /**
     * 删除AI模型配置
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:aimodelconfig:remove')")
    @Log(title = "AI模型配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(aiModelConfigService.deleteAiModelConfigByIds(ids));
    }

    /**
     * 停用AI模型配置
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:aimodelconfig:edit')")
    @Log(title = "AI模型配置", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/disable")
    public AjaxResult disable(@PathVariable Long id) {
        return toAjax(aiModelConfigService.disableAiModelConfig(id));
    }

    /**
     * 启用AI模型配置
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:aimodelconfig:edit')")
    @Log(title = "AI模型配置", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/enable")
    public AjaxResult enable(@PathVariable Long id) {
        return toAjax(aiModelConfigService.enableAiModelConfig(id));
    }

    /**
     * 查询所有活跃的AI模型配置
     */
    @GetMapping("/active")
    public AjaxResult getActive() {
        List<AiModelConfig> list = aiModelConfigService.selectActiveAiModelConfigs();
        return success(list);
    }
}