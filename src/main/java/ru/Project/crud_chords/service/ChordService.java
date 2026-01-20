package ru.Project.crud_chords.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Project.crud_chords.model.Chord;
import ru.Project.crud_chords.repository.ChordRepository;

import java.util.List;

@Service // Помечаем для спринга как сервис, потому что в этом классе будет реализована логика
public class ChordService { // Очень важно создавать отдельный класс для
                                // каждой части приложения, принцип единственной ответственности и всё такое :D


    @Autowired // Именно это реализация DI, опять же,
                    // создаем экземпляр и внедряем его как зависимость (точнее приказываем спрингу с этим разобраться)

    private final ChordRepository chordRepository;
    public ChordService(ChordRepository chordRepository) {
        this.chordRepository = chordRepository;
    } // репозитори и есть зависимость для сервиса

    public List<Chord> getAllChords() {
        return chordRepository.findAll(); //  Возвращаем все аккорды из БД
    }

    public Chord getChordById(Long id) { // Обращаемся к репозиторию и ищем аккорд по ИД
        return chordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Аккорд не найден"));
                    // Если что-то пойдет не так, то выдаем ошибку через лямбда-функцию
    }

    public void saveChord(Chord chord) {  // Реализация как сохранения, так и апдейта аккорда
        chordRepository.save(chord);
    }

    public void deleteChord(Long id) { // Метод для удаления аккорда из БД по ID
        chordRepository.deleteById(id);
    }

}
