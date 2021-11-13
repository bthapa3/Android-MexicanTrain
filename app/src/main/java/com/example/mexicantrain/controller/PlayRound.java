package com.example.mexicantrain.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mexicantrain.R;
import com.example.mexicantrain.model.Game;
import com.example.mexicantrain.model.Player;
import com.example.mexicantrain.model.Round;
import com.example.mexicantrain.model.Tile;
import com.example.mexicantrain.model.Train;

import java.util.Collections;
import java.util.Vector;


public class PlayRound extends AppCompatActivity implements TileAdapter.OnNotelistener {

    private Button m_playgame;
    private boolean m_newround;
    private TextView m_boneyardtiles, m_engineTile,m_picklabel,m_nextplayer;
    private Game m_game= new Game();
    private RecyclerView m_computertiles,m_humantiles, m_humantrain,m_computertrain, m_mexicantrain;
    private Button m_help, m_next, m_log, m_playhumantrain, m_playcomputertrain, m_playmexicantrain, m_pickboneyard;
    private boolean m_trainpicked=false;
    private  Round.Playtype m_trainplayed=null;
    private boolean m_humannext=false;
    private boolean m_playingboneyard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_round);
        m_humantrain=findViewById(R.id.humantrain);
        m_computertrain=findViewById(R.id.computertrain);
        m_mexicantrain=findViewById(R.id.mexicantrain);
        m_computertiles=findViewById(R.id.computertiles);
        m_humantiles=findViewById(R.id.humantiles);
        m_boneyardtiles=findViewById(R.id.boneyardtiles);
        m_computertiles=findViewById(R.id.computertiles);
        m_humantiles=findViewById(R.id.humantiles);
        m_humantrain=findViewById(R.id.humantrain);
        m_computertrain=findViewById(R.id.computertrain);
        m_mexicantrain=findViewById(R.id.mexicantrain);
        m_engineTile=findViewById(R.id.engineTile);
        m_help=findViewById(R.id.help);
        m_next=findViewById(R.id.next);
        m_log=findViewById(R.id.log);
        m_playhumantrain=findViewById(R.id.playhuman);
        m_playcomputertrain=findViewById(R.id.playcomputer);
        m_playmexicantrain=findViewById(R.id.playmexican);
        m_picklabel=findViewById(R.id.picklabel);
        m_nextplayer=findViewById(R.id.nextplayer);
        m_pickboneyard=findViewById(R.id.pickboneyard);

        GetIntentExtras();
        SetUIChanges();

        m_humannext=m_game.GetRound().Humannext();
        if(m_humannext){
            m_nextplayer.setText("Next player:Human");
            DisplayforHumanTurn();
        }
        else{
            DisplayforComputerTurn();
            m_nextplayer.setText("Next player:Computer");
        }

        //---code till here is used for initial render purposes-----------------------------------//
        m_playhumantrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m_playingboneyard){
                    StringBuilder move_response=new StringBuilder("");
                    //Instead of sendind tile for the boneyard trainnumber is sent in tilenumber value as this is exception.
                    boolean validtile= m_game.GetRound().MoveBoneyardtoTrain(Round.Playtype.Human,move_response);

                    m_playingboneyard=false;
                    if(validtile){
                        Popup("Playing to Boneyard",move_response.toString());
                        m_humannext =m_game.GetRound().Humannext();
                        if(m_humannext){
                            DisplayforHumanTurn();
                            m_nextplayer.setText("Next player:Human");
                        }
                        else{
                            DisplayforComputerTurn();
                            m_nextplayer.setText("Next player:Computer");
                        }
                    }
                    else{
                        Popup("Tile not playable!! Please play again",move_response.toString());
                    }
                    SetUIChanges();
                }
                else{
                    m_trainpicked=true;
                    m_trainplayed=Round.Playtype.Human;
                    Popup("Train chosen: Human", "Please select a valid tile to play on human train");
                    DisplaypickLabel();
                }

            }
        });

        m_playcomputertrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m_playingboneyard){
                    StringBuilder move_response=new StringBuilder("");
                    //Instead of sendind tile for the boneyard trainnumber is sent in tilenumber value as this is exception.
                    boolean validtile= m_game.GetRound().MoveBoneyardtoTrain(Round.Playtype.Computer,move_response);
                    m_playingboneyard=false;
                    if(validtile){
                        Popup("Playing to Boneyard",move_response.toString());
                        m_humannext =m_game.GetRound().Humannext();
                        if(m_humannext){
                            DisplayforHumanTurn();
                            m_nextplayer.setText("Next player:Human");
                        }
                        else{
                            DisplayforComputerTurn();
                            m_nextplayer.setText("Next player:Computer");
                        }
                    }
                    else{
                        Popup("Tile not playable!!! Please play again",move_response.toString());
                    }
                    SetUIChanges();
                }
                else{
                    m_trainpicked=true;
                    m_trainplayed=Round.Playtype.Computer;
                    Popup("Train chosen: Computer", "Please select a valid tile to play on Computer train");
                    DisplaypickLabel();
                }
            }
        });

        m_playmexicantrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m_playingboneyard){
                    StringBuilder move_response=new StringBuilder("");
                    //Instead of sendind tile for the boneyard trainnumber is sent in tilenumber value as this is exception.
                    boolean validtile= m_game.GetRound().MoveBoneyardtoTrain(Round.Playtype.Mexican,move_response);
                    m_playingboneyard=false;
                    if(validtile){
                        Popup("Playing to Boneyard",move_response.toString());
                        m_humannext =m_game.GetRound().Humannext();
                        if(m_humannext){
                            DisplayforHumanTurn();
                            m_nextplayer.setText("Next player:Human");
                        }
                        else{
                            DisplayforComputerTurn();
                            m_nextplayer.setText("Next player:Computer");
                        }
                    }
                    else{
                        Popup("Tile not Playable!! Please play again",move_response.toString());
                    }
                    SetUIChanges();
                }
                else{
                    m_trainpicked=true;
                    m_trainplayed=Round.Playtype.Mexican;
                    Popup("Train chosen: Mexican", "Please select a valid tile to play on Mexican train");
                    DisplaypickLabel();
                }
            }
        });

        m_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m_humannext==false){
                    m_trainpicked=false;
                    StringBuilder response=new StringBuilder("") ;
                    m_game.GetRound().PlayComputer(true, response);
                    Popup("Computer Turn Update",response.toString());
                    m_humannext=m_game.GetRound().Humannext();
                    if(m_humannext){
                        DisplayforHumanTurn();
                        m_nextplayer.setText("Next player:Human");
                    }
                    SetUIChanges();

                }
                else{
                    Toast.makeText(PlayRound.this,"Play tile before pressing yes", Toast.LENGTH_LONG);
                }
            }
        });
        m_pickboneyard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tile boneyardtile= m_game.GetRound().ReturnBoneyard().get(0);
                Vector<Train> trains=m_game.GetRound().GetTrains();
                int continousplayed=m_game.GetRound().getContinuePlayed();
                //if the boneyard tile is not playable
                if(m_game.GetRound().GetPlayer(0).Canplaytile(trains,boneyardtile,continousplayed) ==false){
                    m_game.GetRound().MoveBoneyardtoHuman();
                    String tile_string=String.valueOf(boneyardtile.GetSide1())+'-'+String.valueOf(boneyardtile.GetSide2());
                    Popup("Tile Not Playable!!","Since the tile is not playable to any train, Tile "+tile_string+ " placed to human piles!Marker added.");
                    m_humannext =m_game.GetRound().Humannext();
                    if(m_humannext){
                        DisplayforHumanTurn();
                        m_nextplayer.setText("Next player:Human");
                    }
                    else{
                        DisplayforComputerTurn();
                        m_nextplayer.setText("Next player:Computer");
                    }
                    SetUIChanges();
                }
                //if boneyard tile is playable to atleast one human train.
                else{
                    m_playingboneyard=true;
                    Popup("Boneyard Tile selected","Please place the boneyard tile"+ boneyardtile.Stringified()+ "to a valid train");
                    m_picklabel.setText("Please select train to place the boneyard tile.");
                    m_picklabel.setVisibility(View.VISIBLE);
                    //This depends on the onclick listener for play-train buttons as they are the ones that trigger the move boneyard
                    //functions.
                }

            }
        });
        m_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder response=new StringBuilder("");
                if(m_humannext){
                    m_game.GetRound().PlayHuman(Round.Playtype.Help, -1, response);
                    Popup("Help Provided",response.toString());
                }
            }
        });

    }

    public void SetUIChanges(){
        DisplayComputerTiles();
        DisplayHumanTiles();
        DisplayHumanTrain();
        DisplayComputerTrain();
        DisplayMexicanTrain();
        DisplayEngineTile();
        DisplayBoneyardTile();
    }
    public void DisplayComputerTiles(){
        //displays the human tiles on the text fields
        m_computertiles.setLayoutManager(new GridLayoutManager(this, 11));
        Vector<Tile> computertiles=m_game.GetRound().GetPlayer(1).GetTiles();
        m_computertiles.setAdapter(new TileAdapter(computertiles,"ComputerTiles",this));
    }
    public void DisplayHumanTiles(){
        m_humantiles.setLayoutManager(new GridLayoutManager(this, 11));
        Vector<Tile> humantiles=m_game.GetRound().GetPlayer(0).GetTiles();
        m_humantiles.setAdapter(new TileAdapter(humantiles,"HumanTiles",this));
    }
    public void DisplayHumanTrain(){
        boolean h_markerexists=m_game.GetRound().GetTrain(0).isTrainMarked();
        m_humantrain.setLayoutManager(new GridLayoutManager(this, 6));
        Vector<Tile> humantrain=new Vector<>();
        for(int i=0;i<m_game.GetRound().GetTrain(0).GetAllTiles().size();i++){
            int side1=m_game.GetRound().GetTrain(0).GetAllTiles().get(i).GetSide1();
            int side2=m_game.GetRound().GetTrain(0).GetAllTiles().get(i).GetSide2();
            Tile newTile= new Tile(side1,side2);
            humantrain.add(newTile);
        }
        if(h_markerexists){
            Tile markertile=new Tile();
            humantrain.add(markertile);
        }
        m_humantrain.setAdapter(new TileAdapter(humantrain,"Train",this));
    }
    public void DisplayComputerTrain(){
        boolean C_markerexists=m_game.GetRound().GetTrain(1).isTrainMarked();
        Vector<Tile> computertrain=new Vector<>();
        //Had to do this as the train was being modified due to reference.
        for(int i=0;i<m_game.GetRound().GetTrain(1).GetAllTiles().size();i++){
            int side1=m_game.GetRound().GetTrain(1).GetAllTiles().get(i).GetSide1();
            int side2=m_game.GetRound().GetTrain(1).GetAllTiles().get(i).GetSide2();
            Tile newTile= new Tile(side1,side2);
            computertrain.add(newTile);
        }
        if(C_markerexists){
            Tile markertile=new Tile();
            computertrain.add(markertile);
        }
        //Vector <Tile> computertrain
        Collections.reverse(computertrain);
        computertrain= reverseTileforUI(computertrain);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 6);
        m_computertrain.setLayoutManager(gridLayoutManager);
        m_computertrain.setAdapter(new TileAdapter(computertrain,"Train",this));
    }
    public void DisplayMexicanTrain(){
        m_mexicantrain.setLayoutManager(new GridLayoutManager(this, 6));
        Vector<Tile> mexicantrain=m_game.GetRound().GetTrain(2).GetAllTiles();
        m_mexicantrain.setAdapter(new TileAdapter(mexicantrain,"Train",this));
    }
    public void DisplayEngineTile(){
        Tile engineTile=m_game.GetRound().GetEngineTile();
        m_engineTile.setText("   " + engineTile.GetSide1()+ "\n" + "   "   +engineTile.GetSide2());
    }
    public void DisplayBoneyardTile(){
        Tile nextboneyardTile=m_game.GetRound().ReturnBoneyard().get(0);
        m_boneyardtiles.setText(  nextboneyardTile.GetSide1()+ "--"   +nextboneyardTile.GetSide2());
    }
    public void DisplayforComputerTurn(){
        this.m_playhumantrain.setVisibility(View.INVISIBLE);
        this.m_playcomputertrain.setVisibility(View.INVISIBLE);
        this.m_playmexicantrain.setVisibility(View.INVISIBLE);
        this.m_help.setVisibility(View.INVISIBLE);
        this.m_pickboneyard.setVisibility(View.INVISIBLE);
        this.m_picklabel.setVisibility(View.INVISIBLE);
    }
    public void DisplayforHumanTurn(){
        this.m_playhumantrain.setVisibility(View.VISIBLE);
        this.m_playcomputertrain.setVisibility(View.VISIBLE);
        this.m_playmexicantrain.setVisibility(View.VISIBLE);
        this.m_help.setVisibility(View.VISIBLE);
        this.m_pickboneyard.setVisibility(View.VISIBLE);
    }
    public void DisplaypickLabel(){
        String trainpicked=m_trainplayed.toString();
        m_picklabel.setText(" Train picked: " + trainpicked+ "\n Choose tile to play!!");
        m_picklabel.setVisibility(View.VISIBLE);
    }
   //helper functions to get tiles
    public Vector<Tile> GetPlayertiles(Game a_game, int playernumber){
        return m_game.GetRound().GetPlayer(playernumber).GetTiles();
    }
    public Vector<Tile> GetTraintiles(Game a_game, int trainnumber){
        return m_game.GetRound().GetTrain(trainnumber).GetAllTiles();
    }
    public Vector<Tile> reverseTileforUI(Vector<Tile> a_computertrain){
        Vector<Tile> reversed=new Vector<>();
        for(int i=0;i<a_computertrain.size();i++){
            Tile temp = a_computertrain.get(i);
            temp.Filpside();
            reversed.add(temp);
        }
        return reversed;
    }


    @Override
    public void OnNoteClick(int position) {
        System.out.println("@@ position: "+ position) ;
        if(m_humannext){

            if(m_trainpicked==true){
                StringBuilder move_response=new StringBuilder();
                boolean validtile= m_game.GetRound().PlayHuman(m_trainplayed, position+1,move_response);

                if(validtile==true){
                    m_humannext=m_game.GetRound().Humannext();
                    if(m_humannext){
                        DisplayforHumanTurn();
                        m_nextplayer.setText("Next player:Human");
                    }
                    else{
                        DisplayforComputerTurn();
                        m_nextplayer.setText("Next player:Computer");
                    }
                    SetUIChanges();
                    Popup("Tile moved", move_response.toString());
                }
                else{
                    Popup("Invalid move", move_response.toString());
                }

            }
            else{
                Popup("Train not picked", "Please pick a train before picking the tile");
            }

        }
        else{
            Toast.makeText(this,"Please press next for computer turn",Toast.LENGTH_LONG);

        }
        //finish();
        //startActivity(getIntent());
    }

    public void Popup(String a_Title, String a_Message ){
        AlertDialog dialog=new AlertDialog.Builder(PlayRound.this)
                .setTitle(a_Title)
                .setMessage(a_Message)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    public void GetIntentExtras(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            //This helps to display the right types of files the admin is looking for
            //File types are insurance or Covid-19 results.
            m_newround = bundle.getBoolean("newround");
            boolean humanfirst=bundle.getBoolean("humannext");

            //This decides if new round will be played or what happens
            if(Boolean.TRUE.equals(m_newround)){
                if(Boolean.TRUE.equals(humanfirst)){
                    m_game.setHumanFirst(true);
                }
                else{
                    m_game.setHumanFirst(false);
                }
                m_game.StartGame();
            }
            else if(Boolean.FALSE.equals(m_newround)){
                String filename = bundle.getString("filename");
                m_game.ReadfromFile(filename);
                m_game.StartGamefromFile(filename);
            }
        }
        else{
            //this is an invalid path. There must be a bundle value to play the round.
            Toast.makeText(this,"There was null", Toast.LENGTH_SHORT);
        }


    }
}



