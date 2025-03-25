package com.myapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Extract SMS messages
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        if (pdus != null) {
            for (Object pdu : pdus) {
                SmsMessage message = SmsMessage.createFromPdu((byte[]) pdu);
                String sender = message.getOriginatingAddress();
                String body = message.getMessageBody();

                // Show received message (for debugging)
                Toast.makeText(context, "Message from " + sender + ": " + body, Toast.LENGTH_LONG).show();

                // Process and store in database
                saveToDatabase(context, sender, body);

                // Send confirmation to sender
                sendConfirmation(sender, context);
            }
        }
    }

    private void saveToDatabase(Context context, String sender, String body) {
        // Here, implement database logic to store user details.
        // Example: Use SQLite or Firebase to save sender and message.
    }

    private void sendConfirmation(String phoneNumber, Context context) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, "You have been registered successfully!", null, null);
    }
}
