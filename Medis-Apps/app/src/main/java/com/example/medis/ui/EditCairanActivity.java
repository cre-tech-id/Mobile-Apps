package com.example.medis.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.medis.R;
import com.example.medis.database.DBManager;

import java.util.Calendar;

public class EditCairanActivity extends AppCompatActivity {

    EditText edttanggal, edtcairan, edttotalcairan, edturin, edtberat;
    Button btninput;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cairan);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pink)));
        getSupportActionBar().setTitle("EDIT CAIRAN | KORAN - HD");

        String id_cairan = getIntent().getStringExtra("id_cairan");
        String tanggal = getIntent().getStringExtra("tanggal");
        String urin = getIntent().getStringExtra("urin");
        String berat = getIntent().getStringExtra("berat");
        String batas = getIntent().getStringExtra("batas");
        String total = getIntent().getStringExtra("total");

        edttanggal = findViewById(R.id.edttanggal);
        edtcairan = findViewById(R.id.edtbatas);
        edttotalcairan = findViewById(R.id.edtmenu);
        edturin = findViewById(R.id.edtcairan);
        edtberat = findViewById(R.id.edtberat);
        btninput = findViewById(R.id.btninput);

        edttanggal.setText(tanggal);
        edturin.setText(urin);
        edtberat.setText(berat);
        edtcairan.setText(batas);
        edttotalcairan.setText(total);

        edttanggal.setGravity(Gravity.RIGHT);
        edturin.setGravity(Gravity.RIGHT);
        edttotalcairan.setGravity(Gravity.RIGHT);
        edtcairan.setGravity(Gravity.RIGHT);
        edtberat.setGravity(Gravity.RIGHT);
        edtcairan.setEnabled(false);
        edttotalcairan.setEnabled(false);
        edtcairan.setText("500");

        edtcairan.addTextChangedListener(textWatcher);
        edturin.addTextChangedListener(textWatcher);
        edturin.addTextChangedListener(emptyEdt);
        edttanggal.addTextChangedListener(emptyEdt);
        edtberat.addTextChangedListener(emptyEdt);

        dbManager = new DBManager(this);
        dbManager.open();


        //Add textWatcher to notify the user

        btninput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final long id= Integer.parseInt(id_cairan);
                final String tanggal = edttanggal.getText().toString();
                final Integer cairan = Integer.parseInt(edtcairan.getText().toString());
                final Integer urin = Integer.parseInt(edturin.getText().toString());
                final Integer total_cairan = Integer.parseInt(edttotalcairan.getText().toString());
                final Integer berat = Integer.parseInt(edtberat.getText().toString());
                dbManager.updateCairan(id, tanggal, cairan, urin, total_cairan, berat);
                finish();
            }
        });
        edttanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditCairanActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = day + "/" + month + "/" + year;
                edttanggal.setText(date);
            }
        };

    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (!TextUtils.isEmpty(edtcairan.getText().toString().trim())
                    || !TextUtils.isEmpty(edturin.getText().toString().trim())
            ) {


                int firtValue = TextUtils.isEmpty(edtcairan.getText().toString().trim()) ? 0 : Integer.parseInt(edtcairan.getText().toString().trim());
                int secondValue = TextUtils.isEmpty(edturin.getText().toString().trim()) ? 0 : Integer.parseInt(edturin.getText().toString().trim());

                int answer = firtValue + secondValue;

                Log.e("RESULT", String.valueOf(answer));
                edttotalcairan.setText(String.valueOf(answer));
            }else {
                edttotalcairan.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    TextWatcher emptyEdt = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //On user changes the text
            if(edttanggal.getText().toString().trim().length()!=0 && edturin.getText().toString().trim().length()!=0 && edtberat.getText().toString().trim().length()!=0) {
                btninput.setEnabled(true);
            } else {
                btninput.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


}