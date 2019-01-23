package edu.gatech.spellastory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

public class PictureListAdapter extends ArrayAdapter<String> {

    Context mContext;
    int[] pictureID;

    public PictureListAdapter(Context context, int[] pictureID) {
        super(context, R.layout.listview_picture_words);
        this.pictureID = pictureID;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return pictureID.length;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listview_picture_words, parent, false);
            mViewHolder.mPictureID = (ImageButton) convertView.findViewById(R.id.wordImageButton);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mPictureID.setImageResource(pictureID[position]);
        return convertView;
    }

    static class ViewHolder {
        ImageButton mPictureID;
    }
}
