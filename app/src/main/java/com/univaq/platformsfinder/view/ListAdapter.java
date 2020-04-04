package com.univaq.platformsfinder.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.univaq.platformsfinder.R;
import com.univaq.platformsfinder.model.PlatformTable;
import com.univaq.platformsfinder.model.PlatformsDB;
import com.univaq.platformsfinder.tools.BundleFactory;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
{
    private ArrayList<PlatformTable> dataSet;
    private Context currentContext;

    public ListAdapter(ArrayList<PlatformTable> tables)
    {
        dataSet = tables;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        currentContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.platform_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        PlatformTable table = dataSet.get(position);
        //TextViews filling
        String currentString = nameString(table.denominazione);
        holder.nameTextView.setText(currentString);
        currentString = itemString(table.stato, currentContext.getString(R.string.state_string));
        holder.stateTextView.setText(currentString);
        currentString = itemString(Integer.toString(table.distanzaCosta) + " m", currentContext.getString(R.string.coastDistance_string));
        holder.coastDistanceTextView.setText(currentString);
        currentString = itemString(table.tipo, currentContext.getString(R.string.type_string));
        //Button initialization
        holder.typeTextView.setText(currentString);
        holder.detailsButton.setText(R.string.detailsButton_string);
        holder.detailsButton.setOnClickListener(detailsButtonListener(table));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView nameTextView;
        public TextView stateTextView;
        public TextView coastDistanceTextView;
        public TextView typeTextView;
        public Button detailsButton;

        public ViewHolder(View itemView)
        {
            super(itemView);
            nameTextView = (TextView)itemView.findViewById(R.id.itemName);
            stateTextView = (TextView)itemView.findViewById(R.id.itemState);
            coastDistanceTextView = (TextView)itemView.findViewById(R.id.itemCoastDistance);
            typeTextView = (TextView)itemView.findViewById(R.id.itemType);
            detailsButton = (Button)itemView.findViewById(R.id.itemDetailsButton);
        }
    }

    private String nameString(String name)
    {
        String first = "<font color=#fc1100>" + R.string.name_string + ": </font>";
        String last = "<font color=#030000>" + name +"</font> /n";
        return first + last;
    }

    private String itemString(String obj, String type)
    {
        String first = "<font color=#6a02b5>" + type + ": </font>";
        String last = "<font color=#030000>" + obj +"</font> /n";
        return first + last;
    }

    private View.OnClickListener detailsButtonListener(final PlatformTable table)
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BundleFactory factory = new BundleFactory();
                Intent intent = new Intent(currentContext, DetailsActivity.class);
                intent.putExtras(factory.listDetailsBundle(table));
                currentContext.startActivity(intent);
            }
        };
    }
}
