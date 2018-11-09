package day03.giangth.onlinemanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import day03.giangth.onlinemanagement.R;
import day03.giangth.onlinemanagement.adapter.ProductAdapter;
import day03.giangth.onlinemanagement.adapter.TypeDeviceAdapter;
import day03.giangth.onlinemanagement.model.Order;
import day03.giangth.onlinemanagement.model.Product;
import day03.giangth.onlinemanagement.model.TypeDevice;
import day03.giangth.onlinemanagement.ultil.CheckConnection;
import day03.giangth.onlinemanagement.ultil.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    DrawerLayout mainDrawerLayout;
    Toolbar mainToolbar;
    ViewFlipper mainViewFlipper;
    RecyclerView mainRecyclerView;
    NavigationView mainNavigationView;
    ListView mainListView;
    ArrayList<TypeDevice> typeDeviceArrayList;
    ArrayList<Product> productArrayList;
    TypeDeviceAdapter typeDeviceAdapter;
    ProductAdapter productAdapter;
    int id = 0;
    String nameTypeDevice = "";
    String imgTypeDevice = "";
    public static ArrayList<Order> orderArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        direct();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionBar();
            ActionViewFlipper();
            GetDataTypeDevice();
            GetDataBrandNewProduct();
            CatchOnItemListView();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Check Your Connection!!!");
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

    private void CatchOnItemListView() {
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Check your connection!!!");
                        }
                        mainDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, PhoneActivity.class);
                            intent.putExtra("idtype", typeDeviceArrayList.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Check your connection!!!");
                        }
                        mainDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("idtype", typeDeviceArrayList.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Check your connection!!!");
                        }
                        mainDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Check your connection!!!");
                        }
                        mainDrawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Check your connection!!!");
                        }
                        mainDrawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });
    }

    private void GetDataBrandNewProduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.URLBrandNewProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int ID = 0;
                    String nameProduct = "";
                    Integer priceProduct = 0;
                    String imgProduct = "";
                    String descriptionProduct = "";
                    int IDType = 0;

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            nameProduct = jsonObject.getString("name");
                            priceProduct = jsonObject.getInt("price");
                            imgProduct = jsonObject.getString("image");
                            descriptionProduct = jsonObject.getString("description");
                            IDType = jsonObject.getInt("idtype");
                            productArrayList.add(new Product(ID, nameProduct, priceProduct, imgProduct, descriptionProduct, IDType));
                            productAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDataTypeDevice() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.URLTypeDevice, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            nameTypeDevice = jsonObject.getString("name");
                            imgTypeDevice = jsonObject.getString("image");
                            typeDeviceArrayList.add(new TypeDevice(id, nameTypeDevice, imgTypeDevice));
                            typeDeviceAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    typeDeviceArrayList.add(3, new TypeDevice(0, "Contact", "https://cdn2.iconfinder.com/data/icons/seo-smart-pack/128/grey_new_seo2-41-512.png"));
                    typeDeviceArrayList.add(4, new TypeDevice(0, "Information", "https://png.icons8.com/metro/1600/info.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> advertisementList = new ArrayList<>();
        advertisementList.add("https://cdn.tgdd.vn/Files/2017/03/09/958798/son-tung-oppo-f3_800x450.jpg");
        advertisementList.add("http://img.saobiz.net/d/2016/08/lo-bang-chung-vu-ao-gucci-fake-cua-son-tung-mtp-co-lien-quan-den-hari-won_11.jpg");
        advertisementList.add("http://xinhmoingay.vn/wp-content/uploads/2017/04/Chanel-Coco-Noir-2-6.jpg");

        for (int i = 0; i < advertisementList.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(advertisementList.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mainViewFlipper.addView(imageView);

        }
        mainViewFlipper.setFlipInterval(5000);
        mainViewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);

        mainViewFlipper.setInAnimation(animation_slide_in);
        mainViewFlipper.setOutAnimation(animation_slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mainToolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void direct() {
        mainViewFlipper = (ViewFlipper) findViewById(R.id.mainViewFlipper);
        mainListView = (ListView) findViewById(R.id.mainListView);
        mainNavigationView = (NavigationView) findViewById(R.id.mainNavigationView);
        mainRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        mainDrawerLayout = (DrawerLayout) findViewById(R.id.mainDrawerLayout);
        typeDeviceArrayList = new ArrayList<>();
        typeDeviceArrayList.add(0, new TypeDevice(0, "Main", "https://png.icons8.com/metro/1600/home.png"));
        typeDeviceAdapter = new TypeDeviceAdapter(typeDeviceArrayList, getApplicationContext());
        mainListView.setAdapter(typeDeviceAdapter);
        productArrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(), productArrayList);
        mainRecyclerView.setHasFixedSize(true);
        mainRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        mainRecyclerView.setAdapter(productAdapter);
        if (orderArrayList != null) {

        } else {
            orderArrayList = new ArrayList<>();
        }
    }
}
