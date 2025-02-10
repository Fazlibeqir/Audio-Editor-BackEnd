package ukim.finki.audioeditor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ukim.finki.audioeditor.service.AudioService;

import java.util.List;

@RestController
@RequestMapping("/audio")
public class AudioController {
    @Autowired
    private AudioService audioService;


    @PostMapping("/uploadAudio")
    public ResponseEntity<String> uploadAudioFile(@RequestParam MultipartFile file) {
        try {
            String fileDownloadUrl = audioService.saveTest(file);
            System.out.println("Download URL: " + fileDownloadUrl);
            return ResponseEntity.ok(fileDownloadUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }

    @PostMapping("/mergeTracks")
    public ResponseEntity<String> mergeAudioFiles(@RequestParam List<MultipartFile> files) {
        try {
            String mergedFileDownloadUrl = audioService.mergeTracks(files);
            System.out.println("Merged File Download URL: " + mergedFileDownloadUrl);
            return ResponseEntity.ok(mergedFileDownloadUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to merge files.");
        }
    }

}
