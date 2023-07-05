package com.example.chatappserver.services;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

@Service
public class FirebaseService {
    private final String bucketName = "messenger-c45c5.appspot.com";
    private final Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/messenger-c45c5-firebase-adminsdk-z5vta-deefee5600.json"));
    private final Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

    public FirebaseService() throws IOException {
    }

    public File multipartToFile(MultipartFile multipartFile, String fileName) {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tempFile;
    }

    public Blob saveFileToCloud(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        return storage.create(blobInfo, Files.readAllBytes(file.toPath()));
    }

    public String getDownloadURL(String avatarName) {
        Blob blob = avatarName != null ? storage.get(BlobId.of(bucketName, avatarName)) : null;
        return blob != null ? blob.signUrl(365, TimeUnit.DAYS).toString() : null;
    }
}
