package click.bitbank.api.infrastructure.config;

import click.bitbank.api.infrastructure.r2dbc.ConnectionMode;
import click.bitbank.api.infrastructure.r2dbc.MultiTenantRoutingConnectionFactory;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableR2dbcRepositories
@EnableR2dbcAuditing(modifyOnCreate = false)
public class DataSourceR2dbcConfig extends AbstractR2dbcConfiguration {

    @Value("${spring.r2dbc.master-url}")
    private String masterUrl;

    @Value("${spring.r2dbc.slave-url}")
    private String slaveUrl;

    @Primary
    @Bean("masterConnectionFactory")
    public ConnectionFactory masterConnectionFactory() {
        return ConnectionFactories.get(masterUrl);
    }

    @Bean("slaveConnectionFactory")
    public ConnectionFactory slaveConnectionFactory() {
        return ConnectionFactories.get(slaveUrl);
    }

    @Nonnull
    @Bean
    @Override
    public ConnectionFactory connectionFactory() {
        MultiTenantRoutingConnectionFactory multiTenantRoutingConnectionFactory = new MultiTenantRoutingConnectionFactory();

        Map<String, ConnectionFactory> factories = new HashMap<>();
        factories.put(ConnectionMode.MASTER.name(), this.masterConnectionFactory());
        factories.put(ConnectionMode.SLAVE.name(), this.slaveConnectionFactory());


        multiTenantRoutingConnectionFactory.setDefaultTargetConnectionFactory(this.masterConnectionFactory());
        multiTenantRoutingConnectionFactory.setTargetConnectionFactories(factories);
        return multiTenantRoutingConnectionFactory;
    }

    @Primary
    @Bean("masterTransactionManager")
    public ReactiveTransactionManager masterTransactionManager(@Qualifier("masterConnectionFactory") ConnectionFactory connectionFactory) {

        return new R2dbcTransactionManager(connectionFactory);
    }

    @Bean("slaveTransactionManager")
    public ReactiveTransactionManager slaveTransactionManager(@Qualifier("slaveConnectionFactory") ConnectionFactory connectionFactory) {
        R2dbcTransactionManager slaveR2dbcTransactionManager = new R2dbcTransactionManager(connectionFactory);
        slaveR2dbcTransactionManager.setEnforceReadOnly(true); // read only

        return slaveR2dbcTransactionManager;
    }

}
