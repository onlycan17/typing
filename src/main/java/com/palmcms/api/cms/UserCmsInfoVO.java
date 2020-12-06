package com.palmcms.api.cms;

import com.palmcms.api.domain.DTO.CmsApplicationDTO;
import com.palmcms.api.domain.DTO.CmsUserDTO;
import com.palmcms.api.domain.DTO.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserCmsInfoVO {

    UserDTO user ;

    CmsAmtInfoVO cmsAmtInfo;

    CmsUserDTO cmsUser ;

    List<UserDTO> managers;

}
