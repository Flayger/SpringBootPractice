package com.flayger.Project2Boot.repositories;


import com.flayger.Project2Boot.model.Booklesson;
import com.flayger.Project2Boot.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooklessonRepository extends JpaRepository<Booklesson, Integer> {

    //вернуть список книг, у которых владелец reader
    List<Booklesson> findByOwner(Reader reader);

    //вернуть владельца вниги

}
