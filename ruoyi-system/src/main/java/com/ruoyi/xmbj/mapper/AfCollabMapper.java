package com.ruoyi.xmbj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.xmbj.domain.AfCollab;
import com.ruoyi.xmbj.domain.NoteBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AfCollab数据访问接口
 */
@Mapper
public interface AfCollabMapper extends BaseMapper<AfCollab> {
    List<NoteBook> getNotesByWorkspaceId(String workspaceId, String ownerUid);

    List<NoteBook> selectNotesByWorkspace(String workspaceId);

    NoteBook getNotesByNoteId(String oid);
}