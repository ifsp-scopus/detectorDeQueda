package com.ifsp.detectorqueda.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ifsp.detectorqueda.R;
import com.ifsp.detectorqueda.business.DetectorQueda;

public class DetectorActivity extends AppCompatActivity {
    private DetectorQueda detector;
    private TextView tvXAxis;
    private TextView tvYAxis;
    private TextView tvZAxis;
    private TextView tvMagnitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detector);

        this.detector = new DetectorQueda(this);
        this.tvXAxis = (TextView)this.findViewById(R.id.tvXAxisValor);
        this.tvYAxis = (TextView)this.findViewById(R.id.tvXAxisValor);
        this.tvZAxis = (TextView)this.findViewById(R.id.tvXAxisValor);
        this.tvMagnitude = (TextView)this.findViewById(R.id.tvMagnitudeValor);
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.tvXAxis.setText(this.detector.acelerometro.getxAxis().toString());
        this.tvYAxis.setText(this.detector.acelerometro.getyAxis().toString());
        this.tvZAxis.setText(this.detector.acelerometro.getzAxis().toString());
        this.tvMagnitude.setText(this.detector.acelerometro.getxAxis().toString());
    }

}