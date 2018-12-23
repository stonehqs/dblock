package com.hqs.dblock.service;

import com.hqs.dblock.annotation.RetryOnFailure;
import com.hqs.dblock.entity.Browse;
import com.hqs.dblock.entity.Catalog;
import com.hqs.dblock.repository.BrowseRepository;
import com.hqs.dblock.repository.CatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class CatalogService {

    @Autowired
    CatalogRepository catalogRepository;

    @Autowired
    BrowseRepository browseRepository;

    @Transactional(rollbackFor = Exception.class)
    public void browseCatalog(Long catalogId, String user) {
        Optional<Catalog>  catalogOptional = catalogRepository.findById(catalogId);
//        Optional<Catalog>  catalogOptional = catalogRepository.findCatalogsForUpdate(catalogId);
//        Optional<Catalog>  catalogOptional = catalogRepository.findCatalogWithPessimisticLock(catalogId);
        if(!catalogOptional.isPresent()) {
            throw new RuntimeException("no catalog found");
        }
        Catalog catalog = catalogOptional.get();

        Browse browse = new Browse();
        browse.setCataId(catalog.getId());
        browse.setUser(user);
        browseRepository.save(browse);

        /*int result = catalogRepository.updateCatalogWithVersion(catalogId, catalog.getBrowseCount()+1,
                catalog.getVersion());

        if(result == 0) {
            throw new RuntimeException("server is busy, please retry");
        }
        log.info("version={},result={}", catalog.getVersion(), result);*/

        catalog.setBrowseCount(catalog.getBrowseCount() + 1);
        catalogRepository.save(catalog);

    }

    @RetryOnFailure
    public void browseCatalogWithRetry(Long catalogId, String user) {
        Optional<Catalog>  catalogOptional = catalogRepository.findById(catalogId);
        if(!catalogOptional.isPresent()) {
            throw new RuntimeException("no catalog found");
        }
        Catalog catalog = catalogOptional.get();
        catalog.setBrowseCount(catalog.getBrowseCount() + 1);
        catalogRepository.save(catalog);
    }

}
