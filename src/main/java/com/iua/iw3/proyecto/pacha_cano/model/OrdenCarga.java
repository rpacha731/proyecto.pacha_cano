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

    @Column(nullable = false)
    private Double pesoInicial;

    @Column(nullable = false)
    private Double pesoFinal;

    @Column(nullable = false)
    private Integer frecuencia; // hacemos una lista estatica

    @Column (nullable = false, length = 5)
    private Integer password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Estados estado;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date fechaHoraRecepcion;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date fechaHoraPesoInicial;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date fechaHoraTurno;

    @Column(nullable = false)
    private Long preset;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date fechaHoraInicioCarga;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date fechaHoraFinCarga;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date fechaHoraPesoFinal;

    @OneToOne
    @JoinColumn(name = "promedio_datos_carga")
    private DatosCarga promedioDatosCarga;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "registro_datos_carga")
    private List<DatosCarga> registroDatosCarga;

    public static Integer generateRandomPassword () {
        Double aux = 10000 + Math.random() * 90000;
        return aux.intValue();
    }

}
