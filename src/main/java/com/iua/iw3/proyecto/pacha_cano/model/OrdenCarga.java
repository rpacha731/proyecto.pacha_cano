package com.iua.iw3.proyecto.pacha_cano.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name="ordenes")
public class OrdenCarga implements Serializable {

    private static final long serialVersionUID = -4871142170558316526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long numeroOrden;

    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "id_camion")
    private Camion camion;

    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "id_chofer")
    private Chofer chofer;

    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date fechaHoraTurno;

    @Column(nullable = false)
    private Long preset;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Estados estado;

    private Integer frecuencia; // hacemos una lista estatica

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date fechaHoraRecepcion;

    // Hasta acá sería el E1

    private Double pesoInicial; // TARA

    @Column (length = 5)
    private Integer password;

    @Column(columnDefinition = "DATETIME")
    private Date fechaHoraPesoInicial;

    // Hasta acá sería el E2

    @Column(columnDefinition = "DATETIME")
    private Date fechaHoraInicioCarga;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "registro_datos_carga")
    private List<DatosCarga> registroDatosCarga;

    @Column(columnDefinition = "DATETIME")
    private Date fechaHoraFinCarga;

    private Double pesoFinal;

    @Column(columnDefinition = "DATETIME")
    private Date fechaHoraPesoFinal;

    // Hasta acá sería el E3

    @OneToOne
    @JoinColumn(name = "promedio_datos_carga")
    private DatosCarga promedioDatosCarga;

    // El E4 es la conciliación

    private String codigoExterno;

    public static Integer generateRandomPassword () {
        Double aux = 10000 + Math.random() * 90000;
        return aux.intValue();
    }

}
