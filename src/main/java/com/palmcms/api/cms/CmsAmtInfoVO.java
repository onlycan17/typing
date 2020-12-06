package com.palmcms.api.cms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsAmtInfoVO
{
    Integer userId;

    Integer totalCmsPayAmt;

    Integer totalWithdrawAmt;

    Integer unpaidAmt;

    Integer depositAmt;

}
