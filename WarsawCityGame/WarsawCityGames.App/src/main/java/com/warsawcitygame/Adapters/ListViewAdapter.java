package com.warsawcitygame.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.warsawcitygame.R;
import com.warsawcitygames.models.friends_models.RankingModel;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;

public class ListViewAdapter extends BaseAdapter
{

    Context context;
    //TODO we need it from database
    String[] ranks;
    String[] levels;
    String[] levelDescriptions;
    String[] names;
    //TODO Drawable pics
    int[] pics;
    List<String> picsl;

    public ListViewAdapter(Context context, String[] ranks, String[] levels, String[] levelDescriptions, String[] names, int[] flag)
    {
        this.context = context;
        this.ranks = ranks;
        this.levels = levels;
        this.levelDescriptions = levelDescriptions;
        this.names = names;
        this.pics = flag;
    }

    public ListViewAdapter(Context context, List<RankingModel> list){
        this.context = context;
        List<String> ranksl = new LinkedList<>();
        List<String> levelsl = new LinkedList<>();
        List<String> levelDesl = new LinkedList<>();
        List<String> namesl = new LinkedList<>();
        picsl = new LinkedList<>();
        if(list!=null){
            Integer i=1;
            for(RankingModel model : list){
                ranksl.add(i.toString());
                levelsl.add(new Integer(model.LevelNumber).toString());
                levelDesl.add(model.LevelName);
                namesl.add(model.PlayerName);
                picsl.add(model.PlayerImage);
                i++;
            }
        }
        ranks = ranksl.toArray(new String[ranksl.size()]);
        levels = levelsl.toArray(new String[ranksl.size()]);
        levelDescriptions = levelDesl.toArray(new String[ranksl.size()]);
        names = namesl.toArray(new String[ranksl.size()]);
    }

    @Override
    public int getCount()
    {
        return ranks.length;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_item, parent, false);
        TextView rank = ButterKnife.findById(itemView, R.id.rank);
        TextView level = ButterKnife.findById(itemView, R.id.level);
        TextView levelDescription = ButterKnife.findById(itemView, R.id.levelTxt);
        TextView name = ButterKnife.findById(itemView, R.id.name);
        ImageView pic = ButterKnife.findById(itemView, R.id.userpic);

        rank.setText(ranks[position]);
        level.setText(levels[position]);
        levelDescription.setText(levelDescriptions[position]);
        name.setText(names[position]);
        Bitmap bm = convertPic(picsl.get(position));
        if(bm!=null){
            pic.setImageBitmap(bm);
        }
        else{
            pic.setImageResource(R.drawable.default_image);
        }
        return itemView;
    }

    public static Bitmap convertPic(String encoded){
        if(encoded==null) return null;
        byte[] imageAsBytes = Base64.decode(encoded.getBytes(), Base64.DEFAULT);
        if(imageAsBytes==null)
            return null;
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
