package com.example.mexicantrain.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mexicantrain.R;

import java.util.Random;

public class PlayerToss extends AppCompatActivity {
    //this image view works as a button.
    private ImageView m_heads, m_tails;
    private Button m_continue;
    private TextView m_displayresult;
    private boolean m_humannext;
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
                    m_displayresult.setText("You picked tails and toss value was tails so  you won the toss.");
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
                startActivity(intent);
                finish();
            }
        });
    }
}

