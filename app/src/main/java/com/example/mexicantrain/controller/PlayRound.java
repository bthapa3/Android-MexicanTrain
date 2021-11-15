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
import android.os.Environment;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mexicantrain.R;
import com.example.mexicantrain.model.Game;
import com.example.mexicantrain.model.Player;
import com.example.mexicantrain.model.Round;
import com.example.mexicantrain.model.Tile;
import com.example.mexicantrain.model.Train;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Vector;


public class PlayRound extends AppCompatActivity implements TileAdapter.OnNotelistener {

    private Button m_playgame;
    private boolean m_newround;
    private TextView m_boneyardtiles, m_engineTile,m_picklabel,m_nextplayer, m_roundnnum, m_humanscore, m_computerscore;
    private Game m_game= new Game();
    private RecyclerView m_computertiles,m_humantiles, m_humantrain,m_computertrain, m_mexicantrain;
    private Button m_help, m_next, m_log, m_playhumantrain, m_playcomputertrain, m_playmexicantrain, m_pickboneyard, m_quit;
    private boolean m_trainpicked=false;
    private  Round.Playtype m_trainplayed=null;
    private boolean m_humannext=false;
    private boolean m_playingboneyard;
    private Vector<String> m_logs=new Vector<>();
    private String m_Filename;

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
        m_roundnnum=findViewById(R.id.roundnumber);
        m_humanscore=findViewById(R.id.humanscore);
        m_computerscore=findViewById(R.id.computerscore);
        m_quit=findViewById(R.id.quit);

        GetIntentExtras();
        SetUIChanges();

