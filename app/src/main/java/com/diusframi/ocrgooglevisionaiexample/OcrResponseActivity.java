package com.diusframi.ocrgooglevisionaiexample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OcrResponseActivity extends AppCompatActivity {

    private String myOcrResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_response);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            myOcrResponse = bundle.getString("OCR_RESPONSE_KEY");
        }

        initView();
    }

    private void initView() {
        TextView ocrResponse = findViewById(R.id.tvOcrResponse);
        TextView ocrResponseFiltered = findViewById(R.id.tvOcrResponseFiltered);

        ocrResponse.setText(myOcrResponse);
        ocrResponseFiltered.setText(searchPattern(myOcrResponse));
    }

    public static String searchPattern(String input) {
        String patron = "C[0O][MN][3E][RPF]C[I1][0O][.,;:]\\s*(\\d{9})";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "No se encuentra ese campo en la lectura OCR";
        }
    }
}
