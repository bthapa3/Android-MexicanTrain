package com.example.mexicantrain.model;

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
public class Computer extends Player {

    /**
     * Computer::Computer
     * Default constructor for the Computer class
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Computer(){ }
    /**
     * Computer::PlayMove
     * Helps to make a move for the computer players turn.Utilizes a winning strategy to play
     * @return boolean value based on whether move is playable or not.
     * @param trainslist list of all the valid train objects
     * @param boneyard boneyard tiles in a vector
     * @param a_response response to be sent to the UI
     * @param continuedmove number of turns played continously before
     * @param a_playtype type of move to be made ::Continue playing or quit.
     * @param a_tileposition tile which is to be played(not valid for computer player)
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean PlayMove(Vector <Train> trainslist, Vector<Tile> boneyard, int continuedmove,int a_tileposition, Round.Playtype a_playtype,StringBuilder a_response)
    {

        Integer tilenumber=0;
        Train train=new Train();
        boolean turn_repeat = false;
        char traintype='X';
        //Priority goes from up to down.
        //check if there is a orphan double train present
        if (continuedmove == 0 && OrphanDoublePresent(trainslist)) {
            traintype=GetOrphanTrain(trainslist);
            //move to the orphan double train as needed.
            if (traintype == 'H' && (checkOrphanandMove(trainslist, trainslist.get(0) ,a_response))) return true;
            if (traintype == 'M' && (checkOrphanandMove(trainslist,  trainslist.get( 2) ,a_response))) return true;
            if (traintype == 'C' && (checkOrphanandMove(trainslist,  trainslist.get(1) ,a_response))) return true;

            if(boneyard.size()>0){
                String Tileadded=boneyard.get(0).Stringified();
                AddtoBack(boneyard.get(0));
                boneyard.remove(0);
                if(BoneyardtoTrain(trainslist,a_response)){
                   // a_response.append("\nTile was picked from boneyard as no valid tiles were present: " + Tileadded);
                    return true;
                };
                a_response.append("Computer could not play on orphan double train so boneyard tile added to computer pile. Tile added"+ Tileadded);
                trainslist.get(1).MarkTrain();
                return  true;
            }
            else{
                a_response.append("No more tiles left on the list of boneyard tiles");
            }
        }

        //checks if the mexican train is empty if so adds tile if available.
        StringBuilder mytilenumber=new StringBuilder("");
        StringBuilder traintoplay= new StringBuilder("");
        if (StartMexicanTrain(trainslist, mytilenumber, traintoplay)) {
            String numbervalue=mytilenumber.toString();
            tilenumber= Integer.parseInt(numbervalue);
            String tile=  String.valueOf(GetTile(tilenumber-1).GetSide1())+"-"+String.valueOf(GetTile(tilenumber-1).GetSide2());
            a_response.append("Computer played tile "+ tile + "to the Mexican train as it starts the mexican train");
            MoveTiletoTrain(trainslist, tilenumber, traintoplay.toString());
            return true;
        }

        //String builder was used for reusing the code obtained from c++ as references are not valid in primitive types.
        //This allows getting two values back at a time.
        StringBuilder stringtilenumber_1=new StringBuilder("");
        StringBuilder trainname= new StringBuilder("");
        //check if you can force other player to play orphan double train & orphan double cannot be played more than two times.
        if (PlayOrphanDoublemove(trainslist, stringtilenumber_1, trainname,  trainslist.get(0)) && continuedmove < 2) {
            tilenumber=  Integer.parseInt(stringtilenumber_1.toString());
            Tile mytile = GetTiles().get(tilenumber-1);
            traintoplay= new StringBuilder("");
            if ((continuedmove == 0) || (continuedmove == 1 && ValidsecondDouble(trainslist, train, mytile))) {
                String tile=  String.valueOf(GetTile(tilenumber-1).GetSide1())+"-"+String.valueOf(GetTile(tilenumber-1).GetSide2());
                a_response.append("Computer played tile "+ tile + "to the " + trainname.toString() +"Train as it forces opponent to play orphan double train");
                MoveTiletoTrain(trainslist, tilenumber, trainname.toString());
                SetReplay(true);
                return true;
            }

        }


        Integer mexicantile, mexicansum, selftile, selfsum;
        Train mexicantrain, selftrain;
        StringBuilder MexicanTile=new StringBuilder("");
        boolean can_playmexican = PlayMexicanTrain(trainslist, MexicanTile, train);

        StringBuilder SelfTile=new StringBuilder("");
        boolean can_playself = PlaySelfTrain(trainslist, SelfTile, trainslist.get(1));

        // second argument trainslist.get(1) is the train of the Human player as current player is Computer.
        //This is a new object because append is used instead of rewriting the value.
        StringBuilder OpponentTile=new StringBuilder("");
        traintoplay=new StringBuilder("");
        boolean can_playopponent=Canplayopponenttrain(trainslist,  trainslist.get(0), OpponentTile, traintoplay);


        //if orphandouble is possible try no to play self train as long  as possible
        //in ase of orphan train on opponent this is skipped.
        if (continuedmove == 1) {
            OrphanDoublePresent(trainslist);
            traintype=GetOrphanTrain(trainslist);
            //(traintype == 'H' || traintype=='C') && can_playmexican
            if ((traintype == 'H' || traintype=='C') && can_playmexican) {
                tilenumber=  Integer.parseInt(MexicanTile.toString());
                String tile=  String.valueOf(GetTile(tilenumber-1).GetSide1())+"-"+String.valueOf(GetTile(tilenumber-1).GetSide2());
                a_response.append("Computer played tile "+ tile + " to the mexican train as it is the largest possible tile to play and helps for orphan double");
                SetRepeating(tilenumber);
                MoveTiletoTrain(trainslist, tilenumber, "Mexican");
                return true;
            }
            else if((traintype =='C' || traintype=='M') && (can_playopponent && trainslist.get(0).isTrainMarked())  ){
                tilenumber=  Integer.parseInt(OpponentTile.toString()) ;
                String tile=  String.valueOf(GetTile(tilenumber-1).GetSide1())+"-"+String.valueOf(GetTile(tilenumber-1).GetSide2());
                a_response.append("Computer played tile "+ tile + " to the Human train as Human train is marked and helps for orphan double");
                SetRepeating(tilenumber);
                MoveTiletoTrain(trainslist, tilenumber, "Human");
                return true;
            }
            else if ((traintype == 'M'||  traintype=='H') && can_playself) {
                tilenumber=  Integer.parseInt(SelfTile.toString());
                String tile=  String.valueOf(GetTile(tilenumber-1).GetSide1())+"-"+String.valueOf(GetTile(tilenumber-1).GetSide2());
                a_response.append("Computer played tile "+ tile + " to the Computer train as it is the largest possible tile to play and helps for orphan double");
                SetRepeating(tilenumber);
                MoveTiletoTrain(trainslist, tilenumber, "Computer");
                return true;
            }
        }

        if (can_playopponent) {
            tilenumber=  Integer.parseInt(OpponentTile.toString()) ;
            String tile=  String.valueOf(GetTile(tilenumber-1).GetSide1())+"-"+String.valueOf(GetTile(tilenumber-1).GetSide2());
            a_response.append("Computer played tile "+ tile + "to the " + traintoplay.toString() +" as the opponent train has marker");
            SetRepeating(tilenumber);
            MoveTiletoTrain(trainslist, tilenumber, traintoplay.toString());
            return true;
        }

        if (can_playmexican && can_playself) {
            selftile=Integer.parseInt(SelfTile.toString());
            mexicantile=Integer.parseInt(MexicanTile.toString());
            selfsum= GetTiles().get(selftile - 1).GetSide1() + GetTiles().get(selftile - 1).GetSide2();
            mexicansum=GetTiles().get(mexicantile - 1).GetSide1() + GetTiles().get(mexicantile - 1).GetSide2();
            if(selfsum >= mexicansum){
                tilenumber=  Integer.parseInt(SelfTile.toString());
                String tile=  String.valueOf(GetTile(tilenumber-1).GetSide1())+"-"+String.valueOf(GetTile(tilenumber-1).GetSide2());
                a_response.append("Computer played tile "+ tile + " to the Computer train as it is the largest possible tile to play");
                SetRepeating(tilenumber);
                MoveTiletoTrain(trainslist, tilenumber, "Computer");
                return true;
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
                a_response.append("Computer played tile "+ tile + " to the mexican train as it is the largest possible tile to play");
                SetRepeating(tilenumber);
                MoveTiletoTrain(trainslist, tilenumber, "Mexican");
                return true;
            }
        }
        if( (can_playself && !can_playmexican)){
            tilenumber=  Integer.parseInt(SelfTile.toString()) ;
            String tile=  String.valueOf(GetTile(tilenumber-1).GetSide1())+"-"+String.valueOf(GetTile(tilenumber-1).GetSide2());
            a_response.append("Computer played tile "+ tile + " to the Computer train as it is the largest possible tile to play");
            SetRepeating(tilenumber);
            MoveTiletoTrain(trainslist, tilenumber, "Computer");
            return true;
        }

        if(!can_playself && can_playmexican){
            tilenumber= Integer.parseInt(MexicanTile.toString());
            String tile=  String.valueOf(GetTile(tilenumber-1).GetSide1())+"-"+String.valueOf(GetTile(tilenumber-1).GetSide2());
            a_response.append("Computer played tile "+ tile + " to the mexican train as it is the largest possible tile to play");
            SetRepeating(tilenumber);
            MoveTiletoTrain(trainslist, tilenumber, "Mexican");
            return  true;
        }
        else {

            //if tile was present on boneyard and picked try to place on the user train.
            String Tileadded=boneyard.get(0).Stringified();
            if (PickBoneyard(boneyard, trainslist.get(1))) {
                if(boneyard.size()>0){
                    a_response.append("Boneyard tile picked\n");
                    if(BoneyardtoTrain(trainslist, a_response)){
                        return true;
                    }
                    else{
                        a_response.append("Tile:" + Tileadded + " was picked from the boneyard");
                        a_response.append(" and was added to computers list of tiles.Computer train marked!!");

                    }
                }
                return true;
            }
            else {
               // cout << "Boneyard had no tiles. so just train is marked." << endl;
                a_response.append("There are no more boneyard tiles left in the boneyard so Train is marked and the turn is passed.");
            }


            return true;

        }
    }

    /**
     * Computer::BoneyardtoTrain
     * Helps to move a playable boneyard tile to one of the valid trains
     * @return boolean value based on if tile was moved
     * @param a_trainslist list of all the train objects
     * @param a_response response to be sent back to the UI
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean BoneyardtoTrain(Vector<Train> a_trainslist, StringBuilder a_response)
    {
        Tile last_tile = GetTiles().get(GetTiles().size() - 1);
        char train='X';
        if (OrphanDoublePresent(a_trainslist)) {
            train=GetOrphanTrain(a_trainslist);
            if (train == 'H') {
                if (CanPlayinTrain(a_trainslist.get(0))) {
                    int tile_number = GetPlayableTile(a_trainslist.get(0));
                    SetRepeating(tile_number);
                    DisplayTileMove(tile_number, a_trainslist.get(0), "boneyard tile matched with orphan double train",a_response);
                    //a_response.append("Train chosen: Human Train\n");
                    MoveTiletoTrain(a_trainslist, tile_number, "Human");
                    return true;

                }
			else {
                   // DisplayandContinue();
                }
            }
            else if (train == 'C') {
                if (CanPlayinTrain(a_trainslist.get(1))) {
                    int tile_number = GetPlayableTile(a_trainslist.get(1));
                    SetRepeating(tile_number);
                    DisplayTileMove(tile_number,a_trainslist.get(1), "boneyard tile matched with orphan double train", a_response);
                    a_response.append("Train chosen: Computer Train\n");
                    MoveTiletoTrain(a_trainslist, tile_number, "Computer");
                    return true;
                }
			else {
			        //Do nothing
                }
            }
            else if (train == 'M') {
                if (CanPlayinTrain(a_trainslist.get(2))) {
                    int tile_number = GetPlayableTile(a_trainslist.get(2));
                    SetRepeating(tile_number);
                    DisplayTileMove(tile_number,a_trainslist.get(2), "boneyard tile matched with orphan double train", a_response);
                    //a_response.append("Train chosen: Mexican Train\n");
                    MoveTiletoTrain(a_trainslist, tile_number, "Mexican");
                    return true;
                }
			else {
                   // DisplayandContinue();
                }
            }
        }
        else {
            //priority player train(opponent)
            if (a_trainslist.get(0).isTrainMarked() && CanPlayinTrain(a_trainslist.get(0))) {
                int tile_number = GetPlayableTile(a_trainslist.get(0));
                SetRepeating( tile_number);
                DisplayTileMove(tile_number, a_trainslist.get(0), "boneyard tile matched with opponent train", a_response);
                MoveTiletoTrain(a_trainslist, tile_number, "Human");
                return true;

            }
            // than play mexican train
		    else if (CanPlayinTrain(a_trainslist.get(2))) {
                int tile_number = GetPlayableTile(a_trainslist.get(2));
                SetRepeating(tile_number);
                DisplayTileMove(tile_number, a_trainslist.get(2), "boneyard tile matched with mexican train", a_response);
                MoveTiletoTrain(a_trainslist, tile_number, "Mexican");
                return true;
            }
            //play computer train at the end(self train)
		    else if (CanPlayinTrain(a_trainslist.get(1))) {
                int tile_number = GetPlayableTile(a_trainslist.get(1));
                SetRepeating( tile_number);
                DisplayTileMove(tile_number, a_trainslist.get(1), "boneyard tile matched with Computer train", a_response);
                MoveTiletoTrain(a_trainslist, tile_number, "Computer");
                return true;
            }
		    else {
		        return false;
            }

        }
        return false;
    }
    /**
     * Computer::CheckOrphanandMove
     * Checks if there is orphan train to be played before playing anything else
     * @return boolean value based on the Orphan train played status
     * @param a_response response to be sent back to UI
     * @param a_train  train where a move is made
     * @param a_trainslist list of all valid train objects
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean checkOrphanandMove(Vector<Train>  a_trainslist,Train  a_train, StringBuilder a_response)
    {

        if (CanPlayinTrain(a_train)) {

            int tilenumber = GetPlayableTile(a_train);
            String Tile=GetTile(tilenumber-1).Stringified();
            a_response.append("Tile: " + Tile+ " moved to the " + a_train.trainType()+ " Train as it is the orphan double train");
            //DisplayTileMove(tilenumber, a_train, "it was the orphan double train");
            MoveTiletoTrain(a_trainslist, tilenumber,a_train.trainType());
            return true;
        }
        return false;
    }
    /**
     * Computer::DisplayTileMove
     * Helps to format the response to be sent back to the UO
     * @return void:: but response returned by passing reference
     * @param a_goal reason why move is made
     * @param a_train train where move is made
     * @param a_response response to be returned
     * @param a_tilenumber the tile number that is played
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void DisplayTileMove(int a_tilenumber, Train a_train, String a_goal,StringBuilder a_response)
    {
        String Tile=GetTile(a_tilenumber-1).Stringified();
        a_response.append("Tile: "+ Tile+  " was picked from boneyard and played to the " + a_train.trainType()+ " Train as " + a_goal);

    }
    /**
     * Computer::SetRepeating
     * Checks if the same player get two turns
     * @param a_tilenumber tile number of the tile that is played in the current term.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void SetRepeating( int a_tilenumber)
    {
        if(GetTiles().size()>0){
            if (GetTiles().get(a_tilenumber - 1).GetSide1() == GetTiles().get(a_tilenumber - 1).GetSide2()) {
                SetReplay(true);
            }
            return;
        }
    }
    /**
     * Computer::MoveTiletoTrain
     * Tries to move given tile to the given Train
     * @param a_tilenumber tilenumber of the tile to be played
     * @param a_train Train where the tile is to be played
     * @param a_trainslist list of the train objects.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void MoveTiletoTrain(Vector<Train> a_trainslist, int a_tilenumber, String a_train)
    {
        Tile tiletoadd = GetTiles().get(a_tilenumber - 1);

        //here train type is checked because we cannot modify on the copy of the train object.
        if (a_train.equals("Human")) {
            CheckTrainMove(a_trainslist.get(0), tiletoadd, a_tilenumber);

        }
        else if (a_train.equals("Computer")) {
            CheckTrainMove(a_trainslist.get(1), tiletoadd, a_tilenumber);
            //since computer player is the only player using this function removing mark from here works.
            a_trainslist.get(1).RemoveMark();

        }
        else if (a_train.equals("Mexican")) {
            CheckTrainMove(a_trainslist.get(2), tiletoadd, a_tilenumber);
        }

    }



}
