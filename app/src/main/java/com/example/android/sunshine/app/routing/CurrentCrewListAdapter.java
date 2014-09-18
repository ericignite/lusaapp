package com.example.android.sunshine.app.routing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.routing.models.CrewMember;

import java.util.ArrayList;

/**
 * Created by lancehughes on 9/16/14.
 */
public class CurrentCrewListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<CrewMember> crewArray;

    public CurrentCrewListAdapter(Context context, LayoutInflater inflater, ArrayList<CrewMember> crewArray)
    {
        this.context = context;
        this.inflater = inflater;
        this.crewArray = crewArray;

    }


    @Override
    public int getCount() {
        int length = crewArray.size();
        return length;
    }

    @Override
    public Object getItem(int i) {
        return crewArray.get(i);
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
            view = inflater.inflate(R.layout.list_item_crew_remove,null);

            holder = new ViewHolder();
            holder.crewMemberName = (TextView) view.findViewById(R.id.crew_member_name);
            holder.removeMemberButton = (Button) view.findViewById(R.id.remove_crew_member_button);

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();

        }

        final int index = i;
        holder.removeMemberButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                RemoveMember(index);

            }
        });
        holder.crewMemberName.setText((String)crewArray.get(i).name);

        return view;
    }

    void RemoveMember(int index)
    {
        AdjustCrewActivity activity = (AdjustCrewActivity)context;
        activity.removeMember(crewArray.get(index));
        crewArray.remove(index);
        notifyDataSetChanged();

    }

    public void UpdateCrew(ArrayList<CrewMember> newCrew)
    {
        crewArray = newCrew;
        notifyDataSetChanged();
    }

    class ViewHolder
    {
        public TextView crewMemberName;
        public Button removeMemberButton;

    }
}
