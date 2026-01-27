package com.ruoyi.xmbj.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.domain.AfUserWorkspace;
import com.ruoyi.xmbj.domain.AfWorkspace;
import com.ruoyi.xmbj.domain.NoteBook;
import com.ruoyi.xmbj.mapper.AfCollabMapper;
import com.ruoyi.xmbj.mapper.AfWorkspaceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工作区Service业务层处理
 *
 * @author ruoyi
 */
@Slf4j
@Service
@Transactional
public class AfWorkspaceService {

    @Autowired
    private AfWorkspaceMapper afWorkspaceMapper;

    @Autowired
    private AfCollabMapper afCollabMapper;

    /**
     * 查询工作区
     *
     * @param workspaceId 工作区主键
     * @return 工作区
     */
    @DataSource(DataSourceType.SLAVE)
    public AfWorkspace selectAfWorkspaceByWorkspaceId(String workspaceId) {
        return afWorkspaceMapper.selectAfWorkspaceByWorkspaceId(workspaceId);
    }

    /**
     * 查询工作区列表
     *
     * @param afWorkspace 工作区
     * @return 工作区
     */
    @DataSource(DataSourceType.SLAVE)
    public List<AfWorkspace> selectAfWorkspaceList(AfWorkspace afWorkspace) {
        return afWorkspaceMapper.selectAfWorkspaceList(afWorkspace);
    }

    /**
     * 新增工作区
     *
     * @param afWorkspace 工作区
     * @return 结果
     */
    @DataSource(DataSourceType.SLAVE)
    public int insertAfWorkspace(AfWorkspace afWorkspace) {
        return afWorkspaceMapper.insertAfWorkspace(afWorkspace);
    }

    /**
     * 修改工作区
     *
     * @param afWorkspace 工作区
     * @return 结果
     */
    @DataSource(DataSourceType.SLAVE)
    public int updateAfWorkspace(AfWorkspace afWorkspace) {
        return afWorkspaceMapper.updateAfWorkspace(afWorkspace);
    }

    /**
     * 批量删除工作区
     *
     * @param workspaceIds 需要删除的工作区主键集合
     * @return 结果
     */
    @DataSource(DataSourceType.SLAVE)
    public int deleteAfWorkspaceByWorkspaceIds(String[] workspaceIds) {
        return afWorkspaceMapper.deleteAfWorkspaceByWorkspaceIds(workspaceIds);
    }

    /**
     * 删除工作区信息
     *
     * @param workspaceId 工作区主键
     * @return 结果
     */
    @DataSource(DataSourceType.SLAVE)
    public int deleteAfWorkspaceByWorkspaceId(String workspaceId) {
        return afWorkspaceMapper.deleteAfWorkspaceByWorkspaceId(workspaceId);
    }

    @DataSource(DataSourceType.SLAVE)
    public List<AfUserWorkspace> selectAfUserWorkspaceList(AfUserWorkspace afUserWorkspace) {
        return afWorkspaceMapper.selectAfUserWorkspaceList(afUserWorkspace);
    }

    @DataSource(DataSourceType.SLAVE)
    public List<NoteBook> getNotesByWorkspaceId(String workspaceId, String uid) {
        List<NoteBook> notes = afCollabMapper.getNotesByWorkspaceId(workspaceId, uid);
        return extractNotesContent(notes);
    }

    private List<NoteBook> extractNotesContent(List<NoteBook> notes) {
        return notes.stream().map(this::extractSingleNoteContent).collect(Collectors.toList());
    }

    private String decodeCollabBlob(String blob) {
        // 实现blob解码逻辑（需要Rust库或Java Yjs解析器）
        return "需要特殊解码的文档内容";
    }

    private String extractQuickNoteContent(String jsonData) {
        // 从JSON数据中提取文本内容
        try {
            // 简单实现：提取delta操作中的文本
            return jsonData.replaceAll(".*\"insert\":\"([^\"]+)\".*", "$1");
        } catch (Exception e) {
            return "无法解析的快速笔记内容";
        }
    }

    public NoteBook getNotesByNoteId(String oid) {
        NoteBook note = afCollabMapper.getNotesByNoteId(oid);
        note.setTitle(decodeCollabBlobTitle(note.getBlob()));
        note.setContent(decodeCollabBlobTitle(note.getBlob()));
        return note;
    }

    /**
     * 解析af_collab的blob内容提取标题（仅标题）
     */
    private String decodeCollabBlobTitle(byte[] blob) {
        try {

            if (blob == null || blob.length == 0) {
                return "空文档";
            }

            // 转换为字符串并清理不可读字符
            String blobText = new String(blob, StandardCharsets.UTF_8);
            String cleanText = blobText.replaceAll("[^\\x20-\\x7E\\n\\r\\t]", " ")
                    .replaceAll("\\s+", " ")
                    .trim();

            if (cleanText.isEmpty()) {
                return "二进制文档";
            }

            // 从清理后的文本提取标题
            return extractTitleFromText(cleanText);

        } catch (Exception e) {
            return "文档解析失败";
        }
    }

    /**
     * 解析快速笔记JSON数据提取标题（仅标题）
     */
    private String extractQuickNoteTitle(String jsonData) {
        try {
            if (jsonData == null || jsonData.trim().isEmpty()) {
                return "空快速笔记";
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonData);

            // 从JSON数组中提取第一个有效文本作为标题
            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    if (node.has("type") && "paragraph".equals(node.get("type").asText())) {
                        if (node.has("delta") && node.get("delta").has("insert")) {
                            String text = node.get("delta").get("insert").asText().trim();
                            if (!text.isEmpty()) {
                                return extractTitleFromText(text);
                            }
                        }
                    }
                }
            }

            return "快速笔记";

        } catch (Exception e) {
            return "JSON解析失败";
        }
    }

    /**
     * 从文本中智能提取标题
     */
    private String extractTitleFromText(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "无标题笔记";
        }

        String[] lines = text.split("\\r?\\n");

        for (String line : lines) {
            String trimmedLine = line.trim();

            if (!trimmedLine.isEmpty()) {
                // 优先提取Markdown标题
                if (trimmedLine.startsWith("#")) {
                    String title = trimmedLine.replaceFirst("^#+\\s*", "").trim();
                    return title.isEmpty() ? "Markdown标题" : title;
                }

                // 使用合适的文本行作为标题
                if (trimmedLine.length() >= 3) {
                    return trimmedLine.length() > 30 ? trimmedLine.substring(0, 30) + "..." : trimmedLine;
                }
            }
        }

        // 使用文本开头部分
        String firstChars = text.trim();
        return firstChars.length() > 20 ? firstChars.substring(0, 20) + "..." : firstChars;
    }

    /**
     * 只提取标题的单一方法
     */
    public NoteBook extractSingleNoteContent(NoteBook note) {
        if ("afCollab".equals(note.getNotebookType())) {
            note.setTitle(decodeCollabBlobTitle(note.getBlob()));
        } else if ("quickNote".equals(note.getNotebookType())) {
            note.setTitle(extractQuickNoteTitle(note.getData()));
        } else {
            note.setTitle("未知笔记类型");
        }
        return note;
    }
}
