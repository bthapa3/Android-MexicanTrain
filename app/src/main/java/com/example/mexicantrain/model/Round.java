package com.example.mexicantrain.model;

import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Vector;

import static android.content.Context.MODE_PRIVATE;
/*
 ************************************************************
 * Name:  Bishal Thapa									   *
 * Project:  Project 3 Mexican Train Android Java				       *
 * Class:  CMPS366 OPL				                       *
 * Date:  11/17/2020				                           *
 ************************************************************
 */
public class Round {

    //private classifiers here
    private int m_currentRound;
    private Vector <Tile> m_boneyardTiles;
    //What the next users playtype will be
    public enum Playtype {Human,Computer, Mexican, Help, Quit};
    //this is for engine tile
    private Tile m_engineTile;
    //number of turns played continuously.
    private Integer m_continousplay=0;
    //three trains: user, mexican and computer
    private Vector <Train>  m_trainsList=new Vector<>();
    //User player and the computer player
    private Vector<Player> m_playersList=new Vector<>();
    private boolean m_humannext=false;
    private int m_humanroundscore;
    private int m_computerroundscore;



    //public classfiers here

    /**
     * Round::Round()
     * Default constructor for the round class
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Round(){ }
    /**
     * Round::Round
     * Constructor for the Round class
     * @param  a_round int current round to be played
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Round(int a_round) {
        m_currentRound = a_round;
        m_humanroundscore = 0;
        m_computerroundscore = 0;
    }
    /**
     * Round Humanscore
     * Getter for the human score of the game
     * @return int score of the human player prior to this round.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public int Humanscore(){
        return m_humanroundscore;
    }
    /**
     * Round::Computerscore
     * Getter for the computer score for the game
     * @return int score of the computer player prior to this round.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public int Computerscore()
    {
        return m_computerroundscore;
    }
    /**
     * Round::Initializegame()
     * Initializes a new round for the game by creating a new deck and shuffling the tiles to player,computer and
     *      boneyard tiles.
     * @author Bishal Thapa
     * @date 11/15/2021
     */

    public void Initializegame() {
        Deck mydeck = new Deck();

        //Get the engine Tile based on the round of the game;
        m_engineTile = mydeck.GetEngineTile(m_currentRound);

        //Shuffling the deck after getting the engine tile.
        mydeck.ShuffleDeck();


        //Computer Player for the game
        Player humanplayer = new Human();
        humanplayer.SetTiles(mydeck.GetPlayerTiles());
        Player computerplayer=new Computer();
        computerplayer.SetTiles(mydeck.GetComputerTiles());
        m_playersList.add(humanplayer);
        m_playersList.add(computerplayer);

        //User player initialized with the random 16 tiles being assigned.
        //m_playersList[1] = new Human(mydeck.GetPlayerTiles());

        //Getting the boneyard tiles.
        m_boneyardTiles = mydeck.GetBoneyardTiles();

        //user train object
        Train humantrain=new Train("Human");
        m_trainsList.add(humantrain);
        m_trainsList.get(0).Addtile(m_engineTile);

        //computer train object
        Train computertrain=new Train("Computer");
        m_trainsList.add(computertrain);
        m_trainsList.get(1).Addtile(m_engineTile);

        //computer train object
        Train mexicantrain=new Train("Mexican");
        m_trainsList.add(mexicantrain);
        m_trainsList.get(2).Addtile(m_engineTile);
    }

