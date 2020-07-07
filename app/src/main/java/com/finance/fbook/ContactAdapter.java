package com.finance.fbook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.lang.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    List<String> itemlist;

    public ContactAdapter(List<String> itemlist)
    {
        this.itemlist = itemlist;
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contact,parent,false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {

        holder.name.setText(itemlist.get(position));
        String stname = "";
        String sp[] = itemlist.get(position).split(" ");
        for(int i=0;i<Math.min(2,sp.length);i++)
        {
            stname+= sp[i].charAt(0);
        }

        stname = stname.toUpperCase();
        holder.shortname.setText(stname);
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public class ContactHolder extends RecyclerView.ViewHolder{

        TextView shortname,name;
        public ContactHolder(@NonNull View itemView) {
            super(itemView);

            shortname = (TextView) itemView.findViewById(R.id.shortname);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
