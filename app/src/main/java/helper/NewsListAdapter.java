package helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.BaseExpandableListAdapter;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.anuragyadav.mobstacassgn.R;
import java.io.*;
import helper.ImageHelper.*;

/**
 * Created by anurag.yadav on 12/15/16.
 */
public class NewsListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> sections;
    private HashMap<String, List<Article>> sectionArticles;

    public NewsListAdapter(Context context, List<String> sections,
                           HashMap<String, List<Article>> sectionArticles) {
        this.context = context;
        this.sections = sections;
        this.sectionArticles = sectionArticles;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.sectionArticles.get(this.sections.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Article expandedListText = (Article) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_child, null);
        }
        TextView articleTitle = (TextView) convertView.findViewById(R.id.article_title);
        articleTitle.setText(expandedListText.getTitle());
        TextView articleTime = (TextView) convertView.findViewById(R.id.artcile_date);
        articleTime.setText(expandedListText.getTime());
        final ImageView articleImage = (ImageView) convertView.findViewById(R.id.article_icon);
        new ImageHelper().setImageView(expandedListText, articleImage);
        return convertView;
    }


    @Override
    public int getChildrenCount(int listPosition) {
        return this.sectionArticles.get(this.sections.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.sections.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.sections.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_parent, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
