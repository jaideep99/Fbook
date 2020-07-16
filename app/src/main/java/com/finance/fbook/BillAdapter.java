package com.finance.fbook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillHolder> {

    List<bill> itemlist =  new ArrayList<bill>();
    private BillAdapter.OnItemClickListener mListener;

    public BillAdapter(List<bill> billList)
    {
        this.itemlist = billList;
    }

    public interface OnItemClickListener{

        void onItemClick(bill x);
    }

    public void setOnItemClickListener(BillAdapter.OnItemClickListener onItemClickListener)
    {
        mListener = onItemClickListener;
    }


    @NonNull
    @Override
    public BillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.bill,parent,false);
        return new BillAdapter.BillHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillHolder holder, final int position) {

        bill curr = itemlist.get(position);
        holder.date.setText(curr.getDate());
        holder.desc.setText(curr.getDesc());

        if(curr.getGave())
        {
            holder.gave.setText("₹ "+String.valueOf(curr.getAmount()));
        }
        else
        {
            holder.got.setText("₹ "+String.valueOf(curr.getAmount()));
        }

        holder.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(itemlist.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public class BillHolder extends RecyclerView.ViewHolder{

        LinearLayout wrapper;
        TextView date,desc,gave,got;
        public BillHolder(@NonNull View itemView) {
            super(itemView);

            wrapper = (LinearLayout) itemView.findViewById(R.id.wrapper);
            date = (TextView) itemView.findViewById(R.id.date);
            desc = (TextView) itemView.findViewById(R.id.desc);
            gave = (TextView) itemView.findViewById(R.id.gave);
            got = (TextView) itemView.findViewById(R.id.got);
        }
    }
}
