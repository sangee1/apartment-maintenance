package com.shangrila.aptmaintenance.repository;

import com.shangrila.aptmaintenance.entity.Flat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlatRepository extends JpaRepository<Flat,Long> {
    boolean existsByName(String name);
}
