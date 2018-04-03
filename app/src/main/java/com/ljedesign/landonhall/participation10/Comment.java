package com.ljedesign.landonhall.participation10;

/*This class acts as a helper class grabbing and setting data for the comments.*/
public class Comment {
    private long id;
    private String comment;
    private String rating;
//This method gets and returns the ID of the comment.
    public long getId() {
        return id;
    }
//This method set the ID of the comment.
    public void setId(long id) {
        this.id = id;
    }
//This method gets and returns the comment.
    public String getComment() {
        return comment;
    }
//This method sets a new comment taking a string parameter as the comment.
    public void setComment(String comment) {

        this.comment = comment;
    }

    //This method gets and returns the rating
    public String getRating() {
        return rating;
    }

    //This method sets the rating
    public void setRating(String rating) {
       this.rating = rating;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        String returnString;
        if(rating != null) {
           returnString   = comment + " - Rating: " + rating;
        }
        else {
            returnString = comment;
        }
        return returnString;
    }
}