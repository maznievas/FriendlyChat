package com.google.firebase.udacity.friendlychat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.udacity.friendlychat.entity.FriendlyMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sts on 21.12.17.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private List<FriendlyMessage> messages;


    public MessagesAdapter() {
        messages = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FriendlyMessage message = messages.get(position);
        holder.messageTextView.setText(message.getText());
        holder.nameTextView.setText(message.getName());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(FriendlyMessage message) {
        messages.add(message);
        notifyDataSetChanged();
    }

    public void setMessagesList(List<FriendlyMessage> messagesList)
    {
        messages = messagesList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.messageTextView)
        TextView messageTextView;
        @BindView(R.id.nameTextView)
        TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


