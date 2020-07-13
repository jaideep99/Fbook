package com.finance.fbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactItemAdapter extends RecyclerView.Adapter<ContactItemAdapter.ContactItemHolder>{

    List<contact> itemlist;
    List<contact> elist;
    ContactFilter filter;
    Context mContext;
    private OnItemClickListener mListener;


    public ContactItemAdapter(Context context,List<contact> itemlist)
    {
        this.mContext = context;
        this.elist = itemlist;
        this.itemlist = new ArrayList<contact>(elist);
        filter =  new ContactFilter(itemlist,this);
    }

    public interface OnItemClickListener{

        void onItemClick(contact customer);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        mListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ContactItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contact_item,parent,false);
        return new ContactItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactItemHolder holder, final int position) {

        holder.name.setText(elist.get(position).getName());
        String stname = "";
        String sp[] = elist.get(position).getName().split(" ");
        for(int i=0;i<Math.min(2,sp.length);i++)
        {
            stname+= sp[i].charAt(0);
        }

        stname = stname.toUpperCase();
        holder.shortname.setText(stname);
        holder.number.setText(elist.get(position).getNumber());

        holder.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(elist.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return elist.size();
    }

    public class ContactItemHolder extends RecyclerView.ViewHolder{

        TextView shortname,name,number;
        LinearLayout wrapper;
        public ContactItemHolder(@NonNull View itemView) {
            super(itemView);

            shortname = (TextView) itemView.findViewById(R.id.shortname);
            name = (TextView) itemView.findViewById(R.id.name);
            number = (TextView) itemView.findViewById(R.id.number);
            wrapper = (LinearLayout) itemView.findViewById(R.id.wrapper);

        }
    }

    public void setList(List<contact> list) {
        this.elist = list;
    }
    //call when you want to filter
    public void filterList(String text) {
        filter.filter(text);
    }
}
