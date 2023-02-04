package com.vz.whatsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vz.whatsapp.Models.Users;
import com.vz.whatsapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {

    ArrayList<Users> list;
    Context context;

    public UserAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Users user=list.get(position);
        //Note we are taking the image from database (online) so we have used Picasso
        Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.avatar).into(holder.imageView);
        holder.userName.setText(user.getUserName());
       //for last message we will do later

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        //initializing all the view
        ImageView imageView;
        TextView userName,lastName;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
           imageView=itemView.findViewById(R.id.profile_image);
           userName=itemView.findViewById(R.id.UserName);
           lastName=itemView.findViewById(R.id.lastMsg);
        }
    }
}
