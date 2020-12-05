package com.palmcms.api.cms;

import com.github.pagehelper.Page;
import com.palmcms.api.domain.DTO.CmsApplicationDTO;
import com.palmcms.api.domain.VO.SearchVO;
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

    public Optional<UserVO> selectUserCmssInfoByUserId(int userId) {
        return cmsMapper.selectUserCmssInfoByUserId(userId);
    }

    public Optional<CmsUserVO> selectCmsInfoByPayerNo(String payerNo) {
        return cmsMapper.selectCmsInfoByPayerNo(payerNo);
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

    public Page<CmsApplicationDTO> getAppList(Integer userId, SearchVO search, int pageNum, int pageSize) {
        return cmsMapper.getAppList(userId, search, pageNum, pageSize);
    }

    public List<CmsApplicationDTO> getAppList(Integer userId) {
        return cmsMapper.getAppList(userId);
    }

    public Optional<CmsApplicationDTO> getAppOne(Integer userId, Integer appId) {
        return cmsMapper.getAppOne(userId, appId);
    }

}
