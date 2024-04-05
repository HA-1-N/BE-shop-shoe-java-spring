package com.example.shopshoejavaspring.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    private final Cloudinary cloudinary;

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path filePath = Paths.get(uploadDir + System.currentTimeMillis() + fileName);
            Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);
            return fileName;
        } catch (Exception e) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
    }

    public String uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }

    // delete file
    public void deleteFile(List <String> publicIds) {
        try {
            ApiResponse apiResponse = cloudinary.api().deleteResources(publicIds,
                    ObjectUtils.asMap("type", "upload", "resource_type", "image"));
            System.out.println(apiResponse);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> uploadImages(List<MultipartFile> files) throws IOException {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            imageUrls.add(imageUrl);
        }

        return imageUrls;
    }

    public void deleteImage(String image) {

    }

    public void deleteImages(List<Long> productQuantityImageIds) {
        List<String> publicIds = new ArrayList<>();
        for (Long productQuantityImageId : productQuantityImageIds) {
            publicIds.add(productQuantityImageId.toString());
        }
        deleteFile(publicIds);
    }
}
