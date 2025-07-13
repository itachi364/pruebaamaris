package com.msvanegasg.pruebaamaris.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fund {

	private String fundId;
    private String name;
    private int minimumAmount;

    @DynamoDbPartitionKey
    public String getFundId() {
        return fundId;
    }
}
