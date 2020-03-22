package com.ksu.catcher.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ksu.catcher.domain.Domain;
import com.ksu.catcher.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

}