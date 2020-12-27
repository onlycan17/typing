package com.palmcms.api.service;

import com.palmcms.api.domain.Locale;
import com.palmcms.api.messages.LocaleCode;
import com.palmcms.api.repository.LocaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by ray on 2017. 12. 31..
 */
@Service
public class LocaleService extends AbstractService<Locale, Integer> {

    @Autowired
    private LocaleRepository repository;

    @Override
    protected JpaRepository<Locale, Integer> getRepository() {
        return repository;
    }

    public List<Locale> fetchAll() {
        return repository.findAllByOrderByOrderNoAsc();
    }

    public Locale getLocaleByLocaleCode(LocaleCode localeCode) {
        var op =  repository.findFirstByLocaleCode(localeCode.getValue());
        if (op.isEmpty()) {
            return new Locale("kor", 1);
        }
        return op.get();
    }

}
