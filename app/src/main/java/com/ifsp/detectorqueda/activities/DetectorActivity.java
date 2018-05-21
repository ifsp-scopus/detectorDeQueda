package com.ifsp.detectorqueda.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ifsp.detectorqueda.R;
import com.ifsp.detectorqueda.services.DetectorQuedaService;

public class DetectorActivity extends AppCompatActivity {
    private Intent servicoDeteccaoQueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detector);

        this.servicoDeteccaoQueda = new Intent(this, DetectorQuedaService.class);
        this.startService(this.servicoDeteccaoQueda);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        this.stopService(this.servicoDeteccaoQueda);
    }
}