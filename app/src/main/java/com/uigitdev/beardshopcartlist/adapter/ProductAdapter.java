package com.uigitdev.beardshopcartlist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uigitdev.beardshopcartlist.R;
import com.uigitdev.beardshopcartlist.model.Product;
import com.uigitdev.beardshopcartlist.viewholder.ProductViewHolder;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Product> productArrayList;
    private ProductAdapterStatus status;

    public ProductAdapter(Context context, ArrayList<Product> productArrayList, ProductAdapterStatus status) {
        this.context = context;
        this.productArrayList = productArrayList;
        this.status = status;
    }

    public interface ProductAdapterStatus {
        void DataRefresh(boolean isRefresh);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        setProductName(((ProductViewHolder) holder).product_name, productArrayList.get(position).getProductName());
        setProductId(((ProductViewHolder) holder).product_id, productArrayList.get(position).getProductId());
        setProductImage(((ProductViewHolder) holder).product_image, productArrayList.get(position).getProductImage());
        setProductPrice(((ProductViewHolder) holder).product_price, productArrayList.get(position).getProductPrice());
        setOnClickItem(((ProductViewHolder) holder).product_parent, productArrayList.get(position).getProductId());
        setOnClickCancel(((ProductViewHolder) holder).product_cancel, position);
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    private void setProductName(TextView textView, String text) {
        textView.setText(text);
    }

    private void setProductId(TextView textView, int id) {
        textView.setText(context.getString(R.string.product_id) + " " + id);
    }

    private void setProductImage(ImageView imageView, int imageId) {
        imageView.setBackgroundResource(imageId);
    }

    private void setProductPrice(TextView textView, float price) {
        textView.setText(context.getString(R.string.currency) + "" + price);
    }

    private void setOnClickItem(RelativeLayout relativeLayout, final int id) {
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Item click: " + id, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setOnClickCancel(RelativeLayout relativeLayout, final int position) {
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productArrayList.remove(position);
                notifyDataSetChanged();
                status.DataRefresh(true);
            }
        });
    }
}
