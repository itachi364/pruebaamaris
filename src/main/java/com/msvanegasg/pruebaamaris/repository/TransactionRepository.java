package com.msvanegasg.pruebaamaris.repository;

import com.msvanegasg.pruebaamaris.models.Transaction;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;
import java.util.stream.*;

@Repository
public class TransactionRepository {

    private final DynamoDbTable<Transaction> table;
    private final DynamoDbIndex<Transaction> userIndex;

    public TransactionRepository(DynamoDbEnhancedClient enhancedClient) {
        this.table = enhancedClient.table("Transactions", TableSchema.fromBean(Transaction.class));
        this.userIndex = table.index("userId-index");
    }

    public Transaction save(Transaction transaction) {
        table.putItem(transaction);
        return transaction;
    }

    public List<Transaction> findByUserId(String userId) {
        QueryConditional conditional = QueryConditional.keyEqualTo(Key.builder().partitionValue(userId).build());

        return StreamSupport.stream(
                userIndex.query(r -> r.queryConditional(conditional)).spliterator(), false)
            .flatMap(p -> StreamSupport.stream(p.items().spliterator(), false))
            .collect(Collectors.toList());
    }


    public Optional<Transaction> findById(String id) {
        return Optional.ofNullable(table.getItem(r -> r.key(k -> k.partitionValue(id))));
    }
}
