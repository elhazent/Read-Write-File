package com.elhazent.picodiploma.myreadwritefile;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnNew;
    Button btnOpen;
    Button btnSave;
    EditText editContent;
    EditText editTitle;

    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNew = findViewById(R.id.new_file);
        btnOpen = findViewById(R.id.load);
        btnSave = findViewById(R.id.save);
        editContent = findViewById(R.id.edit_file);
        editTitle = findViewById(R.id.edt_title);

        btnSave.setOnClickListener(this);
        btnNew.setOnClickListener(this);
        btnOpen.setOnClickListener(this);

        file = getFilesDir();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.save:
                saveFile();
                break;
            case R.id.load:
                openFile();
                break;
            case R.id.new_file:
                newFile();
                break;
        }

    }

    public void newFile(){
        editTitle.setText("");
        editContent.setText("");

        Toast.makeText(this,"Clearing File", Toast.LENGTH_SHORT).show();
    }

    private void loadData(String title){
        Item item = Helper.readFromFile(this,title);
        editTitle.setText(item.getFilename());
        editContent.setText(item.getData());
        Toast.makeText(this,"Loading... " + item.getFilename()+ " data", Toast.LENGTH_SHORT).show();
    }

    public void openFile(){
        showList();
    }

    private void showList() {
        ArrayList<String> data = new ArrayList<>();
        Collections.addAll(data, file.list());

        final CharSequence[] items = data.toArray(new CharSequence[data.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih File..");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadData(items[which].toString());
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void saveFile(){
        if (editTitle.getText().toString().isEmpty()){
            Toast.makeText(this,"Judul Harus Terisi", Toast.LENGTH_SHORT).show();
        } else if (editContent.getText().toString().isEmpty()){
            Toast.makeText(this,"Anda Belum Mengisi Catatan", Toast.LENGTH_SHORT).show();
        } else {
            String title = editTitle.getText().toString();
            String content = editContent.getText().toString();
            Item item = new Item();
            item.setFilename(title);
            item.setData(content);

            Helper.writToFile(item, this);
            Toast.makeText(this,"Saving " + item.getFilename() + " file", Toast.LENGTH_SHORT).show();
        }
    }
}
