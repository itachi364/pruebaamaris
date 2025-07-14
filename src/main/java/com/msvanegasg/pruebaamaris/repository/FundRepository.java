package com.msvanegasg.pruebaamaris.repository;

import com.msvanegasg.pruebaamaris.models.Fund;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import java.util.*;
import java.util.stream.*;

@Repository
public class FundRepository {

    private final DynamoDbTable<Fund> table;

    public FundRepository(DynamoDbEnhancedClient enhancedClient) {
        this.table = enhancedClient.table("Funds", TableSchema.fromBean(Fund.class));
    }

    public Fund save(Fund fund) {
        table.putItem(fund);
        return fund;
    }

    public List<Fund> findAll() {
        return table.scan().items().stream().collect(Collectors.toList());
    }

    public Optional<Fund> findById(String id) {
        return Optional.ofNullable(table.getItem(r -> r.key(k -> k.partitionValue(id))));
    }

    public void deleteById(String id) {
        table.deleteItem(r -> r.key(k -> k.partitionValue(id)));
    }
}

