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

import java.io.IOException;
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
            throw new NotFoundException("No se encuentra la orden de carga con número de orden = " + idOrdenCarga);
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
            Optional<Camion> camionAux = camionRepository.findByCodigoExterno(ordenCarga.getCamion().getCodigoExterno());
            Optional<Chofer> choferAux = choferRepository.findByCodigoExterno(ordenCarga.getChofer().getCodigoExterno());
            Optional<Cliente> clienteAux = clienteRepository.findByCodigoExterno(ordenCarga.getCliente().getCodigoExterno());
            Optional<Producto> productoAux = productoRepository.findByCodigoExterno(ordenCarga.getProducto().getCodigoExterno());

            OrdenCarga orden = OrdenCarga.builder()
                    .numeroOrden(ordenCarga.getNumeroOrden())
                    .codigoExterno(ordenCarga.getCodigoExterno())
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
        try {
            aux = this.getByNumeroOrden(datosCargaRequest.getNumeroOrden());

            if (aux.getEstado().toString().equals("E2") && aux.getPassword().equals(datosCargaRequest.getPassword())) {
                // Guardará los datos si está en estado E2,
                // el password es correcto y
                // pasó el periodo de tiempo de guardado (frecuencia de guardado)

                // Asigno la fecha / hora de inicio de carga (cuando llega el primer dato)
                if (aux.getFechaHoraInicioCarga() == null) aux.setFechaHoraInicioCarga(new Date());

                // Asigno, con la llegada del primer dato, la fecha / hora del último registro guardado
                // (servirá para calcular la frecuencia de guardado)
                if (aux.getFechaHoraFinCarga() == null) aux.setFechaHoraFinCarga(new Date());

                // Si la masa acumulada es menor al preset, es decir, todavía no terminé de cargar
                if (datosCargaRequest.getMasaAcumulada() < aux.getPreset()) {
                    // Calculo si tengo que guardar el valor o no según la frecuencia de guardado
                    Calendar time = Calendar.getInstance();
                    time.setTime(aux.getFechaHoraFinCarga());
                    time.add(Calendar.SECOND, aux.getFrecuencia());

                    if (new Date().before(time.getTime()) && aux.getRegistroDatosCarga().size() != 0) {
                        log.info("NO GUARDÉ " + datosCargaRequest.getMasaAcumulada()); return "OK"; } // No se guarda el registro del dato si todavía no llegué a la frecuencia

                    // A partir de aquí, si se guardaría el dato, pero primero verifico la masa y caudal
                    if (aux.getRegistroDatosCarga().size() > 0) {
                        DatosCarga ultimoGuardado = aux.getRegistroDatosCarga().get(aux.getRegistroDatosCarga().size() - 1);
                        if (datosCargaRequest.getMasaAcumulada() < ultimoGuardado.getMasaAcumulada()
                                || datosCargaRequest.getCaudal() <= 0
                                || datosCargaRequest.getMasaAcumulada() <= 0) { log.info("No if"); return "OK"; }
                    }
                    // No guardo el dato si se cumple el anterior if.
                    // El siguiente dato que llegaría, si no cumple el if, se guardaría por el tiempo de guardado

                    // Ahora sí, guardo el dato
                    aux.setFechaHoraFinCarga(new Date());

                    // Transformo el request en un dato de carga
                    DatosCarga tmp = DatosCarga.builder()
                            .masaAcumulada(datosCargaRequest.getMasaAcumulada())
                            .densidad(datosCargaRequest.getDensidad())
                            .temperatura(datosCargaRequest.getTemperatura())
                            .caudal(datosCargaRequest.getCaudal()).build();

                    // Agrego el dato y sobreescribo toda la lista de datos
                    aux.getRegistroDatosCarga().add(tmp);
                    aux.setRegistroDatosCarga(aux.getRegistroDatosCarga());

                    // Guardo la orden en la base de datos
                    ordenCargaRepository.save(aux);

                    log.info("Guardé " + datosCargaRequest.getMasaAcumulada());

                    return "OK";

                } else {

                    // La masa acumulada llegó al preset, por lo que cierro la orden de carga
                    if (aux.getEstado().toString() == "E2") {
                        aux.setEstado(Estados.E3);
                        ordenCargaRepository.save(aux);
                        return "ORDEN_CERRADA";
                    }

                    // A este return nunca se llegaría, pero el IDE lo pedía ja, ja, ja
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

            Double [] promedios = new Double[]{0.0, 0.0, 0.0};  // [0] = TEMPERATURA * [1] = DENSIDAD * [2] = CAUDAL
            for (DatosCarga dato : aux.getRegistroDatosCarga()) {
                promedios[0] = promedios[0] + dato.getTemperatura();
                promedios[1] = promedios[1] + dato.getDensidad();
                promedios[2] = promedios[2] + dato.getCaudal();
            }

            int totalRegistros = aux.getRegistroDatosCarga().size();
            promedios[0] = promedios[0] / totalRegistros;
            promedios[1] = promedios[1] / totalRegistros;
            promedios[2] = promedios[2] / totalRegistros;

            Double masaAcum = aux.getRegistroDatosCarga().get(totalRegistros - 1).getMasaAcumulada();

            DatosCarga dato = DatosCarga.builder()
                    .masaAcumulada(masaAcum)
                    .temperatura(promedios[0])
                    .densidad(promedios[1])
                    .caudal(promedios[2]).build();

            aux.setPromedioDatosCarga(dato);

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

    @Override
    public String generateCSVOrdenCarga(Long numeroOrden) throws BusinessException {
        try {
            String aux = CreateCSVDatosCarga.generateCSV(numeroOrden);
            if (aux == null) throw new BusinessException("No se pudo crear el CSV");
            return "El nombre del CSV es = " + aux + " - Se guardó en el directorio raíz del proyecto";
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    private Conciliacion generateConciliacion (OrdenCarga ordenCarga) {
        Double netoBalanza = ordenCarga.getPesoFinal() - ordenCarga.getPesoInicial();

        DatosCarga dato = ordenCarga.getPromedioDatosCarga();

        Conciliacion conci = Conciliacion.builder()
                .numeroOrden(ordenCarga.getNumeroOrden())
                .pesoInicial(ordenCarga.getPesoInicial())
                .pesoFinal(ordenCarga.getPesoFinal())
                .netoBalanza(netoBalanza)
                .difBalanzaYCaudal(netoBalanza - dato.getMasaAcumulada())
                .promedioDatosCarga(dato).build();

        return conci;
    }



    @Override
    public OrdenCarga modify(OrdenCarga ordenCarga) throws BusinessException, NotFoundException {
        load(ordenCarga.getId());
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

    @Override
    public OrdenCarga cambiarFrecuencia(Long idOrdenCarga, Integer frecuencia) throws BusinessException, NotFoundException {
        load(idOrdenCarga);
        try {
            if (frecuencia == 1 || frecuencia == 2 || frecuencia == 5 || frecuencia == 10 || frecuencia == 15) {
                getByNumeroOrden(idOrdenCarga).setFrecuencia(frecuencia);
                ordenCargaRepository.save(getByNumeroOrden(idOrdenCarga));
                return getByNumeroOrden(idOrdenCarga);
            } else {
                throw new BusinessException("La frecuencia especificada no es válida. Pruebe con 1, 2, 5, 10, 15");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw  new BusinessException(e);
        }
    }

}
