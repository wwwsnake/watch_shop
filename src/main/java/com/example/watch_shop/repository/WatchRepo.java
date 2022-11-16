package com.example.watch_shop.repository;

import com.example.watch_shop.entity.WatchEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchRepo extends CrudRepository<WatchEntity, Integer> {

}
