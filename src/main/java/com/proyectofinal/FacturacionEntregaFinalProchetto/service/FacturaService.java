package com.proyectofinal.FacturacionEntregaFinalProchetto.service;

import com.proyectofinal.FacturacionEntregaFinalProchetto.dto.FacturaDTO;
import com.proyectofinal.FacturacionEntregaFinalProchetto.dto.LineaDTO;
import com.proyectofinal.FacturacionEntregaFinalProchetto.entidades.*;
import com.proyectofinal.FacturacionEntregaFinalProchetto.exception.CantidadProductosInsuficienteException;
import com.proyectofinal.FacturacionEntregaFinalProchetto.exception.ElementoNoEncontradoException;
import com.proyectofinal.FacturacionEntregaFinalProchetto.repository.ClienteRepository;
import com.proyectofinal.FacturacionEntregaFinalProchetto.repository.FacturaRepository;
import com.proyectofinal.FacturacionEntregaFinalProchetto.repository.LineaRepository;
import com.proyectofinal.FacturacionEntregaFinalProchetto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacturaService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private LineaRepository lineaRepository;

    @Autowired
    private RestTemplate restTemplate;

    //POST
    public FacturaDTO createFactura(FacturaDTO facturaDTO) {
        Cliente cliente = getClienteOrThrow(facturaDTO.getCliente().getClienteid());

        BigDecimal total = BigDecimal.ZERO;
        int cantidadProductos = 0;

        Set<LineaDTO> lineasDTO = facturaDTO.getLineas();
        Set<Linea> lineasModel = new HashSet<>();

        for (LineaDTO lineaDTO : lineasDTO) {
            Producto producto = getProductoOrThrow(lineaDTO.getProducto().getProductoid());

            String descripcion = Optional.ofNullable(lineaDTO.getDescripcion())
                    .filter(desc -> !desc.isEmpty())
                    .orElse(producto.getDescripcion());

            Linea lineaModel = new Linea();
            lineaModel.setCantidad(lineaDTO.getCantidad());
            lineaModel.setDescripcion(descripcion);
            lineaModel.setPrecio(producto.getPrecio());
            lineaModel.setProducto(producto);
            lineaModel.setFactura(null);

            lineasModel.add(lineaModel);

            total = total.add(producto.getPrecio().multiply(BigDecimal.valueOf(lineaDTO.getCantidad())));
            cantidadProductos += lineaDTO.getCantidad();
        }

        Date fecha = obtenerFecha();

        Factura facturaModel = new Factura();
        facturaModel.setCantidad(cantidadProductos);
        facturaModel.setFecha(fecha);
        facturaModel.setCliente(cliente);
        facturaModel.setTotal(total);

        if (!lineasModel.isEmpty()) {
            final Factura finalFacturaModel = facturaModel;

            lineasModel.forEach(lineaModel -> {
                lineaModel.setFactura(finalFacturaModel);
                finalFacturaModel.addLinea(lineaModel);
            });
            facturaRepository.save(finalFacturaModel);

            actualizarStock(lineasModel);
        } else {
            System.out.println("No hay líneas en la factura");
        }

        return convertirFacturaModelADTO(facturaModel);
    }

    //Obtener cliente o lanzar una exception
    private Cliente getClienteOrThrow(Integer clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ElementoNoEncontradoException("Cliente no encontrado"));
    }

    //Obtener producto o lanzar una exception
    private Producto getProductoOrThrow(Integer productoId) {
        return productoRepository.findById(productoId)
                .orElseThrow(() -> new ElementoNoEncontradoException("Producto no encontrado"));
    }

    //Obtener la fecha
    private Date obtenerFecha() {
        int maxRetries = 3;
        int retryCount = 0;

        //Se intenta multiples veces usar WordClock, sino se usa la fecha Date().
        while (retryCount < maxRetries) {
            try {
                WordClock worldClock = restTemplate.getForObject("http://worldclockapi.com/api/json/utc/now", WordClock.class);
                String currentDateTime = worldClock.getCurrentDateTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                return dateFormat.parse(currentDateTime);
            } catch (HttpServerErrorException e) {
                retryCount++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return new Date();
            }
        }

        throw new RuntimeException("No se pudo acceder al servicio WorldClock despues de varios intentos");
    }

    //Se pasa un objeto FacturaModel a FacturaDTO
    private FacturaDTO convertirFacturaModelADTO(Factura facturaModel) {
        FacturaDTO facturaDTO = new FacturaDTO();
        facturaDTO.setFacturaid(facturaModel.getFacturaid());
        facturaDTO.setCantidad(facturaModel.getCantidad());
        facturaDTO.setFecha(facturaModel.getFecha());
        facturaDTO.setTotal(facturaModel.getTotal());
        facturaDTO.setCliente(facturaModel.getCliente());
        facturaDTO.setLineas(convertirLineasModelADTO(facturaModel.getLineas()));
        return facturaDTO;
    }

    //Se convierte un objeto LineasModel y se lo pasa a DTO
    private Set<LineaDTO> convertirLineasModelADTO(Set<Linea> lineasModel) {
        return lineasModel.stream()
                .map(this::convertirLineaModelADTO)
                .collect(Collectors.toSet());
    }

    //Se convierte un objeto LineaModel a LineaDTO
    private LineaDTO convertirLineaModelADTO(Linea lineaModel) {
        LineaDTO lineaDTO = new LineaDTO();
        lineaDTO.setLineaid(lineaModel.getLineaid());
        lineaDTO.setCantidad(lineaModel.getCantidad());
        lineaDTO.setDescripcion(lineaModel.getDescripcion());
        lineaDTO.setPrecio(lineaModel.getPrecio());
        return lineaDTO;
    }

    //GET
    public List<FacturaDTO> getAllFacturas() {
        List<Factura> facturas = facturaRepository.findAll();
        return pasarFacturasModelADTO(facturas);
    }

    //GET by id
    public FacturaDTO getFacturaById(Integer id) {
        Factura facturaModel = facturaRepository.findById(id)
                .orElseThrow(() -> new ElementoNoEncontradoException("Factura no encontrada con ID: " + id));

        facturaModel.getLineas().size();

        return convertirFacturaModelADTO(facturaModel);
    }

    //Se pasa un objeto FacturasModel a DTO
    private List<FacturaDTO> pasarFacturasModelADTO(List<Factura> facturasModel) {
        return facturasModel.stream()
                .map(this::convertirFacturaModelADTO)
                .collect(Collectors.toList());
    }

    //Actualizar el stock
    private void actualizarStock(Set<Linea> lineasModel) {
        for (Linea lineaModel : lineasModel) {
            Producto producto = lineaModel.getProducto();
            int cantidadSolicitada = lineaModel.getCantidad();

            //Validar que la cantidad de producto solicitada sea mayor a la cantidad de stock
            if (producto.getCantidad() < cantidadSolicitada) {
                throw new CantidadProductosInsuficienteException("No hay suficiente stock de: " + producto.getDescripcion());
            }

            producto.setCantidad(producto.getCantidad() - cantidadSolicitada);
            productoRepository.save(producto);
        }
    }

}
