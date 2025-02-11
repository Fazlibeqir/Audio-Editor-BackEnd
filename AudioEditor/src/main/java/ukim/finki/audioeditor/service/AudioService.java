package ukim.finki.audioeditor.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AudioService {
    private Storage storage;
    @EventListener
    public void init(ApplicationReadyEvent event) {
        try {
            // Use the absolute path where Render mounts your secret file
            File serviceAccountFile = new File("/etc/secrets/service-account.json");

            if (!serviceAccountFile.exists()) {
                throw new IOException("Service account file not found at: " + serviceAccountFile.getAbsolutePath());
            }

            try (InputStream credentialsStream = new FileInputStream(serviceAccountFile)) {
                storage = StorageOptions.newBuilder()
                        .setCredentials(GoogleCredentials.fromStream(credentialsStream))
                        .setProjectId("audio-editor-database")
                        .build()
                        .getService();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

    private String generateFileName(String originalFileName) {
        String extension = getExtension(originalFileName);
        if (extension == null || extension.isEmpty()) {
            extension = "mp3";
        }
        return UUID.randomUUID().toString() + "." + extension;
    }

    public String saveTest(MultipartFile file) throws IOException {
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
        String downloadUrl = blob.getMediaLink();
        return downloadUrl;
    }

    public String mergeTracks(List<MultipartFile> files) throws IOException, InterruptedException {
        // Save the uploaded files to the local file system
        File[] audioFiles = new File[files.size()];
        for (int i = 0; i < files.size(); i++) {
            String uniqueFileName = generateFileName(files.get(i).getOriginalFilename());
            audioFiles[i] = convertMultipartFileToFile(files.get(i),uniqueFileName);
        }

        String mergedFilePath = "merged_audio.mp3";
        mergeAudioFiles(audioFiles, mergedFilePath);

        File mergedFile = new File(mergedFilePath);
        String bucketName = "audio-editor-database.firebasestorage.app";
        String audioName = generateFileName(mergedFile.getName());
        Map<String, String> map = new HashMap<>();
        map.put("firebaseStorageDownloadTokens", audioName);

        BlobId blobId = BlobId.of(bucketName, audioName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setMetadata(map)
                .setContentType("audio/mpeg")
                .build();

        storage.create(blobInfo, new FileInputStream(mergedFile));

        Blob blob = storage.get(blobId);
        String downloadUrl = blob.getMediaLink();
        return downloadUrl;
    }

    private File convertMultipartFileToFile(MultipartFile file, String uniqueFileName) throws IOException {
        File convFile =File.createTempFile("temp",uniqueFileName);
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private void mergeAudioFiles(File[] audioFiles, String outputFilePath) throws IOException, InterruptedException {
       File fileList = File.createTempFile("ffmpeg_file_list", ".txt");
       try (FileOutputStream fos = new FileOutputStream(fileList)){
           for(File audioFile : audioFiles){
               String escapedPath = audioFile.getAbsolutePath().replace("'", "\\'");
               String line = "file '" + escapedPath + "'\n";
               fos.write(line.getBytes());
           }
       }
        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg",
                "-y",
                "-f", "concat",
                "-safe", "0",
                "-i", fileList.getAbsolutePath(),
                "-c", "copy",
                outputFilePath
        );
        pb.redirectErrorStream(true); // Merge stdout and stderr
        Process process = pb.start();
        String processOutput = new String(process.getInputStream().readAllBytes());
        process.waitFor();

        fileList.delete(); // Clean up the temporary file list

        // Check if ffmpeg failed
        if (process.exitValue() != 0) {
            throw new IOException("ffmpeg process failed: " + processOutput);
        }
    }
}