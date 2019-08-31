package com.uigitdev.beardshopcartlist;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uigitdev.beardshopcartlist.adapter.ProductAdapter;
import com.uigitdev.beardshopcartlist.model.Product;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ProductAdapter.ProductAdapterStatus {
    private ArrayList<Product> products;
    private RelativeLayout back_arrow, payment_button;
    private TextView cart_list, shipping_price, total_price, empty_cart;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ProductAdapter productAdapter;
    private CardView payment_parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setType();
        setAdapterType();
        initProducts();
        clickBackArrow();
        clickPayment();
        setProductAdapter();
    }

    private void setType() {
        back_arrow = findViewById(R.id.back_arrow);
        cart_list = findViewById(R.id.cart_list);
        payment_button = findViewById(R.id.payment_button);
        shipping_price = findViewById(R.id.shipping_price);
        total_price = findViewById(R.id.total_price);
        empty_cart = findViewById(R.id.empty_cart);
        payment_parent = findViewById(R.id.payment_parent);
    }

    private void setAdapterType() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setProductAdapter() {
        productAdapter = new ProductAdapter(MainActivity.this, products, this);
        recyclerView.setAdapter(productAdapter);
    }

    private void setTotalPriceTitle(float total) {
        total_price.setText(getString(R.string.currency) + "" + total);
    }

    private void setShippingPriceTitle(float shipping) {
        shipping_price.setText(getString(R.string.currency) + "" + shipping);
    }

    private void countPrices() {
        float shipping = 0, total = 0;
        for (int i = 0; i < products.size(); i++) {
            shipping += products.get(i).getShippingPrice();
            total += products.get(i).getProductPrice();
        }

        if (shipping != 0) {
            total += shipping;
        }
        setShippingPriceTitle(shipping);
        setTotalPriceTitle(total);
    }

    private void clickPayment() {
        payment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (products.size() != 0) {
                    Toast.makeText(MainActivity.this, "Payment", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void clickBackArrow() {
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Back Arrow", Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("ResourceType")
    private void paymentButtonStyle() {
        if (products.size() == 0) {
            payment_button.setClickable(false);
            payment_button.setFocusable(false);
            payment_parent.setCardBackgroundColor(Color.parseColor(getString(R.color.colorSubtitle)));
        } else {
            payment_button.setClickable(true);
            payment_button.setFocusable(true);
            payment_parent.setCardBackgroundColor(Color.parseColor(getString(R.color.colorPayment)));
        }
    }

    private void setCartListCount(int piece) {
        if (piece == 0) {
            cart_list.setText(getString(R.string.cart_list));
            empty_cart.setVisibility(View.VISIBLE);
        } else {
            cart_list.setText(getString(R.string.cart_list) + " ( " + piece + " )");
            empty_cart.setVisibility(View.GONE);
        }
    }

    private void initProducts() {
        products = new ArrayList<>();
        products.add(new Product(7845, getString(R.string.item_1), R.drawable.item_1, 2, 0));
        products.add(new Product(7994, getString(R.string.item_2), R.drawable.item_2, 8, 0));
        products.add(new Product(7456, getString(R.string.item_3), R.drawable.item_3, 14, 2));
        products.add(new Product(1258, getString(R.string.item_4), R.drawable.item_4, 9, 1));

        setCartListCount(products.size());
        countPrices();
        paymentButtonStyle();
    }

    @Override
    public void DataRefresh(boolean isRefresh) {
        if (isRefresh) {
            setCartListCount(products.size());
            countPrices();
            paymentButtonStyle();
        }
    }
}
