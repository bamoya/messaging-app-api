package com.bamoya.whatsapp_clone.service;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class FileService {
  @Value("${application.file.uploads.media-output-path}")
  private String MEDIA_BASE_FOLDER;

  public String getPath(@NonNull MultipartFile file, @NonNull String senderId) {
    // we want the path to be like this /basPath/users/senderId/file.ext
    String fileSubPath = MEDIA_BASE_FOLDER + separator + "users" + separator + senderId;
    return uploadFile(file, fileSubPath);
  }

  private String uploadFile(@NonNull MultipartFile file, @NonNull String subPath) {
    File targetFolder = new File(subPath);
    if (!targetFolder.exists()) {
      // create folder
      boolean created = targetFolder.mkdirs();
      if (!created) {
        log.error("can't create folder {}", targetFolder);
        return null;
      }
    }
    // if target folder exists let's save file
    String targetFilePath =
        subPath + separator + currentTimeMillis() + getFileExtension(file.getName());
    Path filePath = Paths.get(targetFilePath);

    try {
      Files.write(filePath, file.getBytes());
      log.info("File saved to {}", targetFilePath);
      return targetFilePath;
    } catch (IOException e) {
      log.error("error while saving file {}", targetFilePath);
    }

    return null;
  }

  private String getFileExtension(@NonNull String fileName) {
    if (fileName.isBlank()) {
      return "";
    }
    int lastDotIdx = fileName.lastIndexOf('.');
    if (lastDotIdx == -1) {
      return "";
    }

    return fileName.substring(lastDotIdx).toLowerCase();
  }
}
