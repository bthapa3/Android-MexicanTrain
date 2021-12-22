package com.example.mexicantrain.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mexicantrain.R;
import com.example.mexicantrain.model.Tile;

import java.util.Vector;

/*
 ************************************************************
 * Name:  Bishal Thapa									   *
 * Project:  Project 3 Mexican Train Android Java				       *
 * Class:  CMPS366 OPL				                       *
 * Date:  11/17/2020				                           *
 ************************************************************
 */
public class TileAdapter extends RecyclerView.Adapter<TileAdapter.holder> {
    private Vector<Tile> traintiles;
    private String storagetype;
    private OnNotelistener m_onNotelistener;

    /**
     * TileAdapter::TileAdapter
     * Constructor for the Tileadaptor class
     * @param onNotelistener  notelistener object
     * @param storagetype this states where the tile adapter is used i.e train or tiles list
     * @param traintiles list of all the tiles to be displayed
     * @author Bishal Thapa
     * @date 11/15/2021
     * @Help :  https://www.youtube.com/watch?v=69C1ljfDvl0
     */
    public TileAdapter(Vector<Tile> traintiles, String storagetype, OnNotelistener onNotelistener) {
        this.traintiles = traintiles;
        this.storagetype=storagetype;
        this.m_onNotelistener=onNotelistener;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.singletilelayout,parent,false);
        return new holder(view,m_onNotelistener);
    }


    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        String text;
        if(storagetype.equals("Train") && traintiles.get(position).GetSide1()== traintiles.get(position).GetSide2()){
            //this detects the marker is present in the train.
            if(traintiles.get(position).GetSide1()== -1 && traintiles.get(position).GetSide2()== -1){
                text= " (M) ";
            }
            else{
                text=String.valueOf(traintiles.get(position).GetSide1())+ "\n"+ String.valueOf(traintiles.get(position).GetSide2());
            }
        }
        else{
            text=String.valueOf(traintiles.get(position).GetSide1())+ "-"+ String.valueOf(traintiles.get(position).GetSide2());
        }

        holder.tilename.setText(text);
    }

    @Override
    public int getItemCount() {
        return traintiles.size();
    }

    class holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tilename;
        OnNotelistener onNotelistener;
        public holder(@NonNull View ItemView, OnNotelistener onNotelistener){
            super(ItemView);
            tilename=(TextView) ItemView.findViewById(R.id.tile);
            this.onNotelistener=onNotelistener;
            tilename.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(storagetype.equals("HumanTiles"))
            onNotelistener.OnNoteClick(getAdapterPosition());
        }
    }

    //returns the int position to Playround activity on click for getting the tile number
    public interface OnNotelistener{
        void OnNoteClick(int position);
    }

}
