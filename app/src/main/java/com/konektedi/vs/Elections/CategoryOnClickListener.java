package com.konektedi.vs.Elections;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Sy on 4/1/2018.
 */


class CategoryOnClickListener implements View.OnClickListener {
    private final int position;
    private Context context;

    public CategoryOnClickListener(Context c, int position) {
        this.position = position;
        this.context = c;
    }

    public void onClick(View v) {
        Toast.makeText(context, "" + position,
                Toast.LENGTH_SHORT).show();
    }
}
