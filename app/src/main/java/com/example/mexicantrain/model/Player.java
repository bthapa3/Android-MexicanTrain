package com.example.mexicantrain.model;

import java.util.Vector;
/*
 ************************************************************
 * Name:  Bishal Thapa									   *
 * Project:  Project 3 Mexican Train Android Java				       *
 * Class:  CMPS366 OPL				                       *
 * Date:  11/17/2020				                           *
 ************************************************************
 */
public class Player {


    /**
     * Player::Player()
     * Default constructor for the player class
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Player(){ }
    /**
     * Player::Player()
     * Constructor for the player class
     * @param a_tiles tiles allocated to the player
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public  Player(Vector<Tile> a_tiles)
    {
        m_tileslist=a_tiles;
    }
    /**
     * Player::GetContinousturns()
     * Gets the number of turns played before.
     * @return int value that represents the number of turns played
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public int GetContinousturns(){
        return m_continousturns;
    }
    /**
     * Player::SetContinousturns()
     * Sets the number of turns played before continously
     * @param value which is the number of turns played before.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void SetContinousturns(int value){
        m_continousturns=value;
    }
    /**
     * Player::SetQuitGame()
     * Sets the value to true if user wants to quit the game
     * @param value boolean that helps to quit game
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void SetQuitGame(boolean value){
        m_quitgame=value;
    }
    /**
     * Player:SetValidTile()
     * Helps to make sure a turn was played with a valid move
     * @param value that denoted if a turn was played successfully.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void SetValidTile(boolean value){
        m_validtileplayed=value;
    }
    /**
     * Player::SetReplay()
     * Helps to replay a double turn
     * @param value boolean that denoted if a player gets a double turn.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void SetReplay(boolean value){
        m_replay=value;
    }
    /**
     * Player::Getquitgame()
     * Checks if user wants to quit a game
     * @return boolean value that states if the user wants to quit a game.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean Getquitgame() {
        return m_quitgame;
    }
    /**
     * Player::Isvalidtileplayed()
     * Checks if the move is complete
     * @return boolean value based on whether move was completed successfully.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean Isvalidtileplayed() {
        return m_validtileplayed;
    }
    /**
     * Player::Getreplay()
     * Checks if the player gets a double turn
     * @return boolean value based on whether game player gets another chance to play.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean Getreplay() {
        return m_replay;
    }

    /**
     * Player::SetTiles()
     * assigns tiles to the player tiles
     * @param a_tiles new set of vector of tiles to be assigned to players list of tiles.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void SetTiles(Vector<Tile> a_tiles){
        m_tileslist=a_tiles;
    }
    /**
     * Player::GetTiles()
     * Gets all of the player tiles
     * @return Vector of all the tiles of the player.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Vector<Tile> GetTiles(){
        return m_tileslist;
    }
    /**
     * Player::AddtoBack()
     * Add a tile to players list
     * @param  a_tile The tile to be added.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void AddtoBack(Tile a_tile) {
        m_tileslist.add(a_tile);
    }
    /**
     * Player::GetTile
     * Get a tile from players list
     * @param  a_position int value of the tile that represents the position in vector of tiles.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Tile GetTile(int a_position) {
        //this verifies user asks for tile within range
        //else a -1,-1 tile is sent back
        if (a_position > m_tileslist.size()) {
            Tile randomTile=new Tile();
            return randomTile;
        }
        return m_tileslist.get(a_position);
    }
    /**
     * Player::PlayMove
     * Polymorphic function that is used to allow a player to make a move.
     * @param trainslist all the trains objects
     * @param boneyard boneyard tiles list
     * @param continuedmove  number of turns played before
     * @param a_tileposition position of the tile played
     * @param a_playtype enum value that represents the type of move made
     * @param message response returned to the frontend.
     * @return boolean value based on whether a move was made
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean PlayMove(Vector <Train> trainslist, Vector<Tile> boneyard, int continuedmove, int a_tileposition, Round.Playtype a_playtype,StringBuilder message)
    {
        //this function doesnot do anything and is a virtual function.
        return false;
    }

    /**
     * Player::CheckTrainMove
     * Checks if a tile can be played and if playable play to the train
     * @param a_tile tile to be played
     * @param a_Train train to be played to
     * @param a_tilenumber position of tile in the vector
     * @return boolean value based on whether tile was playable or not.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
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

    /**
     * Player::RemoveTile
     * Removes a tile from players list
     * @param a_position position of tile to be removed.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void RemoveTile(int a_position)
    {
        //this makes sure random position doesnot break the code.
        if (a_position > m_tileslist.size()) {
            return;
        }
        m_tileslist.remove(a_position);
    }

    /**
     * Player::OrphanDoublePresent()
     * Checks if one of the train is orphan double train
     * @param a_trainslist list of all train objects
     * @return boolean value based on if orphan double train is present or not.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
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

    /**
     * Player::Isorphantrain
     * Checks if the given train is orphan or not
     * @return boolean value based on whether given train is orphan or not.
     * @param a_train train to be checked
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean IsorphanTrain(Train a_train){
        if ( (a_train.GetAllTiles().size())>1 && (a_train.GetTop().GetSide1() == a_train.GetTop().GetSide2())) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Player::GetOrphanTrain()
     * Returns the orphan train
     * @return char value that represents a train
     * @param a_trainslist list of all train objects
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public char GetOrphanTrain (Vector<Train> a_trainslist)
    {

        if ( (a_trainslist.get(0).GetAllTiles().size())>1 && (a_trainslist.get(0).GetTop().GetSide1() == a_trainslist.get(0).GetTop().GetSide2())) {
            return 'H';

        }
        else if ((a_trainslist.get(1).GetAllTiles().size())>1 && (a_trainslist.get(1).GetTop().GetSide1() == a_trainslist.get(1).GetTop().GetSide2())) {
            return 'C';
        }
        else if ((a_trainslist.get(2).GetAllTiles().size())>1 && (a_trainslist.get(2).GetTop().GetSide1() == a_trainslist.get(2).GetTop().GetSide2())) {
            return 'M';
        }
        return 'X';
    }

    /**
     * Player::PickBoneyard
     * Pick a boneyard tile and mark a train
     * @return boolean value based on if a boneyard tile was picked.
     * @param a_boneyard boneyard tiles list
     * @param a_train train to be marked.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
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
    /**
     * Player::ValidSecondDouble
     * Checks if the player can play a second double tile or not.
     * @return boolean value based on whether player can player a second double tile with out picking from boneyard
     * @param a_trainslist list of all train objects
     * @param a_chosentrain train chosen to play
     * @param a_newtile tile to be played
     * @author Bishal Thapa
     * @date 11/15/2021
     */
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

