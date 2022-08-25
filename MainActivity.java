package com.example.qrpacket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText inputbox;
    Button button;
    ImageView qrimage;
    Button scanbtn;
    public static TextView scantext;
    Button copy;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        scantext=(TextView)findViewById(R.id.scantext);
        scanbtn=(Button) findViewById(R.id.scanbtn);
        copy = (Button) findViewById(R.id.copy);

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData Clip = ClipData.newPlainText("EditText" , scantext.getText().toString());
                clipboard.setPrimaryClip(Clip);
                Toast.makeText(MainActivity.this , "Copied the text" , Toast.LENGTH_SHORT).show();
            }
        });

        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), scannerView.class));
            }
        });

        inputbox = findViewById(R.id.inputbox);
        button = findViewById(R.id.button);
        qrimage = findViewById(R.id.qrimage);

        //int[] randomcol = new int[]{  Color.BLACK , Color.RED  , Color.MAGENTA , Color.BLUE , Color.GREEN , Color.BLACK , Color.RED  , Color.MAGENTA , Color.BLUE , Color.GREEN  };
//        int[] randomcol = new int[501];
//        for (int i = 0 ; i<500 ; i = i+6 ){
//            randomcol[i] = Color.BLACK;
//            randomcol[i+1] = Color.RED;
//            randomcol[i+2] = Color.MAGENTA;
//            randomcol[i+3] = Color.BLUE;
//            randomcol[i+4] = Color.BLUE;
//            randomcol[i+5] = Color.GREEN;
//
//        }
        int[] randomcol = new int[]{   Color.RED  , Color.MAGENTA , Color.BLUE , Color.GREEN  , Color.BLACK ,
                Color.rgb(255, 165, 0) ,  Color.rgb(255, 192, 203) ,
                Color.rgb(128, 0, 0) , Color.rgb(128, 0, 128) ,
                Color.rgb(0, 0, 128) ,
                Color.RED  , Color.MAGENTA , Color.BLUE , Color.GREEN , Color.BLACK ,
                Color.rgb(255, 165, 0) ,  Color.rgb(255, 192, 203) ,
                Color.rgb(128, 0, 0) , Color.rgb(128, 0, 128) ,
                Color.rgb(0, 0, 128)     };


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                Random random = new Random();
                int myrand = random.nextInt( randomcol.length /2 );
                int myrand2 = random.nextInt( 4 );
                String data = inputbox.getText().toString().trim();
                MultiFormatWriter writer = new MultiFormatWriter();
                try {

                    BitMatrix bitMatrix = new MultiFormatWriter().encode(data , BarcodeFormat.QR_CODE, 300, 300);
                    Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.RGB_565);
                    for (int x = 0; x< 300; x++){
                        for (int y=0; y< 300; y++){
                            bitmap.setPixel(x,y,bitMatrix.get(x,y)? randomcol[  myrand ] : randomcol[  myrand +myrand2+1]);
                            // bitmap.setPixel(x,y,bitMatrix.get(x,y)? randomcol[  myrand ] : Color.WHITE);
                            //myrand++;
                        }
                    }
                    qrimage.setImageBitmap(bitmap);
                    Toast.makeText(MainActivity.this, "QR Code Generated", Toast.LENGTH_SHORT).show();
                    button.setText("Change Colour Schema");

                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });

        mAdView = new AdView(this);

        mAdView.setAdSize(AdSize.BANNER);

        mAdView.setAdUnitId("ca-app-pub-4397519653000621/4527637756");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        });

    }
}