package com.sk.dao;

import com.sk.model.Board;

import java.util.List;

public interface BoardDao {
    List<Board> findAll();
}
