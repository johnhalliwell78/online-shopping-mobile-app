package day03.giangth.onlinemanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.squareup.picasso.Picasso;
import day03.giangth.onlinemanagement.R;
import day03.giangth.onlinemanagement.model.Order;
import day03.giangth.onlinemanagement.model.Product;

import java.text.DecimalFormat;

public class DescriptionProduct extends AppCompatActivity {
    Toolbar toolbarDescriptionProduct;
    ImageView imgDescriptionProduct;
    TextView txtName, txtPrice, txtDescription;
    Spinner spinnerDescriptionProduct;
    Button btnBuy;

    int id = 0;
    String name = "";
    int price = 0;
    String image = "";
    String description = "";
    int idtype = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_product);
        direct();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
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


    private void EventButton() {
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.orderArrayList.size() > 0) {
                    int s1 = Integer.parseInt(spinnerDescriptionProduct.getSelectedItem().toString());
                    boolean exist = false;
                    for (int i = 0; i < MainActivity.orderArrayList.size(); i++) {
                        if (MainActivity.orderArrayList.get(i).getId() == id) {
                            MainActivity.orderArrayList.get(i).setNumber(MainActivity.orderArrayList.get(i).getNumber() + s1);
                            if (MainActivity.orderArrayList.get(i).getNumber() >= 10) {
                                MainActivity.orderArrayList.get(i).setNumber(10);
                            }
                            MainActivity.orderArrayList.get(i).setPrice(price * MainActivity.orderArrayList.get(i).getNumber());
                            exist = true;
                        }
                    }
                    if (exist == false) {
                        int number = Integer.parseInt(spinnerDescriptionProduct.getSelectedItem().toString());
                        long priceOrder = number * price;
                        MainActivity.orderArrayList.add(new Order(id, name, priceOrder, image, number));
                    }

                } else {
                    int number = Integer.parseInt(spinnerDescriptionProduct.getSelectedItem().toString());
                    long priceOrder = number * price;
                    MainActivity.orderArrayList.add(new Order(id, name, priceOrder, image, number));
                }
                Intent intent = new Intent(getApplicationContext(), day03.giangth.onlinemanagement.activity.Order.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] number = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, number);
        spinnerDescriptionProduct.setAdapter(arrayAdapter);
    }

    private void GetInformation() {


        Product product = (Product) getIntent().getSerializableExtra("descriptionProduct");
        id = product.getID();
        name = product.getName();
        price = product.getPrice();
        image = product.getImage();
        description = product.getDescription();
        idtype = product.getIdtype();

        txtName.setText(name);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtPrice.setText("Price: " + decimalFormat.format(price) + " VND");
        txtDescription.setText(description);
        Picasso.with(getApplicationContext()).load(image)
                .placeholder(R.drawable.no)
                .error(R.drawable.loadingimage)
                .into(imgDescriptionProduct);


    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarDescriptionProduct);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDescriptionProduct.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void direct() {
        toolbarDescriptionProduct = (Toolbar) findViewById(R.id.toolbarDescriptionProduct);
        imgDescriptionProduct = (ImageView) findViewById(R.id.imageViewDescriptionProduct);
        txtName = (TextView) findViewById(R.id.txtNameDescriptionProduct);
        txtPrice = (TextView) findViewById(R.id.txtPriceDescriptionProduct);
        txtDescription = (TextView) findViewById(R.id.txtViewDescriptionProduct);
        spinnerDescriptionProduct = (Spinner) findViewById(R.id.spinnerDescriptionProduct);
        btnBuy = (Button) findViewById(R.id.btnBuy);


    }
}
