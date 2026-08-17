package pe.edu.vallegrande.app.config;

import com.mongodb.ConnectionString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

@Configuration
public class MongoConfig {

    @Value("${mongodb.local.uri}")
    private String localUri;

    @Value("${mongodb.cloud.uri}")
    private String cloudUri;

    @Bean(name = {"reactiveMongoTemplate", "localMongoTemplate"})
    @Primary
    public ReactiveMongoTemplate localMongoTemplate() {
        ConnectionString connectionString = new ConnectionString(localUri);
        return new ReactiveMongoTemplate(new SimpleReactiveMongoDatabaseFactory(connectionString));
    }

    @Bean(name = "cloudMongoTemplate")
    public ReactiveMongoTemplate cloudMongoTemplate() {
        ConnectionString connectionString = new ConnectionString(cloudUri);
        return new ReactiveMongoTemplate(new SimpleReactiveMongoDatabaseFactory(connectionString));
    }

}
