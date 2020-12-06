package com.palmcms.api.cms;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.palmcms.api.domain.DTO.CmsApplicationDTO;
import com.palmcms.api.domain.DTO.CmsUserDTO;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.domain.VO.ResultVO;
import com.palmcms.api.domain.VO.SearchVO;
import com.palmcms.api.security.PalmToken;
import com.palmcms.api.security.SecurityUtils;
import com.palmcms.api.user.UserMapper;
import com.palmcms.api.user.UserService;
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
        Optional<UserDTO> oUserDTO = userMapper.selectUserById(userId);
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
        Optional<CmsAmtInfoVO> oCmsAmtInfoVO = cmsMapper.getCmsAmtInfoByUserId(userDTO.getId());

        // CMS정보
        Optional<CmsUserDTO> oCmsUserDTO = cmsMapper.getCmsUserByPayerNo(userDTO.getPayerNo());

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
        return cmsMapper.getCmsAmtInfoByUserId(userId);
    }

    public Optional<CmsUserDTO> getCmsUserByPayerNo(String payerNo) {
        return cmsMapper.getCmsUserByPayerNo(payerNo);
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
        return cmsMapper.getAppOneByUserId(userId, appId);
    }



    public List<CmsApplicationDTO> getAppListByManagerUserId(Integer managerUserId, String keywordType, String keywordText, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return cmsMapper.getAppListByManagerUserId(managerUserId, keywordType, keywordText);
    }


    public Optional<CmsApplicationDTO> getAppOneByManagerUserId(Integer managerUserId, Integer appId) {
        return cmsMapper.getAppOneByManagerUserId(managerUserId, appId);
    }



    public Page<CmsApplicationDTO> getAppList(String keywordType, String keywordText, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return cmsMapper.getAppList(keywordType, keywordText);
    }




    public Optional<CmsApplicationDTO> getAppOne(Integer appId) {
        return cmsMapper.getAppOne(appId);
    }

}
