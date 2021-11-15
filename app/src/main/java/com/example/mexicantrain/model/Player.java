package com.example.mexicantrain.model;

import java.util.Vector;

public class Player {
    private Vector<Tile> m_tileslist=new Vector<>();
    //this checks if the game should be quit if the user serialized the game
    private boolean m_quitgame=false;
    //this checks if the valid tile was played
    private boolean m_validtileplayed=false;
    private boolean m_replay=false;
    private int m_continousturns=0;

    public int GetContinousturns(){
        return m_continousturns;
    }
    public void SetContinousturns(int value){
        m_continousturns=value;
    }
    public void SetQuitGame(boolean value){
        m_quitgame=value;
    }
    public void SetValidTile(boolean value){
        m_validtileplayed=value;
    }
    public void SetReplay(boolean value){
        m_replay=value;
    }

    public boolean Getquitgame() {
        return m_quitgame;
    }

    public boolean Isvalidtileplayed() {
        return m_validtileplayed;
    }

    public boolean Getreplay() {
        return m_replay;
    }

    public Player(){

    }
    public  Player(Vector<Tile> a_tiles)
    {
        m_tileslist=a_tiles;
    }


    public void SetTiles(Vector<Tile> a_tiles){
        m_tileslist=a_tiles;
    }
    public Vector<Tile> GetTiles(){
        return m_tileslist;
    }

    public void AddtoBack(Tile a_tile) {
        m_tileslist.add(a_tile);
    }
    //this helps to return tile at a specific position
    public Tile GetTile(int a_position) {
        //this verifies user asks for tile within range
        //else a -1,-1 tile is sent back
        if (a_position > m_tileslist.size()) {
            Tile randomTile=new Tile();
            return randomTile;
        }
        return m_tileslist.get(a_position);
    }

