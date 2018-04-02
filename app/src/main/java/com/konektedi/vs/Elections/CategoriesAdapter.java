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

public class CategoriesAdapter extends BaseAdapter {

    private List<CategoriesModel> categoriesModelList;
    private Context mContext;

    // Gets the context so it can be used later
    public CategoriesAdapter(Context c, List<CategoriesModel> categoriesModelList) {
        this.mContext = c;
        this.categoriesModelList = categoriesModelList;
    }

    // Total number of things contained within the adapter
    public int getCount() {
        return categoriesModelList.size();
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

        btn.setText(categoriesModelList.get(position).getCategory());
        // filenames is an array of strings
        btn.setTextColor(Color.parseColor("#FFFFFF"));
        btn.setBackgroundResource(R.drawable.btn_primary);
        btn.setId(position);

        btn.setOnClickListener(new CategoryOnClickListener(mContext,position));

        return btn;
    }
}
