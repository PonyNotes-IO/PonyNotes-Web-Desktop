package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.NoteShare;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface NoteShareMapper {

    /**
     * 根据分享ID查询分享记录
     * @param shareId 分享ID
     * @return 分享记录对象
     */
    NoteShare selectByShareId(@Param("shareId") String shareId);

    /**
     * 根据分享ID和令牌查询分享记录
     * @param shareId 分享ID
     * @param shareToken 分享令牌
     * @return 分享记录对象
     */
    NoteShare selectByShareIdAndToken(@Param("shareId") String shareId, @Param("shareToken") String shareToken);

    /**
     * 根据笔记ID查询分享记录
     * @param noteId 笔记ID
     * @return 分享记录列表
     */
    List<NoteShare> selectByNoteId(@Param("noteId") String noteId);

    /**
     * 根据作者ID查询分享记录
     * @param authorId 作者ID
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 分享记录列表
     */
    List<NoteShare> selectByAuthorId(@Param("authorId") String authorId, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 新增分享记录
     * @param noteShare 分享记录对象
     * @return 影响行数
     */
    int insert(NoteShare noteShare);

    /**
     * 更新分享记录
     * @param noteShare 分享记录对象
     * @return 影响行数
     */
    int update(NoteShare noteShare);

    /**
     * 删除分享记录
     * @param shareId 分享ID
     * @return 影响行数
     */
    int delete(@Param("shareId") String shareId);

    /**
     * 更新分享状态
     * @param shareId 分享ID
     * @param isActive 是否激活
     * @return 影响行数
     */
    int updateActiveStatus(@Param("shareId") String shareId, @Param("isActive") Boolean isActive);

    /**
     * 增加访问次数
     * @param shareId 分享ID
     * @return 影响行数
     */
    int incrementAccessCount(@Param("shareId") String shareId);

    /**
     * 根据笔记ID删除所有分享记录
     * @param noteId 笔记ID
     * @return 影响行数
     */
    int deleteByNoteId(@Param("noteId") String noteId);

    /**
     * 根据作者ID查询分享记录总数
     * @param authorId 作者ID
     * @return 分享记录总数
     */
    int countByAuthorId(@Param("authorId") String authorId);

}
