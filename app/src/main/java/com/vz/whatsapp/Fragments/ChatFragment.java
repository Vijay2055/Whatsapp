package com.vz.whatsapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vz.whatsapp.Adapters.UserAdapter;
import com.vz.whatsapp.Models.Users;
import com.vz.whatsapp.R;
import com.vz.whatsapp.databinding.FragmentChatBinding;

import java.util.ArrayList;


public class ChatFragment extends Fragment {



    public ChatFragment() {
        // Required empty public constructor
    }

     FragmentChatBinding binding;
    ArrayList<Users>list=new ArrayList<>();
     FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentChatBinding.inflate(inflater,container,false);
        UserAdapter adapter=new UserAdapter(list,getContext());
        binding.chatRecyclerView.setAdapter(adapter);
        database=FirebaseDatabase.getInstance();

       LinearLayoutManager linearLayout =new LinearLayoutManager(getContext());
        binding.chatRecyclerView.setLayoutManager(linearLayout);

        //getting the image from the database to show at the profile of user
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users users=dataSnapshot.getValue(Users.class);
                    users.getUserId(dataSnapshot.getKey());
                    list.add(users);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



         return binding.getRoot();


    }
}