package org.art.java_core.xlsx;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sheet data model.
 */
public class PageJsonStructure {

    @SerializedName("title")
    public String title;
    @SerializedName("headers")
    public Map<String, String> headers  = new HashMap<>();
    @SerializedName("pages:rows")
    public List<Map<String, String>> hitsPages  = new ArrayList<>();
}
