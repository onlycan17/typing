package com.palmcms.api.cms;

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

    public Optional<UserCmsInfoVO> selectUserCmssInfoByUserId(int userId) {
        return cmsMapper.selectUserCmssInfoByUserId(userId);
    }

    public List<ManagerVO> selectManagersByChurchId(int churchId) {
        return cmsMapper.selectManagersByChurchId(churchId);
    }


}
