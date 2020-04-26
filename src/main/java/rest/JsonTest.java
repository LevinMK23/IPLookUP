package rest;

import com.google.gson.Gson;

public class JsonTest {
    public static void main(String[] args) {
        User u1 = new User("AAA", 12);
        Gson gson = new Gson();
        String user1 = gson.toJson(u1);
        System.out.println(user1);
        User u2 = gson.fromJson(user1, User.class);
        System.out.println(u2);

    }
}
