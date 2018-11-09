package day03.giangth.onlinemanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import day03.giangth.onlinemanagement.R;
import day03.giangth.onlinemanagement.adapter.LaptopAdapter;
import day03.giangth.onlinemanagement.model.Product;
import day03.giangth.onlinemanagement.ultil.CheckConnection;
import day03.giangth.onlinemanagement.ultil.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LaptopActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    ListView listView;
    LaptopAdapter laptopAdapter;
    ArrayList<Product> productArrayList;
    int idtype = 0;
    int page = 1;
    View footerView;
    boolean isLoading = false;
    boolean limitData = false;
    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        direct();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            getIDType();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Check your connection!!!");
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuOrder:
                Intent intent = new Intent(getApplicationContext(), day03.giangth.onlinemanagement.activity.Order.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void LoadMoreData() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DescriptionProduct.class);
                intent.putExtra("descriptionProduct", productArrayList.get(position));
                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false && limitData == false) {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }

            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String URLPhone = Server.URLPhone + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLPhone, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String nameLaptop = "";
                int priceLaptop = 0;
                String desLaptop = "";
                String imageLaptop = "";
                int idtype = 0;
                if (response != null && response.length() != 2) {
//                    listView.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            nameLaptop = jsonObject.getString("name");
                            priceLaptop = jsonObject.getInt("price");
                            imageLaptop = jsonObject.getString("image");
                            desLaptop = jsonObject.getString("description");
                            idtype = jsonObject.getInt("idtype");
                            productArrayList.add(new Product(id, nameLaptop, priceLaptop, imageLaptop, desLaptop, idtype));
                            laptopAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    limitData = true;
//                    listView.removeFooterView(footerView);
                    CheckConnection.showToast_Short(getApplicationContext(), "Data's out");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected HashMap<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idtype", String.valueOf(idtype));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getIDType() {
        idtype = getIntent().getIntExtra("idtype", -1);
        Log.d("id type", idtype + "");

    }

    private void direct() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.laptopToolbar);
        listView = (ListView) findViewById(R.id.laptopListView);
        productArrayList = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(getApplicationContext(), productArrayList);
        listView.setAdapter(laptopAdapter);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = layoutInflater.inflate(R.layout.progressbar, null);
        mHandler = new mHandler();
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    listView.addFooterView(footerView);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
