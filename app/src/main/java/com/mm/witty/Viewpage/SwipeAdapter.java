package com.mm.witty.Viewpage;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mm.witty.R;

/**
 * Created by MacBook on 8/11/17.
 */

public class SwipeAdapter extends PagerAdapter{
private int[] image_resources ={R.drawable.xkcd1,R.drawable.xkcd2,R.drawable.xkcd3,R.drawable.xkcd4,
        R.drawable.xkcd5,R.drawable.xkcd6,R.drawable.xkcd7, R.drawable.xkcd8,R.drawable.xkcd9,
        R.drawable.xkcd10,R.drawable.xkcd11,R.drawable.xkcd12,R.drawable.xkcd13,R.drawable.xkcd14,
        R.drawable.xkcd15,R.drawable.xkcd16,R.drawable.xkcd17};

private Context ctx;
    private LayoutInflater layoutInflater;

    public SwipeAdapter(Context ctx)
    { this.ctx =ctx;

    }

    @Override
    public int getCount() {

        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {

        return (view==(RelativeLayout) o);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container,false);
        ImageView imageView =(ImageView)item_view.findViewById(R.id.image_view);
        TextView textView = (TextView) item_view.findViewById(R.id.image_count);
        imageView.setImageResource(image_resources[position]);
        textView.setText("Image : " +position);
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object o) {
        container.removeView((RelativeLayout) o);
    }
}
