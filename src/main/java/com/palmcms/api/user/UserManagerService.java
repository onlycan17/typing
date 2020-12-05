package com.palmcms.api.user;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.domain.DTO.UserRoleDTO;
import com.palmcms.api.domain.DTO.UserTokenDTO;
import com.palmcms.api.domain.VO.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserManagerService {

    @Autowired
    private UserManagerMapper userManagerMapper;


    public Page<UserDTO> getUserList(Integer userId, String keywordType, String keywordText, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return userManagerMapper.getUserList(userId, keywordType, keywordText);
    }

}
