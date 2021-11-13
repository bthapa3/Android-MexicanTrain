package com.example.mexicantrain.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mexicantrain.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class StartGame extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_STORAGE=1000;
    private static final int READ_REQUEST_CODE=42;
    private Button m_startbutton, m_loadbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }


        m_startbutton=findViewById(R.id.startbutton);
        m_loadbutton=findViewById(R.id.loadbutton);
        //check and request permissions


        m_startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PlayerToss.class);
                intent.putExtra("newround",true);
                intent.putExtra("humanscore",0);
                intent.putExtra("computerscore",0);
                startActivity(intent);
                finish();
            }
        });
        m_loadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertdialog = new AlertDialog.Builder(StartGame.this);
                alertdialog.setTitle("Please select your file");
                //Download is where I saved the files on device.
                String file_directory = Environment.getExternalStorageDirectory().getAbsolutePath() +"/Download";
                File directory = new File(file_directory);
                final File[] files_list = directory.listFiles();

                Vector<String> files_name = new Vector<>(files_list.length);

                //find the names of the file and add them to the list of strings
                for (File file : files_list) {
                    String name = file.getName();
                    files_name.add(name);
                }

                String[] File_Names = new String[files_name.size()];
                File_Names = files_name.toArray(File_Names);

                // displays the list of files and starts another activity when a file is chosen.
                alertdialog.setItems(File_Names, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int these) {
                        ListView select = ((AlertDialog) dialog).getListView();
                        String fileName = (String) select.getAdapter().getItem(these);
                        Intent intent = new Intent(StartGame.this, PlayRound.class);
                        intent.putExtra("newround",false);
                        intent.putExtra("filename", fileName);
                        startActivity(intent);
                    }
                });
                alertdialog.show();
            }
        });

    }



}