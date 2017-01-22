package com.sk.service;

import com.sk.model.Menu;

import java.util.List;

public interface MenuService {
    Menu add(Menu menu);

    void remove(Menu menu);

    List<Menu> findByName(String menuName);

    List<Menu> findAll();

    Menu findById(int id);

    void update(Menu menu);

}
