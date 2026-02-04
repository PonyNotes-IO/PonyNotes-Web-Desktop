package com.ruoyi.xmbj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.domain.SignInLog;
import com.ruoyi.xmbj.domain.SignInLogStatistics;
import com.ruoyi.xmbj.domain.ThirdPartySign;

import java.util.List;

/**
 * 登录日志Mapper接口
 *
 * @author ruoyi
 */
@DataSource(DataSourceType.SLAVE)
public interface SignInLogMapper extends BaseMapper<SignInLog> {
    /**
     * 查询登录日志列表
     *
     * @param signInLog 登录日志
     * @return 登录日志集合
     */
    public List<SignInLogStatistics> selectSignInLogList(SignInLogStatistics signInLog);

    public List<SignInLog>  selectSignInOverview(SignInLog signInLog);

    List<ThirdPartySign> signThirdPartList(ThirdPartySign thirdPartySign);

    int unbindThirdPartSign(ThirdPartySign thirdPartySign);
}