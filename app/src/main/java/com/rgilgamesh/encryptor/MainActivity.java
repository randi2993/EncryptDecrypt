package com.rgilgamesh.encryptor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.rgilgamesh.encryptor.Services.CryptoServices;

import org.w3c.dom.Text;

import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class MainActivity extends AppCompatActivity {
    private CryptoServices Service;
    private EditText AddEdit;
    private EditText ResultEdit;
    private Button btnEncrypt;
    private Button btnDecrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.loadInstances();
        this.configureComponents();
        this.loadTextResultFromAddText();
        this.loadActionButtons();
    }

    private void loadInstances(){
        Service = new CryptoServices();
        AddEdit = (EditText) findViewById(R.id.addText);
        ResultEdit = (EditText) findViewById(R.id.resultText);
        btnEncrypt = (Button) findViewById(R.id.btnEncrypt);
        btnDecrypt = (Button) findViewById(R.id.btnDecrypt);
    }

    protected void configureComponents(){
        AddEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (AddEdit.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });

        ResultEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (ResultEdit.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });
    }

    protected void loadTextResultFromAddText(){
        EditText addEdit = (EditText) findViewById(R.id.addText);
        final EditText resultText = (EditText) findViewById(R.id.resultText);

        addEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // loadAddTextOnResultText();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                loadAddTextOnResultText();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // loadAddTextOnResultText();
            }

            private void loadAddTextOnResultText(){
                String text = AddEdit.getText().toString();
            }
        });
    }

    public void loadActionButtons(){
        btnEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = AddEdit.getText().toString();

                if(text.length() > 0){
                    try{
                        text = Service.encodeText(text);
                        ResultEdit.setText(text);
                    }catch (Exception ex){
                        ResultEdit.setText("Error: " + ex.getMessage());
                    }
                }
            }
        });

        btnDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = AddEdit.getText().toString();

                if(text.length() > 0){
                    try{
                        text = Service.decodeText(text);
                        ResultEdit.setText(text);
                    }catch (Exception ex){
                        ResultEdit.setText("Error: " + ex.getMessage());
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.btnShare){
            if(ResultEdit.getText().toString().isEmpty()){
                Toast.makeText(this, getString(R.string.shareResultTextEmpty), Toast.LENGTH_LONG).show();
                return false;
            }

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = ResultEdit.getText().toString();
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.shareVia)));
        }else if(id == R.id.btnInfo){
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        }

        return true;
    }







}