    /**
     * Round::InitializefromFIle()
     * Helps to restore the previous round of the game by storing contents given as parameters to the game state parameters.
     * @param  a_currentround the nth round that is currently being played
     * @param  a_humanscore Score of the human player before this round
     * @param  a_computerscore Score of the computer player before this round
     * @param  a_humantiles Tiles list that belong to human player
     * @param  a_computertiles Tiles list that belong to computer player
     * @param  a_boneyardtiles Tiles allocated to boneyard
     * @param  a_humantrain Tiles allocated to human train
     * @param  a_computertrain Tiles allocated to computer train
     * @param  a_mexicantrain Tiles allocated to mexican train
     * @param  a_humanfirst value that represents the first player to start the game
     * @param  a_humantrainmarked value that represents whether human train was marked or not
     * @param  a_computertrainmarked value that represents whether computer train was marked or not.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void InitializefromFile(int a_currentround, int a_humanscore, int a_computerscore, Vector<Tile>a_humantiles, Vector<Tile> a_computertiles,
               Vector<Tile> a_boneyardtiles, Vector<Tile> a_humantrain, Vector<Tile> a_computertrain, Vector<Tile> a_mexicantrain, boolean a_humanfirst ,
               boolean a_humantrainmarked,  boolean a_computertrainmarked
    ){
        m_currentRound=a_currentround;
        //Get the engine Tile based on the round of the game;
        m_engineTile = a_humantrain.get(0);

        //Computer Player for the game
        Player humanplayer = new Human();
        humanplayer.SetTiles(a_humantiles);
        Player computerplayer=new Computer();
        computerplayer.SetTiles(a_computertiles);
        m_playersList.add(humanplayer);
        m_playersList.add(computerplayer);

        //User player initialized with the random 16 tiles being assigned.
        //m_playersList[1] = new Human(mydeck.GetPlayerTiles());

        //Getting the boneyard tiles.
        m_boneyardTiles = a_boneyardtiles;

        //user train object
        Train humantrain=new Train("Human");
        m_trainsList.add(humantrain);
        m_trainsList.get(0).SetallTiles(a_humantrain);

        //computer train object
        Train computertrain=new Train("Computer");
        m_trainsList.add(computertrain);
        m_trainsList.get(1).SetallTiles(a_computertrain);



        //computer train object
        Train mexicantrain=new Train("Mexican");
        m_trainsList.add(mexicantrain);
        m_trainsList.get(2).SetallTiles(a_mexicantrain);
        //is human the first player to start the game.


        m_humannext=a_humanfirst;

        //mark the train if marked in file
        if(a_humantrainmarked){
            m_trainsList.get(0).MarkTrain();
        }
        if(a_computertrainmarked){
            m_trainsList.get(1).MarkTrain();
        }
    }

    /**
     * Round::Gettrain()
     * Helps to get the train object
     * @param trainnumber int that represents the numerical value assigned to train type.
     * @return Train object based on the input value
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Train GetTrain(int trainnumber){
        return m_trainsList.get(trainnumber);
    }

    /**
     * Round::GetTrains()
     * Helps to get train objects
     * @return all the three three train objects present.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Vector<Train> GetTrains(){
        return m_trainsList;
    }

    /**
     * Round::MoveBoneyardtoHuman()
     * This function helps to move boneyard tile to human players tile list.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    //This function is called when tile is not playable so just move tile from one pile to another.
    public void MoveBoneyardtoHuman(){
        Tile tiletomove=m_boneyardTiles.get(0);
        m_playersList.get(0).AddtoBack(tiletomove);
        m_boneyardTiles.remove(0);
        m_humannext=false;
        m_trainsList.get(0).MarkTrain();
    }

    /**
     * Round::MoveBoneyardtoTrain()
     * Helps to move playable boneyard tile to valid playable train.
     * @param a_playtype enum which represents how human wants make a move.
     * @param a_response Response to be sent back by round class to the ui.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean MoveBoneyardtoTrain(Playtype a_playtype, StringBuilder a_response){
        //first add the tile to the human train at the end
        MoveBoneyardtoHuman();
        boolean played=PlayHuman(a_playtype, m_playersList.get(0).GetTiles().size(), a_response);
        if (played==false){
            //remove the tile from the human and push it to boneyard so user can choose train again.
            //human plays again.
            m_humannext=true;
            m_boneyardTiles.add(0,m_playersList.get(0).GetTiles().lastElement());
            m_playersList.get(0).RemoveTile(m_playersList.get(0).GetTiles().size()-1);
        }
        else{
            m_trainsList.get(0).RemoveMark();
        }

        return played;
    }

    /**
     * Round::GetPlayer()
     * Returns player object
     * @param playernumber numerical value given based on user
     * @return player object that corresponds to the given numerical parameter.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Player GetPlayer(int playernumber){
        return m_playersList.get(playernumber);
    }
    /**
     * Round::ReturnBoneyard()
     * Returns list of all boneyard tiles
     * @return Vector<Tile> of boneyard tiles.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Vector<Tile> ReturnBoneyard(){
        return m_boneyardTiles;
    }
    /**
     * Round::GetEngineTile()
     * Returns engine tile
     * @return Tile that represents the engine tile.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Tile GetEngineTile(){
        return m_engineTile;
    }
    /**
     * Round::Humannext()
     * Returns who the next player is
     * @return boolean the next player to play a move
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean Humannext(){
        return m_humannext;
    }


    /**
     * Round::PlayHuman()
     * This helps to make a move for a human player turn.This function is called by UI once a human player makes a move.
     * @param  playtype enum that represents how a player wants to play
     * @param tilenumber tile number that human want to play
     * @param response response to be sent back to the UI.
     * @return boolean value that states whether or not move was made successfully.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean PlayHuman(Playtype playtype, int tilenumber, StringBuilder response){
        //needs to check if next player is human okay for now.
        //uses enum type to check if wants to quit or serialize.
        // Player.m_nextstep quitorplay=Player.m_nextstep.Play;
        System.out.println("@@ expected human player");
        StringBuilder msg=new StringBuilder("");
        Tile  tiletoplay=null;
        if(playtype==playtype.Human || playtype==playtype.Mexican || playtype==playtype.Computer){
            tiletoplay=m_playersList.get(0).GetTile(tilenumber-1);
        }
        boolean validplay= m_playersList.get(0).PlayMove(m_trainsList, m_boneyardTiles,m_continousplay, tilenumber, playtype,msg);
        if(!validplay){
            //next player is again human
            m_humannext=true;
        }
        else if(validplay && tiletoplay.IsdoubleTile()){
            m_humannext=true;
            m_continousplay=m_continousplay+1;
            msg.append("\n Double tile played so you get one more tile to play\n");
        }
        else{
            m_humannext=false;
            m_continousplay=0;
        }

        if(msg.equals("")){
            msg.append("Given tile was not playable");
        }
        response.append(msg.toString());
        boolean returnvalue= validplay;
        m_playersList.get(0).SetValidTile(false);
        return returnvalue;

    }


    /**
     * Round::PlayComputer()
     * Helps to make a move for the computer player.
     * @param a_continue boolean that represents if game should be continued or not.
     * @param a_response Stringbuilder value to be sent back as response.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void PlayComputer(boolean a_continue, StringBuilder a_response){

        StringBuilder response= new StringBuilder("");
        m_playersList.get(1).SetReplay(false);
        //was move played successfully.
        boolean played=m_playersList.get(1).PlayMove(m_trainsList, m_boneyardTiles,m_playersList.get(1).GetContinousturns(), -1, null,response );

        if(played && (m_playersList.get(1).Getreplay()==false)){
            m_playersList.get(1).SetContinousturns(0);
            m_humannext=true;
            a_response.append(response);
        }
        //if the computer played a double tile.
        if(m_playersList.get(1).Getreplay()==true){
            response.append("\nComputer gets one more chance to play as double tile was played");
            m_playersList.get(1).SetReplay(false);
            m_playersList.get(1).SetContinousturns(m_playersList.get(1).GetContinousturns()+1);
            a_response.append(response);
        }
        //should not happen.
        else{
            response.append("Computer failed to play this move or got a chance");
        }

    }

    /**
     * Round::CalculateRoundscore()
     * Calculates and stores the human and computer player scores based on the tiles remaining.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean CalculateRoundscore(){
        //calculates the round score after the end of the game.
        for (int i=0;i<m_playersList.get(0).GetTiles().size();i++){
            m_humanroundscore = m_humanroundscore+  m_playersList.get(0).GetTiles().get(i).GetSide1() + m_playersList.get(0).GetTiles().get(i).GetSide2();
        }

        //calculates the round score after the end of the game.
        for (int i=0;i<m_playersList.get(1).GetTiles().size();i++){
            m_computerroundscore = m_computerroundscore+  m_playersList.get(1).GetTiles().get(i).GetSide1() + m_playersList.get(1).GetTiles().get(i).GetSide2();
        }

        return true;
    }


    /**
     * Round::Playpossible()
     * Checks if the game is over or not
     * @return boolean value based on whether game is over or not.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean Playpossible()
    {
        // if both user trains are marked and boneyard is empty.
        if (m_boneyardTiles.size() == 0 && m_trainsList.get(0).isTrainMarked() && m_trainsList.get(1).isTrainMarked()) {
            return false;
        }
        //if one of the player has emptied his hands.
        else if ((m_playersList.get(0).GetTiles().size() == 0) || (m_playersList.get(1).GetTiles().size() == 0))
        {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Round::getContinuePlayed()
     * @return int continous numbers played by the current player before this.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public int getContinuePlayed(){
        return m_continousplay;
    }

    /**
     * Round::SerializeandQuit()
     * Convert the game state to string value in order to quit game
     * @return  String value that has game state saved
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public String SerializeandQuit(int humanscore, int computerscore) {
        //Everything is stored in one string.
        String mycontent="";
        mycontent=mycontent+ "Round: " + m_currentRound+"\n\n"+ "Computer:\n"+ "   Score: " + computerscore +"\n";
        mycontent=mycontent+ "   Hand: ";

        for (int i = 0; i < m_playersList.get(0).GetTiles().size();i++) {
            mycontent=mycontent+ (m_playersList.get(0).GetTiles().get(i).GetSide1()) + "-" + m_playersList.get(0).GetTiles().get(i).GetSide2() + " ";
        }
        mycontent=mycontent+ "\n"+ "   Train: ";
        if (m_trainsList.get(1).isTrainMarked()) mycontent=mycontent+ "M ";
        for (int i = m_trainsList.get(1).GetAllTiles().size() - 1; i >= 0; i--) {
            mycontent=mycontent+ (m_trainsList.get(1).GetAllTiles().get(i).GetSide2()) + "-" + (m_trainsList.get(1).GetAllTiles().get(i).GetSide1()) + " ";
        }

        mycontent=mycontent+ "\n"+"Human:\n"+"   Score: " + humanscore +"\n"+"   Hand: ";
        for (int i = 0; i < m_playersList.get(1).GetTiles().size(); i++) {
            mycontent=mycontent+ m_playersList.get(1).GetTiles().get(i).GetSide1() + "-" + m_playersList.get(1).GetTiles().get(i).GetSide2() + " ";
        }
        mycontent=mycontent+ "\n"+ "   Train: ";
        for (int i = 0; i< m_trainsList.get(0).GetAllTiles().size() ; i ++) {
             mycontent=mycontent+ m_trainsList.get(0).GetAllTiles().get(i).GetSide1() + "-" + m_trainsList.get(0).GetAllTiles().get(i).GetSide2()+ " ";
        }
        if (m_trainsList.get(0).isTrainMarked()) mycontent=mycontent+ "M";
        mycontent=mycontent+"\n\n"+"Mexican Train: ";
        for (int i = 1; i < m_trainsList.get(2).GetAllTiles().size(); i++) {
            mycontent=mycontent+ m_trainsList.get(2).GetAllTiles().get(i).GetSide1() + "-" + m_trainsList.get(2).GetAllTiles().get(i).GetSide2() + " ";
        }
        mycontent=mycontent+ "\n\n"+"Boneyard: ";
        for (int i = 0; i <m_boneyardTiles.size(); i++) {
            mycontent=mycontent + m_boneyardTiles.get(i).GetSide1() + "-" + (m_boneyardTiles.get(i).GetSide2()) + " ";
        }
        mycontent=mycontent+  "\n\n";
        if(m_humannext){
            mycontent=mycontent+"Next Player: Human";
        }
        else{
            mycontent=mycontent+"Next Player: Computer";
        }
        return mycontent;
    }
}


