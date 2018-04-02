//package com.konektedi.vs.Elections;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//
///**
// * Created by Sy on 4/1/2018.
// */
//
//
//public class GridViewAdapter extends BaseAdapter {
//    private Context mContext;
//
//    public GridViewAdapter(Context context) {
//        mContext = context;
//    }
//
//    public int getCount() {
//        return categoriesList.length;
//    }
//
//    public Object getItem(int position) {
//        return null;
//    }
//
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    // create a new ImageView for each item referenced by the Adapter
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView;
//        if (convertView == null) {
//            // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
//
//        } else {
//            imageView = (ImageView) convertView;
//        }
//
//        imageView.setImageResource(categoriesList[position]);
//        return imageView;
//    }
//
//    private String[] categoriesList = {"President", "Vice", "Monitor", "CR",};
//}
