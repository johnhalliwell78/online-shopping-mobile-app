package day03.giangth.onlinemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import day03.giangth.onlinemanagement.R;
import day03.giangth.onlinemanagement.model.TypeDevice;

import java.util.ArrayList;

public class TypeDeviceAdapter extends BaseAdapter {

    ArrayList<TypeDevice> arrayListTypeDevice;
    Context context;

    public TypeDeviceAdapter(ArrayList<TypeDevice> arrayListTypeDevice, Context context) {
        this.arrayListTypeDevice = arrayListTypeDevice;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListTypeDevice.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListTypeDevice.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView txtNameTypeDevice;
        ImageView imgTypeDevice;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_typedevice, null);
            viewHolder.txtNameTypeDevice = (TextView) convertView.findViewById(R.id.textViewTypeDevice);
            viewHolder.imgTypeDevice = (ImageView) convertView.findViewById(R.id.imageViewTypeDevice);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        TypeDevice typeDevice = (TypeDevice) getItem(position);
        viewHolder.txtNameTypeDevice.setText(typeDevice.getName());
        Picasso.with(context).load(typeDevice.getImage())
                .placeholder(R.drawable.no)
                .error(R.drawable.loadingimage)
                .into(viewHolder.imgTypeDevice);
        return convertView;
    }
}
