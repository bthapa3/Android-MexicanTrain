package com.example.mexicantrain.model;

import android.net.Uri;
import android.os.Environment;
import android.telephony.mbms.MbmsErrors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Game {


    private boolean m_humanfirst;
    private int m_humanscore;
    private int m_computerscore;
    //the integer count of the current round
    private int m_currentround;
    private boolean m_initializedfromfile;

    //new round object
    private Round m_newround=null;

    public Game()
    {
        m_humanfirst = false;
        m_humanscore = 0;
        m_computerscore = 0;
        m_currentround = 1;
        m_initializedfromfile = false;

    }
    public void StartGame(){
        boolean computertrainmarked=false;
        boolean  usertrainmarked=false;
        m_newround = new Round(m_currentround);
        m_newround.Initializegame();
    };

    public void StartGamefromFile(String a_filepath){

        ReadfromFile(a_filepath);
    };

    public void addComputerscore(int a_score) {
        m_computerscore = m_computerscore + a_score;
    }
    public void addUserscore(int a_score) {
        m_humanscore = m_humanscore + a_score;
    }
    public int getUserscore() {
        return m_humanscore;
    }
    public int getComputerscore() {
        return m_computerscore;
    }
    public void setInitializedfromFile(boolean a_isinitialized) {
        m_initializedfromfile = a_isinitialized;
    }

    public boolean isInitializedfromFile() {
        return m_initializedfromfile;
    }

    public void setHumanFirst(boolean a_humanfirst) {
        m_humanfirst = a_humanfirst;
    }
    public boolean isHumanFirst() {
        return m_humanfirst;
    }

    //public boolean ReadfromFile(boolean & computertrainmarked, boolean& usertrainmarked, Round & newround);
    //this was the original method. Vector<String>& vect
    //public void LineSplitter(String str, Vector<String>& vect){

    //};

    public String TrimSpace(String s){
     return "Asdf";
    }

    public void ReadfromFile(String a_filepath){
        a_filepath= Environment.getExternalStorageDirectory().getAbsolutePath() +"/Download"+"/"+a_filepath;
        m_newround=new Round(m_currentround);
        StringBuffer sbuffer =new StringBuffer();
        Vector <String> lines=new Vector<>();

        StringBuilder text=new StringBuilder();
        try{
            BufferedReader br=new BufferedReader(new FileReader(a_filepath));
            String newline;
            while((newline=br.readLine())!=null){
                System.out.println("Reads a line");
                if(!newline.trim().isEmpty()) {
                    lines.add(newline);
                    //text.append("\n");
                }

            }
            br.close();
            System.out.println("text content"+ text);
        }catch (IOException e){
            System.out.println("Error"+ e.getStackTrace());
        }
        for(int i=0;i<lines.size();i++){
            System.out.println("line " +i +"-->"+ lines.get(i).trim());


            //there is 3 spaces and Hand:  before tiles so 4 spaces are hold by those
          //  for (int i = 1; i < computertilesunrefined.size(); i++) {
            //    Tile mytile = Tile(stoi(computertilesunrefined[i].substr(0, 1)), stoi(computertilesunrefined[i].substr(2)));
              //  computerrefined.push_back(mytile);
            //}
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
            //this excluedes the lat item.
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


    public Round GetRound(){
        return m_newround;
    }
}

