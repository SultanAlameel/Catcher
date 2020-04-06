package com.ksu.catcher.repository;

import com.ksu.catcher.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ksu.catcher.domain.Domain;

@Repository
public interface DomainRepository extends CrudRepository<Domain,Long> {

    public Domain findByUserAndName(User user, String name);
}
