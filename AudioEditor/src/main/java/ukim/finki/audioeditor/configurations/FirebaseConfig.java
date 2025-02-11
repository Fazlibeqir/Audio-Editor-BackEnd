package ukim.finki.audioeditor.configurations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseConfig {

    @PostConstruct
    public void initializeFirebase() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) { // Prevent multiple initializations

            File serviceAccountFile = new File("/etc/secrets/service-account.json");

            if (!serviceAccountFile.exists()) {
                throw new IOException("Firebase service account file not found: "
                        + serviceAccountFile.getAbsolutePath());
            }

            // Use try-with-resources to ensure the stream is closed
            try (FileInputStream serviceAccountStream = new FileInputStream(serviceAccountFile)) {
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                        .build();
                FirebaseApp.initializeApp(options);
            }
        }
    }
}
