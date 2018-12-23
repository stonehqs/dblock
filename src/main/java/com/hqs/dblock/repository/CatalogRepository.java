package com.hqs.dblock.repository;

import com.hqs.dblock.entity.Catalog;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface CatalogRepository extends CrudRepository<Catalog, Long> {

    @Query(value = "select * from Catalog a where a.id = :id for update", nativeQuery = true)
    Optional<Catalog> findCatalogsForUpdate(@Param("id") Long id);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE) //代表行级锁
    @Query("select a from Catalog a where a.id = :id")
    Optional<Catalog> findCatalogWithPessimisticLock(@Param("id") Long id);

    @Modifying(clearAutomatically = true) //修改时需要带上
    @Query(value = "update Catalog set browse_count = :browseCount, version = version + 1 where id = :id " +
            "and version = :version", nativeQuery = true)
    int updateCatalogWithVersion(@Param("id") Long id, @Param("browseCount") Long browseCount, @Param("version") Long version);

}
