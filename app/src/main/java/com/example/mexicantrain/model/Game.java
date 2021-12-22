package com.example.mexicantrain.model;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.telephony.mbms.MbmsErrors;

import com.example.mexicantrain.controller.PlayRound;
import com.example.mexicantrain.controller.StartGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
/*
 ************************************************************
 * Name:  Bishal Thapa									   *
 * Project:  Project 3 Mexican Train Android Java				       *
 * Class:  CMPS366 OPL				                       *
 * Date:  11/17/2020				                           *
 ************************************************************
 */
public class Game {


    private boolean m_humanfirst;
    private int m_humanscore;
    private int m_computerscore;
    //the integer count of the current round
    private int m_currentround;
    //new round object
    private Round m_newround=null;

    /**
     * Game::Game
     * Constructor for the game class
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Game()
    {
        m_humanfirst = false;
        m_humanscore = 0;
        m_computerscore = 0;
        m_currentround = 1;

    }
    /**
     * Game::GetcurrentroundNum
     * Returns the round number
     * @return int value that refers to the round number
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public int GetcurrentroundNum(){
        return m_currentround;
    }
    /**
     * Game setRoundNum
     * sets the current round number
     * @param value that is the round number to be set
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void SetRoundNum(int value){
        m_currentround=value;
    }
    /**
     * Game::GetRound
     * Get the current round number
     * @return int value of the round
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Round GetRound(){
        return m_newround;
    }
    /**
     * Game::IncreaseRoundnum
     * Incerases the round number by 1
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void IncreaseRoundnum(){
        m_currentround=m_currentround+1;
    }

    /**
     * Game::StartGame
     * Starts a new round of the game and initializes it
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void StartGame(){

        m_newround = new Round(m_currentround);
        m_newround.Initializegame();
    };
    /**
     * Game::StartGamefromFile
     * Helps to load a game from file
     * @param a_filepath path from where file is to be loaded
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void StartGamefromFile(String a_filepath){
        ReadfromFile(a_filepath);
    };
    /**
     * Game::GetHumanGamesScore
     * returns human score for the game
     * @return human score an integer value
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public int GetHumanGamescore(){
        return m_humanscore;
    }
    /**
     * Game::GetComputerGamescore()
     * returns computer score for the game
     * @return computers score as an integer value
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public int GetComputerGamescore(){
        return m_computerscore;
    }
    /**
     * Game:AddHumanscore
     * Incerase human score by given value
     * @param value value to be added
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void AddHumanScore(int value){
        m_humanscore=m_humanscore+value;
    }
    /**
     * Game::AddComputerScore
     * Increase computer score by given value
     * @param value to be added
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void AddComputerScore(int value){
        m_computerscore=m_computerscore+value;
    }
    /**
     * Game::setHumanFirst
     * Sets if human player is the first one to play
     * @param a_humanfirst boolean that states if the human will be the first one to play
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void setHumanFirst(boolean a_humanfirst) {
        m_humanfirst = a_humanfirst;
    }
    /**
     * Game::isHumanFirst
     * Gets if the human is the first one to play
     * @return boolean value that states if the human is the first player to play
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean isHumanFirst() {
        return m_humanfirst;
    }

    /**
     * Game::ReadfromFile
     * Helps to load game contents from file
     * @param a_filepath file path from where file is to be loaded
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void ReadfromFile(String a_filepath){
        //Each line is read and stored as needed.
        //The following is the path
        a_filepath= Environment.getExternalStorageDirectory().getAbsolutePath() +"/Download"+"/"+a_filepath;
        m_newround=new Round(m_currentround);
        StringBuffer sbuffer =new StringBuffer();
        Vector <String> lines=new Vector<>();

        StringBuilder text=new StringBuilder();
        try{
            BufferedReader br=new BufferedReader(new FileReader(a_filepath));
            String newline;
            while((newline=br.readLine())!=null){
                //System.out.println("Reads a line");
                if(!newline.trim().isEmpty()) {
                    lines.add(newline);
                    //text.append("\n");
                }

            }
            br.close();
            //System.out.println("text content"+ text);
        }catch (IOException e){
            System.out.println("Error"+ e.getStackTrace());
        }

        m_currentround=Integer.parseInt(lines.get(0).trim().substring(7));
        m_computerscore =  Integer.parseInt(lines.get(2).trim().substring(7));
        m_humanscore=Integer.parseInt(lines.get(6).trim().substring(7));

        boolean Human_train_marker=false;
        boolean Computer_train_marker=false;

        Vector<Tile> Computer_tiles=new Vector<>();
        Vector<Tile> Human_tiles=new Vector<>();
        Vector<Tile> Human_train=new Vector<>();
        Vector <Tile> Computer_train =new Vector<>();
        Vector <Tile> Mexican_train=new Vector<>();
        Vector<Tile> Boneyard_tiles=new Vector<>();
        String [] computertilesunrefined = lines.get(3).trim().split(" ");
        String [] humantilesunrefined = lines.get(7).trim().split(" ");
        String [] computertrainunrefined=lines.get(4).trim().split(" ");
        String [] humantrainunrefined=lines.get(8).trim().split(" ");
        String [] mexicantrainunrefined=lines.get(9).trim().split(" ");
        String [] boneyardtilesunrefined=lines.get(10).trim().split(" ");
        String [] nextplayer=lines.get(11).trim().split(" ");
        //second element of the array is player name. Either human or computer.
        m_humanfirst=nextplayer[2].equals("Human");

        for(int j=1;j<computertilesunrefined.length; j++){
            Tile tiletoadd= new Tile( Character.getNumericValue(computertilesunrefined[j].charAt(0)), Character.getNumericValue(computertilesunrefined[j].charAt(2)));
            Computer_tiles.add(tiletoadd);
        }
        for(int j=1;j<humantilesunrefined.length; j++){
            Tile tiletoadd= new Tile( Character.getNumericValue(humantilesunrefined[j].charAt(0)), Character.getNumericValue(humantilesunrefined[j].charAt(2)));
            Human_tiles.add(tiletoadd);
        }
        int endpos=humantrainunrefined.length;
        if(humantrainunrefined[endpos-1].equals("M")){
            System.out.println("There exists a marker for the computer train");
            //this excludes the last item.
            endpos=endpos-1;
            Human_train_marker=true;
        }
        for(int j=1;j<endpos; j++){
            Tile tiletoadd= new Tile( Character.getNumericValue(humantrainunrefined[j].charAt(0)), Character.getNumericValue(humantrainunrefined[j].charAt(2)));
            Human_train.add(tiletoadd);
        }

        int startpos=1;
        if(computertrainunrefined[1].equals("M")){
            System.out.println("There exists a marker for the computer train");
            //this excluedes the lat item.
            startpos=startpos+1;
            Computer_train_marker=true;
        }
        for(int j=computertrainunrefined.length-1; j>=startpos; j--){
            Tile tiletoadd= new Tile( Character.getNumericValue(computertrainunrefined[j].charAt(2)), Character.getNumericValue(computertrainunrefined[j].charAt(0)));
            Computer_train.add(tiletoadd);
        }
        for(int i=0;i<Computer_train.size();i++){
            System.out.println("Computer trian"+ Computer_train.get(i).GetSide1()+ "-" + Computer_train.get(i).GetSide2());
        }


        for(int j=2;j<mexicantrainunrefined.length; j++){
            Tile tiletoadd= new Tile( Character.getNumericValue(mexicantrainunrefined[j].charAt(0)), Character.getNumericValue(mexicantrainunrefined[j].charAt(2)));
            Mexican_train.add(tiletoadd);
        }

        for(int j=1;j<boneyardtilesunrefined.length; j++){
            Tile tiletoadd= new Tile( Character.getNumericValue(boneyardtilesunrefined[j].charAt(0)), Character.getNumericValue(boneyardtilesunrefined[j].charAt(2)));
            Boneyard_tiles.add(tiletoadd);
        }

        m_newround.InitializefromFile(m_currentround, m_humanscore,m_computerscore, Human_tiles, Computer_tiles, Boneyard_tiles,
            Human_train,Computer_train,Mexican_train,m_humanfirst, Human_train_marker,Computer_train_marker);
    }



}

