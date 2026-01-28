package ru.Project.crud_chords.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.Project.crud_chords.DTO.DTOChordForm;
import ru.Project.crud_chords.model.Chord;
import ru.Project.crud_chords.repository.ChordRepository;

import java.io.IOException;
import java.util.List;

import static java.sql.Types.NULL;

@Service // Помечаем для спринга как сервис, потому что в этом классе будет реализована логика
public class ChordService { // Очень важно создавать отдельный класс для
            // каждой части приложения, принцип единственной ответственности и всё такое :D


    @Autowired // Именно это реализация DI, опять же,
            // создаем экземпляр и внедряем его как зависимость (точнее приказываем спрингу с этим разобраться)

    private ChordRepository chordRepository;

    @Autowired
    private FileStorageService fileStorageService;


    private Chord convertDTOFormToChord(DTOChordForm form) { // Конвертер из формы в сущность
        Chord chord = new Chord();
        chord.setId(form.getId());
        chord.setName(form.getName());
        chord.setLevel(form.getLevel());
        chord.setDiagram(form.getDiagram());
        chord.setCategory(form.getCategory());
        chord.setImageUrl(form.getExistingImageUrl());
        return chord;
    }

    private DTOChordForm convertChordToDTOForm(Chord chord) { // Конвертер сущности в форму
        DTOChordForm form = new DTOChordForm();
        form.setId(chord.getId());
        form.setName(chord.getName());
        form.setLevel(chord.getLevel());
        form.setDiagram(chord.getDiagram());
        form.setCategory(chord.getCategory());
        form.setExistingImageUrl(chord.getImageUrl());
        return form;
    }

    @Transactional(readOnly = true) //
    public List<Chord> getAllChords() {
        return chordRepository.findAll(); //  Возвращаем все аккорды из БД
    }

    @Transactional(readOnly = true)
    public DTOChordForm getChordFormById(Long id) { // Обращаемся к репозиторию и ищем аккорд по ИД
        Chord chord = chordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Аккорд не найден"));
                    // Если что-то пойдет не так, то выдаем ошибку через лямбда-функцию
        return convertChordToDTOForm(chord);
    }

    @Transactional(readOnly = true)
    public void saveChord(DTOChordForm form) throws IOException {  // Реализация как сохранения, так и апдейта аккорда
        Chord chord;

        if(form.getId() != NULL) {
            chord = chordRepository.findById(form.getId()).orElseThrow(() -> new RuntimeException("Аккорд не найден"));

            if (form.getImageFile() != null && !form.getImageFile().isEmpty()) {
                if (chord.getImageUrl() != null) {
                    fileStorageService.deleteFile(chord.getImageUrl());
                }
            }
        } else {
            chord = new Chord();
        }

        chord.setName(form.getName());
        chord.setDiagram(form.getDiagram());
        chord.setCategory(form.getCategory());
        chord.setLevel(form.getLevel());

        if(form.getImageFile() != null && !form.getImageFile().isEmpty()) {
            String imageUrl = fileStorageService.storeFile(form.getImageFile());
            chord.setImageUrl(imageUrl);
        } else if (form.getId() == NULL) {
            chord.setImageUrl(null);
        }

        chordRepository.save(chord);

    }

    @Transactional(readOnly = true)
    public void deleteChord(Long id) { // Метод для удаления аккорда из БД по ID
        chordRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Chord> searchChords(String name, String category, String level, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        try {
            if (name != null || category != null || level != null) {
                return chordRepository.searchChords(name, category, level, pageable);
            }

            return chordRepository.findAll(pageable);
        } catch (Exception e) {
            // Если произошла ошибка - возвращаем пустую страницу
            return Page.empty(pageable);
        }
    }

    @Transactional(readOnly = true)
    public List<String> getAllCategories() {
        return chordRepository.findAllCategories();
    }

    @Transactional(readOnly = true)
    public List<String> getAllLevels() {
        return chordRepository.findAllLevels();
    }

}
