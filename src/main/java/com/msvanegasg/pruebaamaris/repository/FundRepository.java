package com.msvanegasg.pruebaamaris.repository;

import com.msvanegasg.pruebaamaris.models.Fund;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        return StreamSupport.stream(table.scan().items().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Fund> findById(String id) {
        return Optional.ofNullable(table.getItem(r -> r.key(k -> k.partitionValue(id))));
    }

    public void deleteById(String id) {
        table.deleteItem(r -> r.key(k -> k.partitionValue(id)));
    }
}
