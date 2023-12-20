package com.proyectofinal.FacturacionEntregaFinalProchetto.service;

import com.proyectofinal.FacturacionEntregaFinalProchetto.entidades.Cliente;
import com.proyectofinal.FacturacionEntregaFinalProchetto.exception.ElementoNoEncontradoException;
import com.proyectofinal.FacturacionEntregaFinalProchetto.repository.ClienteRepository;
import com.proyectofinal.FacturacionEntregaFinalProchetto.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final FacturaRepository facturaRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, FacturaRepository facturaRepository) {
        this.clienteRepository = clienteRepository;
        this.facturaRepository = facturaRepository;
    }

    //GET
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    //GET by id
    public Cliente findById(Integer id) {
        Objects.requireNonNull(id, "El ID proporcionado no puede ser nulo.");
        if (id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        Optional<Cliente> optionalCliente = clienteRepository.findById(id);

        return optionalCliente.orElseThrow(() ->
                new ElementoNoEncontradoException("Cliente no encontrado con ID: " + id));
    }

    //POST
    public Cliente create(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente proporcionado no puede ser nulo.");

        validateClienteData(cliente);

        return clienteRepository.save(cliente);
    }

    //PUT
    public Cliente update(Integer id, Cliente clienteActualizado) {
        Objects.requireNonNull(id, "El ID proporcionado no puede ser nulo.");
        if (id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        Optional<Cliente> optionalCliente = clienteRepository.findById(id);

        Cliente clienteExistente = optionalCliente.orElseThrow(() ->
                new ElementoNoEncontradoException("Cliente no encontrado con ID: " + id));

        updateClienteData(clienteExistente, clienteActualizado);

        return clienteRepository.save(clienteExistente);
    }

    //DELETE
    @Transactional
    public void delete(Integer id) {
        Objects.requireNonNull(id, "El ID proporcionado no puede ser nulo.");
        if (id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        Optional<Cliente> optionalCliente = clienteRepository.findById(id);

        Cliente cliente = optionalCliente.orElseThrow(() ->
                new ElementoNoEncontradoException("Cliente no encontrado con ID: " + id));

        facturaRepository.deleteByCliente(cliente);
        clienteRepository.deleteById(id);
    }

    //Validar los datos del cliente
    private void validateClienteData(Cliente cliente) {
        if (cliente.getDni() == null || cliente.getDni() <= 0) {
            throw new IllegalArgumentException("El DNI del cliente no es válido.");
        }

        if (cliente.getNombre() == null || cliente.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        }
    }

    //Actualizar los datos del cliente
    private void updateClienteData(Cliente clienteExistente, Cliente clienteActualizado) {
        clienteExistente.setDni(clienteActualizado.getDni());
        clienteExistente.setNombre(clienteActualizado.getNombre());
    }
}
