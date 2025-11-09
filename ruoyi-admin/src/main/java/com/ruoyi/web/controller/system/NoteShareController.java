package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.NoteShare;
import com.ruoyi.system.service.NoteShareService;
import com.ruoyi.web.model.NoteShareVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.rjeschke.txtmark.Processor;
@RestController
@RequestMapping("/api/noteshare")
public class NoteShareController {
    @Autowired
    private NoteShareService noteShareService;

    @GetMapping("/getnotecontent/{workspaceId}/{viewId}")
    public String getNoteContent(@PathVariable String workspaceId, @PathVariable String viewId) {
        try {
            if (viewId ==null  || StringUtils.isEmpty(viewId) || StringUtils.isEmpty(workspaceId)){
                viewId = "1fce03264c07b";
            }
            // 1. 调用第三方接口（或数据库）获取 Markdown 内容
            String content = callThirdPartyApi(workspaceId,viewId);
            return StringUtils.isEmpty(content) ? getDefaultHtml() : content;
        } catch (Exception e) {
            // 2. 失败时返回默认 HTML
            return getDefaultHtml();
        }
    }

    @PostMapping("/create")
    @Anonymous
    public AjaxResult createNoteShare(@RequestBody NoteShareVo noteShareVo) {
        try {
            NoteShare noteShare  = new NoteShare();
            int result  = noteShareService.createNoteShare(noteShare);
            return AjaxResult.success("创建共享链接成功");
        } catch (IllegalArgumentException e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    @PostMapping("/getByShareId")
    @Anonymous
    public AjaxResult getByShareId(@RequestParam String shareId ) {
        try {
            NoteShare noteShare  = noteShareService.getNoteByShareId(shareId);
            return AjaxResult.success(noteShare);
        } catch (IllegalArgumentException e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    @PostMapping("/deleteByShareId")
    @Anonymous
    public AjaxResult deleteByShareId(@RequestParam String shareId ) {
        try {
            int noteShare  = noteShareService.deleteNoteShare(shareId);
            return AjaxResult.success("删除数据！");
        } catch (IllegalArgumentException e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    private String callThirdPartyApi(String workspaceId,String viewId) {
        // 实际场景：通过 HTTP 调用、数据库查询等方式获取 Markdown 内容
        if ("1fce03264c07b".equals(viewId)) {
            return  getDefaultHtml();
        }else{
            String error  = "<h1>笔记获取失败</h1><p>请检查链接是否有效，或稍后重试</p>";
            // 访问接口、或者数据库
            try{
                //  访问数据库
                // 2. Markdown 转 HTML
                String markdownContent = "";
                return Processor.process(markdownContent);
            }
            catch (Exception e){
                return error;
            }
        }
    }

    private String getDefaultHtml() {

        StringBuilder defaulthtml = new StringBuilder("<!DOCTYPE html>\n" +
                "<html lang=\"zh-CN\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <title>小马笔记 - 核心功能介绍</title>\n" +
                "  <style>\n" +
                "    body {\n" +
                "      font-family: \"Segoe UI\", \"Microsoft YaHei\", sans-serif;\n" +
                "      line-height: 1.6;\n" +
                "      color: #333;\n" +
                "      margin: 0 auto;\n" +
                "      padding: 20px;\n" +
                "      background: #f9fafb;\n" +
                "    }\n" +
                "    .title {\n" +
                "      color: #4F46E5;\n" +
                "      text-align: center;\n" +
                "      margin: 30px 0;\n" +
                "      font-size: 2rem;\n" +
                "      border-bottom: 2px solid #818CF8;\n" +
                "      padding-bottom: 10px;\n" +
                "    }\n" +
                "    .subtitle {\n" +
                "      color: #1E293B;\n" +
                "      font-size: 1.3rem;\n" +
                "      margin: 25px 0 15px;\n" +
                "      padding-left: 8px;\n" +
                "      border-left: 3px solid #EC4899;\n" +
                "    }\n" +
                "    ul {\n" +
                "      list-style: none;\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "    li {\n" +
                "      margin: 10px 0;\n" +
                "      padding-left: 24px;\n" +
                "      position: relative;\n" +
                "    }\n" +
                "    li:before {\n" +
                "      content: \"•\";\n" +
                "      color: #4F46E5;\n" +
                "      font-weight: bold;\n" +
                "      position: absolute;\n" +
                "      left: 8px;\n" +
                "    }\n" +
                "    .highlight {\n" +
                "      color: #4F46E5;\n" +
                "      font-weight: 600;\n" +
                "    }\n" +
                "    .footer {\n" +
                "      margin-top: 40px;\n" +
                "      text-align: center;\n" +
                "      color: #64748B;\n" +
                "      font-size: 0.9rem;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <h1 class=\"title\">小马笔记——让笔记更简单，让知识更智慧</h1>\n" +
                "  \n" +
                "  <p>一款兼顾易用性与强大功能的笔记工具，以「<span class=\"highlight\">隐私安全</span>」与「<span class=\"highlight\">功能整合</span>」为核心，支持开源、本地优先、云同步，融合多模态记录与AI能力，助力个人与团队高效构建知识系统。</p>\n" +
                "  \n" +
                "  <h2 class=\"subtitle\">核心功能亮点</h2>\n" +
                "  <ul>\n" +
                "    <li><span class=\"highlight\">模块化笔记</span>：像搭积木般自由组合文字、表格、代码等内容，灵活创作</li>\n" +
                "    <li><span class=\"highlight\">多维表</span>：表格、看板、日历等多视图管理数据，适配多样场景</li>\n" +
                "    <li><span class=\"highlight\">任务系统</span>：内置待办、优先级、截止日期，笔记即项目管理</li>\n" +
                "    <li><span class=\"highlight\">模板复用</span>：丰富场景模板+自定义保存，快速复用提效</li>\n" +
                "  </ul>\n" +
                "  \n" +
                "  <h2 class=\"subtitle\">特色手写体验</h2>\n" +
                "  <ul>\n" +
                "    <li>流畅书写+笔迹回放，还原真实纸笔触感</li>\n" +
                "    <li>横线/方格/康奈尔等纸张模板，适配学习与工作</li>\n" +
                "    <li>多笔刷+智能识别（手写转文本、手势擦除），兼顾自由与效率</li>\n" +
                "  </ul>\n" +
                "  \n" +
                "  <h2 class=\"subtitle\">思维发散工具</h2>\n" +
                "  <ul>\n" +
                "    <li>白板页面：手绘图形自动矫正，轻松创作流程图、思维导图</li>\n" +
                "    <li>支持手写笔压感调节+图片导入标注，多人实时协同编辑</li>\n" +
                "  </ul>\n" +
                "  \n" +
                "  <h2 class=\"subtitle\">AI 智能助力</h2>\n" +
                "  <ul>\n" +
                "    <li>自由选择大模型，提供总结、问答、写作辅助</li>\n" +
                "    <li>智能搜索：突破关键词限制，基于自然语言理解推荐上下文</li>\n" +
                "  </ul>\n" +
                "  \n" +
                "  <h2 class=\"subtitle\">隐私与跨平台</h2>\n" +
                "  <ul>\n" +
                "    <li><span class=\"highlight\">隐私保护</span>：本地优先存储+加密传输，核心模块开源，支持Markdown/PDF导出</li>\n" +
                "    <li><span class=\"highlight\">全平台同步</span>：覆盖iPhone、iPad、Windows、MacOS、Android及浏览器</li>\n" +
                "    <li><span class=\"highlight\">API联动</span>：开放接口支持笔记与外部系统无缝对接</li>\n" +
                "  </ul>\n" +
                "  \n" +
                "  <div class=\"footer\">\n" +
                "    小马笔记 —— 适配多场景知识管理需求，兼顾手写自由与AI效率\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>");
        String result = defaulthtml.toString();
        return result;
    }

}
