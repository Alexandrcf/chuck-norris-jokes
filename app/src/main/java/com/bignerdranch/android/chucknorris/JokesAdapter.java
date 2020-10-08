package com.bignerdranch.android.chucknorris;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JokesAdapter extends RecyclerView.Adapter<JokesAdapter.JokesViewHolder> {

    private List<Value> mValue;

    public JokesAdapter(List<Value> value) {
        mValue = value;
    }

    @Override
    public JokesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.number_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        JokesViewHolder viewHolder = new JokesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(JokesViewHolder holder, int position) {
        Value value = mValue.get(position);
        holder.bind(value);
    }

    @Override
    public int getItemCount() {
        return mValue.size();
    }

    class JokesViewHolder extends RecyclerView.ViewHolder {

        TextView listItemNumberView;

        public JokesViewHolder(View itemView) {
            super(itemView);

            listItemNumberView = itemView.findViewById(R.id.tv_number_item);

        }

        void bind(Value value) {
            listItemNumberView.setText(value.joke);
        }
    }
}
