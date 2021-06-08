package com.example.expensesmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetilActivity extends AppCompatActivity {
    private EditText etNim, etNama;
    public String uri;
    ImageView cover;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil);
        etNim = findViewById(R.id.etNim);
        etNama = findViewById(R.id.etNama);
        Intent intent = getIntent();
        if(intent.hasExtra("MAHASISWA")) {
            Mahasiswa mhs = (Mahasiswa) intent.getSerializableExtra("MAHASISWA");
            etNim.setText(mhs.getNim());
            etNama.setText(mhs.getNama());
            etNim.setEnabled(false);
        } else {
            etNim.setEnabled(true);
        }

        cover = findViewById(R.id.isifoto);
        fab = findViewById(R.id.fabisifoto);
        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ImagePicker.Companion.with(DetilActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uri = data.getData().toString();
        cover.setImageURI(Uri.parse(uri));
    }

    public void simpanData(View view){
        String mNIM = etNim.getText().toString();
        String mNama = etNama.getText().toString();
        String mFoto = uri;
        if(mNIM.length() <= 0 || mNama.length() <= 0){
            Toast.makeText(this,"Semua harus Diisi", Toast.LENGTH_LONG).show();
        } else {
            Intent intentJawab = new Intent();
            Mahasiswa mhs = new Mahasiswa(mNIM, mNama, mFoto);
            intentJawab.putExtra("MAHASISWA",mhs);
            setResult(RESULT_OK,intentJawab);
            finish();
        }

    }

    public void batal(View view){
        Intent intentJawab = new Intent();
        setResult(RESULT_CANCELED,intentJawab);
        finish();
    }
}
