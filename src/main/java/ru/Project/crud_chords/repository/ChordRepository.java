package ru.Project.crud_chords.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.Project.crud_chords.model.Chord;

import java.util.List;

@Repository // Уверяют, что волшебная палочка для взаимодействия БД и логики:
/* Spring сам ловит исключения из JDBC и превращает их в свои DataAccessException — меньше try-catch, меньше боли.
   Автоматически подключает твой репозиторий к контексту приложения — можно внедрять через @Autowired или конструктор.
    Работает с разными БД: MySQL, PostgreSQL, MongoDB, Cassandra и даже с in-memory решениями.
*/

public interface ChordRepository extends JpaRepository<Chord, Long> { // Cоздаем интерфейс, на основе
                                                                        // которого умный спринг сделает реализацию
    Page<Chord> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Chord> findByCategory(String category, Pageable pageable);

    @Query("SELECT c FROM Chord c WHERE " +
            "(:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:category IS NULL OR c.category = :category) AND " +
            "(:level IS NULL OR c.level = :level)")
    Page<Chord> searchChords(
            @Param("name") String name,
            @Param("category") String category,
            @Param("level") String level,
            Pageable pageable);

    @Query("SELECT DISTINCT c.category FROM Chord c WHERE c.category IS NOT NULL")
    List<String> findAllCategories();

    @Query("SELECT DISTINCT c.level FROM Chord c WHERE c.level IS NOT NULL")
    List<String> findAllLevels();
}
