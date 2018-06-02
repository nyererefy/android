package com.konektedi.vs.home.categories;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.konektedi.vs.home.candidates.Candidates;
import com.konektedi.vs.R;

import java.util.List;

/**
 * Created by Sy on b/a/2018.
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
        if (categoriesModelList != null)
            return categoriesModelList.size();
        else return 0;
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

    public View getView(final int position,
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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, Candidates.class);
                intent.putExtra("election_id", categoriesModelList.get(position).getElection_id());
                intent.putExtra("category_id", categoriesModelList.get(position).getCategory_id());
                intent.putExtra("category", categoriesModelList.get(position).getCategory());

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        return btn;
    }
}