    /**
     * Player::CanPlayinTrain
     * Checks if user has a valid tile to play in given train
     * @return boolean value based on whether there is a possible tile to play
     * @param a_train train where tile is to be played
     * @author Bishal Thapa
     * @date 11/15/2021
     */
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


    /**
     * Player::Canplaytile
     * Checks if the tile can be played based on all conditions regarding orphan double train.
     * @return boolean value based on whether tile is playable
     * @param continuedmove number of turns played continously before
     * @param tile tile to be played
     * @param trainslist list of all trains
     * @author Bishal Thapa
     * @date 11/15/2021
     */
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


    /**
     * Player::CanPlaytiletoTrain
     * Checks if a given tile can be played to given train
     * @return boolean value based on whether given tile can be played on given train
     * @param a_train train to be played
     * @param a_tiletoplay  tile to be played
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean CanPlaytiletoTrain(Tile a_tiletoplay, Train a_train) {

        //tile where a new tile is attached to the train.
        Tile toptile = a_train.GetTop();

        if (a_tiletoplay.GetSide1() == toptile.GetSide2() || a_tiletoplay.GetSide2() == toptile.GetSide2()) {
                return true;
        }
        return false;
    }
    /**
     * Player::Canplaydouble
     * Checks if there is a double tile to play
     * @return boolean value based on whether there is a double tile to play
     * @param a_train train where double tile is to be played
     * @author Bishal Thapa
     * @date 11/15/2021
     */
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
    /**
     * Player::CanplayNondouble
     * Checks if there is a non double tile to play
     * @return boolean value based on whether there is a non double tile to play
     * @param a_train train where non double tile is to be played
     * @author Bishal Thapa
     * @date 11/15/2021
     */
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
    /**
     * Player::GetPlayabletile
     * Gets playable non double tile
     * @return int position of the tile that is to be played
     * @param a_train train where non double tile is to be played
     * @author Bishal Thapa
     * @date 11/15/2021
     */
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

