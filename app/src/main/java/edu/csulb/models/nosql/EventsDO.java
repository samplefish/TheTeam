package edu.csulb.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "theteam-mobilehub-166427370-Events")

public class EventsDO {
    private String _eventId;
    private String _eventDescription;
    private String _eventName;
    private String _userIGN;
    private String _userId;

    @DynamoDBHashKey(attributeName = "eventId")
    @DynamoDBAttribute(attributeName = "eventId")
    public String getEventId() {
        return _eventId;
    }

    public void setEventId(final String _eventId) {
        this._eventId = _eventId;
    }
    @DynamoDBAttribute(attributeName = "eventDescription")
    public String getEventDescription() {
        return _eventDescription;
    }

    public void setEventDescription(final String _eventDescription) {
        this._eventDescription = _eventDescription;
    }
    @DynamoDBAttribute(attributeName = "eventName")
    public String getEventName() {
        return _eventName;
    }

    public void setEventName(final String _eventName) {
        this._eventName = _eventName;
    }
    @DynamoDBAttribute(attributeName = "userIGN")
    public String getUserIGN() {
        return _userIGN;
    }

    public void setUserIGN(final String _userIGN) {
        this._userIGN = _userIGN;
    }
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }

}
