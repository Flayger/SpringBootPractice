package com.flayger.Project2Boot.services;


import com.flayger.Project2Boot.model.Reader;
import com.flayger.Project2Boot.repositories.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReaderService {

    private final ReaderRepository readerRepository;

    @Autowired
    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public List<Reader> index() {
        return readerRepository.findAll();
    }

    public Reader show(int id) {
        return readerRepository.findById(id).orElse(null);
    }


    @Transactional
    public void save(Reader reader) {
        readerRepository.save(reader);
    }

    @Transactional
    public void update(Reader reader, int id) {
        reader.setId(id);
        readerRepository.save(reader);
    }

    @Transactional
    public void delete(int id) {
        readerRepository.deleteById(id);
    }
}
