package com.sk.service.impl;

import com.sk.dao.MenuDao;
import com.sk.model.Menu;
import com.sk.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Resource(name = "menuDao")
    private MenuDao menuDao;

    @Override
    public Menu add(Menu menu) {
        Integer newId = menuDao.insert(menu);
        menu.setId(newId);
        return menu;
    }

    @Override
    public void remove(Menu menu) {
        if (menu != null) {
            menuDao.delete(menu);
        }
    }

    @Override
    public void update(Menu menu) {
        menuDao.update(menu);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findByName(String menuName) {
        return menuDao.findByName(menuName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Menu findById(int id) {
        return menuDao.findById(id);
    }
}
