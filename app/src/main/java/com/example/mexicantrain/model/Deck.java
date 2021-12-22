package com.example.mexicantrain.model;

import java.util.Random;
import java.util.Vector;

/*
 ************************************************************
 * Name:  Bishal Thapa									   *
 * Project:  Project 3 Mexican Train Android Java				       *
 * Class:  CMPS366 OPL				                       *
 * Date:  11/17/2020				                           *
 ************************************************************
 */
public class Deck {
    private Vector<Tile> m_deck=new Vector<>();
    /**
     * Deck::Deck
     * Default constructor for the deck class. Creates a deck of 55 tiles
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Deck() {
        for (int i = 0;i <= 9;i++) {
            for (int j = i; j <= 9;j++)
            {
                Tile newTile = new Tile(i, j);
                m_deck.add(newTile);
            }
        }
    }
    /**
     * Deck::GetDeck
     * Returns a deck
     * @return Vector of tiles that represents a deck
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Vector<Tile> GetDeck() {
        return m_deck;
    }

    /**
     * Deck::ShuffleDeck
     * Shuffles the tile within the deck to random order
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public void ShuffleDeck(){
        Vector <Tile> shuffleddeck =new Vector<>();
        while (m_deck.size() != 0) {

            //getting a random tile from the deck
            int position= GenerateRandomNumber(m_deck.size());

            //pushing to the shuffled deck starting at position 0 as vector starts from 0.
            shuffleddeck.add(m_deck.get(position));

            //erasing the currently chosen tile from the initial deck.
            m_deck.remove( position);

        }

        m_deck = shuffleddeck;

    };
    /**
     * Deck::GenerateRandomNumber
     * Helper function for shuffling the deck
     * @return random number in the given range
     * @param range that represents the range within which random number is to be generated
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    //helper function that helps for shuffling the tiles properly.
    public int GenerateRandomNumber(int range){
        Random rand = new Random();
        //generates number between 0 and 1;lets assume 0 is heads and 1 is tails.
        int randomNumber = rand.nextInt(range);
        return randomNumber;
    };
    /**
     * Deck::GetEngineTile
     * Returns the engine tile for the round
     * @return Tile that is the engine tile
     * @param a_round current rounds numerical value
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    public Tile GetEngineTile(int a_round){
        //this helps when the round is greater than 100.
        while (a_round > 10) {
            a_round = a_round % 10;
        }
        int tilenumber;

        //sets tilevalue to 0 if round r is divisible by 10.
        if (a_round % 10 == 0)
        {
            tilenumber = 0;
        }
        //sets the tile values accordingly
        else
        {
            tilenumber = 10 - a_round % 10;
        }
        Tile enginetile =  new Tile(tilenumber,tilenumber);
        //removes the engine tile from the current deck.
        //deck.erase(std::remove(deck.begin(), deck.end(), enginetile), deck.end());
        int currentposition = 0;
        for (int i=0; i< m_deck.size();i++) {

            // check the current engine tile and remove it from deck so that user donot get the same value.
            if (m_deck.get(i).GetSide1() == tilenumber && m_deck.get(i).GetSide2() == tilenumber)
            {
                m_deck.remove(currentposition);
            }
            currentposition++;
        }

        return enginetile;
    };
    /**
     * Deck::GetPlayersTiles
     * Returns 16 tiles allocated to the human player
     * @return vector of tiles
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    //returns the players tiles
    public Vector<Tile> GetPlayerTiles(){
        Vector<Tile> playertiles = new Vector<>();
        for (int i= 0; i < 16;i++) {
            playertiles.add(m_deck.get(0));
            m_deck.remove(0);
        }
        return playertiles;
    };
    /**
     * Deck::GetComputertiles
     * Return 16 tiles allocated to the computer player
     * @return vector of Tiles objects
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    //returns the Computers Tiles
    public Vector<Tile> GetComputerTiles(){
        Vector<Tile> computertiles = new Vector<>();
        for (int i = 0; i < 16;i++) {
            computertiles.add(m_deck.get(0));
            m_deck.remove(0);
        }
        return computertiles;

    };
    /**
     * Deck::GetBoneyardTiles
     * Return rest of the tiles allocated to the boneyard
     * @return vector of tiles
     * @author Bishal Thapa
     * @date 11/15/2021
     */
    //returns the Boneyard Tiles
    public Vector<Tile> GetBoneyardTiles(){
        Vector<Tile> bonyardtiles=new Vector<>();
        for (int i = 0; i < 22;i++) {
            bonyardtiles.add(m_deck.elementAt(0));
            m_deck.remove(0);
        }
        return bonyardtiles;
    };
}
