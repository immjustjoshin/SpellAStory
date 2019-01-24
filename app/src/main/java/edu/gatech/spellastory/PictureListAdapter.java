package edu.gatech.spellastory;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import edu.gatech.spellastory.domain.Word;

public class PictureListAdapter extends BaseAdapter {

    private Context mContext;
    private int[] mWordImageID;
    private List<Word> mWords;

    // Constructor
    public PictureListAdapter(Context mContext, int[] mWordImageID, List<Word> mWords) {
        this.mContext = mContext;
        this.mWordImageID = mWordImageID;
        this.mWords = mWords;
    }

    @Override
    public int getCount() {
        return mWords.size();
    }

    @Override
    public Object getItem(int position) {
        return mWords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.listview_picture_words, null);
        ImageView picture =(ImageView)v.findViewById(R.id.wordImageView);
        picture.setImageResource(mWordImageID[position]);
        return v;
    }
}
