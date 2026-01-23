package ru.Project.crud_chords.service;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.Project.crud_chords.DTO.DTOChordForm;
import ru.Project.crud_chords.model.Chord;
import ru.Project.crud_chords.repository.ChordRepository;

import java.util.List;

@Service // Помечаем для спринга как сервис, потому что в этом классе будет реализована логика
public class ChordService { // Очень важно создавать отдельный класс для
            // каждой части приложения, принцип единственной ответственности и всё такое :D


    @Autowired // Именно это реализация DI, опять же,
            // создаем экземпляр и внедряем его как зависимость (точнее приказываем спрингу с этим разобраться)

    private ChordRepository chordRepository;


    private Chord convertDTOFormToChord(DTOChordForm form) { // Конвертер из формы в сущность
        Chord chord = new Chord();
        chord.setId(form.getId());
        chord.setName(form.getName());
        chord.setLevel(form.getLevel());
        chord.setDiagram(form.getDiagram());
        chord.setCategory(form.getCategory());
        chord.setImageUrl(form.getExistingImageURL());
        return chord;
    }

    private DTOChordForm convertChordToDTOForm(Chord chord) { // Конвертер сущности в форму
        DTOChordForm form = new DTOChordForm();
        form.setId(chord.getId());
        form.setName(chord.getName());
        form.setLevel(chord.getLevel());
        form.setDiagram(chord.getDiagram());
        form.setCategory(chord.getCategory());
        form.setExistingImageURL(chord.getImageUrl());
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
    public void saveChord(DTOChordForm form) {  // Реализация как сохранения, так и апдейта аккорда
        Chord chord = convertDTOFormToChord(form);
        chordRepository.save(chord);
    }

    @Transactional(readOnly = true)
    public void deleteChord(Long id) { // Метод для удаления аккорда из БД по ID
        chordRepository.deleteById(id);
    }

}
