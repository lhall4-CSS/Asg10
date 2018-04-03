package com.ljedesign.landonhall.participation10;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.List;

/**
 * Custom Array Adapter to populate custom row
 */

//custom ArrayAdapter
class CommentArrayAdapter extends ArrayAdapter<Comment> {

    private Context context;
    private List<Comment> commentList;
    private CommentsDataSource datasource;

    //constructor, call on creation
    public CommentArrayAdapter(Context context, int resource, List<Comment> objects) {
        super(context, resource, objects);

        this.context = context;
        this.commentList = objects;
        datasource = new CommentsDataSource(context);
        datasource.open();
    }

    //called when rendering the list
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the property we are displaying
        Comment comment = commentList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.item, null);

        TextView commentTV = (TextView) view.findViewById(R.id.commentTV); //grab Text View
        final RadioGroup radioG = (RadioGroup) view.findViewById(R.id.defaultRadioG); // grab radio group
        commentTV.setText(comment.toString()); //Set Comment Field
        if(comment.getRating() != null) { //check if rating is null
            int commentRating = Integer.parseInt(comment.getRating());
            //Check radio button based off of rating
            switch (commentRating) {
                case 1:
                    radioG.check(R.id.rating1);
                    break;
                case 2:
                    radioG.check(R.id.rating2);
                    break;
                case 3:
                    radioG.check(R.id.rating3);
                    break;
                case 4:
                    radioG.check(R.id.rating4);
                    break;
                case 5:
                    radioG.check(R.id.rating5);
                    break;
            }
        }
        //set the radio group id to the comment id
        radioG.setId((int)comment.getId());
        //create an event listener for changing the rating radio button
        radioG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Integer rbID = group.getCheckedRadioButtonId(); //get checked radio button Id
                View selectedRBView = group.findViewById(rbID); //grab radio button
                int selectedRBindx = group.indexOfChild(selectedRBView); //get index of checked radio button in group
                RadioButton selectedRB = (RadioButton) group.getChildAt(selectedRBindx); //get the checked radio button from group based off index
                Integer ratingVal = Integer.parseInt(selectedRB.getText().toString()); //get Value from selected rb and parse int
                datasource.addRating(ratingVal, group.getId()); //update the database with the rating
                refreshComments(datasource.getAllComments()); //refresh list

            }
        });


        return view;
    }
    //Method to refresh List
    public void refreshComments(List<Comment> comments) {
        this.commentList.clear();
        this.commentList.addAll(comments);
        notifyDataSetChanged();
    }
}
