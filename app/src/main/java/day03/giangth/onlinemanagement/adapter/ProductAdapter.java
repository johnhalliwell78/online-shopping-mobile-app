package day03.giangth.onlinemanagement.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import day03.giangth.onlinemanagement.R;
import day03.giangth.onlinemanagement.model.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {
    Context context;
    ArrayList<Product> productArrayList;

    public ProductAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.brandnewproduct, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Product product = productArrayList.get(position);
        holder.txtNameProduct.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtPriceProduct.setText("Price: " + decimalFormat.format(product.getPrice()) + "VND");
        Picasso.with(context).load(product.getImage())
                .placeholder(R.drawable.no)
                .error(R.drawable.loadingimage)
                .into(holder.imgProduct);

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imgProduct;
        public TextView txtNameProduct, txtPriceProduct;


        public ItemHolder(View itemView) {
            super(itemView);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            txtNameProduct = (TextView) itemView.findViewById(R.id.txtNameProduct);
            txtPriceProduct = (TextView) itemView.findViewById(R.id.txtPriceProduct);

        }
    }
}
