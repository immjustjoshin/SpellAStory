package edu.gatech.spellastory.phoneme_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.gatech.spellastory.R;
import edu.gatech.spellastory.data.PhonemeWordsPair;
import edu.gatech.spellastory.domain.Word;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class PhonemeListAdapter extends
        RecyclerView.Adapter<PhonemeListAdapter.ViewHolder> {

    private List<PhonemeWordsPair> phonemeWordsPairs;
    private LayoutInflater inflater;
    private WordClickListener wordClickListener;

    PhonemeListAdapter(List<PhonemeWordsPair> phonemeWordsPairs) {
        this.phonemeWordsPairs = phonemeWordsPairs;
    }

    public void setWordClickListener(WordClickListener wordClickListener) {
        this.wordClickListener = wordClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_phonemelist, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        PhonemeWordsPair phonemeWordsPair = phonemeWordsPairs.get(i);

        TextView phoneme = viewHolder.phonemeTextView;
        phoneme.setText(phonemeWordsPair.getPhoneme().getSpelling());

        RecyclerView images = viewHolder.imagesRecyclerView;
        ImageListAdapter adapter = new ImageListAdapter(phonemeWordsPair.getWords());
        if (wordClickListener != null) {
            adapter.setWordClickListener(wordClickListener);
        }
        images.setAdapter(adapter);
        images.setNestedScrollingEnabled(true);
        images.setLayoutManager(new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public int getItemCount() {
        return phonemeWordsPairs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView phonemeTextView;
        RecyclerView imagesRecyclerView;

        ViewHolder(View itemView) {
            super(itemView);
            phonemeTextView = itemView.findViewById(R.id.tx_phoneme);
            imagesRecyclerView = itemView.findViewById(R.id.rv_images);
        }
    }

    interface WordClickListener {
        void onWordClick(Word word);
    }
}
