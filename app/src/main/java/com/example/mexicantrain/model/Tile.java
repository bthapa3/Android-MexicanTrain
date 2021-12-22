package com.example.mexicantrain.model;
/*
 ************************************************************
 * Name:  Bishal Thapa									   *
 * Project:  Project 3 Mexican Train Android Java				       *
 * Class:  CMPS366 OPL				                       *
 * Date:  11/17/2020				                           *
 ************************************************************
 */
public class Tile {


    private int m_side1;
    private int m_side2;

    /**
     * Tile::Tile
     * Default constructor for the tile class.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Tile() {
        m_side1 = -1;
        m_side2 = -1;

    };
    /**
     * Tile::Tile
     * Constructor for the tile class.
     * @param  firstside First side of the tile
     * @param  secondside Second side of the tile
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Tile(int firstside, int secondside) {
        m_side1 = firstside;
        m_side2 = secondside;
    }
    /**
     * Tile::Isdoubletile
     * Checks if a tile is double tile or not.
     * @return boolean based on status of Tile
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public boolean IsdoubleTile(){
        if(m_side1==m_side2){
            return true;
        }
        else{
            return false;
        }
    }
    /**
     * Tile::Stringified
     * String version of the tile in format x-x
     * @return String side1 + '-'+ side2
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public String Stringified(){
        return String.valueOf(m_side1)+'-'+String.valueOf(m_side2);
    }

    /**
     * Tile::Getside1
     * First side of the tile
     * @return int side of the Tile
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public int GetSide1() {
        return m_side1;
    }
    /**
     * Tile::GetSide2
     * Get side 2 of the tile
     * @return int side of the Tile
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public int GetSide2() {
        return m_side2;
    }
    /**
     * Tile::Flipside
     * Flip sides of the tile i.e assign side a to side b and side b to side a.
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void Filpside()
    {
        int temp = m_side1;
        m_side1 = m_side2;
        m_side2 = temp;
    }


}

