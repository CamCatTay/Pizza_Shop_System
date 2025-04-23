package pizza_shop_system.order.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class JSONUtil {
    // {} Object [] Array

    // Loads a JSON array
    public JSONArray loadJSONArray(String filePath) throws IOException {
        File file = new File(filePath);
        JSONArray data;
        if (!file.exists()) {
            data = new JSONArray(); // Returns empty array if file does not exist
        } else {
            String content = new String(Files.readAllBytes(file.toPath()));
            data = new JSONArray(content);
        }
        return data;
    }

    // Loads a JSON object
    public JSONObject loadJSONObject(String filePath) throws IOException {
        File file = new File(filePath);
        JSONObject data;

        if (!file.exists()) {
            System.out.println("File does not exist " + filePath);
            data = new JSONObject(); // Returns an empty object if file does not exist
        } else {
            String content = new String(Files.readAllBytes(file.toPath()));
            data = new JSONObject(content);
        }

        return data;
    }

    // Save contents of JSON array
    public void saveJSONArray(String filePath, JSONArray contents) throws IOException {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(contents.toString(4));
        }
    }

    // Save contents of JSON object
    public void saveJSONObject(String filePath, JSONObject contents) throws IOException {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(contents.toString(4));
        }
    }
}
