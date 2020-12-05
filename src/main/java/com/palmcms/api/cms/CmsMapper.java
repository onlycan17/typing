package com.palmcms.api.cms;

import com.github.pagehelper.Page;
import com.palmcms.api.domain.DTO.CmsApplicationDTO;
import com.palmcms.api.domain.VO.SearchVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface CmsMapper {

    Optional<UserVO> selectUserCmssInfoByUserId(int userId);

    Optional<CmsUserVO> selectCmsInfoByPayerNo(String payerNo);

    List<ManagerVO> selectManagersByChurchId(int churchId);

    List<CmsApplicationDTO> selectApplicationsByUserId(int userId);

    int insertCmsApp(CmsApplicationDTO cmsApp);

    Page<CmsApplicationDTO> getAppList(Integer userId, SearchVO search, int pageNum, int pageSize);

    List<CmsApplicationDTO> getAppList(Integer userId);

    Optional<CmsApplicationDTO> getAppOne(Integer userId, Integer appId);
}
