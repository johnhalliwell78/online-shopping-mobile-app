package day03.giangth.onlinemanagement.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import day03.giangth.onlinemanagement.R;
import day03.giangth.onlinemanagement.adapter.OrderAdapter;
import day03.giangth.onlinemanagement.ultil.CheckConnection;

import java.text.DecimalFormat;

public class Order extends AppCompatActivity {

    ListView listViewOrder;
    TextView txtNotice;
    static TextView txtPrice;
    Button btnConfirm, btnKeep;
    android.support.v7.widget.Toolbar toolbarOrder;
    OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        direct();
        ActionToolbar();
        CheckData();
        EventUltil();
        CatchOnItemListView();
        EventButton();
    }

    private void EventButton() {
        btnKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.orderArrayList.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(), PurchaseInformation.class);
                    startActivity(intent);
                } else {
                    CheckConnection.showToast_Short(getApplicationContext(), "Empty Order!");
                }
            }
        });
    }

    private void CatchOnItemListView() {
        listViewOrder.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Order.this);
                builder.setTitle("Confirm Delete Product");
                builder.setMessage("Arre You Surre");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.orderArrayList.size() <= 0) {
                            txtNotice.setVisibility(View.VISIBLE);

                        } else {
                            MainActivity.orderArrayList.remove(position);
                            orderAdapter.notifyDataSetChanged();
                            EventUltil();
                            if (MainActivity.orderArrayList.size() <= 0) {
                                txtNotice.setVisibility(View.VISIBLE);
                            } else {
                                txtNotice.setVisibility(View.INVISIBLE);
                                orderAdapter.notifyDataSetChanged();
                                EventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        orderAdapter.notifyDataSetChanged();
                        EventUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventUltil() {
        long priceOrder = 0;
        for (int i = 0; i < MainActivity.orderArrayList.size(); i++) {
            priceOrder += MainActivity.orderArrayList.get(i).getPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtPrice.setText(decimalFormat.format(priceOrder) + " D");
    }

    private void CheckData() {
        if (MainActivity.orderArrayList.size() <= 0) {
            orderAdapter.notifyDataSetChanged();
            txtNotice.setVisibility(View.VISIBLE);
            listViewOrder.setVisibility(View.INVISIBLE);
        } else {
            orderAdapter.notifyDataSetChanged();
            txtNotice.setVisibility(View.INVISIBLE);
            listViewOrder.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarOrder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarOrder.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void direct() {
        listViewOrder = (ListView) findViewById(R.id.listviewOrder);
        txtNotice = (TextView) findViewById(R.id.txtNotice);
        txtPrice = (TextView) findViewById(R.id.txtTongTien);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnKeep = (Button) findViewById(R.id.btnKeep);
        toolbarOrder = (android.support.v7.widget.Toolbar) findViewById(R.id.toobarOrder);
        orderAdapter = new OrderAdapter(Order.this, MainActivity.orderArrayList);
        listViewOrder.setAdapter(orderAdapter);

    }
}