    public boolean PlayMove(Vector <Train> trainslist, Vector<Tile> boneyard, int continuedmove, int a_tileposition, Round.Playtype a_playtype,StringBuilder message)
    {
        System.out.println("@@ this runs");
        //try to return array of booleans here as
        //This move is made for both players
        //this function doesnot do anything and is a virtual function.

        return false;
    }
    public boolean CheckTrainMove(Train a_Train, Tile a_tile, int a_tilenumber )
    {
        //moving a user chosen tile to a Player train
        int x= a_Train.GetAllTiles().lastElement().GetSide2();
        if (a_tile.GetSide1() == a_Train.GetAllTiles().lastElement().GetSide2() || a_tile.GetSide2() == a_Train.GetAllTiles().lastElement().GetSide2())
        {

            //if this is a double tile next tile is flipped accordingly
            if (a_Train.GetAllTiles().lastElement().GetSide1() == a_Train.GetAllTiles().lastElement().GetSide2()  &&
                    (a_tile.GetSide1() != a_Train.GetAllTiles().lastElement().GetSide2()))
            {
                a_tile.Filpside();
            }
            //two consecutive tiles are flipped in order to set matching number to each other.
            else if ((a_Train.GetAllTiles().lastElement().GetSide1() != a_Train.GetAllTiles().lastElement().GetSide2()) &&
                    a_tile.GetSide1() == a_Train.GetAllTiles().lastElement().GetSide1() || a_tile.GetSide2() == a_Train.GetAllTiles().lastElement().GetSide2())
            {
                a_tile.Filpside();
            }
            //adds tile to the Train
            a_Train.Addtile(a_tile);
            //removes tile from the user or computer's tile pile.
            RemoveTile(a_tilenumber - 1);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void RemoveTile(int a_position)
    {
        //this makes sure random position doesnot break the code.
        if (a_position > m_tileslist.size()) {
            return;
        }
        m_tileslist.remove(a_position);
    }

    public boolean OrphanDoublePresent(Vector<Train> a_trainslist)
    {

        if ( (a_trainslist.get(0).GetAllTiles().size())>1 && (a_trainslist.get(0).GetTop().GetSide1() == a_trainslist.get(0).GetTop().GetSide2())) {
            //a_train= 'U';

            return true;
        }

	    else if ((a_trainslist.get(1).GetAllTiles().size())>1 && (a_trainslist.get(1).GetTop().GetSide1() == a_trainslist.get(1).GetTop().GetSide2())) {
            //a_train= 'C';
            int a=a_trainslist.get(1).GetTop().GetSide1();
            int b=a_trainslist.get(1).GetTop().GetSide2();
            return true;
        }
	    else if ((a_trainslist.get(2).GetAllTiles().size())>1 && (a_trainslist.get(2).GetTop().GetSide1() == a_trainslist.get(2).GetTop().GetSide2())) {
            //a_train= 'M';
            return true;
        }
        int v=a_trainslist.get(2).GetTop().GetSide1();
        int m=a_trainslist.get(2).GetTop().GetSide2();
        return false;
    }


    public boolean IsorphanTrain(Train a_train){
        if ( (a_train.GetAllTiles().size())>1 && (a_train.GetTop().GetSide1() == a_train.GetTop().GetSide2())) {
            return true;
            //return true;
        }
        else {
            return false;
        }
    }
    public char GetOrphanTrain (Vector<Train> a_trainslist)
    {

        if ( (a_trainslist.get(0).GetAllTiles().size())>1 && (a_trainslist.get(0).GetTop().GetSide1() == a_trainslist.get(0).GetTop().GetSide2())) {
            return 'H';
            //return true;
        }
        else if ((a_trainslist.get(1).GetAllTiles().size())>1 && (a_trainslist.get(1).GetTop().GetSide1() == a_trainslist.get(1).GetTop().GetSide2())) {
            return 'C';
            //return true;
        }
        else if ((a_trainslist.get(2).GetAllTiles().size())>1 && (a_trainslist.get(2).GetTop().GetSide1() == a_trainslist.get(2).GetTop().GetSide2())) {
            return 'M';
            //return true;
        }
        return 'X';
    }

    public boolean PickBoneyard(Vector<Tile> a_boneyard, Train a_train)
    {

        //if there is more tiles left
        if (a_boneyard.size() > 0) {
            Tile boneyardfront = a_boneyard.get(0);
            a_boneyard.remove(0);
            AddtoBack(boneyardfront);
            a_train.MarkTrain();
            return true;
        }
        // if no tiles left simply mark  the train and pass the turn
        else {
            a_train.MarkTrain();
            return false;
        }

    }

    public boolean ValidsecondDouble(Vector<Train> a_trainslist, Train a_chosentrain, Tile a_newtile)
    {
        Tile usertrainTop, computertrainTop, mexicantrainTop;
        if (a_chosentrain.trainType() == "Human") {

            usertrainTop = a_newtile;
            computertrainTop = a_trainslist.get(1).GetTop();
            mexicantrainTop = a_trainslist.get(2).GetTop();
        }
        else if (a_chosentrain.trainType() == "Computer") {

            usertrainTop = a_trainslist.get(0).GetTop();
            computertrainTop = a_newtile;
            mexicantrainTop = a_trainslist.get(2).GetTop();
        }
        else {
            usertrainTop = a_trainslist.get(0).GetTop();
            computertrainTop = a_trainslist.get(1).GetTop();
            mexicantrainTop = a_newtile;
        }

        //checking players have a valid single tile to play after second double.
        for (int i=0;i<GetTiles().size();i++) {
            if ((GetTiles().get(i).GetSide1() == usertrainTop.GetSide2() || GetTiles().get(i).GetSide2() == usertrainTop.GetSide2()) && GetTiles().get(i).GetSide1() != GetTiles().get(i).GetSide2()) {
                return true;
            }
        }
        if (a_trainslist.get(1).isTrainMarked()) {
            for (int i=0;i<GetTiles().size();i++) {
                if ((GetTiles().get(i).GetSide1() == computertrainTop.GetSide2() || GetTiles().get(i).GetSide2() == computertrainTop.GetSide2()) && GetTiles().get(i).GetSide1() != GetTiles().get(i).GetSide2()) {
                    return true;
                }
            }
        }
        for (int i=0;i<GetTiles().size();i++) {
            if ((GetTiles().get(i).GetSide1() == mexicantrainTop.GetSide2() || GetTiles().get(i).GetSide2() == mexicantrainTop.GetSide2()) && GetTiles().get(i).GetSide1() != GetTiles().get(i).GetSide2()) {
                return true;
            }
        }

        return false;


    }
    public void BoneyardtoTrain(Vector <Train> a_trainslist, boolean a_replay, boolean a_validtile)
    {
        //this is a virtual function that has no work for player class.

    }

    public boolean CanPlayinTrain(Train a_train) {

        //tile where a new tile is attached to the train.
        Tile toptile = a_train.GetTop();

        for (int i = 0; i < GetTiles().size(); i++) {
            if (GetTiles().get(i).GetSide1() == toptile.GetSide2() || GetTiles().get(i).GetSide2() == toptile.GetSide2()) {
                return true;
            }
        }

        return false;
    }

    public boolean Canplaytile(Vector <Train> trainslist, Tile tile, int continuedmove){
        if (OrphanDoublePresent(trainslist) && continuedmove==0) {
           // char a_train=GetOrphanTrain(trainslist);
          //  SetValidTile(true);
            if (IsorphanTrain(trainslist.get(0)) && CanPlaytiletoTrain(tile, trainslist.get(0))) {
                return true;
            }
            else if (IsorphanTrain(trainslist.get(1)) && CanPlaytiletoTrain(tile, trainslist.get(1))) {
                return true;
            }
            else if (IsorphanTrain(trainslist.get(2)) && CanPlaytiletoTrain(tile, trainslist.get(2))) {
                return true;
            }
            else{
                return false;
            }

        }
        else if(CanPlaytiletoTrain(tile, trainslist.get(0))){
            return true;
        }
        else if(CanPlaytiletoTrain(tile, trainslist.get(2))){
            return true;
        }
        else if((trainslist.get(1).isTrainMarked()==true) && (CanPlaytiletoTrain(tile, trainslist.get(1)))) {
            return true;
        }
        return false;
    }




    public boolean CanPlaytiletoTrain(Tile a_tiletoplay, Train a_train) {

        //tile where a new tile is attached to the train.
        Tile toptile = a_train.GetTop();

        if (a_tiletoplay.GetSide1() == toptile.GetSide2() || a_tiletoplay.GetSide2() == toptile.GetSide2()) {
                return true;
        }
        return false;
    }

    public boolean CanPlayDouble(Train a_train)
    {
        //tile where a new tile is attached to the train.
        Tile toptile = a_train.GetTop();
        for (int i = 0; i < GetTiles().size(); i++) {
            if ((GetTiles().get(i).GetSide1() == toptile.GetSide2() || GetTiles().get(i).GetSide2() == toptile.GetSide2())
                    && (GetTiles().get(i).GetSide1()== GetTiles().get(i).GetSide2())) {

                return true;
            }
        }
        return false;
    }
    public boolean CanPlayNonDouble(Train a_train)
    {
        //tile where a new tile is attached to the train.
        Tile toptile = a_train.GetTop();
        for (int i = 0; i < GetTiles().size(); i++) {
            if ((GetTiles().get(i).GetSide1() == toptile.GetSide2() || GetTiles().get(i).GetSide2() == toptile.GetSide2())
                    && (GetTiles().get(i).GetSide1() != GetTiles().get(i).GetSide2())) {

                return true;
            }
        }
        return false;
    }
    public int GetPlayableTile(Train a_train)
    {
        //tile where a new tile is attached to the train.
        Tile toptile = a_train.GetTop();
        int tile_return = -1;
        for (int i = 0; i < GetTiles().size(); i++) {
            if (GetTiles().get(i).GetSide1() == toptile.GetSide2() || GetTiles().get(i).GetSide2() == toptile.GetSide2()) {

                if (tile_return > -1) {
                    if (Getsum(i) > Getsum(tile_return)) {
                        tile_return = i;
                    }
                }
                else {
                    tile_return = i;
                }


            }
        }
        return tile_return+1;
    }


    public int GetPlayableDouble(Train a_train)
    {
        //tile where a new tile is attached to the train.
        Tile toptile = a_train.GetTop();
        for (int i = 0; i < GetTiles().size(); i++) {
            if ((GetTiles().get(i).GetSide1() == toptile.GetSide2() || GetTiles().get(i).GetSide2() == toptile.GetSide2())
                    && (GetTiles().get(i).GetSide1() == GetTiles().get(i).GetSide2())) {
                return i+1;
            }
        }
        return -1;
    }
    public boolean StartMexicanTrain(Vector <Train> a_trainslist, StringBuilder a_tilenumber, StringBuilder a_train)
    {
        if (a_trainslist.get(2).Size() == 1 && CanPlayinTrain(a_trainslist.get(2))) {
            a_tilenumber.append(GetPlayableTile(a_trainslist.get(2)));
            a_train.append("Mexican");
            return true;
        }
        else {
            return false;
        }

    }

    //this function passes object by reference
    public boolean Canplayopponenttrain( Vector <Train> a_trainslist, Train a_opponentTrain, StringBuilder a_tilenumber, StringBuilder  a_train )
    {

        //opponent train is just a copy and not reference as it is used for searching a double tile and not modyfying it.
        if (a_opponentTrain.isTrainMarked()) {
            if (CanPlayDouble(a_opponentTrain)) {
                a_tilenumber.append(GetPlayableDouble(a_opponentTrain));
                a_train.append(a_opponentTrain.trainType());
                return true;

            }
            else if (CanPlayinTrain(a_opponentTrain)) {
                a_tilenumber.append(GetPlayableTile(a_opponentTrain));
                a_train.append(a_opponentTrain.trainType());
                return true;
            }
        }
        return false;
    }


    public boolean PlayOrphanDoublemove(Vector<Train> a_trainslist, StringBuilder a_tilenumber, StringBuilder a_train, Train a_opponentTrain)
    {
        //helps to decide if the opponent train is playable or not.
        boolean opponentplayable = a_opponentTrain.isTrainMarked();
        String opponenttrain = a_opponentTrain.trainType();
        if (opponentplayable) {
            //should be able to play double on the user train and non double on any other train.
            if (CanPlayDouble(a_trainslist.get(0))) {
                if (CanPlayNonDouble(a_trainslist.get(1))|| CanPlayNonDouble(a_trainslist.get(0)) || CanPlayNonDouble(a_trainslist.get(2))) {
                    a_tilenumber.append(GetPlayableDouble(a_trainslist.get(0)));
                    a_train.append("Human");
                    return true;
                }
            }
		    else if (CanPlayDouble(a_trainslist.get(1))) {
                if (CanPlayNonDouble(a_trainslist.get(0)) ||CanPlayNonDouble(a_trainslist.get(1))||  CanPlayNonDouble(a_trainslist.get(2))) {
                    a_tilenumber.append(GetPlayableDouble(a_trainslist.get(1)));
                    a_train.append("Computer");
                    return true;
                }
            }
		    else if (CanPlayDouble(a_trainslist.get(2))) {
                if (CanPlayNonDouble(a_trainslist.get(0)) ||CanPlayNonDouble(a_trainslist.get(2)) || CanPlayNonDouble(a_trainslist.get(1))) {
                    a_tilenumber.append(GetPlayableDouble(a_trainslist.get(2)));
                    a_train.append("Mexican");
                    return true;
                }
            }
		    else {
                return false;
            }
        }
        else {
            if (a_opponentTrain.trainType().equals("Computer")) {

                if (CanPlayDouble(a_trainslist.get(0))  && (CanPlayNonDouble(a_trainslist.get(2))  || CanPlayNonDouble(a_trainslist.get(0))))
                {
                    a_tilenumber.append(GetPlayableDouble(a_trainslist.get(0)));
                    a_train.append("Human");
                    return true;
                }
			    else if (CanPlayDouble(a_trainslist.get(2))  && ( CanPlayNonDouble(a_trainslist.get(0)) || CanPlayNonDouble(a_trainslist.get(2))))
			    {
                    a_tilenumber.append(GetPlayableDouble(a_trainslist.get(2)));
                    a_train.append("Mexican");
                    return true;
                }

            }
            else if (a_opponentTrain.trainType().equals("Human")) {
                if (CanPlayDouble(a_trainslist.get(1)) && (CanPlayNonDouble(a_trainslist.get(2)) || CanPlayNonDouble(a_trainslist.get(1))))
                {
                    a_tilenumber.append(GetPlayableDouble(a_trainslist.get(1)));
                    a_train.append("Computer");
                    return true;
                }
			    else if (CanPlayDouble(a_trainslist.get(2)) && (CanPlayNonDouble(a_trainslist.get(1)) || CanPlayNonDouble(a_trainslist.get(2)) )  )
			    {
                    a_tilenumber.append(GetPlayableDouble(a_trainslist.get(2)));
                    a_train.append("Mexican");
                    return true;
                }
            }
            else {
                return false;
            }
        }
        return false;

    }

    public boolean PlayMexicanTrain(Vector <Train>  a_trainslist, StringBuilder a_tilenumber, Train a_train)
    {
        if (CanPlayinTrain(a_trainslist.get(2))) {
            a_tilenumber.append(GetPlayableTile(a_trainslist.get(2)));
            a_train = a_trainslist.get(2);
            return true;
        }
        return false;
    }
    public boolean PlayOpponentTrain(Vector <Train>  a_trainslist, StringBuilder a_tilenumber, Train a_opponenttrain)
    {
        if (CanPlayinTrain(a_opponenttrain)) {
            a_tilenumber.append(GetPlayableTile(a_opponenttrain));
            //a_train = a_trainslist.get(2);
            return true;
        }
        return false;
    }
    public boolean PlaySelfTrain(Vector <Train> a_trainslist, StringBuilder a_tilenumber, Train a_selftrain)
    {
        if (CanPlayinTrain(a_selftrain)) {
            a_tilenumber.append(GetPlayableTile(a_selftrain));
            return true;
        }
        return false;
    }

    public int Getsum(int a_tilenumber)
    {
        if (a_tilenumber > GetTiles().size()) {
            return 0;
        }
        Tile tile = GetTiles().get(a_tilenumber);
        return tile.GetSide1() + tile.GetSide2();
    }

}

