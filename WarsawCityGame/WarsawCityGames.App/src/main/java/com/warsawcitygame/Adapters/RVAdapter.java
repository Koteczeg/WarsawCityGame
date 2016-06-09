package com.warsawcitygame.Adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.warsawcitygame.Activities.MapsActivity;
import com.warsawcitygame.R;
import com.warsawcitygame.Utils.DelegateAction;

import java.util.List;

import butterknife.ButterKnife;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView title;
        TextView description;
        TextView exp;
        ImageView photo;
        Button mapButton;
        Button acceptButton;
        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            title = (TextView)itemView.findViewById(R.id.mission_title);
            description = (TextView)itemView.findViewById(R.id.mission_description);
            photo = (ImageView)itemView.findViewById(R.id.mission_photo);
            mapButton = (Button)itemView.findViewById(R.id.mission_location);
            acceptButton = (Button)itemView.findViewById(R.id.mission_accept);
            exp = (TextView)itemView.findViewById(R.id.mission_exp);

        }
        @Override
        public void onClick(View v) {

            if (v.getId() == mapButton.getId()){
                //
            }
            if (v.getId() == acceptButton.getId()){
                //
            }
        }

    }



    List<MissionDto> persons;
    DelegateAction delegateAction;

    public RVAdapter(List<MissionDto> persons){
        this.persons = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.missions_row_view, viewGroup, false);

        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.title.setText(persons.get(i).name);
        personViewHolder.description.setText(persons.get(i).description);
        personViewHolder.photo.setImageBitmap(persons.get(i).photo);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}
