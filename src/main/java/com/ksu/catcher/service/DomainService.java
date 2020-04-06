package com.ksu.catcher.service;

import com.ksu.catcher.web.rest.vo.DomainVO;

public interface DomainService {

    void crawle(DomainVO domainVO) ;
    void scan(String crawlingId);
    void completeScan(String scanId) ;
}
