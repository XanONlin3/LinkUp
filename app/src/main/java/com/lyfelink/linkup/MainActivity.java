package com.lyfelink.linkup;

import com.lyfelink.linkup.LVAdapter_Main.Row;
import com.lyfelink.linkup.LVAdapter_Main.Header;
import com.lyfelink.linkup.LVAdapter_Main.Item;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener{


    String[] options;
    String[] optionHeaders;
    /*int [] optionImgs ={R.drawable.linkup, R.drawable.wslink, R.drawable.location, R.drawable.soc_media,
                         R.drawable.phone, R.drawable.strike, R.drawable.picture, R.drawable.circle,
                         R.drawable.star, R.drawable.shield}; */
    String[] optionDescriptions;

    ListView list;

    ImageButton imgBtnLinkup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Resources res =getResources();
        options = res.getStringArray(R.array.Options);
        optionHeaders = res.getStringArray(R.array.OptionHeaders);
        optionDescriptions = res.getStringArray(R.array.OptionDesc);

        //pass context and data to custom adapter
        LVAdapter_Main adapter =new LVAdapter_Main();

        //Get list View from activity_main.xml
        list = (ListView) findViewById(R.id.listViewMain);

        //Get rows
        List<Row> rows = generateData();

        //Set List Adapter
        adapter.setRows(rows);
        list.setAdapter(adapter);

        imgBtnLinkup = (ImageButton) findViewById(R.id.imageBtnLinkup);
        imgBtnLinkup.setOnClickListener(this);
    }

    private List<Row> generateData(){
        List<Row> rows = new ArrayList<Row>();

        rows.add(new Header(optionHeaders[0]));
        rows.add(new Item(R.drawable.linkup, options[0], optionDescriptions[0], false));
        rows.add(new Item(R.drawable.wslink, options[1], optionDescriptions[1], false));
        rows.add(new Item(R.drawable.location, options[2], optionDescriptions[2], false));
        rows.add(new Item(R.drawable.soc_media, options[3], optionDescriptions[3], false));
        rows.add(new Item(R.drawable.phone, options[4], optionDescriptions[4], false));
        rows.add(new Item(R.drawable.strike, options[5], optionDescriptions[5], false));
        rows.add(new Item(R.drawable.picture, options[6], optionDescriptions[6], false));
        rows.add(new Header(optionHeaders[1]));
        rows.add(new Item(R.drawable.circle, options[7], optionDescriptions[7], false));
        rows.add(new Item(R.drawable.star, options[8], optionDescriptions[8], false));
        rows.add(new Header(optionHeaders[2]));
        rows.add(new Item(R.drawable.shield, options[9], optionDescriptions[9], false));

        return rows;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void imgBtnClick(){
        startActivity(new Intent("com.lyfelink.linkup.ContactListActivity"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.imageBtnLinkup:
                imgBtnClick();
                break;
        }
    }
}
