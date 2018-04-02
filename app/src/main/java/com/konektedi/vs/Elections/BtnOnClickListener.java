package com.konektedi.vs.Elections;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.konektedi.vs.Elections.Results.ResultsView;

/**
 * Created by Sy on 4/1/2018.
 */

class BtnOnClickListener implements View.OnClickListener {
    private final int position;
    private Context context;

    public BtnOnClickListener(Context c, int position) {
        this.position = position;
        this.context = c;
    }

    public void onClick(View v) {

        switch (position) {
            case 0:
                Intent intent = new Intent(context, ResultsView.class);
                context.startActivity(intent);
                break;
            case 1:
                Toast.makeText(context, "Live Votes" + position,
                        Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(context, "Live Reviews" + position,
                        Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(context, "Statistics" + position,
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
