package com.example.anuragyadav.mobstacassgn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;
import helper.Article;
import helper.ImageHelper;

/**
 * Created by anurag.yadav on 12/15/16.
 */
public class ArticleActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Article article = new Article();
        article.setTitle(intent.getStringExtra("Title"));
        article.setTime(intent.getStringExtra("Summary"));
        article.setImage(intent.getStringExtra("Image"));

        setContentView(R.layout.article_page);

        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(article.getTitle());
        TextView summaryView = (TextView) findViewById(R.id.Summary);
        summaryView.setText(article.getTime());
        ImageView iconView = (ImageView) findViewById(R.id.image);
        new ImageHelper().setImageView(article, iconView);
    }
}
