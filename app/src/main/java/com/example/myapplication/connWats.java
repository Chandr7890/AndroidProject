package com.example.myapplication;//package com.example.myapplication;
//
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class connWats extends AppCompatActivity {
//    private Button button;
//    private EditText text1;
//    private EditText text2;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_conn_wats);
//        text1 = findViewById(R.id.id1);
//        text2 = findViewById(R.id.id2);
//        button = findViewById(R.id.btn);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String phno = text1.getText().toString();
//                String msg = text2.getText().toString();
//                boolean installed = appInstalledOrNot("com.watsapp");
//if(installed){
//    Intent  intent = new Intent(Intent.ACTION_VIEW);
//
//    intent.setData(Uri.parse("https://api.whatsapp.com/send?phone="+"+91"+phno+"&text"+Uri.encode(msg)));
//    startActivity(intent);
//}
//else{
//    Toast.makeText(connWats.this, "wats app not installed on your device", Toast.LENGTH_SHORT).show();
//}
//
//
//            }
//        });
//    }
//    private boolean appInstalledOrNot(String url){
//        PackageManager packageManager = getPackageManager();
//        boolean app_installed;
//        try {
//            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
//            app_installed=true;
//        }
//        catch (PackageManager.NameNotFoundException e){
//            app_installed=false;
//        }
//        return app_installed;
//
//    }
//}

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class connWats extends AppCompatActivity {

  private EditText text1;
  private EditText text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn_wats);

        text1 = findViewById(R.id.id1);
        text2 = findViewById(R.id.id2);


        String msg = text2.getText().toString();

        Button whatsappButton = findViewById(R.id.btn);

        whatsappButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsAppWithMessage("HI");
            }
        });
    }

    private void openWhatsAppWithMessage(String message) {
        String phoneNumber = "91"+text1.getText().toString(); // Replace with the recipient's phone number, including the country code (e.g., +1 for the United States)
        String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}