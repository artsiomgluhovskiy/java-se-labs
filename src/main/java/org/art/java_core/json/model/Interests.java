package org.art.java_core.json.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interests {

    @SerializedName("literature")
    @Expose
    private String literature;

    @SerializedName("hobbies")
    @Expose
    private List<String> hobbies;
}
