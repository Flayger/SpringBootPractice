package com.flayger.Project2Boot.controllers;


import com.flayger.Project2Boot.model.Reader;
import com.flayger.Project2Boot.services.BooklessonService;
import com.flayger.Project2Boot.services.ReaderService;
import com.flayger.Project2Boot.util.ReaderValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/lesson/readers")
public class ReaderController {

    private final ReaderValidator readerValidator;
    private final ReaderService readerService;
    private final BooklessonService booklessonService;

    @Autowired
    public ReaderController(ReaderValidator readerValidator, ReaderService readerService, BooklessonService booklessonService) {
        this.readerValidator = readerValidator;
        this.readerService = readerService;
        this.booklessonService = booklessonService;
    }

    //страница со списком читателей
    @GetMapping()
    public String index(Model model){
        model.addAttribute("readers", readerService.index());
        return "lesson/readers/index";
    }

    //страница конкретного читателя
    @GetMapping("/{id}")
    public String showReader(@PathVariable("id") int id, Model model){
        Reader reader = readerService.show(id);
        model.addAttribute("reader", reader);
        model.addAttribute("readerBooks", booklessonService.findByOwner(reader));
        //список всех книг у человека
        return "lesson/readers/show";
    }

    //страница с созданием читателя
    @GetMapping("/new")
    public String newReader(@ModelAttribute("reader") Reader reader){
        return "lesson/readers/new";
    }

    //метод сохранения в бд
    @PostMapping("/save")
    public String saveReader(@ModelAttribute("reader") @Valid Reader reader,
                             BindingResult bindingResult){

        readerValidator.validate(reader, bindingResult);

        if(bindingResult.hasErrors())
            return "lesson/readers/new";

        readerService.save(reader);
        return "redirect:/lesson/readers";
    }

    //страница редактирования читателя
    @GetMapping("/{id}/edit")
    public String editReader(Model model, @PathVariable("id") int id){
        model.addAttribute("reader", readerService.show(id));
        return "lesson/readers/edit";
    }

    //метод сохранения изменений читателя
    @PatchMapping("/{id}")
    public String updateReader(@ModelAttribute("reader") @Valid Reader reader,
                               BindingResult bindingResult,
                               @PathVariable("id") int id){
        readerValidator.validate(reader, bindingResult);
        if(bindingResult.hasErrors())
            return "lesson/readers/edit";

        readerService.update(reader, id);
        return "redirect:/lesson/readers";
    }

    //метод удаления читателя
    @DeleteMapping("/{id}/delete")
    public String deleteReader(@PathVariable("id") int id){
        readerService.delete(id);
        return "redirect:/lesson/readers";
    }


}
