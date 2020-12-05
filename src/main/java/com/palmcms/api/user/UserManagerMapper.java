package com.palmcms.api.user;

import com.github.pagehelper.Page;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.domain.DTO.UserRoleDTO;
import com.palmcms.api.domain.DTO.UserTokenDTO;
import com.palmcms.api.domain.VO.SearchVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserManagerMapper {

    Page<UserDTO> getUserList(Integer userId, String keywordType, String keywordText);
}
