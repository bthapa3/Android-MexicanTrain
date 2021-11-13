package com.example.mexicantrain.model;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Vector;

public class Round {

    //private classifiers here
    private int m_currentRound;
    private Vector <Tile> m_boneyardTiles;
    public enum Playtype {Human,Computer, Mexican, Help, Quit,Boneyard};
    //this is the engine tile
    private Tile m_engineTile;

    private Integer m_humangamescore=0;
    private Integer m_computergamescore=0;
    private Integer m_continousplay=0;

    //three trains for the user. mexican and the computer
    private Vector <Train>  m_trainsList=new Vector<>();

    //User player and the computer player
    private Vector<Player> m_playersList=new Vector<>();
    private boolean m_humannext=false;

    private boolean m_gameover;
    private int m_humanroundscore;
    private int m_computerroundscore;
    private int m_totalcomputer;
    private int m_totalplayer;



    //public classfiers here
    public Round(){

    }
    public Round(int a_round) {
        m_currentRound = a_round;
        m_gameover = false;
        m_humanroundscore = 0;
        m_computerroundscore = 0;
    }
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
        Train humantrain=new Train("humantrain");
        m_trainsList.add(humantrain);
        m_trainsList.get(0).Addtile(m_engineTile);

        //computer train object
        Train computertrain=new Train("computertrain");
        m_trainsList.add(computertrain);
        m_trainsList.get(1).Addtile(m_engineTile);

        //computer train object
        Train mexicantrain=new Train("mexicantrain");
        m_trainsList.add(mexicantrain);
        m_trainsList.get(2).Addtile(m_engineTile);

        ///////------------------------------------------------------------------------------//
        System.out.println("@@Engine-"+ m_engineTile.GetSide1() + "-" + m_engineTile.GetSide2());

        for (int i=0;i<m_playersList.get(0).GetTiles().size();i++){
            System.out.println("Human Tiles " + m_playersList.get(0).GetTiles().get(i).GetSide1() + "--"+
                    m_playersList.get(0).GetTiles().get(i).GetSide2()
            );
        }
        for (int i=0;i<m_playersList.get(1).GetTiles().size();i++){
            System.out.println("Computer Tiles " + m_playersList.get(1).GetTiles().get(i).GetSide1() + "--"+
                    m_playersList.get(1).GetTiles().get(i).GetSide2()
            );
        }
    }


    public void InitializefromFile(int a_currentround, int a_humanscore, int a_computerscore, Vector<Tile>a_humantiles, Vector<Tile> a_computertiles,
               Vector<Tile> a_boneyardtiles, Vector<Tile> a_humantrain, Vector<Tile> a_computertrain, Vector<Tile> a_mexicantrain, boolean a_humanfirst ,
               boolean a_humantrainmarked,  boolean a_computertrainmarked
    ){
        m_currentRound=a_currentround;
        m_humangamescore=a_humanscore;
        m_computergamescore=a_computerscore;
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
        Train humantrain=new Train("humantrain");
        m_trainsList.add(humantrain);
        m_trainsList.get(0).SetallTiles(a_humantrain);

        //computer train object
        Train computertrain=new Train("computertrain");
        m_trainsList.add(computertrain);
        m_trainsList.get(1).SetallTiles(a_computertrain);



        //computer train object
        Train mexicantrain=new Train("mexicantrain");
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
    public Train GetTrain(int trainnumber){
        return m_trainsList.get(trainnumber);
    }

    public Vector<Train> GetTrains(){
        return m_trainsList;
    }

    //This function is called when tile is not playable so just move tile from one pile to another.
    public void MoveBoneyardtoHuman(){
        Tile tiletomove=m_boneyardTiles.get(0);
        m_playersList.get(0).AddtoBack(tiletomove);
        m_boneyardTiles.remove(0);
        m_humannext=false;
        m_trainsList.get(0).MarkTrain();
    }

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

        return played;
    }

    public Player GetPlayer(int playernumber){
        return m_playersList.get(playernumber);
    }
    public Vector<Tile> ReturnBoneyard(){
        return m_boneyardTiles;
    }
    public Tile GetEngineTile(){
        return m_engineTile;
    }
    public boolean Humannext(){
        return m_humannext;
    }



    //this should return whether if the tile given was playable or not.
    //if the boolean value was false, response should give the response as to why tile was not moveable.

    //Hint...While picking from Boneyard check the tile to play and if the tile is moveable.
    //If not call move tile to Human through one of the functions inside the round class.
    //If moveable ask to pick a train to and move the tile to the train.
    public boolean PlayHuman(Playtype playtype, int tilenumber, StringBuilder response){
        //needs to check if next player is human okay for now.
       //uses enum type to check if wants to quit or serialize.
        // Player.m_nextstep quitorplay=Player.m_nextstep.Play;
        System.out.println("@@ expected human player");
        StringBuilder msg=new StringBuilder("");
        Tile tiletoplay=null;
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
        //instead access the private value of quit of player class using getter and setter.
        //if(quitorplay == Player.m_nextstep.Quit){
            //Must serialize and quit here.
        //}
        if(msg.equals("")){
            msg.append("Given tile was not playable");
        }
        response.append(msg.toString());
        //return true;
        boolean returnvalue= validplay;
        m_playersList.get(0).SetValidTile(false);
        return returnvalue;

    }

    //bool a_continue represents if the user wants to quit or continue playing.
    public void PlayComputer(boolean a_continue, StringBuilder a_response){
        //Player.m_nextstep quitorplay=Player.m_nextstep.Play;
        //PlayMove(Vector <Train> trainslist, Vector<Tile> boneyard, int continuedmove)

        StringBuilder response= new StringBuilder("");
        boolean played=m_playersList.get(1).PlayMove(m_trainsList, m_boneyardTiles,m_continousplay, -1, null,response );

        if(played){
            m_humannext=true;
            a_response.append(response);
        }
        else{
            response.append("Computer failed to play this move");
        }
        if(m_playersList.get(0).Getquitgame() == true){
            //Must serialize and quit here.
            System.out.println("@@ Game quits.");
        }
    }


    public boolean CalculateRoundscore(){
        //calculates the round score after the end of the game.
        for (int i=0;i<m_playersList.get(0).GetTiles().size();i++){
            m_humanroundscore = m_humanroundscore+  m_playersList.get(0).GetTiles().get(i).GetSide1() + m_playersList.get(0).GetTiles().get(i).GetSide2();
        }

        //calculates the round score after the end of the game.
        for (int i=0;i<m_playersList.get(1).GetTiles().size();i++){
            m_computerroundscore = m_computerroundscore+  m_playersList.get(1).GetTiles().get(i).GetSide1() + m_playersList.get(1).GetTiles().get(i).GetSide2();
        }

        //cout << "Human Player's score for the round:" << m_playerscore << endl;
        //cout << "Computer Player's score for the round:" << m_computerscore << endl;

        return true;
    }



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

    public int getContinuePlayed(){
        return m_continousplay;
    }

}


