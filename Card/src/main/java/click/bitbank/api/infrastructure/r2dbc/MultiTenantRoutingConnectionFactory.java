package click.bitbank.api.infrastructure.r2dbc;

import org.springframework.r2dbc.connection.lookup.AbstractRoutingConnectionFactory;
import org.springframework.transaction.reactive.TransactionSynchronizationManager;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;

public class MultiTenantRoutingConnectionFactory extends AbstractRoutingConnectionFactory {

    @Nonnull
    @Override
    protected Mono<Object> determineCurrentLookupKey() {
        return TransactionSynchronizationManager.forCurrentTransaction().flatMap(transactionSynchronizationManager ->
            transactionSynchronizationManager.isActualTransactionActive() ?
                transactionSynchronizationManager.isCurrentTransactionReadOnly() ?
                    Mono.just(ConnectionMode.SLAVE.name()) : Mono.just(ConnectionMode.MASTER.name())
                : Mono.just(ConnectionMode.MASTER.name())
        );
    }

}
