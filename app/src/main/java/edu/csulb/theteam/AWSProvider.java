package edu.csulb.theteam;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;

import com.amazonaws.mobile.auth.userpools.CognitoUserPoolsSignInProvider;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class AWSProvider {
    private static AWSProvider instance = null;
    private Context context;
    private AWSConfiguration awsConfiguration;
    private PinpointManager pinpointManager;
    private CognitoUserPool userPool;

    private AmazonDynamoDBClient dbClient = null;
    private DynamoDBMapper dbMapper = null;

    public static AWSProvider getInstance() {
        return instance;
    }

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new AWSProvider(context);
        }
    }

    private AWSProvider(Context context) {
        this.context = context;
        this.awsConfiguration = new AWSConfiguration(context);
        this.userPool = new CognitoUserPool(context, awsConfiguration);



        IdentityManager identityManager = new IdentityManager(context, awsConfiguration);
        IdentityManager.setDefaultIdentityManager(identityManager);
        identityManager.addSignInProvider(CognitoUserPoolsSignInProvider.class);

    }

    /*public class getUserPool extends AsyncTask<Void, Integer, Integer>{
        @Override
        protected Integer doInBackground(Void... params) {
            IdentityManager identityManager = new IdentityManager(context, awsConfiguration);
            userPool = new CognitoUserPool(context,"us-west-2_oasxuIIUd","k6gf0jm7oc007tgue54hgn37r","tb51jc3i9j38a3iotesdeun8lnc8p5f12jj1mfu50e0mseni323");

            return 1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer ==1){
                Log.e("getUserPool success","nice.");
            }
        }
    }*/

    public CognitoUserPool getCognitoUserPool(){
        return this.userPool;

    }

    public Context getContext() {
        return this.context;
    }

    public AWSConfiguration getConfiguration() {
        return this.awsConfiguration;
    }

    public IdentityManager getIdentityManager() {
        return IdentityManager.getDefaultIdentityManager();
    }

    public PinpointManager getPinpointManager() {
        if (pinpointManager == null) {
            final AWSCredentialsProvider cp = getIdentityManager().getCredentialsProvider();
            PinpointConfiguration config = new PinpointConfiguration(
                    getContext(), cp, getConfiguration());
            pinpointManager = new PinpointManager(config);
        }
        return pinpointManager;
    }

    public DynamoDBMapper getDynamoDBMapper() {
        if (dbMapper == null) {
            final AWSCredentialsProvider cp = getIdentityManager().getCredentialsProvider();
            dbClient = new AmazonDynamoDBClient(cp);
            dbMapper = DynamoDBMapper.builder()
                    .awsConfiguration(getConfiguration())
                    .dynamoDBClient(dbClient)
                    .build();
        }
        return dbMapper;
    }

    /*public void startGetUserPool(){
        new getUserPool().execute();
    }*/
}