package com.example.mynews.Image;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mynews.R;
import com.example.mynews.Utils.ImageLoaderUtil;
import com.example.mynews.Utils.ToolUtils;
import com.example.mynews.beans.ImageBean;

import java.util.List;

/**
 * Created by frank on 2016/9/12.
 */
public class ImageAdapter extends RecyclerView.Adapter {
    private List<ImageBean> images;
    private Context context;
    private int mMaxHeight;
    private int mMaxWidth;

    public ImageAdapter(Context context) {
        this.context = context;
        mMaxWidth= ToolUtils.getWidthInPx(context)-20;
        mMaxHeight=ToolUtils.getHeightInPx(context)-ToolUtils.getStatusHeight(context)-ToolUtils.dip2px(context,96);
    }

    public void setData(List<ImageBean> list){
        this.images=list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.item_iamge,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageBean image=images.get(position);
        if(images==null){
            return;
        }
        ViewHolder vh= (ViewHolder) holder;
        vh.tvTitle.setText(image.getTitle());

        float scale=image.getWeight()/mMaxWidth;
        int mHeight= (int) (image.getHeight()/scale);
        if(mHeight>mMaxWidth){
            mHeight=mMaxHeight;
        }
        vh.ivImage.setLayoutParams(new LinearLayout.LayoutParams(mMaxWidth,mHeight));
        ImageLoaderUtil.display(context,image.getThumbUrl(),vh.ivImage);
    }

    @Override
    public int getItemCount() {
        if (images==null){
            return 0;
        }
        return images.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvTitle;
        public ImageView ivImage;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle= (TextView) itemView.findViewById(R.id.tv_image_title);
            ivImage= (ImageView) itemView.findViewById(R.id.iv_image_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
