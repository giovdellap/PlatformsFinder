package com.univaq.platformsfinder.tools;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.univaq.platformsfinder.R;

/**
 * Makes SpanabbleString objects for view elements
 */
public class StringMaker
{
    /**
     * Returns styled string for goButton
     *
     * @param context context
     * @return the spannable string
     */
    public SpannableString mainButtonString(Context context)
    {
        String toInsert = context.getString(R.string.goButton_string);
        SpannableString toReturn = new SpannableString(toInsert);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.WHITE);
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        toReturn.setSpan(colorSpan, 0, toInsert.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        toReturn.setSpan(styleSpan, 0, toInsert.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return toReturn;
    }

    /**
     * Returns styled string for MainActivity's SelPosButton
     *
     * @param context the context
     * @return the spannable string
     */
    public SpannableString selPosButtonString(Context context)
    {
        String toInsert = context.getString(R.string.selPosButton_string);
        SpannableString toReturn = new SpannableString(toInsert);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.WHITE);
        toReturn.setSpan(colorSpan, 0, toInsert.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return toReturn;
    }

    /**
     * Returns styled string for MApActivity's ListViewButton
     *
     * @param context the context
     * @return the spannable string
     */
    public SpannableString listButtonString(Context context)
    {
        String toInsert = context.getString(R.string.listViewButton_string);
        SpannableString toReturn = new SpannableString(toInsert);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.WHITE);
        toReturn.setSpan(colorSpan, 0, toInsert.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return toReturn;
    }

    /**
     * Returns styled string for DetailsActivity and ViewHolder strings
     *
     * @param type first field
     * @param obj second field
     * @param context the context
     * @return the spannable string
     */
    public SpannableString detailsLine(String type, String obj, Context context)
    {
        SpannableString spannableString = new SpannableString(type + " : " + obj + " \n");
        ForegroundColorSpan typeColorSpan = new ForegroundColorSpan(context.getColor(R.color.typeTextColor));
        ForegroundColorSpan objColorSpan = new ForegroundColorSpan(Color.DKGRAY);
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(typeColorSpan, 0, type.length()+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(styleSpan, 0, type.length()+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(objColorSpan, type.length()+2, spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * Returns styled string for ViewHolder's first line
     *
     * @param name first field
     * @param type second field
     * @param context the context
     * @return the spannable string
     */
    public SpannableString itemNameString(String name, String type, Context context)
    {

        SpannableString spannableString = new SpannableString(type + " :" + name + " \n");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getColor(R.color.colorAccent));
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        ForegroundColorSpan nameColorSpan = new ForegroundColorSpan(Color.BLACK);
        spannableString.setSpan(colorSpan, 0, type.length()+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(styleSpan, 0, type.length()+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(nameColorSpan, type.length()+1, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
