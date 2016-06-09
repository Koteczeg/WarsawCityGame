package com.warsawcitygame.Adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.warsawcitygame.R;
import com.warsawcitygames.models.MissionHistoryModel;

import java.util.List;

/**
 * Created by bakala12 on 08.06.2016.
 */
public class MissionHistoryAdapter extends RecyclerView.Adapter<MissionHistoryAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView title;
        TextView description;
        TextView exp;
        ImageView photo;
        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            title = (TextView)itemView.findViewById(R.id.mission_title);
            description = (TextView)itemView.findViewById(R.id.mission_description);
            photo = (ImageView)itemView.findViewById(R.id.mission_photo);
            exp = (TextView)itemView.findViewById(R.id.mission_exp);
        }
    }

    List<MissionHistoryModel> persons;

    public MissionHistoryAdapter(List<MissionHistoryModel> persons){
        this.persons = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mission_history_ro_view, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.title.setText(persons.get(i).missionName);
        personViewHolder.description.setText(persons.get(i).missionDescription);
        Bitmap bm = ListViewAdapter.convertPic(persons.get(i).image);
        personViewHolder.photo.setImageBitmap(bm);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}

