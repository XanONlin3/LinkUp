package com.lyfelink.linkup;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;


/**
 * Package: com.lyfelink.linkup, Project: LinkUp.
 * Created by Jan on 10.10.2014.
 */
public class LinkAddrActivity extends Activity {

    private GoogleMap gMap;
    private EditText etStr;

    final int RQS_GooglePlayServices = 1;

    private TextWatcher watcher= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void afterTextChanged(Editable editable) {
            checkFieldForEmptyValue();
        }
    };

    void checkFieldForEmptyValue(){

        Button saveBtn = (Button) findViewById(R.id.btn_save);

        String s = etStr.getText().toString();

        if(s.equals("")){
            saveBtn.setEnabled(false);
            saveBtn.setBackgroundColor(Color.GRAY);
        }
        else{
            saveBtn.setEnabled(true);
            saveBtn.setBackgroundResource(R.color.blue);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.link_addr);

        Resources res = getResources();

        etStr = (EditText)findViewById(R.id.et_address);
        etStr.setText("");
        etStr.setHint(res.getString(R.string.addr_hint));

        checkFieldForEmptyValue();

    }

    @Override
    protected void onResume() {
        super.onResume();

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        if (resultCode == ConnectionResult.SUCCESS){
           /* Toast.makeText(getApplicationContext(),
                    "isGooglePlayServicesAvailable SUCCESS",
                    Toast.LENGTH_LONG).show();*/

            gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            gMap.setMyLocationEnabled(true);
            gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        }else{
            GooglePlayServicesUtil.getErrorDialog(resultCode, this, RQS_GooglePlayServices).show();
        }
    }

    /*private void onLocationTxtEnter(LatLng point){

        gMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        tvLocInfo.setText(point.toString());
        myMap.animateCamera(CameraUpdateFactory.newLatLng(point));

    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                                    // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
