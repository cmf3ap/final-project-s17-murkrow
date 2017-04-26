package cs4720.cs.virginia.edu.hoowantsbrunch;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static android.content.ContentValues.TAG;

/**
 * Created by Shannon on 4/24/2017.
 */

public class userReview implements java.io.Serializable {

    public String name;
    public static String review;
    public String restaurant;
    public transient int starRating;

    public userReview(String newName, String newReview, String newRestaurant, int newStarRating){
        name = newName;
        review = newReview;
        restaurant = newRestaurant;
        starRating = newStarRating;
    }

    public userReview() {
        name = "";
        review = "";
        restaurant = "";
        starRating = -1;
    }

    public static void writeToFile(userReview newReview){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("/tmp/reviewsZZZ.ser");
            //new FileOutputStream("C:\\Users\\Shannon\\Documents\\UVA\\CS 4720\\reviews.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(newReview);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    public static void readFromFile (){
        userReview e = null;
        try {
            FileInputStream fileIn = new FileInputStream("/tmp/reviewsZZZ.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (userReview) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }

        Log.d(TAG, "readFromFile: " + e.name);
        Log.d(TAG, "readFromFile: " + e.review);
        Log.d(TAG, "readFromFile: " + e.restaurant);
        Log.d(TAG, "readFromFile: " + e.starRating);

        System.out.println("Deserialized Review...");
        System.out.println("Name: " + e.name);
        System.out.println("Review content: " + e.review);
        System.out.println("Restuarant: " + e.restaurant);
        System.out.println("Number: " + e.starRating);
    }

}
