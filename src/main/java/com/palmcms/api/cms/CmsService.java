package com.palmcms.api.cms;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.palmcms.api.domain.DTO.CmsApplicationDTO;
import com.palmcms.api.domain.DTO.CmsUserDTO;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CmsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CmsMapper cmsMapper;

    public UserCmsInfoVO getUserCmsInfoVO(int userId)
    {

        // 사용자정보 with 미납금
        Optional<UserDTO> oUserDTO = Optional.ofNullable(userMapper.selectUserById(userId));
        if ( oUserDTO.isEmpty() )
        {
            return null;
        }
        UserDTO userDTO = oUserDTO.get();

        return getUserCmsInfoVO(userDTO);

    }

    public UserCmsInfoVO getUserCmsInfoVO(UserDTO userDTO)
    {

        // 사용자정보 with 미납금
        Optional<CmsAmtInfoVO> oCmsAmtInfoVO = Optional.ofNullable(cmsMapper.getCmsAmtInfoByUserId(userDTO.getId()));

        // CMS정보
        Optional<CmsUserDTO> oCmsUserDTO = Optional.ofNullable(cmsMapper.getCmsUserByPayerNo(userDTO.getPayerNo()));

        // 담당 매니저 목록
        List<UserDTO> managers = cmsMapper.getManagersByChurchId(userDTO.getChurchId());


        UserCmsInfoVO userCmsInfoVO = new UserCmsInfoVO();

        userCmsInfoVO.setUser(userDTO);
        if (oCmsAmtInfoVO.isPresent())
        {
            userCmsInfoVO.setCmsAmtInfo(oCmsAmtInfoVO.get());
        }
        if (oCmsUserDTO.isPresent())
        {
            userCmsInfoVO.setCmsUser(oCmsUserDTO.get());
        }
        userCmsInfoVO.setManagers(managers);

        return userCmsInfoVO;
    }

    public Optional<CmsAmtInfoVO> getCmsAmtInfoByUserId(Integer userId) {
        return Optional.ofNullable(cmsMapper.getCmsAmtInfoByUserId(userId));
    }

    public Optional<CmsUserDTO> getCmsUserByPayerNo(String payerNo) {
        return Optional.ofNullable(cmsMapper.getCmsUserByPayerNo(payerNo));
    }

    public List<UserDTO> getManagersByChurchId(int churchId) {
        return cmsMapper.getManagersByChurchId(churchId);
    }

    public List<CmsApplicationDTO> getApplicationsByUserId(int userId) {
        return cmsMapper.getApplicationsByUserId(userId);
    }


    public int addCmsApp(CmsApplicationDTO cmsApp) {
        return cmsMapper.addCmsApp(cmsApp);
    }



    public List<CmsApplicationDTO> getAppListByUserId(Integer userId) {
        return cmsMapper.getAppListByUserId(userId);
    }


  public Optional<CmsApplicationDTO> getAppOneByUserId(Integer userId, Integer appId) {
    return Optional.ofNullable(cmsMapper.getAppOneByUserId(userId, appId));
  }


  public Page<CmsApplicationDTO> getAppListByManagerUserId(Integer managerUserId,
      String keywordType, String keywordText, int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    return cmsMapper.getAppListByManagerUserId(managerUserId, keywordType, keywordText);
  }

  public List<CmsApplicationDTO> getAppListByManagerUserId(Integer managerUserId,
      String keywordType, String keywordText) {
    return cmsMapper.getAppListByManagerUserId(managerUserId, keywordType, keywordText);
  }


  public Optional<CmsApplicationDTO> getAppOneByManagerUserId(Integer managerUserId,
      Integer appId) {
    return Optional.ofNullable(cmsMapper.getAppOneByManagerUserId(managerUserId, appId));
  }


  public Page<CmsApplicationDTO> getAppList(String keywordType, String keywordText, int pageNum,
      int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    return cmsMapper.getAppList(keywordType, keywordText);
  }

  public List<CmsApplicationDTO> getAppList(String keywordType, String keywordText) {
    return cmsMapper.getAppList(keywordType, keywordText);
  }


  public Optional<CmsApplicationDTO> getAppOne(Integer appId) {
    return Optional.ofNullable(cmsMapper.getAppOne(appId));
  }

  public int modAppStatus(Integer appId, String cmsAppStatus) {
    return cmsMapper.modAppStatus(appId, cmsAppStatus);
    }

}
