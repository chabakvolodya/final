package com.sk.dao;

import java.io.Serializable;
import java.util.List;

public interface GeneralDao<T, PK extends Serializable> {

    PK insert(T newInstance);

    T findById(PK id);

    List<T> findAll();

    void update(T transientObject);

    void delete(T persistentObject);

}
