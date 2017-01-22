package com.sk.service.impl;

import com.sk.dao.BoardDao;
import com.sk.model.Board;
import com.sk.service.BoardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {

    @Resource(name = "boardDao")
    private BoardDao boardDao;

    @Override
    public List<Board> findAll() {
        return boardDao.findAll();
    }

    @Override
    public Board findById(int id) {
        return boardDao.findAll().stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .get();
    }
}
