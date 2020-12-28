package com.palmcms.api.csboard.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.palmcms.api.csboard.CsBoardRegDTO;
import com.palmcms.api.csboard.CsBoardVO;
import com.palmcms.api.csboard.domain.CsBoardLocale;
import com.palmcms.api.csboard.mapper.CsBoardMapper;
import com.palmcms.api.csboard.domain.CsBoard;
import com.palmcms.api.csboard.repository.CsBoardRepository;
import com.palmcms.api.messages.LocaleCode;
import com.palmcms.api.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class CsBoardReplyService extends AbstractService<CsBoard, Integer> {

    @Autowired
    private CsBoardRepository repository;

    @Autowired
    private CsBoardMapper csBoardMapper;

    @Override
    protected JpaRepository<CsBoard, Integer> getRepository() {
        return repository;
    }

    public Page<CsBoard> fetchAllByBoardGroupId(int pageNum, int pageSize, LocaleCode localeCode, Integer boardGroupId) {
        PageHelper.startPage(pageNum, pageSize);
        return csBoardMapper.fetchAllByBoardGroupId(localeCode, boardGroupId);
    }

    public Optional<CsBoard> fetchFirstByBoardGroupIdAndBoardId(LocaleCode localeCode, Integer boardGroupId, Integer boardId) {
        return csBoardMapper.fetchFirstByBoardGroupIdAndBoardId(localeCode, boardGroupId, boardId);
    }

    public Page<CsBoard> fetchAllByBoardGroupIdAndWriteUserId(int pageNum, int pageSize, LocaleCode localeCode, Integer boardGroupId, Integer writerUserId) {
        PageHelper.startPage(pageNum, pageSize);
        return csBoardMapper.fetchAllByBoardGroupIdAndWriteUserId(localeCode, boardGroupId, writerUserId);
    }

    public Optional<CsBoard> fetchFirstByBoardGroupIdAndBoardIdAndWriteUserId(LocaleCode localeCode, Integer boardGroupId, Integer boardId, Integer writerUserId) {
        return csBoardMapper.fetchFirstByBoardGroupIdAndBoardIdAndWriteUserId(localeCode, boardGroupId, boardId, writerUserId);
    }
//
//    public void modify(CsBoardRegDTO dto)
//    {
//        fetchById(dto.getId()).ifPresent(item -> {
//            update(item, dto);
//        });
//    }
//
//    public void add(CsBoardRegDTO dto)
//    {
//        CsBoard source = new CsBoard();
//        source.setBoardGroupId(dto.getBoardGroupId());
//        source.setCsCategoryType(dto.getCsCategoryType());
//        source.setWriterUserId(dto.getWriterUserId());
//
//        source.setCsBoardLocales(makeLocale(source, dto));
//
//        save(source);
//
//        dto.setId(source.getId());
//    }
//
//
//    private void update(CsBoard source, CsBoardRegDTO dto) {
//
//        source.setCsBoardLocales(makeLocale(source, dto));
//
//        source.setModificationDate(new Timestamp(System.currentTimeMillis()));
//
//        save(source);
//    }
//
//    private Set<CsBoardLocale> makeLocale(CsBoard source, CsBoardRegDTO dto) {
//        Set<CsBoardLocale> itemLocaleList = new HashSet<>();
//
//        if (!StringUtils.isEmpty(dto.getKorTitle())) {
//            var item = CsBoardLocale.builder()
//                    .title(dto.getKorTitle())
//                    .content(dto.getKorContent())
//                    .build();
//            item.setCsBoard(source);
//            itemLocaleList.add(item);
//        }
//        if (!StringUtils.isEmpty(dto.getEngTitle())) {
//            var item = CsBoardLocale.builder()
//                    .title(dto.getEngTitle())
//                    .content(dto.getEngContent())
//                    .build();
//            item.setCsBoard(source);
//            itemLocaleList.add(item);
//        }
//        if (!StringUtils.isEmpty(dto.getJpnTitle())) {
//            var item = CsBoardLocale.builder()
//                    .title(dto.getJpnTitle())
//                    .content(dto.getJpnContent())
//                    .build();
//            item.setCsBoard(source);
//            itemLocaleList.add(item);
//        }
//        if (!StringUtils.isEmpty(dto.getZhoTitle())) {
//            var item = CsBoardLocale.builder()
//                    .title(dto.getZhoTitle())
//                    .content(dto.getZhoContent())
//                    .build();
//            item.setCsBoard(source);
//            itemLocaleList.add(item);
//        }
//
//        return itemLocaleList;
//    }
}
