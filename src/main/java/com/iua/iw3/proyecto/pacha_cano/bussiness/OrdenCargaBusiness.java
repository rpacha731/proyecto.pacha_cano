package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.FoundException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;
import com.iua.iw3.proyecto.pacha_cano.model.Estados;
import com.iua.iw3.proyecto.pacha_cano.model.OrdenCarga;
import com.iua.iw3.proyecto.pacha_cano.persistence.OrdenCargaRepository;
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
    public OrdenCarga load(Long aLong) throws BusinessException, NotFoundException {
        Optional<OrdenCarga> o;
        try {
            o = ordenCargaRepository.findByNumeroOrden(aLong);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (o.isEmpty())
            return null;// throw new NotFoundException("No se encuentra la orden de carga con numero de orden = " + aLong);
        return o.get();
    }

    @Override
    public OrdenCarga create(OrdenCarga object) throws BusinessException, FoundException {
        try {
            OrdenCarga o = load(object.getNumeroOrden());
            if (o != null) {
                throw new FoundException("Ya existe una orden de carga con numero de orden = " + object.getNumeroOrden());
            }

        } catch (NotFoundException e) {
            log.error("****************************************************");
            try {
                OrdenCarga ordenCarga = OrdenCarga.builder()
                        .numeroOrden(object.getNumeroOrden())
                        .camion(object.getCamion())
                        .chofer(object.getChofer())
                        .cliente(object.getCliente())
                        .producto(object.getProducto())
                        .fechaHoraRecepcion(new Date())
                        .fechaHoraTurno(object.getFechaHoraTurno())
                        .preset(object.getPreset())
                        .estado(Estados.E1)
                        .frecuencia(1)
                        .build();
                return ordenCargaRepository.save(ordenCarga);
            } catch (Exception f) {
                log.error(f.getMessage(), f);
                throw new BusinessException(f);
            }
        }
        return null;
        //return ordenCargaRepository.save(ordenCarga);
    }


    @Override
    public OrdenCarga modify(OrdenCarga object) throws BusinessException, NotFoundException {
        OrdenCarga old = load(object.getId());
        try {
            return ordenCargaRepository.save(object);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    @Override
    public void deleteById(Long aLong) throws BusinessException, NotFoundException {
        load(aLong);
        try {
            ordenCargaRepository.deleteById(aLong);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }
}
