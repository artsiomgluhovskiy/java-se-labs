package org.art.java_core.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.art.java_core.json.model.Interests;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InterestsModelDeserializer implements JsonDeserializer<Interests> {

    @Override
    public Interests deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject interestsObject = json.getAsJsonObject();
        Gson gson = new Gson();
        Interests interests = new Interests();
        List<String> hobbies = new ArrayList<>();
        JsonElement hobbiesElement = interestsObject.get("hobbies");
        if (hobbiesElement.isJsonArray()) {
            hobbies = gson.fromJson(hobbiesElement, new TypeToken<List<String>>(){}.getType());
        } else {
            String hobby = gson.fromJson(hobbiesElement, String.class);
            hobbies.add(hobby);
        }
        interests.setHobbies(hobbies);
        interests.setLiterature(gson.fromJson(interestsObject.get("literature"), String.class));
        return interests;
    }
}
