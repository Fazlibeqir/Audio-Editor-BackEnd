package ukim.finki.audioeditor.models;

import com.google.cloud.firestore.annotation.PropertyName;
import org.springframework.stereotype.Component;
//// src/models/AudioTrackModel.js
//export default class AudioTrackModel {
//    constructor({ id, blob, type, ref = null }) {
//        this.id = id;
//        this.blob = blob;
//        this.type = type; // e.g. "record" or "import"
//        this.ref = ref;
//    }
//}


@Component
public class AudioMetadata {
    private String id;
    private String fileName;
    private String filePath;
    private String status;
    private Long fileSize;
    private String processedURL;

    // Constructor with parameters
    public AudioMetadata(String fileName, String filePath, String status, Long fileSize, String processedURL) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.status = status;
        this.fileSize = fileSize;
        this.processedURL = processedURL;
    }

    // Default constructor
    public AudioMetadata() {
    }

    // Firestore-specific annotations (optional)
    @PropertyName("id")
    public String getId() {
        return id;
    }
    @PropertyName("id")
    public void setId(String id) {
        this.id = id;
    }
    @PropertyName("fileName")
    public String getFileName() {
        return fileName;
    }

    @PropertyName("filePath")
    public String getFilePath() {
        return filePath;
    }

    @PropertyName("status")
    public String getStatus() {
        return status;
    }

    @PropertyName("fileSize")
    public Long getFileSize() {
        return fileSize;
    }

    @PropertyName("processedURL")
    public String getProcessedURL() {
        return processedURL;
    }
}
