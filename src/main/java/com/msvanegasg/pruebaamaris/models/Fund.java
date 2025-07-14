package com.msvanegasg.pruebaamaris.models;

import com.msvanegasg.pruebaamaris.enums.FundCategory;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@DynamoDbBean
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fund {

    private String fundId;
    private String name;
    private int minimumAmount;
    private FundCategory category;

    @DynamoDbPartitionKey
    public String getFundId() {
        return fundId;
    }
}
