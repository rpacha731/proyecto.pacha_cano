package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.exceptions.*;
import com.iua.iw3.proyecto.pacha_cano.mailing.dtos.SimpleNotificacionEmail;
import com.iua.iw3.proyecto.pacha_cano.mailing.services.MailService;
import com.iua.iw3.proyecto.pacha_cano.model.*;
import com.iua.iw3.proyecto.pacha_cano.persistence.*;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.DatosCargaRequest;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.PesoFinalRequest;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.PesoInicialRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class OrdenCargaBusinessImpl implements OrdenCargaBusiness {

    private final OrdenCargaRepository ordenCargaRepository;
    private final CamionRepository camionRepository;
    private final ClienteRepository clienteRepository;
    private final ChoferRepository choferRepository;
    private final ProductoRepository productoRepository;
    private final MailService mailService;
//    private final SimpMessagingTemplate messagingTemplate;

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
    public List<Producto> listAllProductos() throws BusinessException {
        try {
            return productoRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    @Override
    public List<Camion> listAllCamiones() throws BusinessException {
        try {
            return camionRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    @Override
    public List<Chofer> listAllChoferes() throws BusinessException {
        try {
            return choferRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    @Override
    public List<Cliente> listAllClientes() throws BusinessException {
        try {
            return clienteRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    @Override
    public List<OrdenCarga> listAllByEstado(Integer indiceEstado) throws BusinessException, NotFoundException {
        try {
            List<OrdenCarga> aux;
            switch (indiceEstado) {
                case 1:
                    aux = ordenCargaRepository.findAllByEstado(Estados.E1);
                    break;
                case 2:
                    aux = ordenCargaRepository.findAllByEstado(Estados.E2);
                    break;
                case 3:
                    aux = ordenCargaRepository.findAllByEstado(Estados.E3);
                    break;
                case 4:
                    aux = ordenCargaRepository.findAllByEstado(Estados.E4);
                    break;
                default:
                    throw new BusinessException("No existe el estado indicado - Estado: E" + indiceEstado);
            }
            if (aux.isEmpty()) throw new NotFoundException("No se encontraron ordenes de carga con el estado indicado");
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
            throw new NotFoundException("No se encontró la orden de carga con el id indicado - Id: " + idOrdenCarga);
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
        if (o.isPresent())
            throw new DuplicateException("Ya existe una orden de carga con el numero de orden = " + numOrden);
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
        if (o.isEmpty())
            throw new NotFoundException("No se encontró la orden de carga con el numero de orden indicado - Numero de orden: " + numOrden);
        return o.get();
    }

    @Override
    public OrdenCarga create(OrdenCarga ordenCarga) throws BusinessException, DuplicateException {
        loadByNumeroOrden(ordenCarga.getNumeroOrden()); // Verifica que no exista una orden de carga con el mismo número de orden
        try {
            Optional<Camion> camionAux = this.camionRepository.findByCodigoExterno(ordenCarga.getCamion().getCodigoExterno());
            Optional<Chofer> choferAux = this.choferRepository.findByCodigoExterno(ordenCarga.getChofer().getCodigoExterno());
            Optional<Cliente> clienteAux = this.clienteRepository.findByCodigoExterno(ordenCarga.getCliente().getCodigoExterno());
            Optional<Producto> productoAux = this.productoRepository.findByCodigoExterno(ordenCarga.getProducto().getCodigoExterno());

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
            log.warn(orden.toString());
            return ordenCargaRepository.save(orden);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    @Override
    public OrdenCarga adjuntarTara(PesoInicialRequest pesoInicialRequest) throws BusinessException, NotFoundException, WrongDateException {
        OrdenCarga aux = this.getByNumeroOrden(pesoInicialRequest.getNumeroOrden());
        Calendar time = Calendar.getInstance();
        time.setTime(aux.getFechaHoraTurno());
        time.add(Calendar.HOUR, 72); // Validez de 72 horas después de la fecha de turno
        if (System.currentTimeMillis() <= aux.getFechaHoraTurno().getTime() && System.currentTimeMillis() >= time.getTimeInMillis())
            throw new WrongDateException("Todavía no es la fecha / hora del turno (o venció) de la orden con numero de orden = " + aux.getNumeroOrden());
        aux.setPesoInicial(pesoInicialRequest.getPesoInicial());
        aux.setFechaHoraPesoInicial(new Date());
        aux.setEstado(Estados.E2);

        Integer passwd;
        do {
            passwd = OrdenCarga.generateRandomPassword();
        } while (this.ordenCargaRepository.findByPassword(passwd).isPresent());

        aux.setPassword(passwd);
        return this.ordenCargaRepository.save(aux);
    }

    @Override
    public String adjuntarDatoCarga(DatosCargaRequest datosCargaRequest) throws BusinessException, NotFoundException {
        OrdenCarga aux = this.getByNumeroOrden(datosCargaRequest.getNumeroOrden());

        // Guardará los datos si está en estado E2,
        // el password es correcto y
        // pasó el periodo de tiempo de guardado (frecuencia de guardado)
        if (aux.getEstado().equals(Estados.E2) && aux.getPassword().equals(datosCargaRequest.getPassword())) {

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
                    log.info("NO GUARDÉ " + datosCargaRequest.getMasaAcumulada());
                    return "OK"; // TODO devolver tiempo restante de llenado
                } // No se guarda el registro del dato si todavía no llegué a la frecuencia

                // A partir de aquí, si se guardaría el dato, pero primero verifico la masa sea mayor al anterior y
                // caudales y densidad dentro de los rangos o mayores a 0
                if (aux.getRegistroDatosCarga().size() > 0) {
                    DatosCarga ultimoGuardado = aux.getRegistroDatosCarga().get(aux.getRegistroDatosCarga().size() - 1);
                    if (datosCargaRequest.getMasaAcumulada() < ultimoGuardado.getMasaAcumulada()
                            || datosCargaRequest.getCaudal() <= 0
                            || datosCargaRequest.getMasaAcumulada() <= 0
                            || datosCargaRequest.getDensidad() < 0
                            || datosCargaRequest.getDensidad() > 1) {
                        log.info("No if");
                        return "OK"; // TODO devolver tiempo restante de llenado
                    }
                }
                // No guardo el dato si se cumple el anterior if.
                // El siguiente dato que llegaría, si no cumple el if, se guardaría por el tiempo de guardado

                // Ahora sí, guardo el dato
                aux.setFechaHoraFinCarga(new Date());

                // Agrego el dato y sobreescribo toda la lista de datos
                aux.getRegistroDatosCarga().add(DatosCarga.builder()
                        .masaAcumulada(datosCargaRequest.getMasaAcumulada())
                        .densidad(datosCargaRequest.getDensidad())
                        .temperatura(datosCargaRequest.getTemperatura())
                        .caudal(datosCargaRequest.getCaudal()).build());
//                aux.setRegistroDatosCarga(aux.getRegistroDatosCarga());

                // Guardo la orden en la base de datos
                ordenCargaRepository.save(aux);
                log.info("Guardé " + datosCargaRequest.getMasaAcumulada());
                return "OK"; // TODO devolver tiempo restante de llenado

            } else {

                // La masa acumulada llegó al preset, por lo que cierro la orden de carga
                if (aux.getEstado().equals(Estados.E2)) {
                    aux.setEstado(Estados.E3);
                    this.ordenCargaRepository.save(aux);
                    return "ORDEN_CERRADA";
                }

                // A este return nunca se llegaría, pero el IDE lo pedía ja, ja, ja
                return "CANCEL";
            }

        } else {
            throw new BusinessException("La orden no está en estado E2, puede que se haya cancelado | La password es incorrecta.");
        }
    }

    @Override
    public OrdenCarga cerrarOrden(Long numeroOrden) throws BusinessException, NotFoundException {
        OrdenCarga orden = this.getByNumeroOrden(numeroOrden);
        if (orden.getEstado().equals(Estados.E2)) {
            orden.setEstado(Estados.E3);
        } else {
            throw new BusinessException("La orden no se puede cerrar, no está en estado E2");
        }
        return this.ordenCargaRepository.save(orden);
    }

    @Override
    public Conciliacion adjuntarPesoFinal(PesoFinalRequest pesoFinalRequest) throws BusinessException, NotFoundException {
        OrdenCarga aux = this.getByNumeroOrden(pesoFinalRequest.getNumeroOrden());
        if (!aux.getEstado().equals(Estados.E3))
            throw new BusinessException("No se puede adjuntar el peso final, la orden no está cerrada");

        aux.setFechaHoraPesoFinal(new Date());
        aux.setPesoFinal(pesoFinalRequest.getPesoFinal());
        aux.setEstado(Estados.E4);

        Double[] promedios = new Double[]{0.0, 0.0, 0.0};  // [0] = TEMPERATURA * [1] = DENSIDAD * [2] = CAUDAL
        for (DatosCarga dato : aux.getRegistroDatosCarga()) {
            promedios[0] += dato.getTemperatura();
            promedios[1] += dato.getDensidad();
            promedios[2] += dato.getCaudal();
        }

        int totalRegistros = aux.getRegistroDatosCarga().size();
        promedios[0] /= totalRegistros;
        promedios[1] /= totalRegistros;
        promedios[2] /= totalRegistros;

        Double masaAcum = aux.getRegistroDatosCarga().get(totalRegistros - 1).getMasaAcumulada();

        aux.setMasaAcumuladaTotal(masaAcum);
        aux.setTemperaturaPromedio(promedios[0]);
        aux.setDensidadPromedio(promedios[1]);
        aux.setCaudalPromedio(promedios[2]);

        this.ordenCargaRepository.save(aux);

        return this.generateConciliacion(aux);
    }

    @Override
    public Conciliacion getConciliacion(Long numeroOrden) throws BusinessException, NotFoundException {
        OrdenCarga o = this.getByNumeroOrden(numeroOrden);
        if (!o.getEstado().equals(Estados.E4))
            throw new BusinessException("La orden no está cerrada, no se puede crear a conciliación");
        return this.generateConciliacion(o);
    }

    @Override
    public FileInputStream generateCSVOrdenCarga(Long numeroOrden) throws BusinessException {
        try {
            return this.generateCSV(numeroOrden);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
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
    public OrdenCarga cambiarFrecuencia(Long numeroOrden, Integer frecuencia) throws BusinessException, NotFoundException {
        try {
            OrdenCarga aux = getByNumeroOrden(numeroOrden);
            if (frecuencia == 1 || frecuencia == 2 || frecuencia == 5 || frecuencia == 10 || frecuencia == 15) {
                aux.setFrecuencia(frecuencia);
                ordenCargaRepository.save(aux);
                return aux;
            } else {
                throw new BusinessException("La frecuencia especificada no es válida. Pruebe con 1, 2, 5, 10, 15");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    private FileInputStream generateCSV(Long numOrden) throws IOException {
        File file = new File("datosCarga-" + numOrden.intValue() + ".csv");
        if (!file.exists()) {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("masaAcumulada,densidad,temperatura,caudal");
            bufferedWriter.newLine();

            String[] datos = new String[4];

            Random ran = new Random();

            double masaAcum = 0.0, aux, minTemp = 15.0;

            for (int i = 1; i < 1000; i++) {
                aux = ran.nextInt(5); // el aux es el caudal en cierta forma,
                // es lo que pasa por el caudalímetro y es la masa que se acumuló
                masaAcum = masaAcum + aux;
                datos[0] = String.valueOf(masaAcum);

                datos[1] = String.valueOf((ran.nextInt(10) / 10.0));

                datos[2] = String.valueOf(minTemp + (double) ran.nextInt(35) + (ran.nextInt(10) / 10.0));

                datos[3] = String.valueOf(aux);

                bufferedWriter.write(String.join(",", datos));
                bufferedWriter.newLine();
            }

            bufferedWriter.close();

            return new FileInputStream(file);
        }
        return new FileInputStream(file);
    }

    private Conciliacion generateConciliacion(OrdenCarga ordenCarga) {
        Double netoBalanza = ordenCarga.getPesoFinal() - ordenCarga.getPesoInicial();

        return Conciliacion.builder()
                .numeroOrden(ordenCarga.getNumeroOrden())
                .pesoInicial(ordenCarga.getPesoInicial())
                .pesoFinal(ordenCarga.getPesoFinal())
                .netoBalanza(netoBalanza)
                .difBalanzaYCaudal(netoBalanza - ordenCarga.getMasaAcumuladaTotal())
                .masaAcumuladaTotal(ordenCarga.getMasaAcumuladaTotal())
                .temperaturaPromedio(ordenCarga.getTemperaturaPromedio())
                .densidadPromedio(ordenCarga.getDensidadPromedio())
                .caudalPromedio(ordenCarga.getCaudalPromedio()).build();
    }

    @Async
    public void enviarMail() throws BusinessException {
        try {
            this.mailService.sendEmail(SimpleNotificacionEmail.builder()
                            .asunto("Prueba de envio de correo")
                            .body("Prueba de envio de correo body")
                            .destinatario("leonelpacha14@gmail.com")
                            .title("Prueba de envio de correo title")
                            .redirectUrl("http://localhost:4200/").build());
        } catch (MailSenderException e) {
            throw new BusinessException(e.getMessage());
        }

    }

}
