package com.example.sebastiaan.sebastiaanjoustra_pset3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sebastiaan on 20-Sep-17.
 */

public class TrackListAdapter extends ArrayAdapter<Track>{


    public TrackListAdapter(Context context, ArrayList<Track> tracks) {
        super(context, R.layout.custom_row, tracks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customRow = inflater.inflate(R.layout.custom_row, parent, false);

        Track listItem = getItem(position);

        TextView tvTrackName = (TextView) customRow.findViewById(R.id.tvRowItemTrackName);
        TextView tvArtist = (TextView) customRow.findViewById(R.id.tvRowItemArtist);

        tvTrackName.setText(listItem.getSongName());
        tvArtist.setText(listItem.getArtist());

        return customRow;
    }
}
