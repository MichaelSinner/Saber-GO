package com.uan.michaelsinner.sabergo.Utilities;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.uan.michaelsinner.sabergo.Data.ImageWheel;
import com.uan.michaelsinner.sabergo.R;

import java.util.List;

import github.hellocsl.cursorwheel.CursorWheelLayout;

/**
 * Created by Michael Sinner on 24/11/2017.
 */

public class AdapterImageWheel extends CursorWheelLayout.CycleWheelAdapter {

    private Context mContext;
    private List<ImageWheel> itemsMenu;
    private LayoutInflater inflater;
    private int gravity;

    public AdapterImageWheel(Context mContext, List<ImageWheel> itemsMenu) {
        this.mContext = mContext;
        this.itemsMenu = itemsMenu;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return itemsMenu.size();
    }

    @Override
    public ImageWheel getItem(int position) {
        return itemsMenu.get(position);
    }

    @Override
    public View getView(View parent, int position) {
        ImageWheel image = getItem(position);
        View view = inflater.inflate(R.layout.wheel_image_layout, null, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_wheel);
        imageView.setImageResource(image.imageResource);
        return view;
    }
}
