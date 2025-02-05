package ukim.finki.audioeditor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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


//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadAudioFile(@RequestParam("file") MultipartFile file) {
//
//        return audioService.uploadAudioFile(file);
//    }
//    @GetMapping("/status/{jobId}")
//    public ResponseEntity<String> getProcessingStatus(@PathVariable String jobId){
//        return audioService.getProcessingStatus(jobId);
//    }
}
