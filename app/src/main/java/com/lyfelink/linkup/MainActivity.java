package com.lyfelink.linkup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
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
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener{


    String[] options;
    String[] optionHeaders;
    int [] optionImgs ={R.drawable.linkup, R.drawable.wslink,
                            R.drawable.location, R.drawable.atom2,
                            R.drawable.phone, R.drawable.strike,
                            R.drawable.picture, R.drawable.circle,
                            R.drawable.star, R.drawable.shield};
    String[] optionDescriptions;

    ListView list;

    final int RQS_GooglePlayServices = 1;

    ImageButton imgBtnLinkup_NFC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Resources res =getResources();
        options = res.getStringArray(R.array.Options);
        optionHeaders = res.getStringArray(R.array.OptionHeaders);
        optionDescriptions = res.getStringArray(R.array.OptionDesc);

        //pass context and data to custom adapter
        final LVAdapter_Main adapter =new LVAdapter_Main();

        //Get list View from activity_main.xml
        list = (ListView) findViewById(R.id.listViewMain);

        //Get rows
        List<Row> rows = generateData(); //thread this?

        //Set List Adapter
        adapter.setRows(rows);
        list.setAdapter(adapter);

        //Set list view onClickItemListener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                //TextView text = (TextView) view.findViewById(R.id.txtViewOption);
                //String item = (String) text.getText();

                String item = position +"";

                //Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();

                int itemPosition =Integer.parseInt(item);

                 switch(itemPosition){

                     case 1: //Add new contact
                         //todo
                         break;

                     case 2: //Link a website
                         launch_linkWebsiteAct();
                         break;

                     case 3: //Link a location
                         launch_linkAddrAct();
                         break;

                     case 4: //Link social media
                         //todo
                         break;

                     case 5: //Link phone number
                         launch_linkPhoneAct();
                         break;

                     case 6: //Link business card
                         //todo
                         break;

                     case 7: //Link photo/logo
                         //todo
                         break;

                     case 9: //View profile
                         //todo
                         break;

                     case 10: //View contacts
                         launch_contactList();
                         break;

                     case 12: //Security
                         //todo
                         break;

                     default:
                         break;
                 }//switch END
            }
        });

        // NFC BTN
        imgBtnLinkup_NFC = (ImageButton) findViewById(R.id.imageBtnLinkup);
        imgBtnLinkup_NFC.setOnClickListener(this);


    }

    private List<Row> generateData(){
        List<Row> rows = new ArrayList<Row>();

        rows.add(new Header(optionHeaders[0]));
        rows.add(new Item(optionImgs[0], options[0], optionDescriptions[0], false));
        rows.add(new Item(optionImgs[1], options[1], optionDescriptions[1], false));
        rows.add(new Item(optionImgs[2], options[2], optionDescriptions[2], false));
        rows.add(new Item(optionImgs[3], options[3], optionDescriptions[3], false));
        rows.add(new Item(optionImgs[4], options[4], optionDescriptions[4], false));
        rows.add(new Item(optionImgs[5], options[5], optionDescriptions[5], false));
        rows.add(new Item(optionImgs[6], options[6], optionDescriptions[6], false));
        rows.add(new Header(optionHeaders[1]));
        rows.add(new Item(optionImgs[7], options[7], optionDescriptions[7], false));
        rows.add(new Item(optionImgs[8], options[8], optionDescriptions[8], false));
        rows.add(new Header(optionHeaders[2]));
        rows.add(new Item(optionImgs[9], options[9], optionDescriptions[9], false));

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
        //startActivity(new Intent("com.lyfelink.linkup.ContactListActivity"));
    }

    private void launch_contactList() {
        startActivity(new Intent("com.lyfelink.linkup.ContactListActivity"));
    }
    private void launch_linkWebsiteAct() {
        startActivity(new Intent("com.lyfelink.linkup.LinkListActivity"));
    }
    private void launch_linkPhoneAct() {
        startActivity(new Intent("com.lyfelink.linkup.LinkPhoneActivity"));
    }
    private void launch_linkAddrAct() {
        startActivity(new Intent("com.lyfelink.linkup.LinkAddrActivity"));
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
