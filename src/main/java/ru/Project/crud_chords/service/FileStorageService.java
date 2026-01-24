package ru.Project.crud_chords.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.StandardCopyOption;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    @Value("$app.upload.dir")
    private String uploadDir;

    public String storeFile(MultipartFile file) throws IOException { // Метод сохраняет файл на сервере
                                                                        // и возвращает относительный путь к файлу
        if(file.isEmpty()) {
            return null;
        }

        Path uploadPath = Paths.get(uploadDir); // Если директория не существует, то создаем её
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = file.getOriginalFilename(); // Генерирует оригинальное имя для файла
        String fileExtension = "";
        if(originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")); // Определяем расширение файла
        }

        String fileName = UUID.randomUUID() + fileExtension; // Даем файлу UUID с его расширением

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING); // Сохраняем файл

        return "/uploads/" + fileName; // Возвращает относительный путь
    }

    public void deleteFile(String fileUrl) throws IOException { // Удаляет файл при удалении
                                                                    // аккорда или изменении изображения
        if(fileUrl != null && fileUrl.startsWith("/uploads/")) {
            String fileName = fileUrl.substring("/uploads/".length());
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Files.deleteIfExists(filePath);
        }
    }
}
