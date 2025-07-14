package com.msvanegasg.pruebaamaris.repository;

import com.msvanegasg.pruebaamaris.models.UserBalance;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class UserBalanceRepository {

    private final DynamoDbTable<UserBalance> table;

    public UserBalanceRepository(DynamoDbEnhancedClient enhancedClient) {
        this.table = enhancedClient.table("UserBalances", TableSchema.fromBean(UserBalance.class));
    }

    public UserBalance save(UserBalance userBalance) {
        table.putItem(userBalance);
        return userBalance;
    }

    public Optional<UserBalance> findById(String id) {
        return Optional.ofNullable(table.getItem(r -> r.key(k -> k.partitionValue(id))));
    }

    public void deleteById(String id) {
        table.deleteItem(r -> r.key(k -> k.partitionValue(id)));
    }

    public List<UserBalance> findAll() {
        return StreamSupport.stream(table.scan().items().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<UserBalance> findByUserId(String userId) {
        return StreamSupport.stream(
                table.index("userId-index")
                     .query(r -> r.queryConditional(QueryConditional.keyEqualTo(k -> k.partitionValue(userId))))
                     .spliterator(), false
        ).flatMap(page -> page.items().stream())
         .collect(Collectors.toList());
    }

}
