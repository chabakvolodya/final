package com.sk.dao;

import com.sk.model.Menu;

import java.util.List;

public interface MenuDao extends GeneralDao<Menu, Integer> {
    List<Menu> findByName(String name);
}
