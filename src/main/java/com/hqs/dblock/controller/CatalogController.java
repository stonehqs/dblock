package com.hqs.dblock.controller;

import com.hqs.dblock.service.CatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @PostMapping("/catalog")
    public String browseCatalog(Long catalogId, String user) {
        try {
            catalogService.browseCatalog(catalogId, user);
        } catch (Exception e) {
            log.error("{}", e);
        }
        return "success";
    }
    @PostMapping("/catalogRetry")
    public String browseCatalogWithRetry(Long catalogId, String user) {
        try {
            catalogService.browseCatalogWithRetry(catalogId, user);
        }catch (Exception e) {
            log.error("{}",e);
        }
        return "success";
    }
}
