package com.palmcms.api.csboard.mapper;

import com.github.pagehelper.Page;
import com.palmcms.api.csboard.domain.CsBoard;
import com.palmcms.api.csboard.domain.CsBoardReply;
import com.palmcms.api.messages.LocaleCode;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public interface CsBoardMapper {

    Page<CsBoard> fetchAllByBoardGroupId(LocaleCode localeCode, Integer boardGroupId);

    Optional<CsBoard> fetchFirstByBoardGroupIdAndBoardId(LocaleCode localeCode, Integer boardGroupId, Integer boardId);

    Page<CsBoard> fetchAllByBoardGroupIdAndWriteUserId(LocaleCode localeCode, Integer boardGroupId, Integer writerUserId);

    Optional<CsBoard> fetchFirstByBoardGroupIdAndBoardIdAndWriteUserId(LocaleCode localeCode, Integer boardGroupId, Integer boardId, Integer writerUserId);

    Page<CsBoardReply> fetchAllByBoardId(Integer boardId);

}
