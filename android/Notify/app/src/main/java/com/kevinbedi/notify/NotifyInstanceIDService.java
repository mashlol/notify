package com.kevinbedi.notify;

import com.google.firebase.iid.FirebaseInstanceIdService;

public class NotifyInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        GcmTokenManager.storeToken(this);
    }
}
