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
import com.example.android.sunshine.app.routing.models.CrewMember;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by lancehughes on 9/16/14.
 */
public class AddCrewListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<CrewMember> allMembersList = new ArrayList<CrewMember>();
    ArrayList<CrewMember> visibleMembersList = new ArrayList<CrewMember>();

    public AddCrewListAdapter(Context context, LayoutInflater inflater, ArrayList<CrewMember> crewArray)
    {
        this.context = context;
        this.inflater = inflater;
        this.visibleMembersList.addAll(crewArray);
        this.allMembersList.addAll(crewArray);
    }


    @Override
    public int getCount() {
        int length = visibleMembersList.size();
        return length;
    }

    @Override
    public Object getItem(int i) {
        return visibleMembersList.get(i);
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
            holder.crewMemberName = (TextView) view.findViewById(R.id.crew_member_name);
            holder.addMemberButton = (Button) view.findViewById(R.id.add_crew_member_button);

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();

        }

        final int index = i;
        holder.addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMember(index);
                Button button = (Button) view.findViewById(R.id.add_crew_member_button);
                button.setBackgroundColor(Color.GREEN);
            }
        });
        holder.crewMemberName.setText((String) visibleMembersList.get(i).name);

        return view;

    }

    void addMember(int index)
    {
        CrewMember member = visibleMembersList.get(index);
        AddCrewActivity parentActivity = (AddCrewActivity)context;

        parentActivity.AddMember(member);
        parentActivity.finish();
    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        visibleMembersList.clear();


        if (charText.length() == 0) {
            visibleMembersList.addAll(allMembersList);
        } else {
            for (CrewMember member : allMembersList) {
                if (member.name.toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    visibleMembersList.add(member);
                }
            }
        }
        notifyDataSetChanged();
    }


    class ViewHolder
    {
        public TextView crewMemberName;
        public Button addMemberButton;

    }
}
