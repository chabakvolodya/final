package com.sk.service.impl;

import com.sk.dao.DishCategoryDao;
import com.sk.model.DishCategory;
import com.sk.service.DishCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class DishCaregoryServiceImpl implements DishCategoryService {

    @Resource(name = "dishCategoryDao")
    private DishCategoryDao dishCategoryDao;

    @Override
    @Transactional(readOnly = true)
    public List<DishCategory> findAll() {
        return dishCategoryDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public DishCategory findById(int id) {
        return dishCategoryDao.findById(id);
    }
}
