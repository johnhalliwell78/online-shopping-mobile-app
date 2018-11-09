package day03.giangth.onlinemanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import day03.giangth.onlinemanagement.R;
import day03.giangth.onlinemanagement.ultil.CheckConnection;
import day03.giangth.onlinemanagement.ultil.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PurchaseInformation extends AppCompatActivity {
    EditText txtNamePurchase, txtEmailPurchase, txtPhonePurchase;
    Button btnConfirmPurchase, btnBackToOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_information);
        direct();
        btnBackToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            EventButton();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Check Your Connection");
        }
    }

    private void EventButton() {
        btnConfirmPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String namePurchase = txtNamePurchase.getText().toString().trim();
                final String phonePurchase = txtPhonePurchase.getText().toString().trim();
                final String emailPurchase = txtEmailPurchase.getText().toString().trim();

                if (namePurchase.length() > 0 && phonePurchase.length() > 0 && emailPurchase.length() > 0) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLOrder, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String idorder) {
                            Log.d("madonhang", idorder);
                            if (Integer.parseInt(idorder) > 0) {
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.URLOrderInformation, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")) {
                                            MainActivity.orderArrayList.clear();
                                            CheckConnection.showToast_Short(getApplicationContext(), "succesfull");
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.showToast_Short(getApplicationContext(), "continue order");
                                        } else {
                                            CheckConnection.showToast_Short(getApplicationContext(), "Error Order");
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0; i < MainActivity.orderArrayList.size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("idorder", idorder);
                                                jsonObject.put("idproduct", MainActivity.orderArrayList.get(i).getId());
                                                jsonObject.put("nameproduct", MainActivity.orderArrayList.get(i).getName());
                                                jsonObject.put("priceproduct", MainActivity.orderArrayList.get(i).getPrice());
                                                jsonObject.put("numberproduct", MainActivity.orderArrayList.get(i).getNumber());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("namepurchase", namePurchase);
                            hashMap.put("phonepurchase", phonePurchase);
                            hashMap.put("emailpurchase", emailPurchase);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);


                } else {
                    CheckConnection.showToast_Short(getApplicationContext(), "Check your Purchase Information");
                }

            }
        });
    }

    private void direct() {
        txtNamePurchase = (EditText) findViewById(R.id.txtNamePurchase);
        txtEmailPurchase = (EditText) findViewById(R.id.txtEmailPurchase);
        txtPhonePurchase = (EditText) findViewById(R.id.txtPhonePurchase);

        btnConfirmPurchase = (Button) findViewById(R.id.btnConfirmPurchaseInformation);
        btnBackToOrder = (Button) findViewById(R.id.btnBackToOrder);
    }
}
