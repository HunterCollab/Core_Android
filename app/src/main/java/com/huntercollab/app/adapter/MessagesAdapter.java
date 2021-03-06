package com.huntercollab.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.socialmediaapp.R;
import com.huntercollab.app.network.loopjtasks.MessageModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessagesAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context mContext;
    private ArrayList<MessageModel> mMessageList;
    private String mUserEmail;

    private MessagesAdapter instance;

    //@author: Hugh Leow
    //@brief: Constructor initialized with values passed into the adapter
    //@params: [Context context] [ArrayList<MessageModel> messageList] [String userEmail]
    public MessagesAdapter(Context context, ArrayList<MessageModel> messageList, String userEmail) {
        mContext = context;
        mMessageList = messageList == null ? new ArrayList<MessageModel>() : messageList;
        mUserEmail = userEmail;
    }

    //@author: Hugh Leow
    //@brief:
    //Sets logged in user into a string
    //Used to determine if they are the sender or not and what views need to be inflated
    //@params: [String mUserEmail]
    //@pre condition: User has no value
    //@post condition: User has a value
    public void setUser(String mUserEmail) {
        this.mUserEmail = mUserEmail;
    }

    //@author: Hugh Leow
    //@brief:
    //Sets messages passed into the adapter into an Arraylist
    //All messages are of class MessageModel.java
    //@params: [ArrayList<MessageModel> mMessageList]
    //@pre condition: Messages are not available to view/use
    //@post condition: Message are available to view/use
    public void setMessages(ArrayList<MessageModel> mMessageList) {
        this.mMessageList = mMessageList;
    }

    //@author: Hugh Leow
    //@brief: Returns the total number of messages
    //@return: int of total number of messages for the view
    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    //@author: Hugh Leow
    //@brief: Determines if message was sent by user or someone else
    //@params: [int position]
    //@return: int for VIEW_TYPE_MESSAGE_SENT or VIEW_TYPE_MESSAGE_RECEIVED
    @Override
    public int getItemViewType(int position) {
        MessageModel message = (MessageModel) mMessageList.get(position);

        if (message.getSender().equals(mUserEmail)) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    //@author: Hugh Leow
    //@brief: Inflates either the item_message_sent.xml or item_message_received.xml based on if the message was from 'self' or others
    //@params: [ViewGroup parent] [int viewType]
    //@pre condition: Nothing inflated inside the view
    //@post condition: Rows inflated based on 'viewType'
    //@return: ViewHolder with the inflated views
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    //@author: Hugh Leow
    //@brief: Passes message object to a ViewHolder so that contents can be bound to UI
    //@params: [ViewHolder holder] [int position]
    //@pre condition: views are not binded to the view holder
    //@post condition: views are binded to the view holder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageModel message = (MessageModel) mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    //@author: Hugh Leow
    //@brief: Stores and recycles 'sent messages' views as they are scrolled off the screen
    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        void bind(MessageModel message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            long dateInMilli = message.getTime();
            DateFormat convert = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            //convert.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date result = new Date(dateInMilli);
            timeText.setText(convert.format(result));
        }
    }

    //@author: Hugh Leow
    //@brief: Stores and recycles 'received messages' views as they are scrolled off the screen
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        //ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
            //profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
        }

        void bind(MessageModel message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            long dateInMilli = message.getTime();
            DateFormat convert = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            //convert.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date result = new Date(dateInMilli);
            timeText.setText(convert.format(result));
            nameText.setText(message.getDisplayName());

            // Insert the profile image from the URL into the ImageView.
            //Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
        }
    }

}