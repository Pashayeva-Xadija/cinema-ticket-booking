package com.queue.repository;

import com.queue.model.ServiceType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    boolean existsByName(String name);
    boolean existsByCode(String code);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from ServiceType s where s.id = :id")
    ServiceType lockById(@Param("id") Long id);
}

