package ukim.finki.audioeditor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ukim.finki.audioeditor.models.AudioMetadata;
import ukim.finki.audioeditor.service.AudioService;

@RestController
@RequestMapping("/audio")
public class AudioController {
    @Autowired
    private AudioService audioService;

    @PostMapping("/upload")
    public ResponseEntity<String> creatAudioMetaData(@RequestBody AudioMetadata audioMetadata){
        audioService.createAudioMetaData(audioMetadata);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"Audio metadata created successfully\"}");

    }


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

//    @GetMapping("/status/{jobId}")
//    public ResponseEntity<String> getProcessingStatus(@PathVariable String jobId){
//        return audioService.getProcessingStatus(jobId);
//    }
}
