package com.wxp.customview;

import android.app.Fragment;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements OnFragmentInteractionListener {

    private ListView mLv = null;
    private ArrayAdapter<String> mAdapter = null;
    private List<String> mAllViews = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String[] views = getResources().getStringArray(R.array.views);
        for (String view : views) {
            mAllViews.add(view);
        }
        mAdapter = new ArrayAdapter<String>(this,R.layout.main_recydemo_item,R.id.id_item_name,mAllViews);
        mLv = (ListView) findViewById(R.id.id_main_lv);
        mLv.setAdapter(mAdapter);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Class clazz = Class.forName(MainActivity.this.getPackageName() + "." + mAllViews.get(position));
                    Fragment fragment = (Fragment) clazz.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.id_main_frame, fragment).commit();
                } catch (Exception e) {

                }

            }
        });

        try {
            Class clazz = Class.forName(MainActivity.this.getPackageName() + "." + mAllViews.get(0));
            Fragment fragmentInit = (Fragment) clazz.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.id_main_frame, fragmentInit).commit();
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
