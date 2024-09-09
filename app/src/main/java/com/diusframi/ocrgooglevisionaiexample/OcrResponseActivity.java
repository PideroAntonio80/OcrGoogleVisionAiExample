package com.diusframi.ocrgooglevisionaiexample;

import static com.diusframi.ocrgooglevisionaiexample.Constants.COPIED_TEXT_KEY;
import static com.diusframi.ocrgooglevisionaiexample.Constants.OCR_RESPONSE_KEY;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OcrResponseActivity extends AppCompatActivity {

    private String myOcrResponse;

    private ImageView ivCopy, ivShare;
    private TextView ocrResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_response);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            myOcrResponse = bundle.getString(OCR_RESPONSE_KEY);
        }

        initView();
        initListeners();
    }

    private void initView() {
        ivCopy = findViewById(R.id.ivCopy);
        ivShare = findViewById(R.id.ivShare);
        ocrResponse = findViewById(R.id.tvOcrResponse);

//        TextView ocrResponseFiltered = findViewById(R.id.tvOcrResponseFiltered);

        ocrResponse.setText(myOcrResponse);
//        ocrResponseFiltered.setText(searchPattern(myOcrResponse));
    }

    private void initListeners() {
        ivCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copiarTextoAlPortapapeles(ocrResponse, view.getContext());
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copiarYCompartirTexto(ocrResponse, view.getContext());
            }
        });

        ocrResponse.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mostrarDialogoConfirmacion(view.getContext());
                return false;
            }
        });
    }

    // Función para copiar texto al portapapeles
    public void copiarTextoAlPortapapeles(TextView textView, Context context) {
        // Obtén el texto del TextView
        String texto = textView.getText().toString();

        // Obtén el servicio de portapapeles
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        // Crea un objeto ClipData con el texto a copiar
        ClipData clip = ClipData.newPlainText(COPIED_TEXT_KEY, texto);

        // Copia el texto al portapapeles
        clipboard.setPrimaryClip(clip);

        // Muestra un mensaje de confirmación (opcional)
        Toast.makeText(context, getResources().getString(R.string.toast_text_copied), Toast.LENGTH_SHORT).show();
    }

    // Función para copiar texto y ofrecer enviarlo por WhatsApp o email
    public void copiarYCompartirTexto(TextView textView, Context context) {
        copiarTextoAlPortapapeles(textView, context);

        // Crear un Intent para compartir el texto
        Intent compartirIntent = new Intent(Intent.ACTION_SEND);
        compartirIntent.setType("text/plain");  // Tipo de dato a enviar
        compartirIntent.putExtra(Intent.EXTRA_TEXT, textView.getText().toString());  // El texto que se va a compartir

        // Mostrar las opciones de aplicaciones que pueden manejar el intent
        Intent chooser = Intent.createChooser(compartirIntent, getResources().getString(R.string.share_way));

        // Asegurarse de que hay aplicaciones para manejar el intent antes de lanzarlo
        if (compartirIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(chooser);
        } else {
            Toast.makeText(context, getResources().getString(R.string.share_failed), Toast.LENGTH_SHORT).show();
        }
    }

    public void mostrarDialogoConfirmacion(Context context) {
        // Crear el constructor del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getResources().getString(R.string.warning));  // Título del diálogo
        builder.setMessage(getResources().getString(R.string.delete_sure));  // Mensaje que pregunta al usuario

        // Botón "Sí" y su acción
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ocrResponse.setText("");
            }
        });

        // Botón "No" y su acción
        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Crear y mostrar el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }

//    public void mostrarDialogoCompartir(Context context) {
//        // Crear el constructor del diálogo
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(getResources().getString(R.string.share_title));  // Título del diálogo
//        builder.setMessage(getResources().getString(R.string.share_description));  // Mensaje que pregunta al usuario
//
//        // Botón "Sí" y su acción
//        builder.setPositiveButton(getResources().getString(R.string.accept), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//        // Botón "No" y su acción
//        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        // Crear y mostrar el diálogo
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }

//    public static String searchPattern(String input) {
//        String patron = "C[0O][MN][3E][RPF]C[I1][0O][.,;:]\\s*(\\d{9})";
//        Pattern pattern = Pattern.compile(patron);
//        Matcher matcher = pattern.matcher(input);
//
//        if (matcher.find()) {
//            return matcher.group(1);
//        } else {
//            return "No se encuentra ese campo en la lectura OCR";
//        }
//    }
}
