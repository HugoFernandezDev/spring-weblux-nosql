package pe.edu.vallegrande.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.app.model.Customer;
import pe.edu.vallegrande.app.service.CustomerService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final ReactiveMongoTemplate localMongoTemplate;
    private final ReactiveMongoTemplate cloudMongoTemplate;

    @Autowired
    public CustomerServiceImpl(
            @Qualifier("localMongoTemplate") ReactiveMongoTemplate localMongoTemplate,
            @Qualifier("cloudMongoTemplate") ReactiveMongoTemplate cloudMongoTemplate) {
        this.localMongoTemplate = localMongoTemplate;
        this.cloudMongoTemplate = cloudMongoTemplate;
    }

    // --- OPERACIONES LOCAL (database_local) ---
    @Override
    public Flux<Customer> findAllLocal() {
        log.info("Consultando clientes en MongoDB Local (database_local)");
        return localMongoTemplate.findAll(Customer.class);
    }

    @Override
    public Mono<Customer> findByIdLocal(String id) {
        log.info("Buscando cliente por ID en MongoDB Local: " + id);
        return localMongoTemplate.findById(id, Customer.class);
    }

    @Override
    public Mono<Customer> saveLocal(Customer customer) {
        log.info("Guardando cliente en MongoDB Local (database_local)");
        Customer localCustomer = createCustomerCopy(customer);
        return localMongoTemplate.save(localCustomer);
    }

    // --- OPERACIONES CLOUD (database_cloud) ---
    @Override
    public Flux<Customer> findAllCloud() {
        log.info("Consultando clientes en MongoDB Cloud Atlas (database_cloud)");
        return cloudMongoTemplate.findAll(Customer.class);
    }

    @Override
    public Mono<Customer> findByIdCloud(String id) {
        log.info("Buscando cliente por ID en MongoDB Cloud: " + id);
        return cloudMongoTemplate.findById(id, Customer.class);
    }

    @Override
    public Mono<Customer> saveCloud(Customer customer) {
        log.info("Guardando cliente en MongoDB Cloud Atlas (database_cloud)");
        Customer cloudCustomer = createCustomerCopy(customer);
        return cloudMongoTemplate.save(cloudCustomer);
    }

    // --- OPERACIONES SIMULTÁNEAS ---
    @Override
    public Flux<Customer> findAllBoth() {
        log.info("Consultando clientes en ambas bases de datos (Local y Cloud)");
        return Flux.concat(findAllLocal(), findAllCloud());
    }

    @Override
    public Mono<Customer> saveBoth(Customer customer) {
        log.info("Guardando cliente simultáneamente en MongoDB Local y Cloud Atlas");
        return Mono.zip(saveLocal(customer), saveCloud(customer))
                .map(tuple -> tuple.getT1());
    }

    private Customer createCustomerCopy(Customer src) {
        Customer c = new Customer();
        c.setId(src.getId()); // Preserva ID si viene especificado, o null para auto-generar
        c.setDni(src.getDni());
        c.setFirstName(src.getFirstName());
        c.setLastName(src.getLastName());
        c.setState(src.getState() != null ? src.getState() : "A");
        return c;
    }

}