package com.flayger.Project2Boot.controllers;


import com.flayger.Project2Boot.model.Booklesson;
import com.flayger.Project2Boot.model.Reader;
import com.flayger.Project2Boot.services.BooklessonService;
import com.flayger.Project2Boot.services.ReaderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/lesson/books")
public class BooklessonController {

    private final BooklessonService booklessonService;
    private final ReaderService readerService;

    @Autowired
    public BooklessonController(BooklessonService booklessonService, ReaderService readerService) {
        this.booklessonService = booklessonService;
        this.readerService = readerService;
    }

    //страница всех книг

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", booklessonService.index());
        return "lesson/books/index";
    }

    //создать
    @GetMapping("/new")
    public String createBook(@ModelAttribute("book") Booklesson booklesson, Model model){
        return "lesson/books/new";
    }

    //сохранить
    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") @Valid Booklesson booklesson,
                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println("error");
            return "lesson/books/new";
        }
        System.out.println("save proshel");
        booklessonService.save(booklesson);
        return "redirect:/lesson/books";
    }

    //изменить
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("reader") Reader reader){
        model.addAttribute("book", booklessonService.show(id));

        Optional<Reader> owner = Optional.ofNullable(booklessonService.getBookOwner(id));

        if(owner.isPresent())
            model.addAttribute("reader", owner.get());
        else
            model.addAttribute("readers", readerService.index());
        return "lesson/books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", booklessonService.show(id));
        //model.addAttribute("readers", readerDAO.index());
        return "/lesson/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Booklesson booklesson,
                           BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "/lesson/books/edit";

        booklessonService.update(booklesson, id);
        return "redirect:/lesson/books";
    }
    //удалить

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id){
        booklessonService.delete(id);
        return "redirect:/lesson/books";
    }

    //освободить книгу от владельца
    @PatchMapping("/{id}/release")
    public String releaseOwner(@PathVariable("id") int id){
        booklessonService.releaseOwner(id);
        return  "redirect:/lesson/books/" + id;
    }

    //назначить владельца
    @PatchMapping("/{id}/assign")
    public String assignOwner(@ModelAttribute("reader") Reader reader,
                               @PathVariable("id") int id){
        System.out.println("reader id" + reader.getId());
        booklessonService.assignOwner(id, reader);
        return "redirect:/lesson/books/" + id;
    }
}
