package com.ruoyi.xmbj.controller;

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
import com.ruoyi.xmbj.domain.AfUser;
import com.ruoyi.xmbj.service.IAfUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户管理11Controller
 * 
 * @author ruoyi
 * @date 2026-01-21
 */
@RestController
@RequestMapping("/afuser/usermgr")
public class AfUserController extends BaseController
{
    @Autowired
    private IAfUserService afUserService;

    /**
     * 查询用户管理11列表
     */
    @PreAuthorize("@ss.hasPermi('afuser:usermgr:list')")
    @GetMapping("/list")
    public TableDataInfo list(AfUser afUser)
    {
        startPage();
        List<AfUser> list = afUserService.selectAfUserList(afUser);
        return getDataTable(list);
    }

    /**
     * 导出用户管理11列表
     */
    @PreAuthorize("@ss.hasPermi('afuser:usermgr:export')")
    @Log(title = "用户管理11", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AfUser afUser)
    {
        List<AfUser> list = afUserService.selectAfUserList(afUser);
        ExcelUtil<AfUser> util = new ExcelUtil<AfUser>(AfUser.class);
        util.exportExcel(response, list, "用户管理11数据");
    }

    /**
     * 获取用户管理11详细信息
     */
    @PreAuthorize("@ss.hasPermi('afuser:usermgr:query')")
    @GetMapping(value = "/{uid}")
    public AjaxResult getInfo(@PathVariable("uid") Long uid)
    {
        return success(afUserService.selectAfUserByUid(uid));
    }

    /**
     * 新增用户管理11
     */
    @PreAuthorize("@ss.hasPermi('afuser:usermgr:add')")
    @Log(title = "用户管理11", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AfUser afUser)
    {
        return toAjax(afUserService.insertAfUser(afUser));
    }

    /**
     * 修改用户管理11
     */
    @PreAuthorize("@ss.hasPermi('afuser:usermgr:edit')")
    @Log(title = "用户管理11", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AfUser afUser)
    {
        return toAjax(afUserService.updateAfUser(afUser));
    }

    /**
     * 删除用户管理11
     */
    @PreAuthorize("@ss.hasPermi('afuser:usermgr:remove')")
    @Log(title = "用户管理11", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uids}")
    public AjaxResult remove(@PathVariable Long[] uids)
    {
        return toAjax(afUserService.deleteAfUserByUids(uids));
    }
}
