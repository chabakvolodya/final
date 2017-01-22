package com.sk.dao;

import com.sk.model.Position;

import java.util.List;

public interface PositionDao {
    List<Position> findAll();

    Position findById(int id);
}
