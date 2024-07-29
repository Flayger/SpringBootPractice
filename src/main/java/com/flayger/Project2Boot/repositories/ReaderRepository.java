package com.flayger.Project2Boot.repositories;

import com.flayger.Project2Boot.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Integer> {

    Reader findByName(String name);
}
