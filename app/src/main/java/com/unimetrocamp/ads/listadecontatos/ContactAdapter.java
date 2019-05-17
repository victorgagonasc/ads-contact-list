package com.unimetrocamp.ads.listadecontatos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private Context mCtx;
    private List<Contact> contactList;

    public ContactAdapter(Context mCtx, List<Contact> contactList) {
        this.mCtx = mCtx;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);

        holder.textViewName.setText(contact.getName());
        holder.textViewPhone.setText(contact.getPhone());
        holder.textViewEmail.setText(contact.getEmail());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewPhone, textViewEmail;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.tvName);
            textViewPhone = itemView.findViewById(R.id.tvPhone);
            textViewEmail = itemView.findViewById(R.id.tvEmail);
        }
    }
}
