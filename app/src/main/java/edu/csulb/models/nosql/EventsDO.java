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
    private Set<String> _currentParticipants;
    private String _eventName;
    private String _eventTime;
    private Boolean _isLimited;
    private Double _maxParticipants;
    private String _userId;

    @DynamoDBHashKey(attributeName = "eventId")
    @DynamoDBAttribute(attributeName = "eventId")
    public String getEventId() {
        return _eventId;
    }

    public void setEventId(final String _eventId) {
        this._eventId = _eventId;
    }
    @DynamoDBAttribute(attributeName = "currentParticipants")
    public Set<String> getCurrentParticipants() {
        return _currentParticipants;
    }

    public void setCurrentParticipants(final Set<String> _currentParticipants) {
        this._currentParticipants = _currentParticipants;
    }
    @DynamoDBAttribute(attributeName = "eventName")
    public String getEventName() {
        return _eventName;
    }

    public void setEventName(final String _eventName) {
        this._eventName = _eventName;
    }
    @DynamoDBAttribute(attributeName = "eventTime")
    public String getEventTime() {
        return _eventTime;
    }

    public void setEventTime(final String _eventTime) {
        this._eventTime = _eventTime;
    }
    @DynamoDBAttribute(attributeName = "isLimited")
    public Boolean getIsLimited() {
        return _isLimited;
    }

    public void setIsLimited(final Boolean _isLimited) {
        this._isLimited = _isLimited;
    }
    @DynamoDBAttribute(attributeName = "maxParticipants")
    public Double getMaxParticipants() {
        return _maxParticipants;
    }

    public void setMaxParticipants(final Double _maxParticipants) {
        this._maxParticipants = _maxParticipants;
    }
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }

}
