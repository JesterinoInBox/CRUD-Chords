package ru.Project.crud_chords.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Entity // Помечаем как сущность для JPA
@Table(name = "chords") // Указываем имя таблицы
@Data // Аннотация Lombok, создаст геттеры, сеттеры, toString(), equals() и hashCode()
public class Chord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Создаем уникальный ID аккорда
    private long id;


    @NotBlank(message = "Название обязательно")
    @Size(min = 1, max = 10, message = "Название от 1 до 10 символов")
    @Column(nullable = false) // Отключаем сохранение аккорда без имени
    private String name;

    @Column(columnDefinition = "TEXT") // Определяем тип столбца как TEXT,
    private String diagram;            // так как VARCHAR не может быть многострочным

    @NotBlank(message = "Необходимо выбрать категорию")
    private String category;

    @NotBlank(message = "Выберите уровень сложности аккорда")
    private String level;

    @Column(name = "image_url") // Определяем имя для ссылок на изображения
    private String imageUrl;

}
