package ru.Project.crud_chords.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DTOChordForm { // Добавили DTO для разделения сущности для БД и формы
    private long id;

    @NotBlank(message = "Название аккорда не должно быть пустым")
    @Size(min = 1, max = 10, message = "Название от 1 до 10 символов")
    private String name;

    private String diagram;

    @NotBlank(message = "Выберите категорию")
    private String category;

    @NotBlank(message = "Выберите уровень сложности")
    private String level;

    private MultipartFile imageFile;

    private String existingImageUrl;
}
