package com.example.mexicantrain.model;

public class Tile {


    private int m_side1;
    private int m_side2;

    public Tile() {
        m_side1 = -1;
        m_side2 = -1;

    };
    public Tile(int firstside, int secondside) {
        m_side1 = firstside;
        m_side2 = secondside;
    }
    public boolean IsdoubleTile(){
        if(m_side1==m_side2){
            return true;
        }
        else{
            return false;
        }
    }
    public String Stringified(){
        return String.valueOf(m_side1)+'-'+String.valueOf(m_side2);
    }

    public int GetSide1() {
        return m_side1;
    }
    public int GetSide2() {
        return m_side2;
    }
    public void Filpside()
    {
        int temp = m_side1;
        m_side1 = m_side2;
        m_side2 = temp;
    }


}

