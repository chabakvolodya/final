package com.sk.service;

import com.sk.model.Board;

import java.util.List;

public interface BoardService {
    List<Board> findAll();

    Board findById(int id);
}
