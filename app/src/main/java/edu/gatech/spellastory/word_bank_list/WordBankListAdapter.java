package edu.gatech.spellastory.word_bank_list;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import edu.gatech.spellastory.R;
import edu.gatech.spellastory.domain.Word;

public class WordBankListAdapter extends RecyclerView.Adapter<WordBankListAdapter.ViewHolder> {

    private Context context;
    private List<String> completedWords;

    WordBankListAdapter(Context context, List<String> completedWords) {
        this.context = context;
        this.completedWords = completedWords;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_word_bank_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ImageView image = viewHolder.imageWord;
        TextView name = viewHolder.pictureName;

        image.setImageDrawable(setPictureFor(completedWords.get(i)));
        name.setText(completedWords.get(i));

    }

    @Override
    public int getItemCount() {
        return completedWords.size();
    }

    private Drawable setPictureFor(String word) {
        try {
            InputStream ims = context.getAssets().open("pictures/" + word + ".png");
            return Drawable.createFromStream(ims, null);
        } catch (IOException e) {
            // Could not find picture associated with the word
            e.printStackTrace();
        }
        return null;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageWord;
        TextView pictureName;

        ViewHolder(View itemView) {
            super(itemView);
            imageWord = itemView.findViewById(R.id.iv_picture);
            pictureName = itemView.findViewById(R.id.tx_name);
        }
    }
}
