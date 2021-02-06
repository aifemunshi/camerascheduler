package com.javainuse.taskconfig.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

@Configuration
public class DynamoDBConfig {
	
	
	  @Value("${amazon.dynamodb.endpoint}")
	  private String dBEndpoint;
	 
	  @Value("${amazon.access.key}")
	  private String accessKey;
	 
	  @Value("${amazon.access.secret.key}")
	  private String secretKey;
	  
	  @Value("${amazon.dynamodb.region}")
	  private String awsRegion;
	 
	  @Bean
	  public AmazonDynamoDB amazonDynamoDB(){
		  return AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dBEndpoint, awsRegion))
				  .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey))).build();
	  }

}