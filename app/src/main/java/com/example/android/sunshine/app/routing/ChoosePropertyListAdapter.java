package com.example.android.sunshine.app.routing;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.routing.models.PropertyInRoute;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by eric johnson on 9/16/14.
 */
public class ChoosePropertyListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<PropertyInRoute> allPropertiesList = new ArrayList<PropertyInRoute>();
    ArrayList<PropertyInRoute> visiblePropertiesList = new ArrayList<PropertyInRoute>();

    public ChoosePropertyListAdapter(Context context, LayoutInflater inflater, ArrayList<PropertyInRoute> propertyArray) {
        this.context = context;
        this.inflater = inflater;
        this.visiblePropertiesList.addAll(propertyArray);
        this.allPropertiesList.addAll(propertyArray);
    }

    @Override
    public int getCount() {
        int length = visiblePropertiesList.size();
        return length;
    }

    @Override
    public Object getItem(int i) {
        return visiblePropertiesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_property, null);

            holder = new ViewHolder();
            holder.propertyName = (TextView) view.findViewById(R.id.property_name);
            holder.budgetHrs = (TextView) view.findViewById(R.id.budget_hours);
            holder.serviceName = (TextView) view.findViewById(R.id.service_name);
            holder.drivePropertyButton = (Button) view.findViewById(R.id.drive_next_property);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final int index = i;
        holder.drivePropertyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDriveTime(index);
                Button button = (Button) view.findViewById(R.id.drive_next_property);
                button.setBackgroundColor(Color.GREEN);
            }
        });
        holder.propertyName.setText((String) visiblePropertiesList.get(i).propertyName);
        holder.budgetHrs.setText((String) visiblePropertiesList.get(i).budgetHrs);
        holder.serviceName.setText((String) visiblePropertiesList.get(i).serviceName);

        return view;

    }

    void startDriveTime(int index) {
        PropertyInRoute member = visiblePropertiesList.get(index);
        ChoosePropertyActivity parentActivity = (ChoosePropertyActivity) context;

        parentActivity.startDriveTime(member);
        parentActivity.finish();
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        visiblePropertiesList.clear();

        if (charText.length() == 0) {
            visiblePropertiesList.addAll(allPropertiesList);
        } else {
            for (PropertyInRoute property : allPropertiesList) {
                if (property.propertyName.toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    visiblePropertiesList.add(property);
                }
            }
        }
        notifyDataSetChanged();
    }

    class ViewHolder {
        public TextView propertyName;
        public TextView budgetHrs;
        public TextView serviceName;
        public Button drivePropertyButton;

    }
}
