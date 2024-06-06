package com.fpoly.sd18306.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {
	public String uploadFile(MultipartFile file) {
		if (!file.isEmpty()) {
			Path root = Paths.get("static/images");
			try {
				Files.createDirectories(root);

				String name = String.valueOf(new Date().getTime());
				String fileName = String.format("%s%s", name, (".jpg"));
				// String fileName = file.get(index).getOriginalFilename();
				Files.copy(file.getInputStream(), root.resolve(fileName));
				return fileName;
			} catch (IOException e) {
				System.out.println(e.getMessage());
				throw new RuntimeException("erro");
			}
		}
		return null;
	}
	
	public boolean removeFile(String name) {
		try {
			Path root = Paths.get(String.format("static/images/%s", name));
			return Files.deleteIfExists(root);
		}catch(Exception e) {
			return false;
		}
	}
}
