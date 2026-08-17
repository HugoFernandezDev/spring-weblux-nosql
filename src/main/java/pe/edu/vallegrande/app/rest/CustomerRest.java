package pe.edu.vallegrande.app.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.app.model.Customer;
import pe.edu.vallegrande.app.service.CustomerService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/customer")
@Tag(name = "Customer", description = "API reactiva para gestión simultánea de Clientes en MongoDB Local (Docker) y MongoDB Cloud (Atlas)")
public class CustomerRest {

    private final CustomerService customerService;

    @Autowired
    public CustomerRest(CustomerService customerService) {
        this.customerService = customerService;
    }

    // ==========================================
    // 🍃 ENDPOINTS LOCAL (database_local)
    // ==========================================
    @Operation(summary = "GET Local: Listar clientes de MongoDB Local (Docker)")
    @GetMapping("/local")
    public Flux<Customer> findAllLocal() {
        return customerService.findAllLocal();
    }

    @Operation(summary = "GET Local por ID: Buscar cliente por ID en MongoDB Local (Docker)")
    @GetMapping("/local/{id}")
    public Mono<Customer> findByIdLocal(@PathVariable String id) {
        return customerService.findByIdLocal(id);
    }

    @Operation(summary = "POST Local: Guardar cliente en MongoDB Local (Docker)")
    @PostMapping("/local/save")
    public Mono<Customer> saveLocal(@RequestBody Customer customer) {
        return customerService.saveLocal(customer);
    }

    // ==========================================
    // ☁️ ENDPOINTS CLOUD (database_cloud - Atlas)
    // ==========================================
    @Operation(summary = "GET Cloud: Listar clientes de MongoDB Cloud Atlas")
    @GetMapping("/cloud")
    public Flux<Customer> findAllCloud() {
        return customerService.findAllCloud();
    }

    @Operation(summary = "GET Cloud por ID: Buscar cliente por ID en MongoDB Cloud Atlas")
    @GetMapping("/cloud/{id}")
    public Mono<Customer> findByIdCloud(@PathVariable String id) {
        return customerService.findByIdCloud(id);
    }

    @Operation(summary = "POST Cloud: Guardar cliente en MongoDB Cloud Atlas")
    @PostMapping("/cloud/save")
    public Mono<Customer> saveCloud(@RequestBody Customer customer) {
        return customerService.saveCloud(customer);
    }

    // ==========================================
    // ⚡ ENDPOINTS SIMULTÁNEOS (Local + Cloud)
    // ==========================================
    @Operation(summary = "GET All: Listar clientes de ambas bases de datos (Local + Cloud)")
    @GetMapping
    public Flux<Customer> findAllBoth() {
        return customerService.findAllBoth();
    }

    @Operation(summary = "POST Both: Guardar cliente SIMULTÁNEAMENTE en Local y Cloud Atlas")
    @PostMapping("/save")
    public Mono<Customer> saveBoth(@RequestBody Customer customer) {
        return customerService.saveBoth(customer);
    }

}
