package com.palmcms.api.code;

import com.palmcms.api.domain.VO.CommonCodeDTO;
import com.palmcms.api.messages.LocaleCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CodeService {

    @Autowired
    private CodeMapper codeMapper;


    public List<CommonCodeDTO> selectCodesByCategory(String category, LocaleCode localeCode) {
        return codeMapper.selectCodesByCategory(category, localeCode);
    }



}
