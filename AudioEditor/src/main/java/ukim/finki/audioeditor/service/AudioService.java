package ukim.finki.audioeditor.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
import ukim.finki.audioeditor.models.AudioMetadata;

import java.util.concurrent.ExecutionException;
//import ukim.finki.audioeditor.processing.FFmpegProcessingService;
//import ukim.finki.audioeditor.repository.FirestoreRepository;

@Service
public class AudioService {

    public void createAudioMetaData(AudioMetadata audioMetadata) {
        Firestore firestore = FirestoreClient.getFirestore();

        DocumentReference docReference = firestore.collection("audioMetadata").document();
        audioMetadata.setId(docReference.getId());

        ApiFuture<WriteResult> apiFuture = docReference.set(audioMetadata);
        try{
            apiFuture.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
    }
}
//    final private FirestoreRepository firestoreRepository;
//    final private FFmpegProcessingService ffmpegProcessingService;
//
//
//    public AudioService(FirestoreRepository firestoreRepository, FFmpegProcessingService ffmpegProcessingService) {
//        this.firestoreRepository = firestoreRepository;
//        this.ffmpegProcessingService = ffmpegProcessingService;
//    }


//    public String generateJobId() {
//        return UUID.randomUUID().toString();
//    }

    // Upload audio file and initiate processing
//    public ResponseEntity<String> uploadAudioFile(MultipartFile file) {
//        String jobId = generateJobId(); // Generate a unique job ID
//        String filePath = "audioFiles/" + file.getOriginalFilename();
//        Long fileSize = file.getSize();
//
//        try {
//            // Upload the file to Firebase Storage
//            firestoreRepository.uploadFileToStorage(file.getInputStream(), filePath);
//
//            // Create metadata entry in Firestore
//            firestoreRepository.createAudioMetadata(jobId, file.getOriginalFilename(), fileSize, filePath);
//
//            // Trigger backend to process the file asynchronously
//            ffmpegProcessingService.processAudioFile(jobId, filePath);
//
//            return ResponseEntity.ok("File uploaded and processing started");
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
//        }
//    }

    // Fetch processing status from Firestore
//    public ResponseEntity<String> getProcessingStatus(String jobId) {
//        String status = firestoreRepository.getProcessingStatus(jobId);
//        return ResponseEntity.ok(status);
//    }

