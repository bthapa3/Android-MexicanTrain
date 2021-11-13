package com.example.mexicantrain.model;

import java.util.Vector;

public class Train {

    private Vector<Tile> m_traintiles=new Vector<>();
    private String m_typeoftrain;
    private boolean m_trainmarked;
    public Train() { }
    public Train( String a_train )
    {
        m_typeoftrain = a_train;
        m_trainmarked = false;
    }
    public void SetTrainType(String traintype){
        m_typeoftrain=traintype;
    }

    public void Addtile(Tile a_tiletobeadded){
        m_traintiles.add(a_tiletobeadded);
    };


    //returns all the tiles of the train
    public Vector<Tile> GetAllTiles() {

        return m_traintiles;
    };

    public boolean isTrainMarked() {
        return m_trainmarked;
    };


    public Tile GetTop() {
        return m_traintiles.lastElement();
        //return  new Tile();
    }

    public void RemovelastTile(){
        m_traintiles.remove(m_traintiles.size()-1);
    }
    public void SetallTiles(Vector<Tile> a_traintiles){
        m_traintiles=a_traintiles;
    }

    public void MarkTrain() {
        m_trainmarked = true;
    }
    public void RemoveMark() {
        m_trainmarked = false;
    }
    public int Size() {
        return m_traintiles.size();
    }
    public String trainType() {
        return m_typeoftrain;
    }
}
