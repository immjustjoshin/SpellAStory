package edu.gatech.spellastory.phoneme_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import edu.gatech.spellastory.R;
import edu.gatech.spellastory.domain.Word;

public class ImageListAdapter extends
        RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private List<Word> words;
    private PhonemeListAdapter.WordClickListener wordClickListener;

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
        View view = inflater.inflate(R.layout.item_imagelist, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Word word = words.get(i);

        viewHolder.placeholderButton.setText(word.getSpelling());
        viewHolder.word = word;
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button placeholderButton;
        Word word;

        ViewHolder(View itemView) {
            super(itemView);
            placeholderButton = itemView.findViewById(R.id.btn_image_placeholder);
            placeholderButton.setOnClickListener(new View.OnClickListener() {
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
