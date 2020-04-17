package demo;

import static spark.Spark.*;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
//import com.sun.tools.javac.util.List;
import org.bson.Document;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SparkDemo {
  public static void main(String[] args) {
    // open connection
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    // get ref to database
    MongoDatabase db = mongoClient.getDatabase("MyDatabase");
    // get ref to collection
    MongoCollection<Document> userCollection = db.getCollection("Users");

    // Init Gson
    Gson gson = new Gson();
    port(1235);
    // 2 way communication
    webSocket("/ws", WebSocketHandler.class); // open socket and leave it open
    get("/hello", (req, res) -> "hi"); // test
    post("/api/authenticate", (req, res) -> {
      String bodyString = req.body();
      AuthDto authDto = gson.fromJson(bodyString, AuthDto.class);
      List<Document> potentialUser = userCollection.find(new Document("username", authDto.username))
              .into(new ArrayList<>());
      if (potentialUser.size() != 1){
        AuthResponseDto responseDto = new AuthResponseDto(false, "user not found");
        return gson.toJson(responseDto);
      }
      Document userDocument = potentialUser.get(0);
      if(!userDocument.getString("password").equals(authDto.password)){
        AuthResponseDto responseDto = new AuthResponseDto(false, "password doesn't match");
        return gson.toJson(responseDto);
      }
      AuthResponseDto responseDto = new AuthResponseDto(true, null);
      return gson.toJson(responseDto);

    });
    post("/api/register", (req, res) -> {
      String bodyString = req.body();
      AuthDto authDto = gson.fromJson(bodyString, AuthDto.class);

      List<Document> potentialUser = userCollection.find(new Document("username", authDto.username))
              .into(new ArrayList<>());

      if(!potentialUser.isEmpty()){
        AuthResponseDto responseDto = new AuthResponseDto(false, "user already exists");
        return gson.toJson(responseDto);
      }

      Document newUser = new Document()
              .append("username", authDto.username)
              .append("password", authDto.password);
      userCollection.insertOne(newUser);
      AuthResponseDto authResponseDto = new AuthResponseDto(true, null);
      return gson.toJson(authResponseDto);
    });
  }
}
