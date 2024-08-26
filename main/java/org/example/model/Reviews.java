package org.example.model;
import org.bson.types.ObjectId;
import java.util.Date;

public class Reviews {
    private ObjectId customerId;
    private double rating;
    private String comment;
    private String date;

    public Reviews() {
    }

    public Reviews(ObjectId customerId, double rating, String comment, String date) {
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public ObjectId getCustomerId() {
        return customerId;
    }

    public void setCustomerId(ObjectId customerId) {
        this.customerId = customerId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reviews{" +
                "reviewerId=" + customerId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", reviewDate=" + date +
                '}';
    }

}

