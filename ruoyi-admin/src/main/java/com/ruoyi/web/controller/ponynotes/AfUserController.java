package com.ruoyi.web.controller.ponynotes;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.xmbj.domain.AfWorkspace;
import com.ruoyi.xmbj.domain.ClientUser;
import com.ruoyi.xmbj.service.ClientUserService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * 小马笔记用户管理控制器
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/ponynotes/afusers")
public class AfUserController extends BaseController {

    @Autowired
    private ClientUserService clientUserService;


    /**
     * 分页查询AfUser列表
     * 用户列表
     * @param clientUser 查询条件 ,用户账号
     * 用户账号：输入手机号查询用户
     * 用户邮箱：输入邮箱查询用户
     * 账号状态: 全部，正常，停用
     * @return 分页结果
     */
    @PreAuthorize("@ss.hasPermi('system:afusers:list')")
    @GetMapping("/list")
    public TableDataInfo list(ClientUser clientUser) {
        startPage(); // 若依分页工具，自动从请求中获取pageNum/pageSize
        List<ClientUser> list = clientUserService.selectAfUserList(clientUser);
        return getDataTable(list); // 若依标准分页返回格式（替代直接返回AjaxResult）
    }

    /**
     * 根据ID查询AfUser详情
     * @param afuserId 用户ID
     * @return 用户详情
     */
    @PreAuthorize("@ss.hasPermi('system:afusers:query')")
    @GetMapping("/getAfuser/{afuserId}")
    public AjaxResult getAfUser(@PathVariable(value = "afuserId") Long afuserId) {
        // 非空校验
        if (afuserId == null || afuserId <= 0) {
            return AjaxResult.error("用户ID不能为空且必须为正整数");
        }
        ClientUser clientUser = clientUserService.selectAfUserById(afuserId);
        if (clientUser == null) {
            return AjaxResult.error("用户不存在");
        }
        return AjaxResult.success(clientUser);
    }

    /**
     * 修改AfUser信息
     *
     * @param clientUser 用户信息
     * @return 操作结果
     */
    @PreAuthorize("@ss.hasPermi('system:afusers:edit')")
    @Log(title = "AfUser管理", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody ClientUser clientUser) {
        // 主键校验
        if (clientUser.getUid() == null || clientUser.getUid() <= 0) {
            return AjaxResult.error("用户ID不能为空");
        }
        // 业务操作
        int result = clientUserService.updateAfUser(clientUser);
        if (result > 0) {
            return AjaxResult.success("修改用户成功");
        } else {
            return AjaxResult.error("修改用户失败，请检查数据");
        }
    }

    /**
     * 批量删除AfUser
     *
     * @param afuserIds 用户ID数组
     * @return 操作结果
     */
    @PreAuthorize("@ss.hasPermi('system:afusers:remove')")
    @Log(title = "AfUser管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/remove/{afuserIds}")
    public AjaxResult remove(@PathVariable Long[] afuserIds) {
        // 非空校验（修复原代码参数名不一致问题：afuserIds vs userIds）
        if (StringUtils.isEmpty(afuserIds) || afuserIds.length == 0) {
            return AjaxResult.error("请选择需要删除的用户");
        }
        int result = clientUserService.remove(afuserIds);
        if (result > 0) {
            return AjaxResult.success("成功删除" + result + "条用户数据");
        } else {
            return AjaxResult.error("删除用户失败，请检查数据");
        }
    }

    /**
     * 批量停用AfUser
     *
     * @param afuserIds 用户ID数组
     * @return 操作结果
     */
    @PreAuthorize("@ss.hasPermi('system:afusers:edit')")
    @Log(title = "AfUser管理", businessType = BusinessType.UPDATE)
    @PutMapping("/stop/{afuserIds}")
    public AjaxResult changeStatus(@PathVariable Long[] afuserIds) {
        // 非空校验 + 规范方法名（changestatus -> changeStatus）
        if (StringUtils.isEmpty(afuserIds) || afuserIds.length == 0) {
            return AjaxResult.error("请选择需要停用的用户");
        }
        int result = clientUserService.changestatus(afuserIds);
        if (result > 0) {
            return AjaxResult.success("成功停用" + result + "个用户");
        } else {
            return AjaxResult.error("停用用户失败，请检查数据");
        }
    }

    /**
     * 查询用户关联的工作区信息
     *
     * @param afuserId 用户ID
     * @return 工作区信息
     */
    @PreAuthorize("@ss.hasPermi('system:afusers:query')")
    @GetMapping("/getAfUserWorkSpace/{afuserId}")
    public TableDataInfo getAfUserWorkSpace(@PathVariable Long afuserId) {
        startPage();
        // 非空校验
        if (afuserId == null || afuserId <= 0) {
            // return AjaxResult.error("用户ID不能为空且必须为正整数");
            return getDataTable(null); // 返回空数据表信息（适配前端期望的格式）
        }
        ClientUser clientUser = clientUserService.selectAfUserById(afuserId);
        if (clientUser == null) {
            // return AjaxResult.error("用户不存在");
            return getDataTable(null); // 返回空数据表信息（适配前端期望的格式）
        }

        List<AfWorkspace> list = clientUserService.selectAfWorkspaceList(new AfWorkspace().setOwnerUid(String.valueOf(afuserId)));

//        List<AfWorkspace> list = clientUserService.selectAfWorkspaceList(new AfWorkspace().setUid(afuserId));

        return getDataTable(list);
    }


}
