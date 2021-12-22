package com.example.mexicantrain.model;

import android.widget.Switch;

import com.example.mexicantrain.controller.PlayerToss;

import java.util.Set;
import java.util.Vector;
/*
 ************************************************************
 * Name:  Bishal Thapa									   *
 * Project:  Project 3 Mexican Train Android Java				       *
 * Class:  CMPS366 OPL				                       *
 * Date:  11/17/2020				                           *
 ************************************************************
 */
public class Human extends Player {

    /**
     * Human::Human
     * Default constructor for Human class
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Human(){ }

    /**
     * Human::PlayMove
     * Helps to make a human players move based on human players inputs
     * @return boolean value based on if  move was possible or not.
     * @param trainslist vector of list of all train objects
     * @param boneyard boneyard tiles
     * @param continuedmove int number of turns played continuously before.
     * @param a_playtype type of move human player made
     * @param a_msg response sent after move
     * @param a_tileposition position of tile to be played if exists.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean PlayMove(Vector <Train> trainslist, Vector<Tile> boneyard, int continuedmove, int a_tileposition, Round.Playtype a_playtype,StringBuilder a_msg)
    {
        String tiletobeplayed="";
        if(a_playtype== Round.Playtype.Human || a_playtype== Round.Playtype.Computer|| a_playtype== Round.Playtype.Mexican){
            //there will be a tile to be played in this case.
            tiletobeplayed= String.valueOf(GetTile(a_tileposition-1).GetSide1())  +'-'+ String.valueOf(GetTile(a_tileposition-1).GetSide2());
        }
        System.out.println("@@ Here");
        char trainplayed='X';
        Train Train_tiles=null;
        //this conversion exists because of the need to reuse the code. Doesnot make sense otherwise.
        if(a_playtype.equals(Round.Playtype.Human)){trainplayed='H';Train_tiles=trainslist.get(0);}
        if(a_playtype.equals(Round.Playtype.Mexican)){trainplayed='M';Train_tiles=trainslist.get(2);}
        if(a_playtype.equals(Round.Playtype.Computer)){trainplayed='C';Train_tiles=trainslist.get(1);}
        int tilenumber = 0;
        //if the tile chosen can be attached with train chosen.
        boolean replay = false;
        //validating the tilenumber so that user chooses a valid tilenumber to play

        if(a_playtype.equals(Round.Playtype.Help)){
            //suggests the help for the human
            a_msg.append(Playsuggestor(trainslist, boneyard, continuedmove));
            return false;
        }
        else if(a_playtype.equals(Round.Playtype.Quit)){

            SetQuitGame(true);
            return false;
        }
        else if ((continuedmove < 1) && OrphanDoublePresent(trainslist))
        {
            //char orphantrain=GetOrphanTrain(trainslist);
            if(!IsorphanTrain(Train_tiles)){
                //ask to pick a tile from boneyard as the train is not an orphan double train.
                a_msg.append("You didnot play on the valid orphan train.Select a playable orphan train.");
                return false;
            }
        }
        if (trainplayed == 'H')
        {

            if (continuedmove == 1) {

                if (CheckandPlacesecDouble(trainslist, trainslist.get(0), "Human", a_tileposition,a_msg)) {
                    (trainslist.get(0)).RemoveMark();
                    return true;
                }
            }
            else {
                PlaceCustomTiletoTrain(trainslist.get(0), "Human",a_tileposition);
                if(Isvalidtileplayed()) {
                    a_msg.append("The following tiles was played to the Human train: " + tiletobeplayed);
                    if ((trainslist.get(0)).isTrainMarked())
                    {
                        trainslist.get(0).RemoveMark();
                    }
                    return true;
                }

            }
        }
        else if (trainplayed == 'C') {
            char orphantrain=GetOrphanTrain(trainslist);
            if (trainslist.get(1).isTrainMarked() || orphantrain == 'C') {
                if (continuedmove == 1) {
                    return CheckandPlacesecDouble(trainslist, trainslist.get(1), "Computer", a_tileposition,a_msg);
                } else {
                    PlaceCustomTiletoTrain(trainslist.get(1), "Computer", a_tileposition);
                }
                if(Isvalidtileplayed()) {
                    a_msg.append("The following tiles was played to the Computer train: " + tiletobeplayed);
                    return true;
                }
            }
            else{
                a_msg.append("Sorry cannot play given tile to unmarked computer train. Tile: " + tiletobeplayed);
                return false;
            }
        }
        else if (trainplayed == 'M')
        {
            if (continuedmove == 1) {

                return CheckandPlacesecDouble(trainslist, trainslist.get(2),  "Mexican", a_tileposition,a_msg);
            }
            else {
                PlaceCustomTiletoTrain(trainslist.get(2), "Mexican",a_tileposition);
            }
            if(Isvalidtileplayed()) {
                a_msg.append("The following tiles was played to the Mexican train: " + tiletobeplayed);
                return true;
            }
            return false;

        }
        else {
            a_msg.append("The tile you chose in invalid! Please select a valid playable tile");

        }

        //move was not successful if reached here so return false
        return false;

    }



    /**
     *  Human::Playsuggestor
     *  Help human to play a winning strategy
     * @return StringBuilder values that stores the string suggestion on Train and Tile to play
     * @param trainslist list of trains objects
     * @param continuedmove number of turns continously played before
     * @param boneyard list of boneyard tiles.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public StringBuilder Playsuggestor(Vector <Train> trainslist, Vector<Tile> boneyard, int continuedmove)
    {

        Integer tilenumber=0;
        Train train=new Train();
        boolean turn_repeat = false;
        char traintype='X';
        StringBuilder suggestion=new StringBuilder("");
        //Priority goes from up to down.
        //check if there is a orphan double train present
        if (continuedmove == 0 && OrphanDoublePresent(trainslist)) {
            traintype=GetOrphanTrain(trainslist);
            //move to the orphan double train as needed.

            if (traintype == 'H' && (SuggestOrphanMove(trainslist, trainslist.get(0) ,suggestion))) return suggestion;
            if (traintype == 'M' && (SuggestOrphanMove(trainslist,  trainslist.get( 2) ,suggestion))) return suggestion;
            if (traintype == 'C' && (SuggestOrphanMove(trainslist,  trainslist.get(1) ,suggestion))) return suggestion;

            suggestion.append("You donot have valid tile to play trains. Pick boneyard tiles and Continue");
            return suggestion;
        }

        //checks if the mexican train is empty if so adds tile if available.
        StringBuilder mytilenumber=new StringBuilder("");
        StringBuilder traintoplay=new StringBuilder("");
        if (StartMexicanTrain(trainslist, mytilenumber, traintoplay)) {
            String numbervalue=mytilenumber.toString();
            int adf=Integer.parseInt(numbervalue);
            tilenumber= Integer.parseInt(numbervalue)- 1;
            String tile=  String.valueOf(GetTile(tilenumber).GetSide1())+"-"+String.valueOf(GetTile(tilenumber).GetSide2());
            suggestion.append("Play tile "+ tile + "to the Mexican train as it starts the mexican train");
            return suggestion;
        }

        //String builder was used for reusing the code obtained from c++ as references are not valid in primitive types.
        //This allows getting two values back at a time.
        StringBuilder stringtilenumber_1=new StringBuilder("");
        StringBuilder trainname= new StringBuilder("");
        //check if you can force other player to play orphan double train & orphan double cannot be played more than two times.
        if (PlayOrphanDoublemove(trainslist, stringtilenumber_1, trainname,  trainslist.get(1)) && continuedmove < 2) {
            tilenumber=  Integer.parseInt(stringtilenumber_1.toString()) - 1;
            Tile mytile = GetTiles().get(tilenumber);
            if ((continuedmove == 0) || (continuedmove == 1 && ValidsecondDouble(trainslist, train, mytile))) {
                String tile=  String.valueOf(GetTile(tilenumber).GetSide1())+"-"+String.valueOf(GetTile(tilenumber).GetSide2());
                suggestion.append("Play tile "+ tile + "to the " + trainname.toString() +" Train as it forces opponent to play orphan double train");
                return suggestion;

            }
        }



        Integer mexicantile, mexicansum, selftile, selfsum, opponenttile;
        Train mexicantrain, selftrain;
        StringBuilder MexicanTile=new StringBuilder("");
        boolean can_playmexican = PlayMexicanTrain(trainslist, MexicanTile, train);

        StringBuilder SelfTile=new StringBuilder("");
        boolean can_playself = PlaySelfTrain(trainslist, SelfTile, trainslist.get(0));


        // second argument trainslist.get(1) is the train of the Computer player as current player is Human.
        //This is a new object because append is used instead of rewriting the value.
        StringBuilder OpponentTile=new StringBuilder("");
        traintoplay=new StringBuilder("");
        boolean can_playopponent=Canplayopponenttrain(trainslist,  trainslist.get(1), OpponentTile, traintoplay);


        //if orphandouble is possible try no to play self train as long  as possible
        //in case of orphan train on opponent this is skipped.
        if (continuedmove == 1) {
            OrphanDoublePresent(trainslist);
            traintype=GetOrphanTrain(trainslist);
            if ((traintype == 'H' || traintype=='C') && can_playmexican) {
                tilenumber=  Integer.parseInt(MexicanTile.toString()) -1;
                String tile=  String.valueOf(GetTile(tilenumber).GetSide1())+"-"+String.valueOf(GetTile(tilenumber).GetSide2());
                suggestion.append("Play tile "+ tile + " to the mexican train as it is the largest possible tile to play and helps for orphan double");
                return suggestion;

            }
            //if computer train can be played and if it is marked
            else if((traintype =='H' || traintype=='M') && (can_playopponent && trainslist.get(1).isTrainMarked())  ){
                tilenumber=  Integer.parseInt(OpponentTile.toString()) -1;
                String tile=  String.valueOf(GetTile(tilenumber).GetSide1())+"-"+String.valueOf(GetTile(tilenumber).GetSide2());
                suggestion.append("Play tile "+ tile + " to the Computer train as computer train is marked and helps for orphan double");
                return suggestion;
            }
            else if ((traintype == 'M' || traintype=='C') && can_playself) {
                tilenumber=  Integer.parseInt(SelfTile.toString())-1 ;
                String tile=  String.valueOf(GetTile(tilenumber).GetSide1())+"-"+String.valueOf(GetTile(tilenumber).GetSide2());
                suggestion.append("Play tile "+ tile + " to the human train as it is the largest possible tile to play and helps for orphan double");
                return suggestion;
            }
        }

        if (can_playopponent) {
            tilenumber=  Integer.parseInt(OpponentTile.toString()) - 1;
            String tile=  String.valueOf(GetTile(tilenumber).GetSide1())+"-"+String.valueOf(GetTile(tilenumber).GetSide2());
            suggestion.append("Play tile "+ tile + "to the Computer Train as the opponent train has marker");
            return suggestion;
        }

        if (can_playmexican && can_playself) {
            selftile=Integer.parseInt(SelfTile.toString());
            mexicantile=Integer.parseInt(MexicanTile.toString());
            selfsum= GetTiles().get(selftile - 1).GetSide1() + GetTiles().get(selftile - 1).GetSide2();
            mexicansum=GetTiles().get(mexicantile - 1).GetSide1() + GetTiles().get(mexicantile - 1).GetSide2();
            if(selfsum >= mexicansum){
                tilenumber=  Integer.parseInt(SelfTile.toString())-1;
                String tile=  String.valueOf(GetTile(tilenumber).GetSide1())+"-"+String.valueOf(GetTile(tilenumber).GetSide2());
                suggestion.append("Play tile "+ tile + " to the human train as it is the largest possible tile to play");
                return suggestion;
            }
        }
        if (can_playmexican && can_playself)   {
            selftile=Integer.parseInt(SelfTile.toString());
            mexicantile=Integer.parseInt(MexicanTile.toString()) ;
            selfsum= GetTiles().get(selftile - 1).GetSide1() + GetTiles().get(selftile - 1).GetSide2();
            mexicansum=GetTiles().get(mexicantile - 1).GetSide1() + GetTiles().get(mexicantile - 1).GetSide2();
            if(selfsum < mexicansum){
                tilenumber= Integer.parseInt(MexicanTile.toString());
                String tile=  String.valueOf(GetTile(tilenumber-1).GetSide1())+"-"+String.valueOf(GetTile(tilenumber-1).GetSide2());
                suggestion.append("Play tile "+ tile + " to the mexican train as it is the largest possible tile to play");
                return suggestion;
            }
        }
        if( (can_playself && !can_playmexican)){
            tilenumber=  Integer.parseInt(SelfTile.toString());
            String tile=  String.valueOf(GetTile(tilenumber-1).GetSide1())+"-"+String.valueOf(GetTile(tilenumber-1).GetSide2());
            suggestion.append("Play tile "+ tile + " to the human train as it is the largest possible tile to play");
            return suggestion;
        }

        if(!can_playself && can_playmexican){
            tilenumber= Integer.parseInt(MexicanTile.toString());
            String tile=  String.valueOf(GetTile(tilenumber-1).GetSide1())+"-"+String.valueOf(GetTile(tilenumber-1).GetSide2());
            suggestion.append("Play tile "+ tile + " to the mexican train as it is the largest possible tile to play");
            return suggestion;
        }
        else {

            suggestion.append("You do not have any valid tiles to play on a valid train. So pick a tile from boneyard to continue");
            return suggestion;

        }
    }


    /**
     * Human::SuggestOrphanMove
     * Help if the orphan double train exists
     * @return boolean value based on if orphan double can be played ::important a_msg is returned by reference
     * @param a_trainslist list of all valid trains
     * @param a_msg suggestion for human move
     * @param a_train orphan train where tile is to be played
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean SuggestOrphanMove(Vector<Train> a_trainslist, Train a_train,StringBuilder a_msg)
    {
        if (CanPlayinTrain(a_train)) {

            int tilenumber = GetPlayableTile(a_train)-1;
            String tile=  String.valueOf(GetTile(tilenumber).GetSide1())+"-"+String.valueOf(GetTile(tilenumber).GetSide2());
            a_msg.append("Play tile "+ tile + "to the " + a_train.trainType() +"Train as it is the orphan double train.");
            // DisplaySuggestion(tilenumber, a_train, "it is the orphan double train");
            return true;
        }
        return false;
    }


    /**
     * Human::CheckandPlacesecDouble
     * Place the second double tile to playable train.
     * @return boolean based on if tile was played
     * @param a_trainslist list of trains
     * @param a_train train type of chosen train
     * @param a_chosenTrain  train object for the chosen train
     * @param a_response response to the UI based on the move
     * @param a_position position of the tile in the vector.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean CheckandPlacesecDouble(Vector<Train> a_trainslist, Train a_chosenTrain, String a_train, int a_position,StringBuilder a_response)
    {
        //tilenumber to play
        int tile_number = a_position;
        //represents if tile can be placed or not
        boolean placement_possible = false;
        //tile chosen by the user.
        Tile userinput = GetTile(tile_number - 1);

        //if player doesnot choose double tile for the second time
        if (userinput.GetSide1() != userinput.GetSide2()) {
            PlaceCustomTiletoTrain(a_chosenTrain, a_train, tile_number);
            if(Isvalidtileplayed()){
                a_response.append("Tile:"+ userinput.Stringified() +"was added to the" + a_train+ "Train");
                return true;
            }
            else{
                a_response.append("Tile was not playable please play again!!");
                return false;
            }
        }
        //if it is a double tile for second turn
        else {
            //if a train matches a double tile.
            if (a_chosenTrain.GetTop().GetSide2() == userinput.GetSide1() || a_chosenTrain.GetTop().GetSide2() == userinput.GetSide1()) {

                //CheckifvalidTileispresent
                boolean double_tile_is_valid = ValidsecondDouble(a_trainslist, a_chosenTrain, userinput);
                if (double_tile_is_valid) {
                    PlaceCustomTiletoTrain(a_chosenTrain, a_train, tile_number);
                    if(Isvalidtileplayed()){
                        a_response.append("Tile: " +userinput.Stringified() + " was added to the" + a_train+ "Train");
                        return true;

                    }
                    else{
                        a_response.append("Sorry double move cannot be made as you cannot play a single tile after this move!!");
                        return false;
                    }

                }
                else {
                    a_response.append("Sorry double move cannot be made as you cannot play a single tile after this move");
                    return false;
                }
            }
            else {
                a_response.append("Tile was not playable please play again!!");
                return false;

            }
        }

    }



    /**
     * Human::PlaceCustomTiletoTrain
     *Place a given tile number to given train
     * @param a_train train where tile is to be placed
     * @param a_tilenumber number of the tile to be played
     * @param a_trainname type of the train where tile is played
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void PlaceCustomTiletoTrain(Train a_train, String a_trainname, int a_tilenumber)
    {
        //using the last tile of the player list which is the boneyard tile.
        //int tilenumber = GetPlayerTiles().size();
        int tilenumber = a_tilenumber;
        Tile nextmove = GetTiles().get(tilenumber - 1);
        //Check TrainMove check if the move to the Train is valid or not and returns true if the
        //move was successful.
        if (CheckTrainMove(a_train, nextmove, tilenumber)) {
            //this gives one extra chance in condition of OrphanDouble
            if (nextmove.GetSide1() == nextmove.GetSide2()) {
                SetReplay(true);
            }
            SetValidTile(true);
            //only work if the train type is User or computer
            //traintype.RemoveMark();
        }
        else
        {
            //This function is not called in this case so should not occur.
           // cout << "The tile you chose cannot be placed on the " + a_trainname + " train " << endl;

        }
    }
}
