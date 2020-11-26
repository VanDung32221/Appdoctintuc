package com.nhh.news24h.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhh.news24h.R;
import com.nhh.news24h.activity.MainActivity;
import com.nhh.news24h.activity.NewsViewActivity;
import com.nhh.news24h.adapter.Paper24HAdapter;
import com.nhh.news24h.listener.ILoadMore;
import com.nhh.news24h.listener.OnItemClick;
import com.nhh.news24h.model.Description;
import com.nhh.news24h.model.Paper24H;
import com.nhh.news24h.parse.XmlDomParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fragment24H extends Fragment implements OnItemClick {
    private static RecyclerView rv24H;
    private static Paper24HAdapter paper24HAdapter;
    private static List<Paper24H> paper24HList;
    private static XmlDomParser domParser;
    private static List<String> linkURL;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_24h, container, false);
        rv24H = view.findViewById(R.id.rv_24h);
        paper24HList = new ArrayList<>();
        domParser = new XmlDomParser();
        linkURL = new ArrayList<>();

        rv24H.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        paper24HAdapter = new Paper24HAdapter(rv24H, paper24HList, MainActivity.getContext(), this);
        rv24H.setAdapter(paper24HAdapter);
        new Paper24HData().execute("https://www.24h.com.vn/upload/rss/tintuctrongngay.rss");
        return view;
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getActivity(), NewsViewActivity.class);
        intent.putExtra("link", linkURL.get(position));
        startActivity(intent);
    }


    public static class Paper24HData extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return readUrl(strings[0]);
        }

        @Override
        protected void onPostExecute(final String s) {
            paper24HList.clear();
            LoadData(s);

            paper24HAdapter.setLoadMore(new ILoadMore() {
                @Override
                public void onLoadMore() {
                    if (paper24HList.size() <= 5) {
                        paper24HList.add(null);
                        paper24HAdapter.notifyItemInserted(paper24HList.size() - 1);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                paper24HList.remove(paper24HList.size() - 1);
                                paper24HAdapter.notifyItemRemoved(paper24HList.size());
                                LoadData(s);
                            }
                        }, 3000);
                    }
                }
            });

            super.onPostExecute(s);


        }
    }

    private static void LoadData(String s) {
        Document document = domParser.getDocument(s);
        NodeList itemList = document.getElementsByTagName("item");
        NodeList nodeListDescription = document.getElementsByTagName("description");
        String title = "";
        String linkUrl = "";
        String pubDate = "";
        String anhUrl = "";
        for (int i = 0; i < itemList.getLength(); i++) {
            String cdata = nodeListDescription.item(i + 1).getTextContent();
            Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\\\"][^>]*>");
            Matcher matcher = p.matcher(cdata);

            if (matcher.find()) {
                anhUrl = matcher.group(1);
            }

            Element element = (Element) itemList.item(i);

            title = domParser.getValue(element, "title");
            linkUrl = domParser.getValue(element, "link");
            pubDate = domParser.getValue(element, "pubDate");
            paper24HList.add(new Paper24H(title, new Description(anhUrl), pubDate, linkUrl));
            linkURL.add(linkUrl);
        }
        paper24HAdapter.setData(paper24HList);
    }


    private static String readUrl(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private String removeCdata(String data) {
        data = data.replace("<![CDATA[", "");
        data = data.replace("]]>", "");
        return data;
    }

}
