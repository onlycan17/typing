package com.palmcms.api.code;

import com.palmcms.api.domain.VO.CommonCodeDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CodeMapper {

    List<CommonCodeDTO> selectCodesByCategory(String category);

}
