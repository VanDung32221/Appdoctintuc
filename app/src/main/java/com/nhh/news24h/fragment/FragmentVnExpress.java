package com.nhh.news24h.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhh.news24h.R;
import com.nhh.news24h.activity.MainActivity;
import com.nhh.news24h.activity.NewsViewActivity;
import com.nhh.news24h.adapter.VnExpressAdapter;
import com.nhh.news24h.listener.OnItemClick;
import com.nhh.news24h.model.Description;
import com.nhh.news24h.model.VnExpress;
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

public class FragmentVnExpress extends Fragment implements OnItemClick {
    private static RecyclerView rvVnExpress;
    private static VnExpressAdapter vnExpressAdapter;
    private static List<VnExpress> vnExpressList;
    private static XmlDomParser domParser;
    private static List<String> linkURL;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vnexpress, container, false);
        rvVnExpress = view.findViewById(R.id.rv_vnexpress);
        vnExpressList = new ArrayList<>();
        domParser = new XmlDomParser();
        linkURL = new ArrayList<>();
        rvVnExpress.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        vnExpressAdapter = new VnExpressAdapter(vnExpressList, MainActivity.getContext(), this);
        rvVnExpress.setAdapter(vnExpressAdapter);
        new VnExpressData().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
        return view;
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getActivity(), NewsViewActivity.class);
        intent.putExtra("link", linkURL.get(position));
        startActivity(intent);
    }

    public static class VnExpressData extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return readUrl(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            vnExpressList.clear();
            Document document = domParser.getDocument(s);
            NodeList itemList = document.getElementsByTagName("item");
            NodeList nodeListDescription = document.getElementsByTagName("description");
            String title = "";
            String pubDate = "";
            String linkUrl = "";
            String anhUrl = "";
            for (int i = 0; i < itemList.getLength(); i++) {
                String cdata = nodeListDescription.item(i + 1).getTextContent();
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\\\"][^>]*>");
                Matcher matcher = p.matcher(cdata);

                if (matcher.find()) {
                    anhUrl = matcher.group(1);
                    Log.d("anh", anhUrl + "....." + i);
                }

                Element element = (Element) itemList.item(i);

                title = domParser.getValue(element, "title");
                linkUrl = domParser.getValue(element, "link");
                pubDate = domParser.getValue(element, "pubDate");
                vnExpressList.add(new VnExpress(title, new Description(anhUrl), pubDate, linkUrl));
                linkURL.add(linkUrl);
            }

            vnExpressAdapter.setData(vnExpressList);

            super.onPostExecute(s);

        }
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

}
