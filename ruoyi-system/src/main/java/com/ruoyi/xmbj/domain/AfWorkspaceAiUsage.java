package com.ruoyi.xmbj.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 工作区AI使用量实体
 *
 * @author ruoyi
 */
@Data
@Table(name = "af_workspace_ai_usage")
public class AfWorkspaceAiUsage {

    /** 统计日期 */
    @Column(name = "created_at")
    private Date createdAt;

    /** 工作区ID */
    @Column(name = "workspace_id")
    private String workspaceId;

    /** 当日AI搜索请求次数 */
    @Column(name = "search_requests")
    private Integer searchRequests;

    /** 当日AI搜索消耗令牌数 */
    @Column(name = "search_tokens_consumed")
    private Long searchTokensConsumed;

    /** 当日AI索引消耗令牌数 */
    @Column(name = "index_tokens_consumed")
    private Long indexTokensConsumed;

    @Column(name = "ai_responses_count")
    private Long aiResponsesCount;

    @Column(name = "ai_image_responses_count")
    private Long aiImageResponsesCount;


}
