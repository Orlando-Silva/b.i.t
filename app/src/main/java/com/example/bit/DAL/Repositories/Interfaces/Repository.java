package com.example.bit.DAL.Repositories.Interfaces;

import java.util.List;

public interface Repository<T> {

    void closeRepo();

    boolean insert(T object);

    boolean update(T object, String... properties);

    boolean delete(T object);

    T queryById(T object);

    List<T> query(T object, String... properties);

    List<T> queryAll();
}

