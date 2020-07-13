package com.finance.fbook;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class ContactFilter extends Filter {

    private List<contact> contactList;
    private List<contact> filteredContactList;
    private ContactItemAdapter adapter;

    public ContactFilter(List<contact> contactList, ContactItemAdapter adapter) {
        this.adapter = adapter;
        this.contactList = contactList;
        this.filteredContactList = new ArrayList<contact>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredContactList.clear();
        final FilterResults results = new FilterResults();

        //here you need to add proper items do filteredContactList
        if (constraint == null || constraint.length() == 0) {
            filteredContactList.addAll(contactList);
        }
        else
        {
            String filterPattern = constraint.toString().toLowerCase().trim();
            for (final contact item : contactList) {
                if (item.getName().toLowerCase().trim().contains(filterPattern)) {
                    filteredContactList.add(item);
                }
            }
        }



        results.values = filteredContactList;
        results.count = filteredContactList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.setList(filteredContactList);
        adapter.notifyDataSetChanged();
    }
}
