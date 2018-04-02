package com.konektedi.vs.Elections;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.konektedi.vs.R;

import java.util.List;

/**
 * Created by Sy on 4/1/2018.
 */


public class BtnAdapter extends BaseAdapter {

    private List<BtnModel> btnModelList;
    private Context mContext;

    // Gets the context so it can be used later
    public BtnAdapter(Context c, List<BtnModel> btnModelList) {
        this.mContext = c;
        this.btnModelList = btnModelList;
    }

    // Total number of things contained within the adapter
    public int getCount() {
        return btnModelList.size();
    }

    // Require for structure, not really used in my code.
    public Object getItem(int position) {
        return null;
    }

    // Require for structure, not really used in my code. Can
    // be used to get the id of an item in the adapter for 
    // manual control. 
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position,
                        View convertView, ViewGroup parent) {
        Button btn;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            btn = new Button(mContext);
//            btn.setLayoutParams(new GridView.LayoutParams(100, 55));
//            btn.setPadding(8, 8, 8, 8);
        } else {
            btn = (Button) convertView;
        }

        btn.setText(btnModelList.get(position).getTitle());
        // filenames is an array of strings
        btn.setTextColor(Color.parseColor("#FFFFFF"));
        btn.setBackgroundResource(R.drawable.btn_info);
        btn.setId(position);

        btn.setOnClickListener(new BtnOnClickListener(mContext,position));

        return btn;
    }
}

