package com.example.mynews.News;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mynews.R;
import com.example.mynews.Utils.ImageLoaderUtil;
import com.example.mynews.beans.NewsBean;

import java.util.List;

/**
 * Created by frank on 2016/9/14.
 */
public class NewsAdapter extends RecyclerView.Adapter{
    private List<NewsBean> newsBeens;
    private Context context;
    private OnItemClickListener mItemClickListener;
    public static final int TYPE_ITEM=0;
    public static final int TYPE_FOOT=1;
    private boolean mShowFooter=true;

    public NewsAdapter(Context context){
        this.context=context;

    }
    public void setData(List<NewsBean> newsBeens){
        this.newsBeens=newsBeens;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if(viewType==TYPE_ITEM){
           View view=LayoutInflater.from(context).inflate(R.layout.item_news,null);
           ItemViewHolder itemViewHolder=new ItemViewHolder(view);
           return itemViewHolder;
       }
        else{
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.footer,null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            NewsBean news=newsBeens.get(position);
            if(news==null){
                return;
            }
            ItemViewHolder itemViewHolder= (ItemViewHolder) holder;

            itemViewHolder.tvTitle.setText(news.getTitle());
            itemViewHolder.tvDesc.setText(news.getDigest());
            // itemViewHolder.imageView.setImageURI(Uri.parse(news.getImgsrc()));
            ImageLoaderUtil.display(context,news.getImgsrc(),itemViewHolder.imageView);
        }
    }

    public NewsBean getItem(int position){
        return newsBeens==null?null:newsBeens.get(position);
    }
    public void isShowFooter(boolean isShowFooter){
        this.mShowFooter=isShowFooter;
    }
    public boolean isShowFooter(){
        return mShowFooter;
    }
    @Override
    public int getItemCount() {
        int begin=mShowFooter?1:0;
        if(newsBeens==null){
            return begin;
        }
        return newsBeens.size()+begin;
    }

    @Override
    public int getItemViewType(int position) {
        if(!mShowFooter){
            return TYPE_ITEM;
        }
        if(position+1==getItemCount()){
            return TYPE_FOOT;
        }else {
            return TYPE_ITEM;
        }

    }

    public interface OnItemClickListener{
        void onItemClick(View item,int position);
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.mItemClickListener=itemClickListener;
    }
    public class FooterViewHolder extends RecyclerView.ViewHolder{

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView tvTitle;
        private TextView tvDesc;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.iv_news);
            tvTitle= (TextView) itemView.findViewById(R.id.tv_news_title);
            tvDesc= (TextView) itemView.findViewById(R.id.tv_news_desc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mItemClickListener!=null){
                mItemClickListener.onItemClick(view,this.getPosition());
            }
        }
    }
}
