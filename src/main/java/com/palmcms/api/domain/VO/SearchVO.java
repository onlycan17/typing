package com.palmcms.api.domain.VO;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchVO {
    private String keywordType;
    private String keywordText;
}
