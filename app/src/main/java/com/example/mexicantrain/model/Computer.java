package com.example.mexicantrain.model;

import java.util.Vector;

public class Computer extends Player {


    public Computer(){

    }

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
            if (traintype == 'U' && (checkOrphanandMove(trainslist, trainslist.get(0) ,a_response))) return true;
            if (traintype == 'M' && (checkOrphanandMove(trainslist,  trainslist.get( 2) ,a_response))) return true;
            if (traintype == 'C' && (checkOrphanandMove(trainslist,  trainslist.get(1) ,a_response))) return true;


            if(boneyard.size()>0){
                String Tileadded=boneyard.get(0).Stringified();
                a_response.append(" Placed a boneyard tile to computer tiles list in this case. Tile added"+ Tileadded);
                AddtoBack(boneyard.get(0));
                boneyard.remove(0);
                return  true;
            }
            else{
                System.out.println("Out of index error");
            }
        }

        //checks if the mexican train is empty if so adds tile if available.
        StringBuilder mytilenumber=new StringBuilder("");
        StringBuilder traintoplay= new StringBuilder("");
        if (StartMexicanTrain(trainslist, mytilenumber, traintoplay)) {
            String numbervalue=mytilenumber.toString();
            tilenumber= Integer.parseInt(numbervalue);
            String tile=  String.valueOf(GetTile(tilenumber-1).GetSide1())+"-"+String.valueOf(GetTile(tilenumber-1).GetSide2());
            a_response.append("Played tile "+ tile + "to the Mexican train as it starts the mexican train");
            MoveTiletoTrain(trainslist, tilenumber, traintoplay.toString());
            return true;
        }

        //String builder was used for reusing the code obtained from c++ as references are not valid in primitive types.
        //This allows getting two values back at a time.
        StringBuilder stringtilenumber_1=new StringBuilder("");
        StringBuilder trainname= new StringBuilder("");
        //check if you can force other player to play orphan double train & orphan double cannot be played more than two times.
        if (PlayOrphanDoublemove(trainslist, stringtilenumber_1, trainname,  trainslist.get(1)) && continuedmove < 2) {
            tilenumber=  Integer.parseInt(stringtilenumber_1.toString());
            Tile mytile = GetTiles().get(tilenumber);
            traintoplay= new StringBuilder("");
            if ((continuedmove == 0) || (continuedmove == 1 && ValidsecondDouble(trainslist, train, mytile))) {
                String tile=  String.valueOf(GetTile(tilenumber-1).GetSide1())+"-"+String.valueOf(GetTile(tilenumber-1).GetSide2());
                a_response.append("Play tile "+ tile + "to the " + trainname.toString() +"it forces opponent to play orphan double train");
                MoveTiletoTrain(trainslist, tilenumber, trainname.toString());
                return true;
            }
        }

        // second argument trainslist.get(1) is the train of the opponent player as current player is computer.
        //This is a new object because append is used instead of rewriting the value.
        StringBuilder stringtilenumber_2=new StringBuilder("");
        traintoplay= new StringBuilder("");
        if (Playopponenttrain(trainslist,  trainslist.get(1), stringtilenumber_2, traintoplay)) {
            tilenumber=  Integer.parseInt(stringtilenumber_2.toString()) - 1;
            String tile=  String.valueOf(GetTile(tilenumber).GetSide1())+"-"+String.valueOf(GetTile(tilenumber).GetSide2());
            a_response.append("Play tile "+ tile + "to the " + traintoplay.toString() +"as the opponent train has marker");
            MoveTiletoTrain(trainslist, tilenumber, traintoplay.toString());
            return true;
        }


        Integer mexicantile, mexicansum, selftile, selfsum;
        Train mexicantrain, selftrain;
        StringBuilder stringtilenumber_3=new StringBuilder("");
        boolean can_playmexican = PlayMexicanTrain(trainslist, stringtilenumber_3, train);
        if (can_playmexican) {
            mexicantile = Integer.parseInt(stringtilenumber_3.toString());
            mexicansum = GetTiles().get(mexicantile - 1).GetSide1() + GetTiles().get(mexicantile - 1).GetSide2();
            mexicantrain = train;
        }
        StringBuilder stringtilenumber_4=new StringBuilder("");
        boolean can_playself = PlaySelfTrain(trainslist, stringtilenumber_4, trainslist.get(0));
        if (can_playself) {
            selftile = Integer.parseInt(stringtilenumber_4.toString());
            selfsum = GetTiles().get(selftile - 1).GetSide1() + GetTiles().get(selftile - 1).GetSide2();
            selftrain = trainslist.get(0);
        }

        //if orphandouble is possible try no to play self train as long  as possible
        //in case of orphan train on opponent this is skipped.
        if (continuedmove == 1) {
            OrphanDoublePresent(trainslist);
            traintype=GetOrphanTrain(trainslist);
            if (traintype == 'H' && can_playmexican) {
                tilenumber=  Integer.parseInt(stringtilenumber_3.toString()) -1;
                String tile=  String.valueOf(GetTile(tilenumber).GetSide1())+"-"+String.valueOf(GetTile(tilenumber).GetSide2());
                a_response.append("Play tile "+ tile + " to the mexican train as it is the largest possible tile to play and helps for orphan double");
                MoveTiletoTrain(trainslist, tilenumber, "Mexican");
                return true;

            }
            else if (traintype == 'M' && can_playself) {
                tilenumber=  Integer.parseInt(stringtilenumber_4.toString())-1 ;
                String tile=  String.valueOf(GetTile(tilenumber).GetSide1())+"-"+String.valueOf(GetTile(tilenumber).GetSide2());
                a_response.append("Play tile "+ tile + " to the human train as it is the largest possible tile to play and helps for orphan double");
                MoveTiletoTrain(trainslist, tilenumber, "Human");
                return true;
            }
        }

        if (can_playmexican && can_playself) {
            selftile=Integer.parseInt(stringtilenumber_4.toString());
            mexicantile=Integer.parseInt(stringtilenumber_3.toString()) -1;
            selfsum= GetTiles().get(selftile - 1).GetSide1() + GetTiles().get(selftile - 1).GetSide2();
            mexicansum=GetTiles().get(mexicantile - 1).GetSide1() + GetTiles().get(mexicantile - 1).GetSide2();
            if(selfsum >= mexicansum){
                tilenumber=  Integer.parseInt(stringtilenumber_4.toString())-1;
                String tile=  String.valueOf(GetTile(tilenumber).GetSide1())+"-"+String.valueOf(GetTile(tilenumber).GetSide2());
                a_response.append("Play tile "+ tile + " to the Computer train as it is the largest possible tile to play");
                MoveTiletoTrain(trainslist, tilenumber, "Computer");
                return true;
            }
        }
        if( (can_playself && !can_playmexican)){
            tilenumber=  Integer.parseInt(stringtilenumber_4.toString()) -1;
            String tile=  String.valueOf(GetTile(tilenumber).GetSide1())+"-"+String.valueOf(GetTile(tilenumber).GetSide2());
            a_response.append("Play tile "+ tile + " to the Computer train as it is the largest possible tile to play");
            MoveTiletoTrain(trainslist, tilenumber, "Computer");
            return true;
        }
        if (can_playmexican && can_playself)   {
            selftile=Integer.parseInt(stringtilenumber_4.toString());
            mexicantile=Integer.parseInt(stringtilenumber_3.toString()) ;
            selfsum= GetTiles().get(selftile - 1).GetSide1() + GetTiles().get(selftile - 1).GetSide2();
            mexicansum=GetTiles().get(mexicantile - 1).GetSide1() + GetTiles().get(mexicantile - 1).GetSide2();
            if(selfsum < mexicansum){
                tilenumber= Integer.parseInt(stringtilenumber_3.toString());
                String tile=  String.valueOf(GetTile(tilenumber).GetSide1())+"-"+String.valueOf(GetTile(tilenumber).GetSide2());
                a_response.append("Play tile "+ tile + " to the mexican train as it is the largest possible tile to play");
                MoveTiletoTrain(trainslist, tilenumber, "Mexican");
                return true;
            }
        }

        if(!can_playself && can_playmexican){
            tilenumber= Integer.parseInt(stringtilenumber_3.toString()) -1;
            String tile=  String.valueOf(GetTile(tilenumber).GetSide1())+"-"+String.valueOf(GetTile(tilenumber).GetSide2());
            a_response.append("Play tile "+ tile + " to the mexican train as it is the largest possible tile to play");
            MoveTiletoTrain(trainslist, tilenumber, "Mexican");
            return  true;
        }
        else {

            a_response.append("You donot have any valid tiles to play. So pick a tile from boneyard to continue");
            return true;

        }
    }


    public void BoneyardtoTrain(Vector<Train> a_trainslist, boolean a_replay)
    {
        Tile last_tile = GetTiles().get(GetTiles().size() - 1);
        char train='X';
        if (OrphanDoublePresent(a_trainslist)) {
            train=GetOrphanTrain(a_trainslist);
            if (train == 'H') {
                if (CanPlayinTrain(a_trainslist.get(0))) {
                    int tile_number = GetPlayableTile(a_trainslist.get(0));
                    SetRepeating(a_replay, tile_number);
                    DisplayTileMove(tile_number, a_trainslist.get(0), "boneyard tile matched with orphan double train");
                    MoveTiletoTrain(a_trainslist, tile_number, "Human");
                    return;

                }
			else {
                   // DisplayandContinue();
                }
            }
            else if (train == 'C') {
                if (CanPlayinTrain(a_trainslist.get(1))) {
                    int tile_number = GetPlayableTile(a_trainslist.get(1));
                    SetRepeating(a_replay, tile_number);
                    DisplayTileMove(tile_number,a_trainslist.get(1), "boneyard tile matched with orphan double train");
                    MoveTiletoTrain(a_trainslist, tile_number, "Computer");
                    return;
                }
			else {
                    //DisplayandContinue();
                }
            }
            else if (train == 'M') {
                if (CanPlayinTrain(a_trainslist.get(2))) {
                    int tile_number = GetPlayableTile(a_trainslist.get(2));
                    SetRepeating(a_replay, tile_number);
                    DisplayTileMove(tile_number,a_trainslist.get(2), "boneyard tile matched with orphan double train");
                    MoveTiletoTrain(a_trainslist, tile_number, "Mexican");
                    return;
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
                SetRepeating(a_replay, tile_number);
                DisplayTileMove(tile_number, a_trainslist.get(0), "boneyard tile matched with opponent train");
                MoveTiletoTrain(a_trainslist, tile_number, "Human");
                return;

            }
            // than play mexican train
		else if (CanPlayinTrain(a_trainslist.get(2))) {
                int tile_number = GetPlayableTile(a_trainslist.get(2));
                SetRepeating(a_replay, tile_number);
                DisplayTileMove(tile_number, a_trainslist.get(2), "boneyard tile matched with mexican train");
                MoveTiletoTrain(a_trainslist, tile_number, "Mexican");
                return;
            }
            //play computer train at the end(self train)
		else if (CanPlayinTrain(a_trainslist.get(1))) {
                int tile_number = GetPlayableTile(a_trainslist.get(1));
                SetRepeating(a_replay, tile_number);
                DisplayTileMove(tile_number, a_trainslist.get(1), "boneyard tile matched with self train");
                MoveTiletoTrain(a_trainslist, tile_number, "Computer");
                return;
            }
		else {
                //cout << "The boneyard train doesnot match any train so could not be placed anywhere!" << endl;
               // DisplayandContinue();
            }
        }
    }

    public boolean checkOrphanandMove(Vector<Train>  a_trainslist,Train  a_train, StringBuilder a_response)
    {

        if (CanPlayinTrain(a_train)) {

            int tilenumber = GetPlayableTile(a_train);
            a_response.append("Tile moved to orphan double train");
            //DisplayTileMove(tilenumber, a_train, "it was the orphan double train");
            MoveTiletoTrain(a_trainslist, tilenumber,a_train.trainType());
            return true;
        }
        return false;
    }

    public void DisplayTileMove(int a_tilenumber, Train a_train, String a_goal)
    {
        //String tile = to_string(GetPlayerTiles().at(a_tilenumber - 1).GetSide1()) + '-' + to_string(GetPlayerTiles().at(a_tilenumber - 1).GetSide2());
        //string trainname = a_train.trainType();
        //cout << "Tile: " << tile << " was moved to the " << trainname << " as " << a_goal << endl;
        //cout << "---------------------------------------------------------------------------------" << endl;
        //cout << "Enter y to continue>>";
        //string dummyinput;
        //cin >> dummyinput;


    }

    public void SetRepeating(boolean a_replay, int a_tilenumber)
    {
        if (GetTiles().get(a_tilenumber - 1).GetSide1() == GetTiles().get(a_tilenumber - 1).GetSide2()) {
            a_replay = true;
        }
        return;
    }

    public void MoveTiletoTrain(Vector<Train> a_trainslist, int a_tilenumber, String a_train)
    {
        Tile tiletoadd = GetTiles().get(a_tilenumber - 1);
        String trn= a_train ;
        String ap=trn;
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
        else {
            //String trn= a_train.trainType() ;
            //String ap=trn;
        }
    }



}
