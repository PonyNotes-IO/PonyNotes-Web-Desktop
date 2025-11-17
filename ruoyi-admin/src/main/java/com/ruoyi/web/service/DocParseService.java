package com.ruoyi.web.service;

import com.ruoyi.web.model.ParseRequest;

public interface DocParseService {

    <T>T parsePdf(ParseRequest request);
}
