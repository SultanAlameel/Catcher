package com.ksu.catcher.service;

import com.ksu.catcher.domain.Domain;
import com.ksu.catcher.domain.Report;
import com.ksu.catcher.domain.User;
import com.ksu.catcher.repository.DomainRepository;
import com.ksu.catcher.repository.ReportRepository;
import com.ksu.catcher.repository.UserRepository;
import com.ksu.catcher.web.rest.vo.DomainVO;
import com.ksu.catcher.zap.CrawlingStatusThread;
import com.ksu.catcher.zap.ScanningStatusThread;
import com.ksu.catcher.zap.ZapClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DomainServiceImpl implements DomainService {

    private final ReportRepository reportRepository ;
    private final DomainRepository domainRepository ;
    private final UserRepository userRepository ;
    private final ZapClient zapClient ;

    public DomainServiceImpl(ReportRepository reportRepository,
                             DomainRepository domainRepository,
                             UserRepository userRepository,
                             ZapClient zapClient) {
        this.reportRepository = reportRepository;
        this.domainRepository = domainRepository;
        this.userRepository = userRepository;
        this.zapClient = zapClient;
    }


    @Override
    public void crawle(DomainVO domainVO) {
        User user = userRepository.findById(domainVO.getUserId()).get();
        Domain domain = domainRepository.findByUserAndName(user, domainVO.getDomainName());

        // create Report
        Report report = new Report();
        report.setDomain(domain);
        report.setDate(LocalDate.now());
        report.setExecuting(Boolean.FALSE);

        report = reportRepository.save(report);

        //start crawling
        String crawlingId = zapClient.startCrawling(domainVO.getDomainName());

        //update report
        report.setCrawlId(crawlingId);
        reportRepository.save(report);

        CrawlingStatusThread crawlingStatusThread = new CrawlingStatusThread(crawlingId, zapClient) ;
        crawlingStatusThread.run();
    }

    @Override
    public void scan(String crawlingId) {
        //get Report
        Report report = reportRepository.findByCrawlId(crawlingId);

        String scanId = zapClient.startScan(report.getDomain().getName());

        //update report
        report.setScanId(scanId);
        reportRepository.save(report);

        ScanningStatusThread scanningStatusThread = new ScanningStatusThread(scanId, zapClient, this);
        scanningStatusThread.run();
    }

    @Override
    public void completeScan(String scanId) {
        Report report = reportRepository.findByScanId(scanId);

        report.setExecuting(Boolean.TRUE);
        reportRepository.save(report);
    }
}
