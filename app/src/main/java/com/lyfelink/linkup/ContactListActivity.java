package com.lyfelink.linkup;

import com.lyfelink.linkup.LVAdapter_Contact.Row;
import com.lyfelink.linkup.LVAdapter_Contact.Header;
import com.lyfelink.linkup.LVAdapter_Contact.Item;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Package: com.lyfelink.linkup, Project: LinkUp.
 * Created by Jan on 09.14.2014.
 */
public class ContactListActivity extends ListActivity {

    private LVAdapter_Contact adapter =new LVAdapter_Contact();

    private GestureDetector mGestureDetector;

    private List<Object[]> alphabet = new ArrayList<Object[]>();

    private HashMap<String, Integer> headers = new HashMap<String, Integer>();

    private int sideIndexHeight;
    private static float sideIndex_X;
    private static float sideIndex_Y;
    private int indexListSize;

    //custom gesture detector
    class SideIndexGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distance_X, float distance_Y){

            //Known coordinates of first touch and scroll distance
            sideIndex_X -= distance_X;
            sideIndex_Y -= distance_Y;

            //When user scrolls within side Index we show the
            //proper item in the contact list
            if(sideIndex_X >=0 && sideIndex_Y >=0){
                displayListItem();
            }

            return super.onScroll(e1, e2, distance_X, distance_Y);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lv_contacts);

        mGestureDetector = new GestureDetector(this, new SideIndexGestureListener());

        List <String> contacts = populateContactList();
        Collections.sort(contacts);

        List<Row> rows = new ArrayList<Row>();
        int start =0;
        int end = 0;
        String prevLetter = null;
        Object[] tempIndexItem = null;
        Pattern numberPattern = Pattern.compile("[0-9]");

        for(String contact : contacts) {
            String firstLetter = contact.substring(0, 1);

            //Group numbers together
            if (numberPattern.matcher(firstLetter).matches()){
                firstLetter = "#";
            }

            //If changed to a new letter add prev letter to alphabet scroll
            if(prevLetter != null && !firstLetter.equals(prevLetter)){
                end = rows.size() -1;
                tempIndexItem = new Object[3];
                tempIndexItem[0] = prevLetter.toUpperCase(Locale.US);
                tempIndexItem[1] = start;
                tempIndexItem[2] = end;
                alphabet.add(tempIndexItem);

                start = end + 1;
            }

            //Check if need to add a header row
            if(!firstLetter.equals(prevLetter)){
                rows.add(new Header(firstLetter));
                headers.put(firstLetter, start);
            }

            //Add contact to the list
            rows.add(new Item(contact));
            prevLetter =firstLetter;
        }

        if(prevLetter != null){
            //Save last letter
            tempIndexItem = new Object[3];
            tempIndexItem[0] = prevLetter.toUpperCase(Locale.US);
            tempIndexItem[1] = start;
            tempIndexItem[2] = rows.size() -1;
            alphabet.add(tempIndexItem);
        }

        adapter.setRows(rows);
        setListAdapter(adapter);

        updateList();
    }//onCreate

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        if(mGestureDetector.onTouchEvent(motionEvent)){
            return true;
        }
        else {
            return false;
        }
    }

    //Test Data for listView
    private List<String> populateContactList(){
        List<String> contacts = new ArrayList<String>();
        contacts.add("Lisa Landers");
        contacts.add("Paul Korman");
        contacts.add("Lee May Jones");
        contacts.add("Randolf Rivers");
        contacts.add("Brian Goodan");
        contacts.add("Tina Wills");
        contacts.add("Yeoman Johnson");
        contacts.add("Derik Homesman");
        contacts.add("James Kinder");
        contacts.add("Yancy Penkoff");
        contacts.add("Armin Rodgers");
        contacts.add("Cathy Franson");
        contacts.add("Someguy Kidman");
        contacts.add("Somegirl Kidwoman");
        contacts.add("Fuji San");
        contacts.add("Tower High");
        contacts.add("Herman Waters");
        contacts.add("Alex Wildman");
        contacts.add("Verna Domes");
        contacts.add("0");
        contacts.add("2");
        contacts.add("9");
        //check foreign names > Kanji/Hangul
        //check special characters > @,^,$,@,%
        return contacts;
    }

    public void displayListItem(){
        LinearLayout sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
        sideIndexHeight = sideIndex.getHeight();

        //compute num of px for every side item
        //compute num of px for every side item
        double pixelPerIndexItem = (double) sideIndexHeight/indexListSize;

        //compute item index the given event pos belongs to
        int itemPosition = (int) (sideIndex_Y / pixelPerIndexItem);

        //get the item
        if(itemPosition < alphabet.size()){
            Object[] indexItem = alphabet.get(itemPosition);
            int subitemPosition = headers.get(indexItem[0]);

            //ListView list = (ListView) findViewById(android.R.id.list);
            getListView().setSelection(subitemPosition);
        }
    }

    //Add text to alphabet scroller
    public void updateList(){

        LinearLayout sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
        sideIndex.removeAllViews();
        indexListSize = alphabet.size();
        if(indexListSize < 1){
            return;
        }

        int indexMaxSize = (int) Math.floor(sideIndex.getHeight() / 20);
        int tempIndexListSize = indexListSize;
        while (tempIndexListSize > indexMaxSize){
            tempIndexListSize /= 2;
        }

        double delta;
        if(tempIndexListSize > 0){
            delta = indexListSize / tempIndexListSize;
        }
        else {
            delta = 1;
        }

        TextView tempTV;
        for(double i =1; i <= indexListSize; i += delta){
            Object[] tempIndexItem = alphabet.get((int) i - 1);
            String tempLetter = tempIndexItem[0].toString();

            tempTV = new TextView(this);
            tempTV.setText(tempLetter);
            tempTV.setGravity(Gravity.CENTER);
            tempTV.setTextSize(15);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            tempTV.setLayoutParams(params);
            sideIndex.addView(tempTV);
        }

        sideIndexHeight = sideIndex.getHeight();

        sideIndex.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                //Coordinates of touch
                sideIndex_X = motionEvent.getX();
                sideIndex_Y = motionEvent.getY();

                //Display proper item from contact list
                displayListItem();

                return false;
            }
        });
    }
}
