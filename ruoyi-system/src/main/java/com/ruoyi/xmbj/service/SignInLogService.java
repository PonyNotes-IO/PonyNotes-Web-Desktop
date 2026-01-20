package com.ruoyi.xmbj.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.domain.SignInLogStatistics;
import com.ruoyi.xmbj.domain.ThirdPartySign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.xmbj.domain.SignInLog;
import com.ruoyi.xmbj.mapper.SignInLogMapper;

/**
 * 登录日志Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class SignInLogService {
    @Autowired
    private SignInLogMapper signInLogMapper;

    /**
     * 查询登录日志列表
     *
     * @param SignInLogStatistics 登录日志
     * @return 登录日志
     */
    @DataSource(DataSourceType.slave)
    public List<SignInLogStatistics> selectSignInLogList(SignInLogStatistics signInLogStatistics) {
        return signInLogMapper.selectSignInLogList(signInLogStatistics);
    }

    /**
     * 查询登录概况
     *
     * @param signInLog 登录日志
     * @return 登录概况
     */
    @DataSource(DataSourceType.slave)
    public List<SignInLog> selectSignInOverview(SignInLog signInLog) {
        return signInLogMapper.selectSignInOverview(signInLog);
    }

    @DataSource(DataSourceType.slave)
    public List<ThirdPartySign> signThirdPartList(ThirdPartySign thirdPartySign) {
        return signInLogMapper.signThirdPartList(thirdPartySign);
    }

    @DataSource(DataSourceType.slave)
    public int unbindThirdPartSign(ThirdPartySign thirdPartySign) {
        return signInLogMapper.unbindThirdPartSign(thirdPartySign);
    }
}