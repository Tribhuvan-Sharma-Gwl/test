package com.example.Rupicard;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.cloud.spring.core.GcpProjectIdProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
@Component
public class GoogleSheetsUtil {
    public static Sheets getSheetsService(GcpProjectIdProvider projectIdProvider)
            throws IOException, GeneralSecurityException {
        InputStream credentialsInputStream = GoogleSheetsUtil.class.getResourceAsStream("/credentials.json");
        GoogleCredential credentials = GoogleCredential
                .fromStream(credentialsInputStream)
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GoogleJsonFactory.getDefaultInstance(),
                credentials)
                .setApplicationName(projectIdProvider.getProjectId())
                .build();
    }
}
