package rest;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class RestTest {

    static String key = "trnsl.1.1.20180428T123137Z.0b3e938bb39c6483.0e1dce0bee5b7feb2baa60e734a2bd35a73740b2";

    public static void main(String[] args) {
        String word = new Scanner(System.in).nextLine();
        String lang = "en-ru";
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(String.format("https://translate.yandex.net/api/v1.5/tr.json/translate?key=%s&text=%s&lang=%s", key, word, lang))
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            // Get response body
            String jsonAns = Objects.requireNonNull(response.body()).string();
            System.out.println(jsonAns);
            Unit unit = new Gson().fromJson(jsonAns, Unit.class);
            System.out.println(unit.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
