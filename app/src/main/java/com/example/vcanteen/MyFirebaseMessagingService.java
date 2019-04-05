package com.example.vcanteen;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    Dialog myDialog;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        System.out.println("received noti---");
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            System.out.println("received noti"+body);
            NotificationHelper.displayNotification(getApplicationContext(), title, body);
            startPopupActivity(body);
        }
    }

//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
//
//        if (remoteMessage.getNotification() != null) {
//            String title = remoteMessage.getNotification().getTitle();
//            String body = remoteMessage.getNotification().getBody();
//
//            NotificationHelper.displayNotification(getApplicationContext(), title, body);
////            Toast.makeText(getApplicationContext(), "received noti : ", Toast.LENGTH_LONG).show();
//            System.out.println("2: "+title+"\n"+body);
//            startPopupActivity(body);
//            //------------
//
//
//
//
//
//
//
//
//
////            myDialog = new Dialog(getBaseContext());
////            TextView title2;
////            Button dismissBtn;
////            myDialog.setContentView(R.layout.popup_order_done);
////            title2 =(TextView) myDialog.findViewById(R.id.popup_order_done_title);
//
////            dismissBtn = (Button) myDialog.findViewById(R.id.dismiss_btn);
////            dismissBtn.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////
////                }
////            });
//
////            dismissBtn.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    myDialog.dismiss();
////                }
////            });
////            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
////            myDialog.show();
//            //-------------
//
//        }
//    }
    Dialog dialog;
    private void startPopupActivity(String notiType) {
//        Intent intent = new Intent(this, PopupActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.order_row_layout, parent, false);
//        final View_Holder holder = new View_Holder(v);
        dialog = new Dialog(getApplicationContext());
        if(notiType.equals("NOTI_DONE")) {
            dialog.setContentView(R.layout.popup_order_done);
//            Button dismissBtn = (Button)
        } else if (notiType.equals("NOTI_5MIN")) {
            dialog.setContentView(R.layout.popup_5min);
        } else if (notiType.equals("NOTI_TIMEOUT")){
            dialog.setContentView(R.layout.popup_timeout);
        } else if (notiType.equals("NOTI_CANCELLED")){
            dialog.setContentView(R.layout.popup_timeout);
        }
        (dialog.findViewById(R.id.dismiss_btn))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        dialog.dismiss();

//                        System.out.println("current slot number = "+slotNumber.getText());
                    }
                });
        dialog.show();


    }
}
