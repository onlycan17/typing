package com.palmcms.api.user.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.domain.DTO.UserRoleDTO;
import com.palmcms.api.domain.DTO.UserTokenDTO;
import com.palmcms.api.domain.enums.UserStatusType;
import com.palmcms.api.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<UserDTO> selectUserById(int id) {
        return Optional.ofNullable(userMapper.selectUserById(id));
    }

    public Optional<UserDTO> selectUserByUserLoginId(String userLoginId) {
        return Optional.ofNullable(userMapper.selectUserByUserLoginId(userLoginId));
    }

    public boolean matchesPassword(String rawPassword, String encrypted) {
        return passwordEncoder.matches(rawPassword, encrypted);
    }

    public List<UserRoleDTO> selectUserRoles(int userId) {
        return userMapper.selectUserRoles(userId);
    }

    public List<String> selectUserRoleNames(int userId) {
        return userMapper.selectUserRoleNames(userId);
    }

    public Optional<UserTokenDTO> selectUserToken(String token) {
        return Optional.ofNullable(userMapper.selectUserToken(token));
    }

    public int insertUserToken(UserTokenDTO userTokenDTO) {
        return userMapper.insertUserToken(userTokenDTO);
    }

    public int updateUserTokenExpiredDate(UserTokenDTO userTokenDTO) {
        return userMapper.updateUserTokenExpiredDate(userTokenDTO);
    }

    public int updateUserTokenExpiredDateExipred(String token) {
        return userMapper.updateUserTokenExpiredDateExipred(token);
    }


  public Page<UserDTO> getUserListByManagerUserId(Integer managerUserId, String keywordType,
      String keywordText, int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    return userMapper.getUserListByManagerUserId(managerUserId, keywordType, keywordText);
  }

  public Page<UserDTO> getUserList(String keywordType, String keywordText, int pageNum,
      int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    return userMapper.getUserList(keywordType, keywordText);
  }

  // 엑셀다운로드용
  public List<UserDTO> getUserList(String keywordType, String keywordText) {
    return userMapper.getUserList(keywordType, keywordText);
  }

  public int approveForManager(Integer managerUserId, List<String> userIdList)
  {
    UserStatusType userStatus = UserStatusType.CONFIRMED;
    return userMapper.updateUserStatusByManagerUserId(managerUserId, userIdList, userStatus);
  }

  public int approveForAdmin(Integer adminUserId, List<String> userIdList)
  {
    UserStatusType userStatus = UserStatusType.CONFIRMED;
    return userMapper.updateUserStatus(adminUserId, userIdList, userStatus);
  }


    public int youngForManager(Integer managerUserId, List<String> userIdList)
    {
        return userMapper.saveUserYoungByManagerUserId(managerUserId, userIdList);
    }

    public int youngForAdmin(Integer adminUserId, List<String> userIdList)
    {
        return userMapper.saveUserYoung(adminUserId, userIdList);
    }
}
