package com.ruoyi.web.controller.tool;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.model.ParseRequest;
import com.ruoyi.web.service.DocParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tool/docParse")
public class DocParseController {
    @Autowired
    private DocParseService docParseService;

    @RequestMapping(value = "parse",method = RequestMethod.POST)
    public AjaxResult parse(ParseRequest request) {
        return AjaxResult.success(() -> docParseService.parsePdf(request));
    }
}
