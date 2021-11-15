package com.example.mexicantrain.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mexicantrain.R;

import java.util.Random;

public class PlayerToss extends AppCompatActivity {
    //this image view works as a button.
    private ImageView m_heads, m_tails;
    private Button m_continue;
    private TextView m_displayresult;
    private boolean m_humannext=false;
    private Integer m_nextroundnumber, m_humangamescore, m_computergamescore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_toss);
        m_heads=findViewById(R.id.headsimage);
        m_tails=findViewById(R.id.tailsimage);
        m_continue=findViewById(R.id.continuetogame);
        m_displayresult=findViewById(R.id.resultdisplay);

        Random rand = new Random();
        //generates number between 0 and 1;lets assume 0 is heads and 1 is tails.
        int value = rand.nextInt(2);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            //This helps to display the right types of files the admin is looking for
            //File types are insurance or Covid-19 results.
            m_nextroundnumber = bundle.getInt("Roundnumber");
            m_humangamescore=bundle.getInt("Humanscore");
            m_computergamescore=bundle.getInt("Computerscore");
            //This decides if new round will be played or what happens
        }
        else{
            //this is an invalid path. There must be a bundle value to play the round.
            Toast.makeText(this,"There was null", Toast.LENGTH_SHORT);
        }

        m_heads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_heads.setVisibility(View.INVISIBLE);
                m_tails.setVisibility(View.INVISIBLE);
                m_continue.setVisibility(View.VISIBLE);
                m_displayresult.setVisibility(View.VISIBLE);
                //the logic should not be here.
                if(value==0){
                    m_displayresult.setText("You picked heads and toss value was heads so  you won the toss.");
                    m_humannext=true;
                }
                else{
                    m_displayresult.setText("You picked heads and toss value was tails so  you lost the toss.");
                    m_humannext=false;
                }
            }
        });
        m_tails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_heads.setVisibility(View.INVISIBLE);
                m_tails.setVisibility(View.INVISIBLE);
                m_continue.setVisibility(View.VISIBLE);
                m_displayresult.setVisibility(View.VISIBLE);
                if(value==1){
                    m_displayresult.setText("You picked tails and toss value was tails so you won the toss.");
                    m_humannext=true;
                }
                else{
                    m_displayresult.setText("You picked tails and toss value was heads so  you lost the toss.");
                    m_humannext=false;
                }
            }
        });
        m_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayRound.class);
                intent.putExtra("newround",true);
                intent.putExtra("humannext", m_humannext);
                intent.putExtra("Roundnumber",m_nextroundnumber+1);
                intent.putExtra("Humanscore",m_humangamescore);
                intent.putExtra("ComputerScore",m_computergamescore);
                startActivity(intent);
                finish();
            }
        });
    }
}

