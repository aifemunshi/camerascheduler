package com.javainuse.taskconfig.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "t_dyn_mailrecord")
public class Email {
	
	private String serialId;
	private String sender;
	private String path;
	private String dateTime;
	
	public Email(){}

	public Email(String serialId, String sender, String path, String dateTime) {
		this.serialId = serialId;
		this.sender = sender;
		this.path = path;
		this.dateTime = dateTime;
	}

	@DynamoDBHashKey(attributeName = "serialid")
	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}
	@DynamoDBAttribute(attributeName = "sender")
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	@DynamoDBAttribute(attributeName = "path")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@DynamoDBRangeKey(attributeName = "mailtime")
	public String getDateTime() {
		return dateTime;
	}
    
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "Email [serialId=" + serialId + ", sender=" + sender + ", path=" + path + ", dateTime=" + dateTime + "]";
	}

}
