package com.chan.mytest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


class UserAdapter extends RecyclerView.Adapter<UserAdapter.RecyclerViewAdapter> implements Filterable {

    Context context;
    ArrayList<USER> userList;
    ArrayList<USER> usersList;
    public UserAdapter(Context context, ArrayList<USER> userList) {
        this.context = context;
        this.userList = userList;
        this.usersList = userList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_xml,parent,false);
        return new RecyclerViewAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        USER user = usersList.get(position);
        final int id = user.getId();
        holder.txtName.setText(user.getName());
        holder.txtMobile.setText(user.getMobile());
        holder.txtEmail.setText(user.getEmail());
        holder.photo.setImageBitmap(user.getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, UserDetails.class)
                .putExtra("id", id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if(charString.isEmpty()){
                    usersList = userList;
                }else{
                    ArrayList<USER> filteredList = new ArrayList<>();
                    for(USER user: userList){
                        if (user.getName().toLowerCase().contains(charString.toLowerCase()) || user.getMobile().contains(charSequence)) {
                            filteredList.add(user);
                        }
                    }
                    usersList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = usersList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                usersList = (ArrayList<USER>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder{
        ImageView photo;
        TextView txtName, txtMobile, txtEmail;
        public RecyclerViewAdapter(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            txtName = itemView.findViewById(R.id.name);
            txtMobile = itemView.findViewById(R.id.mobile);
            txtEmail = itemView.findViewById(R.id.email);
        }
    }
}
