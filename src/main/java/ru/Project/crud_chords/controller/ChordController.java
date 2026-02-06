package ru.Project.crud_chords.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.Project.crud_chords.DTO.DTOChordForm;
import ru.Project.crud_chords.model.Chord;
import ru.Project.crud_chords.service.ChordService;

import java.io.IOException;

@Controller // Снова отметка для спринга, указываем что наш контроллер теперь и его тоже.
            // Стоит учесть что он возвращает HTML, а не JSON как @RestContoller
@RequestMapping("/chords") // Задаем базовый путь, все url-ссылки будут начинаться с /chords
public class ChordController {

    @Autowired
    private final ChordService chordService; // DI
    public ChordController(ChordService chordService) {
        this.chordService = chordService;
    }

    @PostMapping("/delete/{id}") // POST-запрос на удаление по ид
    public String deleteChord(@PathVariable Long id) throws IOException { // Берем ид из url
        chordService.deleteChord(id); // Обращаемся к методу нашего сервиса
        return "redirect:/chords"; //Перенаправляем на просмотр аккорда
    }

    @GetMapping("/{id}") // GET-запрос на просмотр нужного аккорда
    public String viewChord(@PathVariable Long id, Model model) {
        model.addAttribute("chord", chordService.getChordFormById(id));
        return "chords/view";
    }

    @GetMapping
    public String listChords(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String level,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Page<Chord> chordsPage = chordService.searchChords(name, category, level, page, size);

        model.addAttribute("chordsPage", chordsPage);
        model.addAttribute("name", name);
        model.addAttribute("category", category);
        model.addAttribute("level", level);
        model.addAttribute("categories", chordService.getAllCategories());
        model.addAttribute("levels", chordService.getAllLevels());

        return "chords/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("DTOChordForm", new DTOChordForm());
        model.addAttribute("categories", chordService.getAllCategories());
        model.addAttribute("levels", chordService.getAllLevels());
        return "chords/form";
    }

    // Сохранение с валидацией
    @PostMapping
    public String saveChord(
            @Valid @ModelAttribute("DTOChordForm") DTOChordForm DTOChordForm,
            BindingResult bindingResult,
            Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", chordService.getAllCategories());
            model.addAttribute("levels", chordService.getAllLevels());
            return "chords/form";
        }

        chordService.saveChord(DTOChordForm);
        return "redirect:/chords";
    }

   
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        DTOChordForm DTOChordForm = chordService.getChordFormById(id);
        model.addAttribute("DTOChordForm", DTOChordForm);
        model.addAttribute("categories", chordService.getAllCategories());
        model.addAttribute("levels", chordService.getAllLevels());
        return "chords/form";
    }
}
