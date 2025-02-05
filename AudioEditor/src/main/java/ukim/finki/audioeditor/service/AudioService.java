package ukim.finki.audioeditor.service;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ukim.finki.audioeditor.models.AudioMetadata;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
//import ukim.finki.audioeditor.processing.FFmpegProcessingService;
//import ukim.finki.audioeditor.repository.FirestoreRepository;

@Service
public class AudioService {
    private Storage storage;

    @EventListener
    public void init(ApplicationReadyEvent event) {
        try {
            ClassPathResource serviceAccount = new ClassPathResource("service-account.json");
            storage = StorageOptions.newBuilder().
                    setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream())).
                    setProjectId("audio-editor-database").build().getService();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createAudioMetaData(AudioMetadata audioMetadata) {
        Firestore firestore = FirestoreClient.getFirestore();

        DocumentReference docReference = firestore.collection("audioMetadata").document();
        audioMetadata.setId(docReference.getId());

        ApiFuture<WriteResult> apiFuture = docReference.set(audioMetadata);
        try {
            apiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
    }

    public String saveTest(MultipartFile file) throws IOException {
        //String projectId = "audio-editor-database";

        // The ID of your GCS bucket
        String bucketName = "audio-editor-database.firebasestorage.app";

        String audioName = generateFileName(file.getOriginalFilename());
        Map<String, String> map = new HashMap<>();
        map.put("firebaseStorageDownloadTokens", audioName);

        BlobId blobId = BlobId.of(bucketName, audioName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setMetadata(map)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getInputStream());

        Blob blob = storage.get(blobId);
        // Create the correct download URL
        String downloadUrl = blob.getMediaLink();

        return downloadUrl;

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

