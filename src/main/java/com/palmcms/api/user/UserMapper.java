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
public interface UserMapper {

    Optional<UserDTO> selectUserById(int id);

    Optional<UserDTO> selectUserByUserLoginId(String loginId);

    List<UserRoleDTO> selectUserRoles(int userId);

    List<String> selectUserRoleNames(int userId);

    Optional<UserTokenDTO> selectUserToken(String token);

    int insertUserToken(UserTokenDTO userTokenDTO);

    int updateUserTokenExpiredDate(UserTokenDTO userTokenDTO);

    Page<UserDTO> getUserListByManagerUserId(Integer managerUserId, String keywordType, String keywordText);

    Page<UserDTO> getUserList(String keywordType, String keywordText);

}
