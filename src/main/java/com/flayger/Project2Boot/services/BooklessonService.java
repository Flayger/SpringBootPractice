package com.flayger.Project2Boot.services;


import com.flayger.Project2Boot.model.Booklesson;
import com.flayger.Project2Boot.model.Reader;
import com.flayger.Project2Boot.repositories.BooklessonRepository;
import com.flayger.Project2Boot.repositories.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooklessonService {

    private final BooklessonRepository booklessonRepository;
    private final ReaderRepository readerRepository;

    @Autowired
    public BooklessonService(BooklessonRepository booklessonRepository, ReaderRepository readerRepository) {
        this.booklessonRepository = booklessonRepository;
        this.readerRepository = readerRepository;
    }

    public List<Booklesson> findByOwner(Reader reader){
        return booklessonRepository.findByOwner(reader);
    }

    public List<Booklesson> index() {
        return booklessonRepository.findAll();
    }

    @Transactional
    public void save(Booklesson booklesson) {
        booklessonRepository.save(booklesson);
    }

    public Booklesson show(int id) {
        return booklessonRepository.findById(id).orElse(null);
    }

    public Reader getBookOwner(int id) {
        Optional<Booklesson> book =  booklessonRepository.findById(id);
        Reader reader = null;
        if(book.isPresent()){
            reader = book.get().getOwner();
        }
        return reader;
    }

    @Transactional
    public void update(Booklesson booklesson, int id) {
        booklesson.setId(id);
        booklessonRepository.save(booklesson);
    }

    @Transactional
    public void delete(int id) {
        Optional<Booklesson> bookToDelete = booklessonRepository.findById(id);
        bookToDelete.ifPresent(booklesson -> booklesson.getOwner().getOwnedBooks().remove(booklesson));

        booklessonRepository.deleteById(id);
    }

    @Transactional
    public void releaseOwner(int id) {
        Optional<Booklesson> book = booklessonRepository.findById(id);
        if(book.isPresent()){
            System.out.println("set owner null");
            Reader reader = book.get().getOwner();
            book.get().setOwner(null);
            reader.getOwnedBooks().remove(book.get());
            booklessonRepository.save(book.get());

        }
    }

    @Transactional
    public void assignOwner(int id, Reader reader) {
        System.out.println("assign");
        Optional<Booklesson> book = booklessonRepository.findById(id);
        book.ifPresent(b -> {
            System.out.println(b.getName());
        });
        Optional<Reader> read = readerRepository.findById(reader.getId());
        read.ifPresent(r -> {
            System.out.println(r.getName());
        });


        if(book.isPresent() && read.isPresent()){
            book.get().setOwner(read.get());
            read.get().getOwnedBooks().add(book.get());
            System.out.println("setting owner book" + book.get().getId() + "  owner " + reader );
            booklessonRepository.save(book.get());
        }
    }
}
