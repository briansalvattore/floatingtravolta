package com.horses.floating.travolta;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);

        stopService(new Intent(this, TravoltaService.class));
    }

    public void onWeb(View view){

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.horsesdeveloper.com"));
        startActivity(browserIntent);
    }

}
