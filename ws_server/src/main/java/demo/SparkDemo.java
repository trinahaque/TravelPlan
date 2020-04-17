package demo;

import static spark.Spark.*;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class SparkDemo {
  public static void main(String[] args) {
    // open connection
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    // get ref to database
    MongoDatabase db = mongoClient.getDatabase("MyDatabase");
    // get ref to collection
    MongoCollection<Document> userCollection = db.getCollection("Users");

    port(1235);
    // 2 way communication
    webSocket("/ws", WebSocketHandler.class); // open socket and leave it open
    get("/hello", (req, res) -> "hi"); // test
  }
}