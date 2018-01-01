package com.saxxhw.library.widget.dropdown;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saxxhw.library.R;
import com.saxxhw.library.base.BaseAdapter;

public class SingleChoiceAdapter extends BaseAdapter<String> {

    private int checkItemPosition = 0;

    public SingleChoiceAdapter(Context context) {
        super(context);
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_default_drop_down, parent, false);
            holder = new ViewHolder();
            holder.mText = convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mText.setText(getItem(position));
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                holder.mText.setTextColor(Color.parseColor("#FF008EFF"));
            } else {
                holder.mText.setTextColor(Color.parseColor("#FF444445"));
            }
        }

        return convertView;
    }

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }


    static class ViewHolder {
        TextView mText;
    }
}
