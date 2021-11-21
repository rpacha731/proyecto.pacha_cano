package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.exceptions.*;
import com.iua.iw3.proyecto.pacha_cano.model.*;
import com.iua.iw3.proyecto.pacha_cano.persistence.*;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.DatosCargaRequest;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.PesoFinalRequest;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.PesoInicialRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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
    public List<OrdenCarga> listAllByEstado(Integer indiceEstado) throws BusinessException, NotFoundException {
        try {
            List<OrdenCarga> aux;
            if (indiceEstado == 1) aux = ordenCargaRepository.findAllByEstado(Estados.E1);
            else if (indiceEstado == 2) aux = ordenCargaRepository.findAllByEstado(Estados.E2);
            else if (indiceEstado == 3) aux = ordenCargaRepository.findAllByEstado(Estados.E3);
            else if (indiceEstado == 4) aux = ordenCargaRepository.findAllByEstado(Estados.E4);
            else throw new NotFoundException("No existe un estado E" + indiceEstado);
            if (aux.isEmpty()) throw new NotFoundException("No hay ninguna orden en estado E" + indiceEstado);
            return aux;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    @Override
    public OrdenCarga load(Long idOrdenCarga) throws BusinessException, NotFoundException {
        Optional<OrdenCarga> o;
        try {
            o = ordenCargaRepository.findById(idOrdenCarga);
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
    public OrdenCarga getByNumeroOrden(Long numOrden) throws BusinessException, NotFoundException {
        Optional<OrdenCarga> o;
        try {
            o = ordenCargaRepository.findByNumeroOrden(numOrden);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (o.isPresent()) return o.get();
        throw new NotFoundException("No existe una orden con número de orden = " + numOrden);
    }

    @Override
    public OrdenCarga create(OrdenCarga ordenCarga) throws BusinessException, DuplicateException {
        try {
            loadByNumeroOrden(ordenCarga.getNumeroOrden());
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
    public OrdenCarga adjuntarTara(PesoInicialRequest pesoInicialRequest) throws BusinessException, WrongDateException {
        OrdenCarga aux;
        try {
            aux = this.getByNumeroOrden(pesoInicialRequest.getNumeroOrden());
            if (System.currentTimeMillis() <= aux.getFechaHoraTurno().getTime())
                throw new WrongDateException("Todavia no es la fecha / hora del turno de la orden con numero de orden = " + aux.getNumeroOrden());
            aux.setPesoInicial(pesoInicialRequest.getPesoInicial());
            aux.setFechaHoraPesoInicial(new Date());
            aux.setEstado(Estados.E2);
            aux.setPassword(OrdenCarga.generateRandomPassword());
            return ordenCargaRepository.save(aux);
        } catch (NotFoundException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public String adjuntarDatoCarga(DatosCargaRequest datosCargaRequest) throws BusinessException {
        OrdenCarga aux;
        try { // VER COMO PUEDO SABER CUAL ES EL ULTIMO
            aux = this.getByNumeroOrden(datosCargaRequest.getNumeroOrden());
//            log.error(aux.toString());
            if (aux.getEstado().toString().equals("E2") && aux.getPassword().equals(datosCargaRequest.getPassword())) {
                log.error("entre");
                // guardará los datos si está en estado E2, la password es correcta y pasó el periodo de tiempo de guardado
                if (aux.getFechaHoraInicioCarga() == null) aux.setFechaHoraInicioCarga(new Date()); // asigno la primera vez la fecha/hora del ultimo registro guardado
                if (aux.getFechaHoraFinCarga() == null) aux.setFechaHoraFinCarga(new Date()); // asigno la primera vez la fecha/hora del ultimo registro guardado
                if (datosCargaRequest.getMasaAcumulada() < aux.getPreset()) { // Si todavia falta para llegar al preset
                    Calendar time = Calendar.getInstance();
                    //log.warn("time con getInstance: " + time.getTime());
                    time.setTime(aux.getFechaHoraFinCarga());
                    //log.warn("time con setTime: " + time.getTime());
                    time.add(Calendar.SECOND, aux.getFrecuencia());
                    //log.warn("time con getFrecuencia: " + time.getTime());
                    if (time.getTime().after(new Date())) {

                        log.warn("NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO " + datosCargaRequest.getMasaAcumulada());

                        return "OK";
                    } // No se guarda el registro del dato
                    aux.setFechaHoraFinCarga(new Date());
                    DatosCarga tmp = DatosCarga.builder()
                            .masaAcumulada(datosCargaRequest.getMasaAcumulada())
                            .densidad(datosCargaRequest.getDensidad())
                            .temperatura(datosCargaRequest.getTemperatura())
                            .caudal(datosCargaRequest.getCaudal()).build();
                    aux.getRegistroDatosCarga().add(tmp);
                    aux.setRegistroDatosCarga(aux.getRegistroDatosCarga());
                    ordenCargaRepository.save(aux);
                    log.warn("guarde");
                    return "OK";

                } else { // si llegó al preset, cierro la orden
                    log.warn("*****************");
                    if (aux.getEstado().toString() == "E2") {
                        aux.setEstado(Estados.E3);
                        ordenCargaRepository.save(aux);
                        return "ORDEN_CERRADA";
                    }
                    return "CANCEL";
                }

            } else {
                throw new BusinessException("La orden no está en estado E2, puede que se haya cancelado | La password es incorrecta. ");
            }
        } catch (NotFoundException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public OrdenCarga cerrarOrden(Long numeroOrden) throws BusinessException, NotFoundException {
        OrdenCarga old = this.getByNumeroOrden(numeroOrden);
        if (old.getEstado().equals(Estados.E2)) {
            old.setEstado(Estados.E3);
        } else {
            throw new BusinessException("La orden no se puede cerrar, no está en estado E2");
        }
        return this.ordenCargaRepository.save(old);
    }

    @Override
    public Conciliacion adjuntarPesoFinal(PesoFinalRequest pesoFinalRequest) throws BusinessException {
        try {
            OrdenCarga aux = this.getByNumeroOrden(pesoFinalRequest.getNumeroOrden());
            if (!aux.getEstado().equals(Estados.E3)) throw new BusinessException("No se puede adjuntar el peso final, la orden no está cerrada");

            aux.setFechaHoraPesoFinal(new Date());
            aux.setEstado(Estados.E4);
            aux.setPesoFinal(pesoFinalRequest.getPesoFinal());

            this.ordenCargaRepository.save(aux);

            return generateConciliacion(aux);
        } catch (NotFoundException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public Conciliacion generateConciliacion(Long numeroOrden) throws BusinessException {
        try {
            OrdenCarga o = this.getByNumeroOrden(numeroOrden);
            if (!o.getEstado().equals(Estados.E4)) throw new BusinessException("La orden no está cerrada, no se puede crear a conciliacion");
            return this.generateConciliacion(o);
        } catch (NotFoundException e) {
            throw new BusinessException(e);
        }
    }

    private Conciliacion generateConciliacion (OrdenCarga ordenCarga) {
        Double [] promedios = new Double[]{0.0, 0.0, 0.0};  // [0] = TEMPERATURA * [1] = DENSIDAD * [2] = CAUDAL
        for (DatosCarga dato : ordenCarga.getRegistroDatosCarga()) {
            promedios[0] = promedios[0] + dato.getTemperatura();
            promedios[1] = promedios[1] + dato.getDensidad();
            promedios[2] = promedios[2] + dato.getCaudal();
        }
        int totalRegistros = ordenCarga.getRegistroDatosCarga().size();
        promedios[0] = promedios[0] / totalRegistros;
        promedios[1] = promedios[1] / totalRegistros;
        promedios[2] = promedios[2] / totalRegistros;

        Double masaAcum = ordenCarga.getRegistroDatosCarga().get(totalRegistros - 1).getMasaAcumulada();
        Double netoBalanza = ordenCarga.getPesoFinal() - ordenCarga.getPesoInicial();

        DatosCarga dato = DatosCarga.builder()
                .masaAcumulada(masaAcum)
                .temperatura(promedios[0])
                .densidad(promedios[1])
                .caudal(promedios[2]).build();

        Conciliacion conci = Conciliacion.builder()
                .numeroOrden(ordenCarga.getNumeroOrden())
                .pesoInicial(ordenCarga.getPesoInicial())
                .pesoFinal(ordenCarga.getPesoFinal())
                .netoBalanza(netoBalanza)
                .difBalanzaYCaudal(netoBalanza - masaAcum)
                .promedioDatosCarga(dato).build();

        return conci;
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
