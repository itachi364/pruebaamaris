package com.msvanegasg.pruebaamaris.models;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;
import com.msvanegasg.pruebaamaris.enums.NotificationType;
import com.msvanegasg.pruebaamaris.enums.TransactionType;

@DynamoDbBean
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBalance {

	private String balanceId;
	private String fundId;
	private String userId;
	private int amount;
	private int balanceAfter;
	private TransactionType type;
	private NotificationType notificationType;
	private long timestamp;

	@DynamoDbPartitionKey
	public String getBalanceId() {
		return balanceId;
	}

	@DynamoDbSecondaryPartitionKey(indexNames = "userId-index")
	public String getUserId() {
		return userId;
	}

}
