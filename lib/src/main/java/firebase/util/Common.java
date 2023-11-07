package firebase.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Common {

    public static void initFirebase() {
        FileInputStream refreshToken = null;
        try {
            refreshToken = new FileInputStream("C:\\Users\\admin\\eclipse-workspace\\NinjaAdventure\\credentials.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl("https://ninja-adventure-528d8-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                refreshToken.close();
            } catch (IOException ex) {
                Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

}