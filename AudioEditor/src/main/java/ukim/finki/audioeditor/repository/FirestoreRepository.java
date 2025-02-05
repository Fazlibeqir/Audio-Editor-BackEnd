//package ukim.finki.audioeditor.repository;
//
//import com.google.cloud.firestore.Firestore;
//import com.google.firebase.cloud.StorageClient;
//import com.google.firebase.*;
////import com.google.firebase.storage.Storage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import ukim.finki.audioeditor.models.AudioMetadata;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.concurrent.ExecutionException;
//
//@Component
//public class FirestoreRepository {
//    private final Firestore firestore;
//
////    private final Storage storage;
//
//    @Autowired
//    public FirestoreRepository(Firestore firestore
////            ,Storage storage
//    ) {
//        this.firestore = firestore;
////        this.storage = StorageClient.getInstance().bucket();
//    }
//
//
//    //TODO: uploadFileToStorage
////    public void uploadFileToStorage(InputStream fileInputStream, String filePath) throws IOException {
////        Blob blob = storage.create(
////                com.google.cloud.storage.BlobInfo.newBuilder(storage.getName(), filePath).build(),
////                fileInputStream
////        );
////        // The file is now uploaded to Firebase Storage and available at gs://audio-editor-database.appspot.com/{filePath}
////    }
//    public void createAudioMetadata(String jobId, String fileName, Long fileSize,String filePath) {
//        // Create a document in Firestore with initial status and metadata
//        firestore.collection("audioFiles").document(jobId)
//                .set(new AudioMetadata(fileName, filePath, "pending", fileSize,""));
//    }
//    public String getProcessingStatus(String jobId) {
//        // Fetch the processing status from Firestore
//        try {
//            return firestore.collection("audioFiles")
//                    .document(jobId)
//                    .get()
//                    .get()
//                    .getString("status");
//        } catch (ExecutionException | InterruptedException e) {
//            return "Error fetching status";
//        }
//    }
//    public void updateAudioMetadata(String jobId, String status, String processedFilePath) {
//        firestore.collection("audioFiles").document(jobId)
//                .update("status", status, "processedURL", processedFilePath);
//    }
//
//}
