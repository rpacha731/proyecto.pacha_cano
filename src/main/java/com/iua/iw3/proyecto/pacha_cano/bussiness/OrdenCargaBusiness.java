package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.DuplicateException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.FoundException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;
import com.iua.iw3.proyecto.pacha_cano.model.*;
import com.iua.iw3.proyecto.pacha_cano.persistence.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OrdenCargaBusiness implements IOrdenCargaBusiness {

    private OrdenCargaRepository ordenCargaRepository;
    private CamionRepository camionRepository;
    private ClienteRepository clienteRepository;
    private ChoferRepository choferRepository;
    private ProductoRepository productoRepository;

    @Override
    public List<OrdenCarga> listAll() throws BusinessException {
        try {
            return ordenCargaRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    @Override
    public OrdenCarga load(Long idOrdenCarga) throws BusinessException, NotFoundException {
        Optional<OrdenCarga> o;
        try {
            o = ordenCargaRepository.findByNumeroOrden(idOrdenCarga);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (o.isEmpty())
            throw new NotFoundException("No se encuentra la orden de carga con id = " + idOrdenCarga);
        return o.get();
    }

    @Override
    public OrdenCarga loadByNumeroOrden(Long numOrden) throws BusinessException, DuplicateException {
        Optional<OrdenCarga> o;
        try {
            o = ordenCargaRepository.findByNumeroOrden(numOrden);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (o.isPresent()) throw new DuplicateException("Ya existe una orden de carga con el numero de orden = " + numOrden);
        return null;
    }

    @Override
    public OrdenCarga create(OrdenCarga ordenCarga) throws BusinessException, DuplicateException {
        try {
            OrdenCarga o = loadByNumeroOrden(ordenCarga.getNumeroOrden());
        } catch (DuplicateException e) {
            throw new DuplicateException(e);
        }
        try {
            Optional<Camion> camionAux = camionRepository.findByPatente(ordenCarga.getCamion().getPatente());
            Optional<Chofer> choferAux = choferRepository.findByDni(ordenCarga.getChofer().getDni());
            Optional<Cliente> clienteAux = clienteRepository.findByRazonSocial(ordenCarga.getCliente().getRazonSocial());
            Optional<Producto> productoAux = productoRepository.findByNombre(ordenCarga.getProducto().getNombre());

            OrdenCarga orden = OrdenCarga.builder()
                    .numeroOrden(ordenCarga.getNumeroOrden())
                    .camion(camionAux.isEmpty() ? ordenCarga.getCamion() : camionAux.get())
                    .chofer(choferAux.isEmpty() ? ordenCarga.getChofer() : choferAux.get())
                    .cliente(clienteAux.isEmpty() ? ordenCarga.getCliente() : clienteAux.get())
                    .producto(productoAux.isEmpty() ? ordenCarga.getProducto() : productoAux.get())
                    .fechaHoraRecepcion(new Date())
                    .fechaHoraTurno(ordenCarga.getFechaHoraTurno())
                    .preset(ordenCarga.getPreset())
                    .estado(Estados.E1)
                    .frecuencia(1)
                    .build();
            return ordenCargaRepository.save(orden);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }


    @Override
    public OrdenCarga modify(OrdenCarga ordenCarga) throws BusinessException, NotFoundException {
        OrdenCarga old = load(ordenCarga.getId());
        try {
            return ordenCargaRepository.save(ordenCarga);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    @Override
    public void deleteById(Long idOrdenCarga) throws BusinessException, NotFoundException {
        load(idOrdenCarga);
        try {
            ordenCargaRepository.deleteById(idOrdenCarga);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }


}
