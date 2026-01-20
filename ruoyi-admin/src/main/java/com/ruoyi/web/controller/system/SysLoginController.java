package com.ruoyi.web.controller.system;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.ruoyi.xmbj.domain.ClientUser;
import com.ruoyi.xmbj.service.ClientUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.AccountQueryRequest;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.PhoneLoginRequest;
import com.ruoyi.common.core.domain.model.WechatLoginRequest;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.web.model.LoginVo;
import com.ruoyi.web.service.EmailService;
import com.ruoyi.web.service.SmsService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.xmbj.api.service.XmbjAuthService;
import com.ruoyi.xmbj.api.protocol.PhoneLoginResponse;
import org.springframework.http.MediaType;

/**
 * 登录验证
 * 
 * @author ruoyi
 */
@RestController
@Api(
        value = "系统用户", tags = {"用户"}
)
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private XmbjAuthService xmbjAuthService;

    @Autowired
    private ClientUserService clientUserService;

    @GetMapping("/api/health")
    @ResponseBody
    @Anonymous
    public AjaxResult health() {
        AjaxResult ajax = AjaxResult.success("test success");
        System.out.println("test success");
        return ajax;
    }

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    @ApiOperation("登录")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 根据账号（手机号/邮箱）查询用户信息
     */
    @Anonymous
    @ApiOperation("用户信息")
    @PostMapping(value = "/api/getuserinfo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult getUserInfo(@RequestBody AccountQueryRequest request) {
        SysUser user = null;
        if ("phone".equals(request.getAccountType())) {
            user = userService.selectUserByPhone(request.getAccount());
        } else if ("email".equals(request.getAccountType())) {
            user = userService.selectUserByEmail(request.getAccount());
        }

        if (user != null) {
            AjaxResult ajax = AjaxResult.success(user);
            // 登录 - 不需要密码验证，因为是通过账号查询直接登录
            String token = loginService.loginWithAccountType(user.getUserName(), user.getPassword(), false);
            ajax.put(Constants.TOKEN, token);
            return ajax;
        } else {
            return AjaxResult.error("Account is not existing");
        }
    }

    @Anonymous
    @PostMapping(value ="/api/loginwithPassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult loginwithPassword(@RequestBody LoginVo loginVo) {
        SysUser user = null;
        // 参数校验
        if ( StringUtils.isBlank(loginVo.getPassword())) {
            return AjaxResult.error("password  can't empty !");
        }
        if("phone".equals(loginVo.getAccountType()) && StringUtils.isEmpty(loginVo.getPhone())){
            return AjaxResult.error("phonenumber  can't empty !");
        }
        if("email".equals(loginVo.getAccountType()) && StringUtils.isEmpty(loginVo.getEmail())){
            return AjaxResult.error("email  can't empty !");
        }

        if("phone".equals(loginVo.getAccountType())){
             user = userService.loginPhonePassword(loginVo.getPhone(), loginVo.getPassword());
        }else if("email".equals(loginVo.getAccountType())){
             user = userService.loginEmailPassword(loginVo.getEmail(), loginVo.getPassword());
        }
        if (user == null) {
            return AjaxResult.error("username or password is error");
        }
        AjaxResult ajax = AjaxResult.success(user);
        // 登录 - 需要密码验证，因为是通过密码直接登录
        String token = loginService.loginWithAccountType(user.getUserName(), user.getPassword(), true);
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }


    @Anonymous
    @ApiOperation("验证码登录")
    @PostMapping(value ="/api/loginWithCode", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult loginWithCode(@RequestBody LoginVo loginVo) {
        if (StringUtils.isEmpty(loginVo.getLoginType())) {
            return AjaxResult.error("please input loginType ");
        }
        if (StringUtils.isBlank(loginVo.getCode())) {
            return AjaxResult.error("verify code can't empty");
        }
        boolean loginType_password = "password".equals(loginVo.getLoginType());
        boolean loginType_code = "code".equals(loginVo.getLoginType());
        SysUser user = null;
        String xmbjAccessToken = null; // 用于保存 XMBJ token

        if ("phone".equals(loginVo.getAccountType()) && loginType_code) {

            // 参数校验
            if (StringUtils.isBlank(loginVo.getPhone())) {
                return AjaxResult.error("phoneNumber  can't empty");
            }

            // 验证验证码
            boolean verifyResult = smsService.verifyLoginCode(loginVo.getPhone(), loginVo.getCode());
            if (!verifyResult) {
                return AjaxResult.error("verify code is error or timeover");
            }

            // 查询用户
            user = userService.selectUserByPhone(loginVo.getPhone());
            if (user == null) {
                // 用户为空注册用户
                user = new SysUser();
                user.setUserName(loginVo.getPhone());
                user.setNickName(loginVo.getPhone());
                user.setPhonenumber(loginVo.getPhone());
                user.setNewAccount(true);
                userService.registerUser(user);
            }

        } else if ("email".equals(loginVo.getAccountType()) && loginType_code) {
            // 参数校验
            if (StringUtils.isBlank(loginVo.getEmail())) {
                return AjaxResult.error("email can't empty!");
            }
            // 验证验证码
            boolean verifyResult = emailService.verifyEmailCode(loginVo.getEmail(), loginVo.getCode());
            if (!verifyResult) {
                return AjaxResult.error("verify code is error or timeover");
            }

            // 查询用户
            user = userService.selectUserByEmail(loginVo.getEmail());

            if (user == null) {
                // 用户为空注册用户
                user = new SysUser();
                user.setEmail(loginVo.getEmail());
                user.setNickName(loginVo.getEmail());
                user.setFirstEmailLogin(true);
                user.setUserName(loginVo.getEmail());
                userService.registerUser(user);
            }

            // 【新增】如果有手机号，尝试获取 XMBJ token（邮箱不支持直接登录 XMBJ）
            // 实际中可能需要额外的登录机制，暂时跳过
            System.out.println("邮箱登录暂不同步 XMBJ token，需要额外认证");

        } else {
            return AjaxResult.error("please input right accountType");
        }
        ClientUser clientUser = clientUserService.getClientUserByUserInfo((StringUtils.isEmpty(loginVo.getPhone())?loginVo.getPhone():loginVo.getEmail()));
        if (clientUser ==null){
            return AjaxResult.error("客户端用户未注册");
        }
        AjaxResult ajax = AjaxResult.success(user);
        String token = loginService.loginWithAccountType(user.getUserName(), user.getPassword());
        ajax.put(Constants.TOKEN, token);

        // 【新增】如果成功获取了 XMBJ token，也返回给客户端
        if (xmbjAccessToken != null && !xmbjAccessToken.isEmpty()) {
            ajax.put("xmbj_access_token", xmbjAccessToken);
            ajax.put("xmbj_token_type", "Bearer");
        }

        return ajax;
    }

    @Anonymous
    @ApiOperation("注册用户")
    @PostMapping(value ="/api/registerUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult registerUser(@RequestBody LoginVo loginVo) {
        if (StringUtils.isEmpty(loginVo.getLoginType())) {
            return AjaxResult.error("please input login type ");
        }
        System.out.println("请输入登录方式"+loginVo.getPhone());
        SysUser user = new SysUser();
        user.setUserName(loginVo.getPhone());
        user.setNickName(loginVo.getPhone());
        user.setPhonenumber(loginVo.getPhone());
        user.setNewAccount(true);
        user.setFirstEmailLogin(true);
        user.setEmail(loginVo.getEmail());
        userService.registerUser(user);
        return AjaxResult.success(user);
    }

    @Anonymous
    @ApiOperation("修改绑定手机号")
    @PostMapping(value ="/api/changePhoneByCode", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult changePhoneByCode(@RequestBody LoginVo loginVo) {
        if (StringUtils.isEmpty(loginVo.getPhone()) || StringUtils.isEmpty(loginVo.getUsername())) {
            return AjaxResult.error("Please enter the changed mobile phone number and login username");
        }
        if (StringUtils.isEmpty(loginVo.getCode())){
            return  AjaxResult.error("Please enter the verification code\n");
        }
        boolean verifycode = smsService.verifyLoginCode(loginVo.getPhone(),loginVo.getCode());
        if (!verifycode){
            return AjaxResult.error("Please enter the verification code or verification code timeover");
        }
        SysUser selectUserByPhone = userService.selectUserByPhone(loginVo.getPhone());
        SysUser user = userService.selectUserByUserName(loginVo.getUsername());
        if(user ==null){
            return  AjaxResult.error("The user account does not exist");
        }
        if(selectUserByPhone !=null  && !selectUserByPhone.getUserId().equals(user.getUserId())){
            return  AjaxResult.error("the phone is existed,  please change other phone number");
        }
        user.setUserName(loginVo.getPhone());
        user.setNickName(loginVo.getPhone());
        user.setPhonenumber(loginVo.getPhone());
        user.setNewAccount(true);
        userService.updateUser(user);
        return AjaxResult.success(user);
    }



    @Anonymous
    @ApiOperation("设置密码")
    @PostMapping(value ="/api/setPassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult setPassword(@RequestBody LoginVo loginVo) {
        if (StringUtils.isEmpty(loginVo.getLoginType())) {
            return AjaxResult.error("请输入登录方式");
        }

        boolean loginType_code = "code".equals(loginVo.getLoginType());
        SysUser user = null;
        if (StringUtils.isBlank(loginVo.getPassword())) {
            return AjaxResult.error("Please enter your loginType");
        }
        if ("phone".equals(loginVo.getAccountType()) ) {
            
            // 参数校验
            if (StringUtils.isBlank(loginVo.getPhone())) {
                return AjaxResult.error("Mobile phone number and verification code cannot be empty");
            }

            // 查询用户
             user = userService.selectUserByPhone(loginVo.getPhone());

            if (user == null) {
                // 用户为空注册用户
                user = new SysUser();
                user.setUserName(loginVo.getPhone());
                user.setNickName(loginVo.getPhone());
                user.setPhonenumber(loginVo.getPhone());
                user.setNewAccount(true);
                String password = SecurityUtils.encryptPassword(loginVo.getPassword());
                user.setPassword(password);
                userService.registerUser(user);
                // return AjaxResult.error("该手机号未注册");
            }else{
                // 用户存在，更新密码等逻辑
                String password = SecurityUtils.encryptPassword(loginVo.getPassword());
                user.setPassword(password);
                userService.updateUser(user);
            }
           

        }else if ("email".equals(loginVo.getAccountType()) ) {
               // 参数校验
            if (StringUtils.isBlank(loginVo.getEmail())) {
                return AjaxResult.error("Email and verification code cannot be empty");
            }
            // 查询用户
            user = userService.selectUserByEmail(loginVo.getEmail());
            if (user == null) {
                user = new SysUser();
                user.setEmail(loginVo.getEmail());
                user.setNickName(loginVo.getEmail());
                user.setUserName(loginVo.getEmail());
                user.setFirstEmailLogin(true);
                user.setEmailPassword(loginVo.getPassword());
                String password = SecurityUtils.encryptPassword(loginVo.getPassword());
                user.setPassword(password);
            }else{
                // 用户存在，更新密码等逻辑
                user.setEmailPassword(loginVo.getPassword());
                String password = SecurityUtils.encryptPassword(loginVo.getPassword());
                user.setPassword(password);
                userService.updateUser(user);
            }
        }
//        return AjaxResult.success(user);
         AjaxResult ajax = AjaxResult.success(user);
         // 登录 - 不需要密码验证，因为是刚设置完密码直接登录
         String token = loginService.loginWithAccountType(user.getUserName(), user.getPassword(), false);
         ajax.put(Constants.TOKEN, token);
         return ajax;
    }

    @Anonymous
    @ApiOperation("绑定手机号")
    @PostMapping(value ="/api/bindPhone", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult bindPhone(@RequestBody LoginVo loginVo) {
        if (StringUtils.isEmpty(loginVo.getLoginType())) {
            return AjaxResult.error("Please enter your loginType");
        }
        if (StringUtils.isEmpty(loginVo.getEmail()) || StringUtils.isEmpty(loginVo.getPhone())) {
            return AjaxResult.error("Please enter the correct account type");
        }
        boolean loginType_code = "code".equals(loginVo.getLoginType());
        SysUser user = null;
        if (!"email".equals(loginVo.getAccountType()) || !loginType_code) {
            return AjaxResult
                    .error("Please enter the correct account type, email login, mobile phone verification code");
        }

        String key = "sms:verify:" + loginVo.getPhone();
        String storedCode = redisTemplate.opsForValue().get(key);
        // 验证验证码
        boolean verifyResult = smsService.verifySmsCode(loginVo.getPhone(), loginVo.getCode(), storedCode);
        if (!verifyResult) {
            return AjaxResult.error("The verification code is incorrect or has expired\n");
        }

        // 查询用户手机号
        user = userService.selectUserByPhone(loginVo.getPhone());
        if (user != null) {
            user.setEmail(loginVo.getEmail());
        } else {
            // 查询用户
            user = userService.selectUserByEmail(loginVo.getEmail());
        }
        if (user == null) {
            // 用户为空注册用户
            user = new SysUser();
            user.setEmail(loginVo.getEmail());
            user.setNickName(loginVo.getEmail());
            user.setUserName(loginVo.getEmail());
            user.setPhonenumber(loginVo.getPhone());
            user.setFirstEmailLogin(true);
            user.setNewAccount(true);
            userService.registerUser(user);
            // return AjaxResult.error("该手机号未注册");
        }
        user.setPhonenumber(loginVo.getPhone());
        user.setNewAccount(true);
        userService.updateUser(user);
        AjaxResult ajax = AjaxResult.success(loginVo);
        return ajax;
    }

    @Anonymous
    @ApiOperation("绑定邮箱")
    @PostMapping(value ="/api/bindEmail", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult bindEmail(@RequestBody LoginVo loginVo) {
        if (StringUtils.isEmpty(loginVo.getLoginType())) {
            return AjaxResult.error("Please enter your loginType");
        }
        if (StringUtils.isBlank(loginVo.getEmail()) || StringUtils.isBlank(loginVo.getCode())) {
            return AjaxResult.error("Email and verification code cannot be empty");
        }
        if (StringUtils.isEmpty(loginVo.getUsername())) {
            return AjaxResult.error("please enter username");
        }
        // 验证验证码
        boolean verifyResult = emailService.verifyEmailCode(loginVo.getEmail(), loginVo.getCode());
        if (!verifyResult) {
            return AjaxResult.error("The verification code is incorrect or has expired\n");
        }
        SysUser user = userService.selectUserByUserName(loginVo.getUsername());
        if (user == null) {
            return AjaxResult.error("user not exist");
        }
        SysUser userByEmail = userService.selectUserByEmail(loginVo.getEmail());

        if (userByEmail != null && user != null && !user.getUserId().equals(userByEmail.getUserId())) {
            return AjaxResult.error("The email address is already linked to another account");
        }
        user.setEmail(loginVo.getEmail());
        user.setFirstEmailLogin(true);
        userService.updateUser(user);
        AjaxResult ajax = AjaxResult.success(user);
        return ajax;
    }

    @Anonymous
    @ApiOperation("更新密码")
    @PostMapping(value ="/api/updatePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult updatePassword(@RequestBody LoginVo loginVo) {
        if (StringUtils.isEmpty(loginVo.getPassword())) {
            return AjaxResult.error("please input your password");
        }
        SysUser user = null;
        if (StringUtils.isBlank(loginVo.getUsername())) {
            return AjaxResult.error("Please enter your username");
        }
        user = userService.selectUserByUserName(loginVo.getUsername());
        if (user == null) {
            return AjaxResult.error("This user is not registered");
        }
        // 用户存在，更新密码等逻辑
        String password = SecurityUtils.encryptPassword(loginVo.getPassword());
        user.setPassword(password);
        userService.updateUser(user);
        return AjaxResult.success(user);
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    @ApiOperation("用户信息")
    public AjaxResult getInfo()
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        if (!loginUser.getPermissions().equals(permissions))
        {
            loginUser.setPermissions(permissions);
            tokenService.refreshToken(loginUser);
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        ajax.put("isDefaultModifyPwd", initPasswordIsModify(user.getPwdUpdateDate()));
        ajax.put("isPasswordExpired", passwordIsExpiration(user.getPwdUpdateDate()));
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    @ApiOperation("获取菜单")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }

    // 检查初始密码是否提醒修改
    public boolean initPasswordIsModify(Date pwdUpdateDate) {
        Integer initPasswordModify = Convert.toInt(configService.selectConfigByKey("sys.account.initPasswordModify"));
        return initPasswordModify != null && initPasswordModify == 1 && pwdUpdateDate == null;
    }

    // 检查密码是否过期
    public boolean passwordIsExpiration(Date pwdUpdateDate) {
        Integer passwordValidateDays = Convert
                .toInt(configService.selectConfigByKey("sys.account.passwordValidateDays"));
        if (passwordValidateDays != null && passwordValidateDays > 0) {
            if (StringUtils.isNull(pwdUpdateDate)) {
                // 如果从未修改过初始密码，直接提醒过期
                return true;
            }
            Date nowDate = DateUtils.getNowDate();
            return DateUtils.differentDaysByMillisecond(nowDate, pwdUpdateDate) > passwordValidateDays;
        }
        return false;
    }
}
