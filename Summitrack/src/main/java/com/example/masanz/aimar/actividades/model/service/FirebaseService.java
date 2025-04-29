package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.entity.AscensionDTO;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {

    @PostConstruct
    public void initializeFirebaseService() {
        try {
            if (FirebaseApp.getApps().isEmpty()) { // Evitar inicialización múltiple
                InputStream serviceAccount = new ClassPathResource("serviceAccountKey.json").getInputStream();
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
                FirebaseApp.initializeApp(options);
                System.out.println("🔥 Firebase inicializado correctamente");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al inicializar Firebase: " + e.getMessage());
        }
    }

    public Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    public List<AscensionDTO> getAllAscensiones(){
        List<AscensionDTO> ascensiones = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> lista = getFirestore().collection("summitrack").get().get().getDocuments();
            for (QueryDocumentSnapshot document : lista) {
                AscensionDTO temporal = document.toObject(AscensionDTO.class);
                ascensiones.add(temporal);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return ascensiones;
    }
}
