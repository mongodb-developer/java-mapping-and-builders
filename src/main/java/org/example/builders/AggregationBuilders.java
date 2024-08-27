package org.example.builders;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.example.model.Product;

import java.util.Arrays;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class AggregationBuilders {
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

            runAverageRatingPipeline(collection);
            updateLowStockProducts(collection);
        }
    }

    // Show top 3 highest rated products details
    private static void runAverageRatingPipeline(MongoCollection<Product> collection) {
        AggregateIterable<Product> result = collection.aggregate(Arrays.asList(
                unwind("$reviews"),
                group("$_id",
                        avg("averageRating", "$reviews.rating"),
                        first("productName", "$productName"),
                        first("category", "$category"),
                        first("price", "$price"),
                        first("brand", "$brand"),
                        first("stock", "$stock")
                ),
                match(gte("averageRating", 4.5)),
                sort(Sorts.descending("averageRating")),
                project(fields(
                        excludeId(),
                        include("productName", "category", "price", "brand", "stock")
                )),
                limit(3)
                )
        );

        System.out.println("High-Rated Products:");
        for (Product doc : result) {
            System.out.println(doc.toProductDetails());
        }
    }


    // Update stock for products that have less than 50 units in stock using Update Builders.
    private static void updateLowStockProducts(MongoCollection<Product> collection) {
        collection.updateMany(
                lt("stock", 50),
                Updates.inc("stock", 20)
        );
        System.out.println("Update: Increased stock for products with less than 50 units.");
    }

}
