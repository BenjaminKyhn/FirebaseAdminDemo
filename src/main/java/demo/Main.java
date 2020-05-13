package demo;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        initializeDB();
        read();

        Map<String, Object> Kelvinmap = new HashMap<>();
        save(Kelvinmap, "Tommy", "Thomas");
    }

    public static void initializeDB() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("./serviceAccount.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://fir-demo-359ba.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
    }

    public static void read() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        DocumentReference documentReference = dbFirestore.collection("users").document("SnapChad");
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        DocumentSnapshot document = future.get();

        if (document.exists()) {
            String navn = document.get("navn").toString();
            System.out.println(navn);
        }
    }

    public static void save(Map<String, Object> entry, String navn, String id) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        entry.put("navn", navn);
        dbFirestore.collection("users").document(id).set(entry);
//        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("users").document().set(entry);
    }
}
