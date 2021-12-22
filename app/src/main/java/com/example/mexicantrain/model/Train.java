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
public class Train {

    private Vector<Tile> m_traintiles=new Vector<>();
    private String m_typeoftrain;
    private boolean m_trainmarked;

    /**
     * Train::Train()
     * Default Constructor for the Train class
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Train() { }

    /**
     * Train::Train
     * Constructor for the Train class.
     * @param  a_train String the type of train being initialized
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Train( String a_train )
    {
        m_typeoftrain = a_train;
        m_trainmarked = false;
    }
    /**
     * Train::AddTile
     * Helps to add tile to the train
     * @param  a_tiletobeadded Tile the tile to be appended.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void Addtile(Tile a_tiletobeadded){
        m_traintiles.add(a_tiletobeadded);
    };

    /**
     * Train::GetAllTiles
     * Returns all the tiles of the train.
     * @return Vector<Tile> List of all the tiles.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Vector<Tile> GetAllTiles() {
        return m_traintiles;
    };

    /**
     * Train::isTrainMarked
     * Returns if the train is marked or not.
     * @return boolean the status of the train marker
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean isTrainMarked() {
        return m_trainmarked;
    };

    /**
     * Train::GetTop
     * Get the top of the tile from vector
     * @return Tile that is the last tile of the train.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Tile GetTop() {
        return m_traintiles.lastElement();
    }

    /**
     * Train::SetallTiles
     * Sets the tiles given as train's tiles
     * @author Bishal Thapa
     * @param  a_traintiles Vector<Tile> which are the list of tiles to be stored.
     * @date 11/15/2021
     */
    public void SetallTiles(Vector<Tile> a_traintiles){
        m_traintiles=a_traintiles;
    }

    /**
     * Train::MarkTrain
     * Sets marker to the Train.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void MarkTrain() {
        m_trainmarked = true;
    }

    /**
     * Train::RemoveMark
     * Removes marker of the Train.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void RemoveMark() {
        m_trainmarked = false;
    }

    /**
     * Train::Size
     * Returns length of the train.
     * @return int size of the vector of tiles.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public int Size() {
        return m_traintiles.size();
    }

    /**
     * Train::trainType
     * Returns Type of the Train
     * @return String type of the train.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public String trainType() {
        return m_typeoftrain;
    }
}
