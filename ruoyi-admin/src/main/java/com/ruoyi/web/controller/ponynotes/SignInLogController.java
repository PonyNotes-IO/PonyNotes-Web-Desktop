package com.ruoyi.web.controller.ponynotes;

import java.util.List;

import com.ruoyi.xmbj.domain.ClientUser;
import com.ruoyi.xmbj.domain.SignInLogStatistics;
import com.ruoyi.xmbj.domain.ThirdPartySign;
import com.ruoyi.xmbj.service.SignInLogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.xmbj.domain.SignInLog;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 登录日志Controller
 * 登录信息
 * @author ruoyi
 * @date 2025-12-27
 */
@RestController
@RequestMapping("/api/ponynotes/signInLog")
public class SignInLogController extends BaseController {
    @Autowired
    private SignInLogService signInLogService;


    /**
     * 查询登录日志列表
     */
    @PreAuthorize("@ss.hasPermi('auth:signInLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(SignInLogStatistics signInLogStatistics) {
        startPage();
        List<SignInLogStatistics> list = signInLogService.selectSignInLogList(signInLogStatistics);
        return getDataTable(list);
    }

    /**
     * 查询登录概况
     */
    @PreAuthorize("@ss.hasPermi('auth:signInLog:list')")
    @GetMapping("/overview")
    public AjaxResult overview(SignInLog signInLog) {
        return AjaxResult.success(signInLogService.selectSignInOverview(signInLog));
    }

    /**
     * 导出登录日志列表
     */
    @PreAuthorize("@ss.hasPermi('auth:signInLog:export')")
    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(SignInLogStatistics signInLogStatistics) {
        List<SignInLogStatistics> list = signInLogService.selectSignInLogList(signInLogStatistics);
        ExcelUtil<SignInLogStatistics> util = new ExcelUtil<SignInLogStatistics>(SignInLogStatistics.class);
        return util.exportExcel(list, "登录日志数据");
    }

    /**
     * 查询登录日志列表
     */
    @PreAuthorize("@ss.hasPermi('auth:signInLog:list')")
    @GetMapping("/signInLogList")
    public TableDataInfo list(SignInLog signInLog) {
        startPage();
        List<SignInLog> list = signInLogService.selectSignInOverview(signInLog);
        return getDataTable(list);
    }

    /**
     * 查询登录日志列表
     */
    @PreAuthorize("@ss.hasPermi('auth:signThirdPartList:list')")
    @GetMapping("/signThirdPartList")
    public AjaxResult signThirdPartList(ThirdPartySign thirdPartySign) {
        List<ThirdPartySign> list = signInLogService.signThirdPartList(thirdPartySign);
        return AjaxResult.success(list);
    }
    /**
     * 解绑第三方登录
     */
    @PreAuthorize("@ss.hasPermi('system:afusers:edit')")
    @Log(title = "AfUser管理", businessType = BusinessType.UPDATE)
    @PutMapping("/unbind")
    public AjaxResult unbind(@Validated @RequestBody ThirdPartySign thirdPartySign) {
        // 业务操作
        int result = signInLogService.unbindThirdPartSign(thirdPartySign);
        if (result > 0) {
            return AjaxResult.success("解绑第三方用户登录成功");
        } else {
            return AjaxResult.error("解绑第三方用户登录失败");
        }
    }
}