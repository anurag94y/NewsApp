package com.example.anuragyadav.mobstacassgn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.google.gson.Gson;
import helper.Article;
import helper.FetchJson;
import helper.NewsListAdapter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by anurag.yadav on 12/15/16.
 */
public class MainActivity extends FragmentActivity {
    private ExpandableListView expandableListView;
    private NewsListAdapter expandableListAdapter;
    private List<String> sections;
    private HashMap<String, List<Article>> sectionArticles;
    private String jsonResponse;
    private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sections = new ArrayList<>();
        sectionArticles = new HashMap<>();
        populateData();

        setContentView(R.layout.activity_main);

        for (Map.Entry<String, List<Article>> entry : sectionArticles.entrySet()) {
            System.out.println("$$$$$$ " + entry.getKey());
            for (Article article : entry.getValue()) {
                System.out.println("$$$$$$ " + article.getTitle());
                System.out.println("$$$$$$ " + article.getTime());
                System.out.println("$$$$$$ " + article.getImage());
                System.out.println("$$$$$$ " + article.getSummary());
            }
        }
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListAdapter = new NewsListAdapter(this, sections, sectionArticles);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                startAnotherActivity(groupPosition, childPosition);
                return false;
            }
        });
    }

    void startAnotherActivity(int groupPosition, int childPosition) {
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra("Title", sectionArticles.get(sections.get(groupPosition)).get(childPosition).getTitle());
        intent.putExtra("Summary", sectionArticles.get(sections.get(groupPosition)).get(childPosition).getSummary());
        intent.putExtra("Image", sectionArticles.get(sections.get(groupPosition)).get(childPosition).getImage());
        startActivity(intent);
    }

    private void populateData() {
        try {
            jsonResponse = new FetchJson().execute().get();
            JSONObject sectionJson = new JSONObject(jsonResponse).getJSONObject("Result").getJSONObject("sections");
            String sectionTitle = "National";

            JSONArray jsonArray = sectionJson.getJSONArray(sectionTitle);
            ArrayList<Article> articles = new ArrayList<>();
            for (int i = 0;i < jsonArray.length(); i++) {
                Article article = new Gson().fromJson(String.valueOf(jsonArray.getJSONObject(i)), Article.class);
                articles.add(article);
            }

            if (articles.size() > 0) {
                sections.add(sectionTitle);
                sectionArticles.put(sectionTitle, articles);
            }
            sectionTitle = "City News";
            jsonArray = sectionJson.getJSONArray(sectionTitle);
            articles = new ArrayList<>();
            for (int i = 0;i < jsonArray.length(); i++) {
                Article article = new Gson().fromJson(String.valueOf(jsonArray.getJSONObject(i)), Article.class);
                articles.add(article);
            }
            if (articles.size() > 0) {
                sections.add(sectionTitle);
                sectionArticles.put(sectionTitle, articles);
            }
         } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