    /**
     * Player::GetPlayableDouble
     * Gets playable double tile
     * @return int position of the tile that is to be played
     * @param a_train train where double tile is to be played
     * @author Bishal Thapa
     * @date 11/15/2021
     */
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
    /**
     * Player::StartMexicanTrain
     * Checks if the mexican train can be started or not
     * @return boolean value based on whether mexican train is not played and can be started.
     * @param a_train mexican train to be returned
     * @param a_tilenumber tile that can be played on mexican train
     * @param a_trainslist vector of all the train objects
     * @author Bishal Thapa
     * @date 11/15/2021
     */
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
    /**
     * Player::Canplayopponenttrain
     * Checks if opponent players train can be played or not.
     * @return boolean value based on whether opponent train can be played or not.
     * @param a_trainslist vector of all train objects
     * @param a_train opponenttrain traintype
     * @param a_tilenumber tile number to be played
     * @param a_opponentTrain opponents train object
     * @author Bishal Thapa
     * @date 11/15/2021
     */
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

    /**
     * Player::PlayOrphanDoubleMove
     * Checks to play a double tile to create an orphan double
     * @param a_trainslist list of all train objects
     * @param a_opponentTrain train object of the opponent player
     * @param a_tilenumber tile number to be played
     * @param a_train train type where double tile can be played.
     * @return boolean value based on whether move was possible
     * @author Bishal Thapa
     * @date 11/15/2021
     */
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
    /**
     * Player::PlayMexicanTrain
     * check if can play mexican train
     * @return boolean value based on whether mexican train is playable
     * @param a_train mexican train
     * @param a_tilenumber tile to be played
     * @param a_trainslist list of all the trains
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean PlayMexicanTrain(Vector <Train>  a_trainslist, StringBuilder a_tilenumber, Train a_train)
    {
        if (CanPlayinTrain(a_trainslist.get(2))) {
            a_tilenumber.append(GetPlayableTile(a_trainslist.get(2)));
            a_train = a_trainslist.get(2);
            return true;
        }
        return false;
    }
    /**
     * Round::PlaySelfTrain
     * Checks if the self train is playable
     * @return boolean value based on whether self train can be played.
     * @param a_trainslist list of all trains
     * @param a_tilenumber tile number that can be played
     * @param a_selftrain self train object
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean PlaySelfTrain(Vector <Train> a_trainslist, StringBuilder a_tilenumber, Train a_selftrain)
    {
        if (CanPlayinTrain(a_selftrain)) {
            a_tilenumber.append(GetPlayableTile(a_selftrain));
            return true;
        }
        return false;
    }
    /**
     * Player::Getsum
     * Total of two sides of the tile
     * @return int total of two sides.
     * @param a_tilenumber tile whose total is to be calculated.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public int Getsum(int a_tilenumber)
    {
        if (a_tilenumber > GetTiles().size()) {
            return 0;
        }
        Tile tile = GetTiles().get(a_tilenumber);
        return tile.GetSide1() + tile.GetSide2();
    }
    // private variables at the bottom
    private Vector<Tile> m_tileslist=new Vector<>();
    //this checks if the game should be quit if the user serialized the game
    private boolean m_quitgame=false;
    //this checks if the valid tile was played
    private boolean m_validtileplayed=false;
    private boolean m_replay=false;
    private int m_continousturns=0;



}

