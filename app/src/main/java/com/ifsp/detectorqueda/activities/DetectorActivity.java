package com.ifsp.detectorqueda.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ifsp.detectorqueda.R;
import com.ifsp.detectorqueda.business.DetectorQueda;

public class DetectorActivity extends AppCompatActivity {
    private DetectorQueda detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detector);

        this.detector = new DetectorQueda(this);
    }
}
