package com.myapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieve SMS message from the intent
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        if (pdus != null) {
            for (Object pdu : pdus) {
                // Convert raw PDU into an SMS message
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                String sender = smsMessage.getOriginatingAddress(); // Get sender's phone number
                String message = smsMessage.getMessageBody(); // Get SMS content

                // Send a confirmation SMS back to the sender
                sendConfirmationSms(sender);

                // Store received SMS details in Firebase
                saveSmsToFirebase(sender, message);
            }
        }
    }

    /**
     * Sends a confirmation SMS to the sender.
     * @param sender The phone number of the sender.
     */
    private void sendConfirmationSms(String sender) {
        if (sender != null) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(sender, null, "You are registered successfully!", null, null);
            Log.d("SmsReceiver", "Reply sent to: " + sender);
        }
    }

    /**
     * Stores received SMS data in Firebase Realtime Database.
     * @param sender The sender's phone number.
     * @param message The received message text.
     */
    private void saveSmsToFirebase(String sender, String message) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true); // Enable offline support
        DatabaseReference dbRef = database.getReference("sms_messages");

        String id = dbRef.push().getKey(); // Generate a unique ID for the SMS
        if (id != null) {
            SmsData smsData = new SmsData(sender, message);
            dbRef.child(id).setValue(smsData)
                    .addOnSuccessListener(aVoid -> Log.d("SmsReceiver", "SMS saved to Firebase successfully."))
                    .addOnFailureListener(e -> Log.e("SmsReceiver", "Failed to save SMS to Firebase", e));
        }
    }
}

/**
 * Model class to store SMS data in Firebase.
 */
class SmsData {
    public String sender;
    public String message;

    // Default constructor required for Firebase
    public SmsData() {}

    public SmsData(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }
}