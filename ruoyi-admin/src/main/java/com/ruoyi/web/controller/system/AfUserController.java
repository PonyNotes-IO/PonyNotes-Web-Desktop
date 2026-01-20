package com.ruoyi.web.controller.system;

import java.util.List;
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
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.AfUser;
import com.ruoyi.system.service.IAfUserService;

/**
 * 用户表 信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/afUser")
public class AfUserController extends BaseController
{
    @Autowired
    private IAfUserService afUserService;

    
    /**
     * 获取用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:afUser:list')")
    @GetMapping("/list")
    public TableDataInfo list(AfUser afUser)
    {
        startPage();
        List<AfUser> list = afUserService.selectUserList(afUser);
        return getDataTable(list);
    }

    /**
     * 根据用户ID获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:afUser:query')")
    @GetMapping("/{uid}")
    public AjaxResult getInfo(@PathVariable("uid") Long uid)
    {
        return AjaxResult.success(afUserService.selectUserById(uid));
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('system:afUser:add')")
    @Log(title = "用户表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AfUser afUser)
    {
        if (StringUtils.isNotEmpty(afUser.getUuid()) && !afUserService.checkUuidUnique(afUser.getUuid())) {
            return AjaxResult.error("新增用户失败，UUID已存在");
        }
        if (StringUtils.isNotEmpty(afUser.getEmail()) && !afUserService.checkEmailUnique(afUser)) {
            return AjaxResult.error("新增用户失败，邮箱已存在");
        }
        return toAjax(afUserService.insertUser(afUser));
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('system:afUser:edit')")
    @Log(title = "用户表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody AfUser afUser)
    {
        if (StringUtils.isNotEmpty(afUser.getEmail()) && !afUserService.checkEmailUnique(afUser)) {
            return AjaxResult.error("修改用户失败，邮箱已存在");
        }
        return toAjax(afUserService.updateUser(afUser));
    }

    /**
     * 删除用户
     */
    @PreAuthorize("@ss.hasPermi('system:afUser:remove')")
    @Log(title = "用户表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{uid}")
    public AjaxResult remove(@PathVariable Long uid)
    {
        return toAjax(afUserService.deleteUserById(uid));
    }

    /**
     * 批量删除用户
     */
    @PreAuthorize("@ss.hasPermi('system:afUser:remove')")
    @Log(title = "用户表", businessType = BusinessType.DELETE)
    @DeleteMapping("/batch")
    public AjaxResult removeBatch(@RequestBody Long[] uids)
    {
        return toAjax(afUserService.deleteUserByIds(uids));
    }
}