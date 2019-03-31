package com.example.vcanteen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class menuListAdapter extends ArrayAdapter {

    //declarations
    int[]b={};
    String[]a={};
    Context c;
    LayoutInflater inflater;

    menuListAdapter(Context context, String[] a, int[] b){
        super(context, R.layout.menu_listview , a);
        this.c=context;
        this.a=a;
        this.b=b;
    }

    // Hold our view for each row
    public class ViewHolder{
        TextView menuName;
        TextView menuPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu_listview,parent,false);
        }

        //view holder object
        final ViewHolder holder = new ViewHolder();

        //initalize our view
        holder.menuName=(TextView) convertView.findViewById(R.id.menuName);
        holder.menuPrice=(TextView) convertView.findViewById(R.id.menuPrice);

        //assign data
        holder.menuName.setText(a[position]);
        holder.menuPrice.setText(""+b[position]+".-");

        return convertView;
    }

}

//package com.example.vcanteen;
//
//        import android.content.Context;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.widget.ArrayAdapter;
//        import android.widget.TextView;
//
//public class menuListAdapter extends ArrayAdapter {
//
//    menuListAdapter(Context context, String[] a, int[] b){
//        super(context, R.layout.menu_listview , a);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent){
//        LayoutInflater menuListInflater = LayoutInflater.from(getContext());
//        View customView = menuListInflater.inflate(R.layout.menu_listview, parent, false);
//
//        String singleItem = (String) getItem(position);
//        TextView menuName = (TextView) customView.findViewById(R.id.menuName);
//
//        int singlePrice = (int)getItem(position);
//        TextView menuPrice = (TextView) customView.findViewById(R.id.menuPrice);
//
//        menuPrice.setText(""+singlePrice+".-");
//        menuName.setText(singleItem);
//        return customView;
//    }
//}
