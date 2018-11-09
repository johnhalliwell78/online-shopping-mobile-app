package day03.giangth.onlinemanagement.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import day03.giangth.onlinemanagement.R;
import day03.giangth.onlinemanagement.model.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> laptopArrayList;

    public LaptopAdapter(Context context, ArrayList<Product> laptopArrayList) {
        this.context = context;
        this.laptopArrayList = laptopArrayList;
    }

    @Override
    public int getCount() {
        return laptopArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return laptopArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView txtNameLaptop, txtPriceLaptop, txtDesLaptop;
        public ImageView laptopImageView;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LaptopAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.laptop, null);
            viewHolder.txtNameLaptop = (TextView) convertView.findViewById(R.id.txtNameLaptop);
            viewHolder.txtPriceLaptop = (TextView) convertView.findViewById(R.id.txtPriceLaptop);
            viewHolder.txtDesLaptop = (TextView) convertView.findViewById(R.id.txtDesLaptop);
            viewHolder.laptopImageView = (ImageView) convertView.findViewById(R.id.laptopImageView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (LaptopAdapter.ViewHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.txtNameLaptop.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPriceLaptop.setText("Price: " + decimalFormat.format(product.getPrice()));
        Log.d("Test ", product.getDescription() + product.getName());
        viewHolder.txtDesLaptop.setMaxLines(2);
        viewHolder.txtDesLaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDesLaptop.setText(product.getDescription());
        Picasso.with(context).load(product.getImage())
                .placeholder(R.drawable.no)
                .error(R.drawable.loadingimage)
                .into(viewHolder.laptopImageView);
        return convertView;
    }
}
