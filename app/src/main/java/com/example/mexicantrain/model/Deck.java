package com.example.mexicantrain.model;

import java.util.Random;
import java.util.Vector;

public class Deck {
    private Vector<Tile> m_deck=new Vector<>();
    public Deck() {
        for (int i = 0;i <= 9;i++) {
            for (int j = i; j <= 9;j++)
            {
                Tile newTile = new Tile(i, j);
                m_deck.add(newTile);
            }
        }
    }
    public Vector<Tile> GetDeck() {
        return m_deck;
    }

    // pop_back equivalent.
    //a.remove(a.size()-1);
    //for shuffling the deck
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
    //helper function that helps for shuffling the tiles properly.
    public int GenerateRandomNumber(int range){
        Random rand = new Random();
        //generates number between 0 and 1;lets assume 0 is heads and 1 is tails.
        int randomNumber = rand.nextInt(range);
        return randomNumber;
    };
    //returns the EngineTile for starting the Game
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
    //returns the players tiles
    public Vector<Tile> GetPlayerTiles(){
        Vector<Tile> playertiles = new Vector<>();
        for (int i= 0; i < 16;i++) {
            playertiles.add(m_deck.get(0));
            m_deck.remove(0);
        }
        return playertiles;
    };
    //returns the Computers Tiles
    public Vector<Tile> GetComputerTiles(){
        Vector<Tile> computertiles = new Vector<>();
        for (int i = 0; i < 16;i++) {
            computertiles.add(m_deck.get(0));
            m_deck.remove(0);
        }
        return computertiles;

    };
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
