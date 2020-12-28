package com.palmcms.api.repository;

import com.palmcms.api.domain.Locale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


/**
 * @author by ray on 2017. 12. 31..
 */
public interface LocaleRepository extends JpaRepository<Locale, Integer> {

  List<Locale> findAllByOrderByOrderNoAsc();

  Optional<Locale> findFirstByLocaleCode(String localeString);


}
