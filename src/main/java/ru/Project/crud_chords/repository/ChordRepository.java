package ru.Project.crud_chords.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Project.crud_chords.model.Chord;

@Repository // Уверяют, что волшебная палочка для взаимодействия БД и логики:
                /*
                    Spring сам ловит исключения из JDBC и превращает их в свои DataAccessException — меньше try-catch, меньше боли.
                    Автоматически подключает твой репозиторий к контексту приложения — можно внедрять через @Autowired или конструктор.
                    Работает с разными БД: MySQL, PostgreSQL, MongoDB, Cassandra и даже с in-memory решениями.

                */

public interface ChordRepository extends JpaRepository<Chord, Long> { // По идее создаем интерфейс, на основе которого умный спринг сделает реализацию


}
