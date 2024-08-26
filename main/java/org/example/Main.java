package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.example.model.Image;
import org.example.model.Product;
import org.example.model.Reviews;
import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Main {
    public static void main(String[] args) {
        ConnectionString connectionString = new ConnectionString(System.getenv("mongodb.uri"));
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();
        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
            MongoDatabase database = mongoClient.getDatabase("ecommerce");
            MongoCollection<Product> collection = database.getCollection("productDetails", Product.class);

            Product newProduct = new Product();
            newProduct.setId(new ObjectId());
            newProduct.setProductName("Wireless Mouse");
            newProduct.setCategory("Electronics");
            newProduct.setPrice(29.99);
            newProduct.setStock(150);
            newProduct.setDescription("Ergonomic wireless mouse with adjustable DPI settings.");
            newProduct.setBrand("TechBrand");
            newProduct.setFeatures("2.4 GHz wireless connectivity, Adjustable DPI from 800 to 1600, Compatible with Windows and macOS");

            List<Image> images = new ArrayList<>();
            images.add(new Image("https://example.com/images/wireless_mouse_1.jpg", "Wireless Mouse Top View"));
            images.add(new Image("https://example.com/images/wireless_mouse_2.jpg", "Wireless Mouse Side View"));
            newProduct.setImages(images);

            List<Reviews> reviews = new ArrayList<>();
            reviews.add(new Reviews(new ObjectId("66cc67443921db64869bcf3d"), 4.5, "Great mouse, very comfortable to use!", "2024-08-18T19:00:00Z"));
            newProduct.setReviews(reviews);

            collection.insertOne(newProduct);
            System.out.println("Inserted product: " + newProduct);

            Product foundProduct = collection.find(Filters.eq("productName", "Wireless Mouse")).first();
            System.out.println("Found product: " + foundProduct);

            collection.updateOne(Filters.eq("_id", newProduct.getId()), Updates.set("stock", 140));
            Product updatedProduct = collection.find(Filters.eq("_id", newProduct.getId())).first();
            System.out.println("Updated product stock: " + updatedProduct.getStock());

            collection.deleteOne(Filters.eq("_id", newProduct.getId()));
            System.out.println("Deleted product: " + newProduct.getProductName());
        }
    }
}
