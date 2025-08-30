package com.bamoya.whatsapp_clone.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileUtils {

  public byte[] readFileFromPath(String filePath) {
    if (filePath == null || filePath.isBlank()) {
      return new byte[0];
    }
    try {
      Path path = new File(filePath).toPath();
      return Files.readAllBytes(path);
    } catch (IOException e) {
      log.warn("Nou file found in the path {}", filePath);
    }

    return new byte[0];
  }
}