        m_humannext=m_game.isHumanFirst();
        if(m_humannext==true){
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
                        m_logs.add(move_response.toString());
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
                        m_logs.add(move_response.toString());
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
                        m_logs.add(move_response.toString());
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
                        m_logs.add(move_response.toString());
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
                //CheckGameover();
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
                        m_logs.add(move_response.toString());
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
                        m_logs.add(move_response.toString());
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
                //CheckGameover();
            }
        });

        m_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m_humannext==false){
                    m_trainpicked=false;
                    StringBuilder response=new StringBuilder("") ;
                    m_game.GetRound().PlayComputer(true, response);
                    m_logs.add(response.toString());
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
                //CheckGameover();
            }
        });
        m_pickboneyard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m_game.GetRound().ReturnBoneyard().size()>0){
                    Tile boneyardtile= m_game.GetRound().ReturnBoneyard().get(0);
                    Vector<Train> trains=m_game.GetRound().GetTrains();
                    int continousplayed=m_game.GetRound().getContinuePlayed();
                    //if the boneyard tile is not playable
                    if(m_game.GetRound().GetPlayer(0).Canplaytile(trains,boneyardtile,continousplayed) ==false){
                        m_game.GetRound().MoveBoneyardtoHuman();
                        String tile_string=String.valueOf(boneyardtile.GetSide1())+'-'+String.valueOf(boneyardtile.GetSide2());
                        m_logs.add("Since the tile is not playable to any train, Tile "+tile_string+ " placed to human piles!Marker added.");
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
                        Popup("Boneyard Tile Playable","Please place the boneyard tile"+ boneyardtile.Stringified()+ "to a valid train");
                        m_picklabel.setText("Please select train to place the boneyard tile.");
                        m_picklabel.setVisibility(View.VISIBLE);
                        //This depends on the onclick listener for play-train buttons as they are the ones that trigger the move boneyard
                        //functions.
                    }
                }
            }
        });
        m_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder response=new StringBuilder("");
                if(m_humannext){
                    m_game.GetRound().PlayHuman(Round.Playtype.Help, -1, response);
                    m_logs.add(response.toString());
                    Popup("Help Provided",response.toString());
                }
            }
        });
        m_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quit();
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
        Displayscoreandround();
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
        Vector<Tile> mexicantrain= (Vector<Tile>) m_game.GetRound().GetTrain(2).GetAllTiles().clone();
        if(mexicantrain.get(0).IsdoubleTile()){
            mexicantrain.remove(0);
        }
        m_mexicantrain.setAdapter(new TileAdapter(mexicantrain,"Train",this));
    }
    public void DisplayEngineTile(){
        Tile engineTile=m_game.GetRound().GetEngineTile();
        m_engineTile.setText("   " + engineTile.GetSide1()+ "\n" + "   "   +engineTile.GetSide2());
    }
    public void DisplayBoneyardTile(){
        if(m_game.GetRound().ReturnBoneyard().size()>0){
            Tile nextboneyardTile=m_game.GetRound().ReturnBoneyard().get(0);
            m_boneyardtiles.setText(  nextboneyardTile.GetSide1()+ "--"   +nextboneyardTile.GetSide2());
        }
    }
    public void Displayscoreandround(){
        Integer roundnum=m_game.GetcurrentroundNum();
        m_roundnnum.setText("Round number: " + roundnum.toString());
        m_roundnnum.setTextSize(14);
        m_humanscore.setTextSize(14);
        m_humanscore.setText("Human score: "+ m_game.GetHumanGamescore());
        m_computerscore.setTextSize(14);
        m_computerscore.setText("Comp score: " + m_game.GetComputerGamescore());
    }

    public void DisplayforComputerTurn(){
        m_playhumantrain.setEnabled(false);
        m_playcomputertrain.setEnabled(false);
        m_playmexicantrain.setEnabled(false);
        this.m_help.setVisibility(View.INVISIBLE);
        this.m_pickboneyard.setVisibility(View.INVISIBLE);
        this.m_picklabel.setVisibility(View.INVISIBLE);
    }
    public void DisplayforHumanTurn(){
        m_playhumantrain.setEnabled(true);
        m_playcomputertrain.setEnabled(true);
        m_playmexicantrain.setEnabled(true);
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
        //System.out.println("@@ position: "+ position) ;
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
                    m_logs.add(move_response.toString());
                    Popup("Tile moved", move_response.toString());
                }
                else{
                    m_logs.add(move_response.toString());
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

    //this checks if the game is over everytime
    public void Popup(String a_Title, String a_Message ){
        AlertDialog dialog=new AlertDialog.Builder(PlayRound.this)
                .setTitle(a_Title)
                .setMessage(a_Message)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        CheckGameover();
                    }
                })
                .create();
        dialog.show();
    }

    //this doesnot check game status.
    public void PopupGeneral(String a_Title, String a_Message, boolean quit ){
        AlertDialog dialog=new AlertDialog.Builder(PlayRound.this)
                .setTitle(a_Title)
                .setMessage(a_Message)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(quit==true){
                            finish();
                        }
                    }
                })
                .create();
        dialog.show();
    }


    public void CheckGameover(){
        if(m_game.GetRound().Playpossible()==false){
            m_game.GetRound().CalculateRoundscore();
            Integer Humanscore=m_game.GetRound().Humanscore();
            Integer Computerscore=m_game.GetRound().Computerscore();
            m_game.AddHumanScore(Humanscore);
            m_game.AddComputerScore(Computerscore);
            Integer HumanGamescore=m_game.GetHumanGamescore();
            Integer ComputerGamescore=m_game.GetComputerGamescore();
            String message= "Human Round score "+ Humanscore + "\nComputer Round score "+ Computerscore+ "\nHuman Game score: "+
                HumanGamescore    + "\nComputer Game score: "+ ComputerGamescore+ "\nDo you want to play one more game?";

            AlertDialog dialog=new AlertDialog.Builder(PlayRound.this)
                    .setTitle("Game over")
                    .setMessage(message)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if(HumanGamescore<ComputerGamescore){
                                m_game.IncreaseRoundnum();
                                m_game.setHumanFirst(true);
                                m_game.StartGame();
                                m_humannext=true;
                                m_nextplayer.setText("Next player:Human");
                                DisplayforHumanTurn();
                                PopupGeneral("New Game","Human had the lower score so human goes next",false);
                                SetUIChanges();
                            }
                            else if(HumanGamescore>ComputerGamescore){
                                m_game.IncreaseRoundnum();
                                m_game.setHumanFirst(false);
                                m_game.StartGame();
                                m_humannext=false;
                                m_nextplayer.setText("Next player:Computer");
                                DisplayforComputerTurn();
                                PopupGeneral("New Game","Computer had the lower score so computer goes next",false);
                                SetUIChanges();
                            }
                            else if(HumanGamescore==ComputerGamescore){
                                Intent intent = new Intent(PlayRound.this,PlayerToss.class);
                                intent.putExtra("Firstround",true);
                                intent.putExtra("Humanscore",HumanGamescore);
                                intent.putExtra("Computerscore", ComputerGamescore);
                                intent.putExtra("Roundnumber", m_game.GetcurrentroundNum());
                                startActivity(intent);
                                finish();
                            }

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            String Winner;
                            if(HumanGamescore>ComputerGamescore){
                                Winner= "Computer Player had the lowest score and won the game";
                            }
                            else if(HumanGamescore< ComputerGamescore){
                                Winner=" Human Player had the lowest score and won the game";
                            }
                            else{
                                Winner="Game tied as both of the players had equal score";
                            }
                            PopupGeneral("This game is over", Winner, true);
                        }
                    })
                    .create();
            dialog.show();


        };
    }


    public void GetIntentExtras(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            //This helps to display the right types of files the admin is looking for
            //File types are insurance or Covid-19 results.
            m_newround = bundle.getBoolean("newround");
            boolean humanfirst=bundle.getBoolean("humannext");
            System.out.println("@@"+ humanfirst);
            Integer humanscore=bundle.getInt("Humanscore");
            Integer computerscore=bundle.getInt("Computerscore");
            Integer roundnum=bundle.getInt("Roundnumber");
            //This decides if new round will be played or what happens
            if(Boolean.TRUE.equals(m_newround)){
                if(Boolean.TRUE.equals(humanfirst)){
                    m_humannext=true;
                    m_game.setHumanFirst(true);
                    m_logs.add("Since the Human player won the toss Human goes first");
                    Popup("New game started","Since the Human player won the toss \nHuman goes first");
                }
                else{
                    m_humannext=false;
                    m_game.setHumanFirst(false);
                    m_logs.add("Since the Computer player won the toss Computer goes first");
                    Popup("New game started","Since the Computer player won the toss \nComputer goes first");
                }
                m_game.SetRoundNum(roundnum);
                m_game.StartGame();
                m_game.AddHumanScore(humanscore);
                m_game.AddComputerScore(computerscore);

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

    //https://stackoverflow.com/questions/10903754/input-text-dialog-android
    public void Quit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to Quit?Enter Filename!! ");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String writecontent= m_game.GetRound().SerializeandQuit(m_game.GetHumanGamescore(), m_game.GetComputerGamescore());
                // Get the directory for the user's public pictures directory.
                System.out.println("@@"+writecontent);
                final File file_path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download");
                //Checking for the path
                if(!file_path.exists())
                {
                    // Make it, if it doesn't exit.
                    file_path.mkdirs();
                }

                final File file = new File(file_path, input.getText().toString());

                try
                {
                    file.createNewFile();
                    FileOutputStream fOut = new FileOutputStream(file);
                    OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                    myOutWriter.append(writecontent);
                    myOutWriter.close();
                    fOut.flush();
                    fOut.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                PopupGeneral("Your file has been serialized","You can quit the game", true);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}



