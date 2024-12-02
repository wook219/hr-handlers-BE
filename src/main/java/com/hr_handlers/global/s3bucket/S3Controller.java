package com.hr_handlers.global.s3bucket;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    // 파일 업로드 핸들러
    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("upload") MultipartFile file) {
        try {
            String fileUrl = s3Service.uploadFile(file);
            return ResponseEntity.ok().body(Map.of(
                    "uploaded", true,
                    "url", fileUrl
            ));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of(
                    "uploaded", false,
                    "error", Map.of("message", "파일 업로드 실패: " + e.getMessage())
            ));
        }
    }

    // 파일 삭제 핸들러
    @DeleteMapping
    public ResponseEntity<?> deleteFile(@RequestParam("fileUrl") String fileUrl) {
        try {
            s3Service.deleteFile(fileUrl);
            return ResponseEntity.ok().body(Map.of("message", "파일 삭제 성공"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "message", "파일 삭제 실패",
                    "error", e.getMessage()
            ));
        }
    }
}
