package com.palmcms.api.cms;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface CmsMapper {

    Optional<UserCmsInfoVO> selectUserCmssInfoByUserId(int userId);

    List<ManagerVO> selectManagersByChurchId(int churchId);

}
