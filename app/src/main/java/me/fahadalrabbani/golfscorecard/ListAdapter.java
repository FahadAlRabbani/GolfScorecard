package me.fahadalrabbani.golfscorecard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by achernar on 24/11/2015.
 */
class ListAdapter extends BaseAdapter {

    private final Context mContext;
    private final Hole[] mHoles;

    public ListAdapter(Context context, Hole[] holes) {
        mContext = context;
        mHoles = holes;
    }

    @Override
    public int getCount() {
        return mHoles.length;
    }

    @Override
    public Object getItem(int position) {
        return mHoles[position];
    }

    @Override
    public long getItemId(int position) {
        return mHoles[position].hashCode();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.holeLabel = convertView.findViewById(R.id.holeLabel);
            holder.strokeCount = convertView.findViewById(R.id.strokeCount);
            holder.removeStrokeButton = convertView.findViewById(R.id.removeStrokeButton);
            holder.addStrokeButton = convertView.findViewById(R.id.addStrokeButton);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.holeLabel.setText(mHoles[position].getLabel());
        holder.strokeCount.setText(String.format(Locale.getDefault(), "%d", mHoles[position].getStrokeCount()));
        holder.removeStrokeButton.setOnClickListener(v -> {
            int updatedStrokeCount = mHoles[position].getStrokeCount() - 1;
            if (updatedStrokeCount < 0) updatedStrokeCount = 0;
            mHoles[position].setStrokeCount(updatedStrokeCount);
            holder.strokeCount.setText(String.format(Locale.getDefault(), "%d", updatedStrokeCount));
        });
        holder.addStrokeButton.setOnClickListener(v -> {
            int updatedStrokeCount = mHoles[position].getStrokeCount() + 1;
            mHoles[position].setStrokeCount(updatedStrokeCount);
            holder.strokeCount.setText(String.format(Locale.getDefault(), "%d", updatedStrokeCount));
        });

        return convertView;
    }

    private static class ViewHolder {
        private TextView holeLabel;
        private TextView strokeCount;
        private Button removeStrokeButton;
        private Button addStrokeButton;
    }

}
