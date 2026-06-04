package com.system.placementportal.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final Cloudinary cloudinary;

    public String uploadResume(MultipartFile file) {

        try {

            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "raw",
                            "folder", "placement-portal/resumes",
                            "use_filename", true,
                            "unique_filename", false,
                            "filename_override", file.getOriginalFilename()
                    )
            );

            System.out.println("Cloudinary Upload Response: " + result);

            return result.get("secure_url").toString();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Resume upload failed: " + e.getMessage());
        }
    }
}