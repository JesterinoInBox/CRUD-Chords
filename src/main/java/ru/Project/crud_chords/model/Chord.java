package ru.Project.crud_chords.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity // Помечаем как сущность для JPA
@Table(name = "chords") // Указываем имя таблицы
@Data // Аннотация Lombok, создаст геттеры, сеттеры, toString(), equals() и hashCode()
public class Chord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Создаем уникальный ID аккорда
    private long id;

    @Column(nullable = false) // Отключаем сохранение аккорда без имени
    private String name;

    @Column(columnDefinition = "TEXT") // Определяем тип столбца как TEXT,
    private String diagram;            // так как VARCHAR не может быть многострочным

    private String category;
    private String level;

    @Column(name = "image_url") // Определяем имя для ссылок на изображения
    private String imageUrl;

}
