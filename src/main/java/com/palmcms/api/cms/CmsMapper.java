package com.palmcms.api.cms;

import com.palmcms.api.domain.DTO.CmsApplicationDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface CmsMapper {

    Optional<UserCmsInfoVO> selectUserCmssInfoByUserId(int userId);

    List<ManagerVO> selectManagersByChurchId(int churchId);

    List<CmsApplicationDTO> selectApplicationsByUserId(int userId);

    int insertCmsApp(CmsApplicationDTO cmsApp);
}
