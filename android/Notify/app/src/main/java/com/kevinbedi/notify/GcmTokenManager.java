package com.kevinbedi.notify;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class GcmTokenManager {

    public static interface Listener {
        void onTokenGenerated();
    }

    private static final String PREFS_NAME = "gcm_token_pref";
    private static final String PREFS_TOKEN_KEY = "gcm_token_key";
    private static final Character[] VALID_TOKEN_CHARACTERS = new Character[] {
            '0', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 
            'i', 'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    private static final int TOKEN_LENGTH = 9;

    private static Listener mListener;

    public static void setListener(Listener listener) {
        mListener = listener;
    }

    public static void removeListener() {
        mListener = null;
    }

    public static void storeToken(final Context context) {
        // We'll save it when they log in
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            return;
        }

        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference outerRef = database.getReference("users/" +
                FirebaseAuth.getInstance().getCurrentUser().getUid() +
                "/token");
        outerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String token;
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    token = dataSnapshot.getValue(String.class);
                } else {
                    token = maybeGenerateToken(context);
                }

                DatabaseReference ref = database.getReference("tokens/" + token + "/gcmToken");
                ref.setValue(refreshedToken);
                outerRef.setValue(token);

                if (mListener != null) {
                    mListener.onTokenGenerated();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO do something?
            }
        });
    }

    public static String getExistingToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        if (prefs.contains(PREFS_TOKEN_KEY)) {
            return prefs.getString(PREFS_TOKEN_KEY, "");
        }
        return null;
    }

    private static String maybeGenerateToken(Context context) {
        String existingToken = getExistingToken(context);
        if (existingToken != null) {
            return existingToken;
        }

        String token = generateToken();
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        prefs.edit().putString(PREFS_TOKEN_KEY, token).apply();
        return token;
    }

    private static String generateToken() {
        String token = "";
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            token += getRandomValidChar();
        }

        return token;
    }

    private static Character getRandomValidChar() {
        return VALID_TOKEN_CHARACTERS[
                (int) Math.floor(Math.random() * VALID_TOKEN_CHARACTERS.length)];
    }
}
