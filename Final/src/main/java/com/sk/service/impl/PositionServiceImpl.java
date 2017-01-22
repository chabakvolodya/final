package com.sk.service.impl;

import com.sk.dao.PositionDao;
import com.sk.model.Position;
import com.sk.service.PositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class PositionServiceImpl implements PositionService {

    @Resource(name = "positionDao")
    private PositionDao positionDao;

    @Override
    @Transactional(readOnly = true)
    public List<Position> findAll() {
        return positionDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Position findById(int id) {
        return positionDao.findById(id);
    }
}
