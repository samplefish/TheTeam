package edu.csulb.theteam;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;


import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.csulb.models.nosql.EventsDO;



public class EventsFragment extends Fragment {
    Button newPostButton;
    Dialog progress;

    RecyclerView recyclerView;

    PaginatedScanList<EventsDO> result;
    DynamoDBMapper mapper;



    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();
        newPostButton = (Button) v.findViewById(R.id.postFeedButton);

        newPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment,
                        new SubmitEventFragment()).commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mapper = AWSProvider.getInstance().getDynamoDBMapper();

        new getUserEvents().execute();
        return view;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView mTypeView;
        private TextView mNameView;
        private TextView mDescription;
        private String itemID;
        private TextView mEmail;
        private ImageView mImageView;
        private TextView mIGN;

        public View view;

        private TextView mPriceView;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            /*view = itemView;
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View itemView){
                    getFragmentManager().beginTransaction().replace(R.id.content_frame,
                            new ViewItemFragment()).commit();
                }
            });*/

        }

        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.card_view, container, false));
            mNameView = (TextView) itemView.findViewById(R.id.title);
            mDescription = (TextView) itemView.findViewById(R.id.description);
            mImageView = (ImageView) itemView.findViewById(R.id.thumbnail);
            mIGN = (TextView) itemView.findViewById(R.id.userIGN);



        }
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
        private List<EventsDO> result;

        public RecyclerViewAdapter(ArrayList<EventsDO> result) {
            this.result = result;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.fragment_events, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = recyclerView.indexOfChild(v);
                    getFragmentManager().beginTransaction().replace(R.id.fragment,
                            new EventsFragment()).commit();
                }
            });
            return new RecyclerViewHolder(inflater, parent);
        }



        public void onBindViewHolder(RecyclerViewHolder holder, int position) {

            holder.mNameView.setText(result.get(position).getEventName());
            holder.mDescription.setText(result.get(position).getEventDescription());
            holder.mIGN.setText("IGN: "+result.get(position).getUserIGN());
            String n = result.get(position).getEventName();
            if(n.contains("league")){
                holder.mImageView.setImageResource(R.drawable.league);
            }
            if(n.contains("fort")){
                holder.mImageView.setImageResource(R.drawable.fortnite);
            }

        }

        @Override
        public int getItemCount() {
            return result.size();
        }


    }

    public class getUserEvents extends AsyncTask<Void, Integer, Integer> {
        protected void onPreExecute() {
            /*progress = new ProgressDialog(getActivity());
            progress.setMessage("Working...");
            progress.show();*/
        }

        protected Integer doInBackground(Void... params) {

            Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
            eav.put(":val1", new AttributeValue().withS(AWSProvider.getInstance().getIdentityManager().getCachedUserID()));

            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withFilterExpression("userId = :val1").withExpressionAttributeValues(eav);

            result = mapper.scan(EventsDO.class, scanExpression);

            return 1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(result.size()!=0){
                Log.e("gotten the thing: ", "brains!!!");
            }
            else{
                Log.e("ugh, dthe size is ", result.size()+"");
            }
            result.loadAllResults();
            ArrayList<EventsDO> resultList = new ArrayList<EventsDO>(result.size());
            Iterator<EventsDO> iterator = result.iterator();
            while(iterator.hasNext()){
                EventsDO element = iterator.next();
                resultList.add(element);
            }


            recyclerView.setAdapter(new RecyclerViewAdapter(resultList));
        }
    }


}