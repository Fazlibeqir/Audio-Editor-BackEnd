//package ukim.finki.audioeditor.processing;
//
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import ukim.finki.audioeditor.repository.FirestoreRepository;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//
//import java.io.File;
//
//@Service
//public class FFmpegProcessingService {
//    final private FirestoreRepository firestoreRepository;
//
//    public FFmpegProcessingService(FirestoreRepository firestoreRepository) {
//        this.firestoreRepository = firestoreRepository;
//    }
////    public void processAudioFile(String jobId, String filePath) {
////        // Simulate fetching file from Firebase Storage
////        String downloadUrl = "storage/path/" + filePath;
////        File inputFile = new File(downloadUrl);
////
////        // Execute FFmpeg command based on required transformations (merge, split, fade, etc.)
////        try {
////            ProcessBuilder processBuilder = new ProcessBuilder(
////                    "ffmpeg", "-i", inputFile.getAbsolutePath(), "-af", "fade=t=in:st=0:d=5", "output.mp3"
////            );
////            processBuilder.start();
////
////            // Once processing is done, upload the processed file to Firebase Storage
////            String processedFilePath = "processedAudio/processed_" + inputFile.getName();
////            File processedFile = new File(processedFilePath);
////            MultipartFile multipartFile = new MockMultipartFile(processedFile.getName(), new FileInputStream(processedFile));
////            firestoreRepository.uploadFileToStorage(multipartFile, processedFilePath);
////
////            // Update Firestore with processing results
////            firestoreRepository.updateAudioMetadata(jobId, "completed", processedFilePath);
////        } catch (IOException e) {
////            firestoreRepository.updateAudioMetadata(jobId, "failed", "");
////        }
////    }
//}
