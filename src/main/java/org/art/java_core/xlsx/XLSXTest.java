package org.art.java_core.xlsx;

import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class XLSXTest {
    public static void main(String[] args) {

        GsonBuilder GSON_BUILDER = new GsonBuilder();
        PageJsonStructure pagesDataJSONStructure = new PageJsonStructure();

        pagesDataJSONStructure.title = "Sheet Title";

        pagesDataJSONStructure.headers.put("Page title", "title");
        pagesDataJSONStructure.headers.put("Page - URL", "url");
        pagesDataJSONStructure.headers.put("When - Date", "date");

        Map<String, String> map = new HashMap<>();

        map.put("title", "Page title 1");
        map.put("url", "https://www.google.com");
        map.put("date", new SimpleDateFormat().format(new Date()));

        pagesDataJSONStructure.hitsPages.add(map);

        map = new HashMap<>();

        map.put("title", "Page title 2");
        map.put("url", "https://www.yandex.com");
        map.put("date", new SimpleDateFormat().format(new Date()));

        pagesDataJSONStructure.hitsPages.add(map);

        //To json
        String json = GSON_BUILDER.create().toJson(pagesDataJSONStructure);

        System.out.println(json);

        //From json
        PageJsonStructure inputJSON = GSON_BUILDER.create().fromJson(json, PageJsonStructure.class);

        XLSXExporter xLSXExporter = new XLSXExporter();

        try (OutputStream o = new FileOutputStream("test-file.xlsx")) {
            xLSXExporter.export(o, inputJSON.title, inputJSON.headers, inputJSON.hitsPages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
