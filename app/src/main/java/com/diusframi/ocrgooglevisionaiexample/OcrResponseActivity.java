package com.diusframi.ocrgooglevisionaiexample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OcrResponseActivity extends AppCompatActivity {

    private String myOcrResponse;
    private String myOcrResponseFiltered;

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

//        String[] ocrResponseArray = myOcrResponse.split("/");
//
//        for (String s : ocrResponseArray) {
//            if (s.toLowerCase().contains("comercio") || s.toLowerCase().contains("conercio")) {
//                myOcrResponseFiltered = s.substring(9);
//            }
//        }
//
//        if (myOcrResponseFiltered != null && !myOcrResponseFiltered.isEmpty()) {
//            ocrResponseFiltered.setText(myOcrResponseFiltered);
//        } else {
//            ocrResponseFiltered.setText("No se encuentra ese campo en la lectura OCR");
//        }
    }

    public static String searchPattern(String input) {
        String patron = "C[0O][MN][3E][RPF]C[I1][0O][.,;:]\\s*(\\d{9})";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1); // Retorna solo el grupo 1
        } else {
            return "No se encontró el patrón.";
        }
    }

//    public static String buscarPatron(String input) {
//        String patron = "C[0O][MN][3E][RPF]C[I1][0O][.,;:]\\s*";
//        Pattern pattern = Pattern.compile(patron);
//        Matcher matcher = pattern.matcher(input);
//
//        if (matcher.find()) {
//            return matcher.group();
//        } else {
//            return "No se encontró el patrón.";
//        }
//    }
}
