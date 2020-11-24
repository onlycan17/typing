package com.palmcms.api.cms;

import com.palmcms.api.domain.DTO.CmsApplicationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CmsService {

    @Autowired
    private CmsMapper cmsMapper;

    public Optional<UserInformationVO> selectUserCmssInfoByUserId(int userId) {
        return cmsMapper.selectUserCmssInfoByUserId(userId);
    }

    public List<ManagerVO> selectManagersByChurchId(int churchId) {
        return cmsMapper.selectManagersByChurchId(churchId);
    }

    public List<CmsApplicationDTO> selectApplicationsByUserId(int userId) {
        return cmsMapper.selectApplicationsByUserId(userId);
    }


    public int insertCmsApp(CmsApplicationDTO cmsApp) {
        return cmsMapper.insertCmsApp(cmsApp);
    }

}
