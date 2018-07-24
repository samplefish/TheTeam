package edu.csulb.theteam;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import edu.csulb.models.nosql.EventsDO;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitEventFragment extends Fragment {

    private static final int RESULT_LOAD_IMAGE = 1;

    String userID;

    EditText etItemName;
    EditText etDescription;
    EditText etIGN;
    Spinner typeSpinner;

    Button submit;
    String name;

    Double price;
    String description;
    String pictureLink;
    String itemID;
    String itemType;
    String ign;

    View v;
    PaginatedScanList<EventsDO> result;
    Integer resultSize;
    ProgressDialog progress;

    SharedPreferences prefs;

    DynamoDBMapper mapper;

    Map<String, String> attributes;

    CognitoUserDetails CUD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_submit_event, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapper = AWSProvider.getInstance().getDynamoDBMapper();
        v = getView();

        etItemName = (EditText) v.findViewById(R.id.etItemName);
        etDescription = (EditText) v.findViewById(R.id.etDescription);
        etIGN= (EditText)v.findViewById(R.id.etIGN);


        submit = (Button) v.findViewById(R.id.bSubmit);

        //new retrieveUser().execute();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemType != "No item selected"){
                    name = etItemName.getText().toString();
                    //price = Double.parseDouble(etPrice.getText().toString());
                    description = etDescription.getText().toString();
                    ign = etIGN.getText().toString();
                    new SubmitEventFragment.checkItems().execute();
                }
                else{
                    Toast.makeText(getActivity(), "Please select a type", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private class checkItems extends AsyncTask<Void, Integer, Integer> {
        protected void onPreExecute(){
            //progress = new ProgressDialog(getActivity());
            //progress.setMessage("Working...");
            //progress.show();
        }
        protected Integer doInBackground(Void... params){


            EventsDO template = new EventsDO();
            template.setUserId(AWSProvider.getInstance().getIdentityManager().getCachedUserID());


            Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
            eav.put(":val1", new AttributeValue().withS(template.getUserId()));
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withFilterExpression("userId = :val1").withExpressionAttributeValues(eav);
            result = mapper.scan(EventsDO.class, scanExpression);

            return 1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer == 1){
                if(result != null){
                    resultSize = result.size();
                    new SubmitEventFragment.updateTable().execute();
                }
            }
        }
    }

    /*private class retrieveUser extends AsyncTask<Void, Integer, Integer>{
        @Override
        protected Integer doInBackground(Void... params){
            /*AWSProvider.getInstance().getCognitoUserPool().getUser().getDetails(new GetDetailsHandler(){
                public void onSuccess(CognitoUserDetails details){
                    attributes = details.getAttributes().getAttributes();
                    Toast.makeText(getActivity(), "Retrieved username: " + attributes.get("username"), Toast.LENGTH_SHORT).show();
                }

                public void onFailure(Exception e){
                    Log.e("failed to get details", "fuck!");


                }
            });
            GetDetailsHandler handler = new GetDetailsHandler() {
                @Override
                public void onSuccess(final CognitoUserDetails list) {
                    CUD = list;
                }

                @Override
                public void onFailure(final Exception exception) {
                    exception.getCause();
                }
            };
            AWSProvider.getInstance().getCognitoUserPool().getCurrentUser().getDetails(handler);
            return 1;
        }
    }*/

    private class updateTable extends AsyncTask<Void, Integer, Integer>{
        @Override
        protected Integer doInBackground(Void... params){
            /*userID = AWSProvider.getInstance().getIdentityManager().getCachedUserID();

            Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
            eav.put(":val1", new AttributeValue().withS(userID));
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withFilterExpression("userId = :val1").withExpressionAttributeValues(eav);
            result = mapper.scan(EventsDO.class, scanExpression);
*/


            EventsDO eventMapper = new EventsDO();

            eventMapper.setEventId(UUID.randomUUID().toString());
            eventMapper.setUserId(AWSProvider.getInstance().getIdentityManager().getCachedUserID());
            eventMapper.setEventDescription(description);
            eventMapper.setUserIGN(ign);
            //eventMapper.setEventDescription(attributes.get("Username"));




            eventMapper.setEventName(name);
            //String email = prefs.getString("email", null);
            //itemMapper.setEmail(email);

            if(eventMapper != null){
                mapper.save(eventMapper);
            }


            return 1;
        }
        protected void onPostExecute(Integer integer){
            super.onPostExecute(integer);
            if(integer ==1)
            {
                Toast.makeText(getActivity(), "Your item has been added.", Toast.LENGTH_SHORT).show();
                //progress.dismiss();
                getFragmentManager().beginTransaction().replace(R.id.fragment,
                        new EventsFragment()).commit();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }

        }


    }
}
