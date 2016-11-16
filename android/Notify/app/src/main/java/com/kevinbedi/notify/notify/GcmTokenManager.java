package com.kevinbedi.notify.notify;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Kevin on 11/15/2016.
 */

public class GcmTokenManager {

    public static void storeToken() {
        // We'll save it when they log in
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            return;
        }

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users/" +
                FirebaseAuth.getInstance().getCurrentUser().getUid() +
                "/token");
        ref.setValue(refreshedToken);
    }
}
