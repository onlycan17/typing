package com.palmcms.api.cms;

import com.github.pagehelper.Page;
import com.palmcms.api.domain.DTO.CmsApplicationDTO;
import com.palmcms.api.domain.DTO.CmsUserDTO;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.domain.VO.SearchVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface CmsMapper {

    CmsAmtInfoVO getCmsAmtInfoByUserId(Integer userId);



    CmsUserDTO getCmsUserByPayerNo(String payerNo);

    List<UserDTO> getManagersByChurchId(int churchId);

    List<CmsApplicationDTO> getApplicationsByUserId(int userId);

    int addCmsApp(CmsApplicationDTO cmsApp);

    List<CmsApplicationDTO> getAppListByUserId(Integer userId);

    CmsApplicationDTO getAppOneByUserId(Integer userId, Integer appId);

    Page<CmsApplicationDTO> getAppListByManagerUserId(Integer managerUserId, String keywordType, String keywordText);

    CmsApplicationDTO getAppOneByManagerUserId(Integer managerUserId, Integer appId);

    Page<CmsApplicationDTO> getAppList(String keywordType, String keywordText);

    CmsApplicationDTO getAppOne(Integer appId);

    int modAppStatus(Integer appId, String cmsAppStatus);
}
