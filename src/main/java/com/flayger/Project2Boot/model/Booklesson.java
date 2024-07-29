package com.flayger.Project2Boot.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "booklesson")
public class Booklesson {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "book name should not be empty")
    private String name;

    @Column(name = "author")
    @NotEmpty(message = "book author should not be empty")
    private String author;

    @Column(name = "year")
    @NotNull(message = "should not be empty")
    @Min(value = 1, message = "year book should not be negative")
    private int year;

    //@Pattern(regexp = "^[1-9]+$|^$",message = "can be empty or positive")
    @ManyToOne()
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private Reader owner;

    public Booklesson() {
    }

    public Booklesson(int id, String name, String author, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Reader getOwner() {
        return owner;
    }

    public void setOwner(Reader owner) {
        this.owner = owner;
    }
}
