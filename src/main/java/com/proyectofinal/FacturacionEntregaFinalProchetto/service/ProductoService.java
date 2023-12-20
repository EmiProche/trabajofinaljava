package com.proyectofinal.FacturacionEntregaFinalProchetto.service;

import com.proyectofinal.FacturacionEntregaFinalProchetto.entidades.Producto;
import com.proyectofinal.FacturacionEntregaFinalProchetto.exception.ElementoNoEncontradoException;
import com.proyectofinal.FacturacionEntregaFinalProchetto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    //GET
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    //POST
    public Producto create(Producto producto) {
        return productoRepository.save(producto);
    }

    //GET by id
    public Producto findById(Integer productId) {
        validateId(productId);

        Optional<Producto> optionalProducto = productoRepository.findById(productId);

        return optionalProducto.orElseThrow(() ->
                new ElementoNoEncontradoException("Producto no encontrado con ID: " + productId));
    }

    //PUT
    public Producto update(Integer productId, Producto productoActualizado) {
        validateId(productId);

        Optional<Producto> optionalProducto = productoRepository.findById(productId);

        Producto productoExistente = optionalProducto.orElseThrow(() ->
                new ElementoNoEncontradoException("Producto no encontrado con ID: " + productId));

        updateProductoData(productoExistente, productoActualizado);

        return productoRepository.save(productoExistente);
    }

    //DELETE
    public void delete(Integer productId) {
        validateId(productId);

        Optional<Producto> optionalProducto = productoRepository.findById(productId);

        if (optionalProducto.isPresent()) {
            productoRepository.deleteById(productId);
        } else {
            throw new ElementoNoEncontradoException("Producto no encontrado con ID: " + productId);
        }
    }

    //Validar el id del producto
    private void validateId(Integer productId) {
        Objects.requireNonNull(productId, "El ID proporcionado no puede ser nulo.");
        if (productId <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }
    }

    //Actualizar los datos del producto
    private void updateProductoData(Producto productoExistente, Producto productoActualizado) {
        productoExistente.setDescripcion(productoActualizado.getDescripcion());
        productoExistente.setPrecio(productoActualizado.getPrecio());
        productoExistente.setCantidad(productoActualizado.getCantidad());
    }

}
