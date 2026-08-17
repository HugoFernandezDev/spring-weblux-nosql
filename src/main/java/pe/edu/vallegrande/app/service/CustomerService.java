package pe.edu.vallegrande.app.service;

import pe.edu.vallegrande.app.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    // Operaciones para MongoDB Local (database_local)
    Flux<Customer> findAllLocal();
    Mono<Customer> findByIdLocal(String id);
    Mono<Customer> saveLocal(Customer customer);

    // Operaciones para MongoDB Cloud Atlas (database_cloud)
    Flux<Customer> findAllCloud();
    Mono<Customer> findByIdCloud(String id);
    Mono<Customer> saveCloud(Customer customer);

    // Operaciones combinadas (Ambas BD simultáneamente)
    Flux<Customer> findAllBoth();
    Mono<Customer> saveBoth(Customer customer);

}