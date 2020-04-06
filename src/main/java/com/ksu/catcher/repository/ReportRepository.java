package com.ksu.catcher.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ksu.catcher.domain.Report;


@Repository
public interface ReportRepository extends CrudRepository<Report,Long> {

    Report findByCrawlId(String crawlingId);

    Report findByScanId(String scanId);
}
