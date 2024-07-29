package com.flayger.Project2Boot.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;


@Entity
@Table(name = "reader")
public class Reader {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "name should not be empty")
    @Size(min = 2, max=100, message = "wrong credentials, between 2 or 100 characters")
    private String name;


    @Column(name = "year")
    @NotNull(message = "should not be empty")
    @Min(value = 1, message = "birth date should not be negative")
    private int year;

    @OneToMany(mappedBy = "owner")
    private List<Booklesson> ownedBooks;


    public Reader() {
    }

    public Reader(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public List<Booklesson> getOwnedBooks() {
        return ownedBooks;
    }

    public void setOwnedBooks(List<Booklesson> ownedBooks) {
        this.ownedBooks = ownedBooks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
