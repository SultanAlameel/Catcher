package com.ksu.catcher.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ksu.catcher.domain.Domain;

@Repository
public interface DomainRepository extends CrudRepository<Domain,Long> {

}
