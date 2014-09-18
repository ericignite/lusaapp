package com.example.android.sunshine.app.routing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.sunshine.app.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by lancehughes on 9/16/14.
 */
public class RouteListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<RouteItem> routeItems = new ArrayList<RouteItem>();

    public RouteListAdapter(Context context, LayoutInflater inflater, ArrayList<RouteItem> routes)
    {
        this.context = context;
        this.inflater = inflater;
        this.routeItems = routes;
    }


    @Override
    public int getCount() {

        return routeItems.size();
    }

    @Override
    public Object getItem(int i) {
        return routeItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null)
        {
            view = inflater.inflate(R.layout.list_item_crew_add,null);

            holder = new ViewHolder();
            holder.propertyName = (TextView) view.findViewById(R.id.property_name);
            holder.budgetHours = (TextView) view.findViewById(R.id.budget_hours);
            holder.serviceName = (TextView) view.findViewById(R.id.service_name);

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();

        }


        holder.propertyName.setText((String) routeItems.get(i).propertyName);
        holder.budgetHours.setText((String) routeItems.get(i).budgetedHours);
        holder.serviceName.setText((String) routeItems.get(i).serviceId);

        return view;

    }


    class ViewHolder
    {
        public TextView propertyName;
        public TextView budgetHours;
        public TextView serviceName;

    }
}
