package day03.giangth.onlinemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import day03.giangth.onlinemanagement.R;
import day03.giangth.onlinemanagement.activity.MainActivity;
import day03.giangth.onlinemanagement.activity.Order;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderAdapter extends BaseAdapter {
    Context context;
    ArrayList<day03.giangth.onlinemanagement.model.Order> orderArrayList;
//     ViewHolder viewHolder = null;

    public OrderAdapter(Context context, ArrayList<day03.giangth.onlinemanagement.model.Order> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @Override
    public int getCount() {
        return orderArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

     class ViewHolder {
        public TextView txtNameOrder, txtPrice;
        public ImageView imgOrder;
        public Button btnminus, btnvalues, btnplus;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.order, null);
            viewHolder.txtNameOrder = (TextView) convertView.findViewById(R.id.txtNameOrder);
            viewHolder.txtPrice = (TextView) convertView.findViewById(R.id.txtPriceOrder);
            viewHolder.imgOrder = (ImageView) convertView.findViewById(R.id.imgOrder);
            viewHolder.btnminus = (Button) convertView.findViewById(R.id.btnminus);
            viewHolder.btnvalues = (Button) convertView.findViewById(R.id.btnvalues);
            viewHolder.btnplus = (Button) convertView.findViewById(R.id.btnplus);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        day03.giangth.onlinemanagement.model.Order order = (day03.giangth.onlinemanagement.model.Order) getItem(position);
        viewHolder.txtNameOrder.setText(order.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPrice.setText(decimalFormat.format(order.getPrice()) + " Đ");
        Picasso.with(context).load(order.getImage())
                .placeholder(R.drawable.no)
                .error(R.drawable.loadingimage)
                .into(viewHolder.imgOrder);
        viewHolder.btnvalues.setText(order.getNumber() + "");
        int number = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if (number >= 10) {
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        } else if (number <= 1) {
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);
        } else if (number > 1) {
            viewHolder.btnminus.setVisibility(View.VISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);
        }

        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int brandNewNumber = Integer.parseInt(finalViewHolder1.btnvalues.getText().toString()) + 1;
                int presentNumber = MainActivity.orderArrayList.get(position).getNumber();
                long presentPrice = MainActivity.orderArrayList.get(position).getPrice();
                MainActivity.orderArrayList.get(position).setNumber(brandNewNumber);
                long brandNewPrice = (presentPrice * brandNewNumber) / presentNumber;
                MainActivity.orderArrayList.get(position).setPrice(brandNewPrice);

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder1.txtPrice.setText(decimalFormat.format(brandNewPrice) + " Đ");

                Order.EventUltil();
                if (brandNewNumber > 9) {
                    finalViewHolder1.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHolder1.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder1.btnvalues.setText(String.valueOf(brandNewNumber));
                } else {
                    finalViewHolder1.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder1.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder1.btnvalues.setText(String.valueOf(brandNewNumber));
                }
            }
        });

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int brandNewNumber = Integer.parseInt(finalViewHolder.btnvalues.getText().toString()) - 1;
                int presentNumber = MainActivity.orderArrayList.get(position).getNumber();
                long presentPrice = MainActivity.orderArrayList.get(position).getPrice();
                MainActivity.orderArrayList.get(position).setNumber(brandNewNumber);
                long brandNewPrice = (presentPrice * brandNewNumber) / presentNumber;
                MainActivity.orderArrayList.get(position).setPrice(brandNewPrice);

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtPrice.setText(decimalFormat.format(brandNewPrice) + " Đ");

                Order.EventUltil();
                if (brandNewNumber < 2) {
                    finalViewHolder.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(brandNewNumber));
                } else {
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(brandNewNumber));
                }
            }
        });


        return convertView;
    }
}
