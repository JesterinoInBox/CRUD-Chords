package ru.Project.crud_chords.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.Project.crud_chords.model.Chord;
import ru.Project.crud_chords.service.ChordService;

@Controller // Снова отметка для спринга, указываем что наш контроллер теперь и его тоже.
            // Стоит учесть что он возвращает HTML, а не JSON как @RestContoller
@RequestMapping("/chords") // Задаем базовый путь, все url-ссылки будут начинаться с /chords
public class ChordController {

    @Autowired
    private final ChordService chordService; // DI
    public ChordController(ChordService chordService) {
        this.chordService = chordService;
    }

    @GetMapping // Обработка HTTP GET-запроса
    public String ListChords(Model model) { // Полагаемся на спринг, он ответственный, model передаст сам
        model.addAttribute("chords", chordService.getAllChords()); // Добавляем аттрибут к модели
        return "chords/list"; // Возвращаем имя шаблона Thymeleaf (templates/chords/list.html)
    }

    @GetMapping("/new") // /chord/new GET запрос
    public String showCreateForm(Model model) {
        model.addAttribute("chord", new Chord()); // Добавляем аккорд
        return "chords/create"; // Выводим страницу создания нового аккорда
    }

    @PostMapping //Обработка HTTP POST-запроса
    public String saveChord(@ModelAttribute Chord chord) { // Связываем поля из формы создания аккорда с объектом chord
        chordService.saveChord(chord); // Передаем в сервис нужный аккорд для сохранения
        return "redirect:/chords"; // перенаправляет на список (Post-Redirect-Get паттерн)
    }

    @GetMapping("/edit/{id}") // GET-запрос для формы редактирования аккорда
    public String showEditForm(@PathVariable Long id, Model model) { // Извлекаем id из url
        model.addAttribute("chord", chordService.getChordById(id)); //
        return "chords/edit";
    }

    @PostMapping("/delete/{id}") // POST-запрос на удаление по ид
    public String deleteChord(@PathVariable Long id) { // Берем ид из url
        chordService.deleteChord(id); // Обращаемся к методу нашего сервиса
        return "redirect:/chords"; //Перенаправляем на просмотр аккорда
    }

    @GetMapping("/{id}") // GET-запрос на просмотр нужного аккорда
    public String viewChord(@PathVariable Long id, Model model) {
        model.addAttribute("chord", chordService.getChordById(id));
        return "chords/view";
    }
}
