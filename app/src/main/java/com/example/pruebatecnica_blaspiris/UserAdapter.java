package com.example.pruebatecnica_blaspiris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolderUser> {

    ArrayList<User> users;
    Context mContext;

    public UserAdapter(Context context, ArrayList<User> users) {
        this.mContext=context;
        this.users=users;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        mContext = parent.getContext();
        return new ViewHolderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolderUser holder, int position) {
        User user=users.get(position);
        holder.setValues(user);
        holder.costrainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserInfoActivty.class);
                intent.putExtra("user", (Serializable) user);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public static class ViewHolderUser extends RecyclerView.ViewHolder {

        public View costrainLayout;
        public TextView name;
        public ImageView image;

        public ViewHolderUser(View view) {

            super(view);
            costrainLayout=view.findViewById(R.id.container);
            name=view.findViewById(R.id.name);
            image=view.findViewById(R.id.image);
        }


        //SET VALUES TO ITEM
        @SuppressLint("SetTextI18n")
        public void setValues(User user) {

            name.setText(user.getName()+" "+user.getSurname());
            try {
                InputStream is = (InputStream) new URL(user.getPhoto()).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                image.setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}













