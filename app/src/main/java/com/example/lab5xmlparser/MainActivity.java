package com.example.lab5xmlparser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab5xmlparser.Adapter.NewsAdapter;
import com.example.lab5xmlparser.Model.News;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<News> newsList;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private EditText edtLink;
    private Button btnGetLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtLink = findViewById(R.id.edtLink);
        btnGetLink = findViewById(R.id.btnGetLink);
        newsList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btnGetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = edtLink.getText().toString();
                new ReadRSS().execute(link);
                adapter = new NewsAdapter(newsList);
                recyclerView.setAdapter(adapter);
                adapter.setOnClickListener(new NewsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        String s = newsList.get(position).getContent();
                        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                        intent.putExtra("link", s);
                        startActivity(intent);
                    }
                });
            }
        });

//        // Set OnItemClickListener
//        try {
//            adapter.setOnClickListener(new NewsAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(int position) {
//                    String s = newsList.get(position).getContent();
//                    Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
//                    intent.putExtra("link", s);
//                    startActivity(intent);
//                }
//            });
//        } catch (Exception e) {
//            Log.e("Error", e.toString());
//        }



    }

    public class ReadRSS extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuffer content = new StringBuffer();
            try {
                URL url = new URL(strings[0]);

                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }

                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");

            String title = "";
            String pubDate = "";
            String content = "";
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                title = parser.getValue(element, "title");
                pubDate = parser.getValue(element, "pubDate");
                content = parser.getValue(element, "link");
                News news = new News(title, pubDate, content);
                newsList.add(news);
            }
            adapter.notifyDataSetChanged();
        }
    }
}