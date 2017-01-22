package com.sk.service;

import com.sk.model.Position;

import java.util.List;

public interface PositionService {

    List<Position> findAll();

    Position findById(int id);

}
