package com.palmcms.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author by dotkebi on 2016. 11. 5..
 */
public abstract class AbstractService<T, K extends Serializable> {

  protected abstract JpaRepository<T, K> getRepository();

  public T save(T t) {
    return getRepository().saveAndFlush(t);
  }

  public void delete(K id) {
    getRepository().deleteById(id);
  }

  public List<T> list() {
    return getRepository().findAll();
  }

  public Optional<T> findFirst() {
    var list = getRepository().findAll();
    if (list.size() == 0) {
      return Optional.empty();
    }
    return Optional.of(list.get(0));
  }

  public Optional<T> fetchById(K id) {
    return getRepository().findById(id);
  }

  public Page<T> fetchAll(Pageable pageable) {
    return getRepository().findAll(pageable);
  }



}
