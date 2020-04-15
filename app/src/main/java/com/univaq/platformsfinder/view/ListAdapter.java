package com.univaq.platformsfinder.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.univaq.platformsfinder.R;
import com.univaq.platformsfinder.model.PlatformTable;
import com.univaq.platformsfinder.tools.BundleFactory;
import com.univaq.platformsfinder.tools.StringMaker;

import java.util.ArrayList;

/**
 * List adapter.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
{
    private ArrayList<PlatformTable> dataSet;
    private Context currentContext;
    private static final String TAG = "LISTADAPTER";

    /**
     * Instantiates a new List adapter.
     *
     * @param tables the tables
     */
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
        StringMaker maker = new StringMaker();
        SpannableString currentString = maker.itemNameString(table.denominazione, currentContext.getString(R.string.name_string), currentContext);
        holder.nameTextView.setText(currentString);
        currentString = maker.detailsLine(currentContext.getString(R.string.state_string), table.stato, currentContext);
        holder.stateTextView.setText(currentString);
        currentString = maker.detailsLine(currentContext.getString(R.string.coastDistance_string), Integer.toString(table.distanzaCosta), currentContext);
        holder.coastDistanceTextView.setText(currentString);
        currentString = maker.detailsLine(currentContext.getString(R.string.type_string), table.tipo, currentContext);
        holder.typeTextView.setText(currentString);
        //Button initialization
        holder.detailsButton.setText(detailsButtonString());
        holder.detailsButton.setOnClickListener(detailsButtonListener(table));

        holder.nameTextView.setTextSize(16);
        holder.typeTextView.setTextSize(14);
        holder.coastDistanceTextView.setTextSize(14);
        holder.stateTextView.setTextSize(14);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    /**
     * View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public CardView cardView;
        public TextView nameTextView;
        public TextView stateTextView;
        public TextView coastDistanceTextView;
        public TextView typeTextView;
        public Button detailsButton;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(View itemView)
        {
            super(itemView);
            Log.d(TAG, "ViewHolder constructor");
            cardView = (CardView)itemView.findViewById(R.id.itemCardView);
            nameTextView = (TextView)itemView.findViewById(R.id.itemName);
            stateTextView = (TextView)itemView.findViewById(R.id.itemState);
            coastDistanceTextView = (TextView)itemView.findViewById(R.id.itemCoastDistance);
            typeTextView = (TextView)itemView.findViewById(R.id.itemType);
            detailsButton = (Button)itemView.findViewById(R.id.itemDetailsButton);
        }
    }

    private SpannableString detailsButtonString()
    {
        SpannableString toReturn = new SpannableString(currentContext.getString(R.string.detailsButton_string));
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.WHITE);
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        toReturn.setSpan(colorSpan, 0, toReturn.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        toReturn.setSpan(styleSpan, 0, toReturn.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return toReturn;
    }

    private View.OnClickListener detailsButtonListener(final PlatformTable table)
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "details button clicked");
                BundleFactory factory = new BundleFactory();
                Intent intent = new Intent(currentContext, DetailsActivity.class);
                intent.putExtras(factory.listDetailsBundle(table));
                currentContext.startActivity(intent);
            }
        };
    }
}
