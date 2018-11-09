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

public class PhoneAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> phoneArrayList;

    public PhoneAdapter(Context context, ArrayList<Product> phoneArrayList) {
        this.context = context;
        this.phoneArrayList = phoneArrayList;
    }

    @Override
    public int getCount() {
        return phoneArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return phoneArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView txtNamePhone, txtPricePhone, txtDesPhone;
        public ImageView phoneImageView;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.phone, null);
            viewHolder.txtNamePhone = (TextView) convertView.findViewById(R.id.txtNamePhone);
            viewHolder.txtPricePhone = (TextView) convertView.findViewById(R.id.txtPricePhone);
            viewHolder.txtDesPhone = (TextView) convertView.findViewById(R.id.txtDesPhone);
            viewHolder.phoneImageView = (ImageView) convertView.findViewById(R.id.phoneImageView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.txtNamePhone.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPricePhone.setText("Price: " + decimalFormat.format(product.getPrice()));
        Log.d("Test " ,product.getDescription() + product.getName());
        viewHolder.txtDesPhone.setMaxLines(2);
        viewHolder.txtDesPhone.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDesPhone.setText(product.getDescription());
        Picasso.with(context).load(product.getImage())
                .placeholder(R.drawable.no)
                .error(R.drawable.loadingimage)
                .into(viewHolder.phoneImageView);
        return convertView;
    }
}
