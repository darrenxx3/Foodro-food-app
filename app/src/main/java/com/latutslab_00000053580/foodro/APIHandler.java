package com.latutslab_00000053580.foodro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.latutslab_00000053580.foodro_home.MainActivity;
import com.latutslab_00000053580.recycler.HistoryAdapter;
import com.latutslab_00000053580.recycler.MenuAdapter;
import com.latutslab_00000053580.recycler.MenuUserAdapter;
import com.latutslab_00000053580.recycler.MerchantAdapter;
import com.latutslab_00000053580.recycler.OrderAdapter;
import com.latutslab_00000053580.sqlite.DbUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class APIHandler {
//    private String endpoint = "https://foodro.000webhostapp.com/api/";
//    private String imageUrl = "https://foodro.000webhostapp.com/";

    private String endpoint = "http://192.168.100.23:3002/api/";
    private String imageUrl = "http://192.168.100.23:3002/";

    public void login(Context context, String email, String password) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, endpoint + "login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);
                    int isSuccess = respObj.getInt("success");
                    if (isSuccess == 1) {
                        Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show();
                    }
                    //String success = respObj.getString("success");
                    JSONArray data = respObj.getJSONArray("data");
                    JSONObject a = data.getJSONObject(0);

                    User user = new User(
                            a.getInt("user_id"),
                            a.getString("firstname"),
                            a.getString("lastname"),
                            a.getString("email"),
                            a.getInt("role_id"),
                            a.getInt("active"),
                            imageUrl + a.getString("image")
                    );

                    DbUser dbUser = new DbUser(context);
                    dbUser.open();
                    dbUser.addUser(user);
                    dbUser.close();
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("LOGIN", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {

                    if (error.networkResponse.statusCode == 400) {
                        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    } else if (error.networkResponse.statusCode == 401) {
                        Toast.makeText(context, "Wrong email or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Uknown Error:" + e.toString(), Toast.LENGTH_SHORT).show();
                }


            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        queue.add(sr);
    }

    public void register(Context context, int role, String firstname, String lastname, String password, String email) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final User[] user = new User[1];

        StringRequest sr = new StringRequest(Request.Method.POST, endpoint + "register.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);
                    int isSuccess = respObj.getInt("success");
                    if (isSuccess == 1) {
                        Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Error, please check your connection" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("role", Integer.toString(role));
                params.put("firstname", firstname);
                params.put("lastname", lastname);
                params.put("email", email);
                params.put("password", password);
                params.put("image", "");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    // list semua merchant
    public void getAllMerchant(Context context, RecyclerView recyclerView) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.GET, endpoint + "getAllMerchant.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);
                    //String success = respObj.getString("success");
                    JSONArray data = respObj.getJSONArray("data");
                    ArrayList<User> users = new ArrayList<User>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject a = data.getJSONObject(i);

                        users.add(
                                new User(
                                        a.getInt("user_id"),
                                        a.getString("firstname"),
                                        a.getString("lastname"),
                                        a.getString("email"),
                                        a.getInt("role_id"),
                                        a.getInt("active"),
                                        imageUrl + a.getString("image")
                                )
                        );
                    }

                    MerchantAdapter adapter = new MerchantAdapter(users, context);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);

                    Toast.makeText(context, "Complete", Toast.LENGTH_SHORT).show();
                    Log.i("VOLLEYDONE", "DONE");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("VOLLEYERROCATCH", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    Log.i("VOLLEY", String.valueOf(error.networkResponse.statusCode));
//                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Error, please check your connection", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(context, "Unknow Error: " + error, Toast.LENGTH_LONG).show();
                }

            }
        });
        queue.add(sr);
    }

    // list semua incoming order (diliat sama merchant)
    public void getOrderMerchant(Context context, int merchant_id, RecyclerView orderRV, boolean history, CardView cardView) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest sr = new StringRequest(Request.Method.POST, endpoint + "getOrderMerchant.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Order> orders = new ArrayList<Order>();
                try {
                    Log.i("JALAN", "2");
                    JSONObject respObj = new JSONObject(response);

                    //String success = respObj.getString("success");
                    JSONArray data = respObj.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject a = data.getJSONObject(i);

                        ArrayList<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
                        JSONArray details = a.getJSONArray("orderDetail");

                        for (int j = 0; j < details.length(); j++) {

                            JSONObject detail = details.getJSONObject(j);
                            JSONObject foodJson = detail.getJSONObject("food");

                            if (history) {
                                if (details.getJSONObject(0).getInt("status_id") == 3) {
                                    Food food = new Food(
                                            foodJson.getInt("food_id"),
                                            foodJson.getString("food_name"),
                                            foodJson.getInt("food_price"),
                                            imageUrl + foodJson.getString("food_image"),
                                            foodJson.getInt("merchant_id"),
                                            foodJson.getInt("listed")
                                    );
                                    orderDetails.add(new OrderDetail(
                                            a.getInt("order_id"),
                                            food,
                                            detail.getInt("status_id"),
                                            detail.getInt("quantity")
                                    ));
                                }
                            } else {
                                if (details.getJSONObject(0).getInt("status_id") == 2 || details.getJSONObject(0).getInt("status_id") == 1) {
                                    Food food = new Food(
                                            foodJson.getInt("food_id"),
                                            foodJson.getString("food_name"),
                                            foodJson.getInt("food_price"),
                                            imageUrl + foodJson.getString("food_image"),
                                            foodJson.getInt("merchant_id"),
                                            foodJson.getInt("listed")
                                    );
                                    orderDetails.add(new OrderDetail(
                                            a.getInt("order_id"),
                                            food,
                                            detail.getInt("status_id"),
                                            detail.getInt("quantity")
                                    ));
                                }
                            }
                        }

                        if (history) {
                            if (details.getJSONObject(0).getInt("status_id") == 3) {
                                JSONObject user = a.getJSONObject("user");
                                User customer = new User(
                                        user.getInt("user_id"),
                                        user.getString("firstname"),
                                        user.getString("lastname"),
                                        user.getString("email"),
                                        1,
                                        user.getInt("active"),
                                        null
                                );
                                orders.add(new Order(a.getInt("order_id"),
                                        customer,
                                        a.getString("orderDate"),
                                        orderDetails
                                ));
                            }
                        } else {
                            if (details.getJSONObject(0).getInt("status_id") == 2 || details.getJSONObject(0).getInt("status_id") == 1) {
                                JSONObject user = a.getJSONObject("user");
                                User customer = new User(
                                        user.getInt("user_id"),
                                        user.getString("firstname"),
                                        user.getString("lastname"),
                                        user.getString("email"),
                                        1,
                                        user.getInt("active"),
                                        null
                                );
                                orders.add(new Order(a.getInt("order_id"),
                                        customer,
                                        a.getString("orderDate"),
                                        orderDetails
                                ));
                            }
                        }
                    }
                    if (orders.isEmpty()) {
                        Log.i("NOORDER", "NOORDER");
                        cardView.setVisibility(View.VISIBLE);
                        orderRV.setVisibility(View.GONE);
                    } else {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                        orderRV.setLayoutManager(linearLayoutManager);

                        if (history) {
                            HistoryAdapter historyAdapter = new HistoryAdapter(context, orders);
                            orderRV.setAdapter(historyAdapter);
                        } else {
//                            DbUser dbUser = new DbUser(context);
//                            dbUser.open();
//                            int role = dbUser.getRole();
//                            dbUser.close();
                            OrderAdapter orderAdapter = new OrderAdapter(context, orders, 2);
                            orderRV.setAdapter(orderAdapter);
                        }

                        Log.i("VOLLEYDONE", "DONE");
                    }

                } catch (JSONException e) {
                    Log.i("JALAN", "3");
                    e.printStackTrace();
                    Log.i("VOLLEYERROCATCH", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {

                    if (error.networkResponse.statusCode == 400) {
                        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    } else if (error.networkResponse.statusCode == 401) {
                        Toast.makeText(context, "Wrong email or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Uknown Error:" + e, Toast.LENGTH_LONG).show();
                }

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("merchant_id", Integer.toString(merchant_id));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    // list semua order yang dibikin oleh customer (diliat sama customer)
    public void getOrderByCustomer(Context context, int user_id, RecyclerView orderRV, boolean history, CardView cardView) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest sr = new StringRequest(Request.Method.POST, endpoint + "getOrderByCustomer.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
                ArrayList<Order> orders = new ArrayList<Order>();
                try {
                    Log.i("JALAN", "2");
                    JSONObject respObj = new JSONObject(response);

                    //String success = respObj.getString("success");
                    JSONArray data = respObj.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject a = data.getJSONObject(i);

                        ArrayList<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
                        JSONArray details = a.getJSONArray("orderDetail");

                        for (int j = 0; j < details.length(); j++) {

                            JSONObject detail = details.getJSONObject(j);
                            JSONObject foodJson = detail.getJSONObject("food");

                            if (history) {
                                if (details.getJSONObject(0).getInt("status_id") == 3) {
                                    Food food = new Food(
                                            foodJson.getInt("food_id"),
                                            foodJson.getString("food_name"),
                                            foodJson.getInt("food_price"),
                                            imageUrl + foodJson.getString("food_image"),
                                            foodJson.getInt("merchant_id"),
                                            foodJson.getInt("listed")
                                    );
                                    orderDetails.add(new OrderDetail(
                                            a.getInt("order_id"),
                                            food,
                                            detail.getInt("status_id"),
                                            detail.getInt("quantity")
                                    ));
                                }
                            } else {
                                if (details.getJSONObject(0).getInt("status_id") == 2 || details.getJSONObject(0).getInt("status_id") == 1) {
                                    Food food = new Food(
                                            foodJson.getInt("food_id"),
                                            foodJson.getString("food_name"),
                                            foodJson.getInt("food_price"),
                                            imageUrl + foodJson.getString("food_image"),
                                            foodJson.getInt("merchant_id"),
                                            foodJson.getInt("listed")
                                    );
                                    orderDetails.add(new OrderDetail(
                                            a.getInt("order_id"),
                                            food,
                                            detail.getInt("status_id"),
                                            detail.getInt("quantity")
                                    ));
                                }
                            }
                        }

                        if (history) {
                            if (details.getJSONObject(0).getInt("status_id") == 3) {
                                JSONObject user = a.getJSONObject("merchant");
                                User customer = new User(
                                        user.getInt("user_id"),
                                        user.getString("firstname"),
                                        user.getString("lastname"),
                                        user.getString("email"),
                                        1,
                                        user.getInt("active"),
                                        null
                                );
                                orders.add(new Order(a.getInt("order_id"),
                                        customer,
                                        a.getString("orderDate"),
                                        orderDetails
                                ));
                            }
                        } else {
                            if (details.getJSONObject(0).getInt("status_id") == 2 || details.getJSONObject(0).getInt("status_id") == 1) {
                                JSONObject user = a.getJSONObject("merchant");
                                User customer = new User(
                                        user.getInt("user_id"),
                                        user.getString("firstname"),
                                        user.getString("lastname"),
                                        user.getString("email"),
                                        1,
                                        user.getInt("active"),
                                        null
                                );
                                orders.add(new Order(a.getInt("order_id"),
                                        customer,
                                        a.getString("orderDate"),
                                        orderDetails
                                ));
                            }
                        }

                    }

                    if (orders.isEmpty()) {
                        orderRV.setVisibility(View.GONE);
                        cardView.setVisibility(View.VISIBLE);
                        return;
                    }

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    orderRV.setLayoutManager(linearLayoutManager);

                    if (history) {
                        HistoryAdapter historyAdapter = new HistoryAdapter(context, orders);
                        orderRV.setAdapter(historyAdapter);
                    } else {
//                        DbUser dbUser = new DbUser(context);
//                        dbUser.open();
//                        int role = dbUser.getRole();
//                        dbUser.close();
                        OrderAdapter orderAdapter = new OrderAdapter(context, orders, 1);
                        orderRV.setAdapter(orderAdapter);
                    }

//                    Toast.makeText(context, "Complete", Toast.LENGTH_SHORT).show();
                    Log.i("VOLLEYDONE", "DONE");
                } catch (JSONException e) {
                    Log.i("JALAN", "3");
                    e.printStackTrace();
                    Log.i("VOLLEYERROCATCH", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    Log.i("VOLLEY", String.valueOf(error.networkResponse.statusCode));
                    if (error.networkResponse.statusCode == 404) {
                        Toast.makeText(context, "You don't have any order yet", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Please check your connection" + error, Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(context, "Unknown Error" + error.toString(), Toast.LENGTH_LONG).show();
                }

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", Integer.toString(user_id));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    // list semua makanan yg ada dijual merchant tertentu (diliat sama customer)
    public void getFoodByMerchant(Context context, int merchant_id, RecyclerView foodRV, int role) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.GET, endpoint + "getFoodByMerchant.php?merchant_id=" + merchant_id, new Response.Listener<String>() {
            ArrayList<Food> foods = new ArrayList<Food>();

            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject respObj = new JSONObject(response);
                    //String success = respObj.getString("success");
                    JSONObject data = respObj.getJSONObject("data");
                    JSONArray foodJson = data.getJSONArray("food");
                    JSONObject merchantJson = data.getJSONObject("merchant");
                    for (int i = 0; i < foodJson.length(); i++) {
                        JSONObject a = foodJson.getJSONObject(i);

                        if (a.getInt("listed") == 1) {
                            foods.add(
                                    new Food(
                                            a.getInt("food_id"),
                                            a.getString("food_name"),
                                            a.getInt("food_price"),
                                            imageUrl + a.getString("food_image"),
                                            a.getInt("merchant_id"),
                                            a.getInt("listed")
                                    )
                            );
                        }

                    }


                    if (foods.isEmpty()) {
                        foodRV.setVisibility(View.GONE);
                        return;
                    }
                    ;

                    if (role == 1) {
                        MenuUserAdapter menuUserAdapter = new MenuUserAdapter(context, merchant_id, foods);
                        foodRV.setLayoutManager(new GridLayoutManager(context, 2));
                        foodRV.setAdapter(menuUserAdapter);


                    } else if (role == 2) {


                        MenuAdapter menuAdapter = new MenuAdapter(context, foods);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                        foodRV.setLayoutManager(linearLayoutManager);
                        foodRV.setAdapter(menuAdapter);
                    }
                    Log.i("VOLLEYDONE", "DONE");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("VOLLEYERROCATCH", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                try{
                    Log.i("VOLLEY", String.valueOf(error.networkResponse.statusCode));

                    if (error.networkResponse.statusCode == 404) {
                        Toast.makeText(context, "No food yet", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Please check your connection" + error, Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(context, "Unknow Error: " + error, Toast.LENGTH_LONG).show();
                }

            }
        });
        queue.add(sr);
    }

    // bikin listing makanan baru (diliat sama merchant)
    public void createFood(Context context, String name, int price, Bitmap image, int merchant_id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String encoded = encodeImage(image);
        StringRequest sr = new StringRequest(Request.Method.POST, endpoint + "createFood.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "New food is successfully listed", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject respObj = new JSONObject(response);

                    //String success = respObj.getString("success");
                    JSONArray data = respObj.getJSONArray("data");
                    JSONObject a = data.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail!, Please check your connection" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("food_name", name);
                params.put("food_price", Integer.toString(price));
                params.put("food_image", encoded);
                params.put("merchant_id", Integer.toString(merchant_id));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    // bikin orderan  baru (diliat sama customer)
    public void createOrder(Context context, int userid, ArrayList<Cart> cartArrayList, Bitmap proof) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String img = encodeImage(proof);
        StringRequest sr = new StringRequest(Request.Method.POST, endpoint + "createOrder.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(context, "Order is placed", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject respObj = new JSONObject(response);

                    //String success = respObj.getString("success");
                    JSONArray data = respObj.getJSONArray("data");
                    JSONObject a = data.getJSONObject(0);
//
                    Toast.makeText(context, "Thanks you for your purchase!", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail!, Please check your connection" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", Integer.toString(userid));


                for (int i = 0; i < cartArrayList.size(); i++) {
                    Cart curCart = cartArrayList.get(i);

                    params.put("food_id["+i+"]", Integer.toString(curCart.getItemID()));
                    params.put("quantity["+i+"]", Integer.toString(curCart.getQuantity()));
                }

                params.put("proof", img);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    // update status makanan  (diliat sama merchant)
    // 1 = pending (otomatis 1 waktu ada order masuk)
    // 2 = ready
    // 3 = finished
    public void updateStatus(Context context, int order_id, int food_id, int newStatus) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest sr = new StringRequest(Request.Method.POST, endpoint + "updateOrderStatus.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Order status is updated", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject respObj = new JSONObject(response);

                    //String success = respObj.getString("success");
                    JSONArray data = respObj.getJSONArray("data");
                    JSONObject a = data.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail to create food = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("order_id", Integer.toString(order_id));
                params.put("food_id", Integer.toString(food_id));
                params.put("status", Integer.toString(newStatus));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    public void updateFood(Context context, int food_id, String name, int price, Bitmap image) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String img = encodeImage(image);
        StringRequest sr = new StringRequest(Request.Method.POST, endpoint + "updateFood.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "New food is successfully listed", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject respObj = new JSONObject(response);

                    //String success = respObj.getString("success");
                    JSONArray data = respObj.getJSONArray("data");
                    JSONObject a = data.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail to create food = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("food_name", name);
                params.put("food_price", Integer.toString(price));
                params.put("food_image", img);
                params.put("food_id", Integer.toString(food_id));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    // delete makanan (untuk merchant)
    public void deleteFood(Context context, int food_id) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest sr = new StringRequest(Request.Method.POST, endpoint + "deleteFood.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject respObj = new JSONObject(response);
                    int success = respObj.getInt("success");
                    Toast.makeText(context, "Delete Food Success", Toast.LENGTH_SHORT).show();

                    //String success = respObj.getString("success");
//                    JSONArray data = respObj.getJSONArray("data");
//                    JSONObject a = data.getJSONObject(0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail!, Please check your connection" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("food_id", Integer.toString(food_id));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    public String encodeImage(Bitmap bm) {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, ba);
        byte[] imgByte = ba.toByteArray();
        String encode = Base64.encodeToString(imgByte, Base64.DEFAULT);
        return encode;
    }
}



