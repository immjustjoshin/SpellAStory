package edu.gatech.spellastory.phoneme_list;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import edu.gatech.spellastory.R;
import edu.gatech.spellastory.domain.Word;

public class ImageListAdapter extends
        RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private List<Word> words;
    private PhonemeListAdapter.WordClickListener wordClickListener;
    private AssetManager assets;

    ImageListAdapter(List<Word> words) {
        this.words = words;
    }

    public void setWordClickListener(PhonemeListAdapter.WordClickListener wordClickListener) {
        this.wordClickListener = wordClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        assets = context.getAssets();
        View view = inflater.inflate(R.layout.item_imagelist, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Word word = words.get(i);

        viewHolder.word = word;
        try {
            InputStream ims = assets.open("pictures/" + word.getSpelling() + ".png");
            Drawable d = Drawable.createFromStream(ims, null);
            viewHolder.pictureImageButton.setImageDrawable(d);
        } catch (IOException e) {
            // Could not find picture associated with the word
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton pictureImageButton;
        Word word;

        ViewHolder(View itemView) {
            super(itemView);
            pictureImageButton = itemView.findViewById(R.id.imageButton_picture);
            pictureImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (wordClickListener != null) {
                        wordClickListener.onWordClick(word);
                    }
                }
            });
        }
    }
}
