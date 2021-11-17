package com.iua.iw3.proyecto.pacha_cano.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name="ordenes")
public class OrdenCarga {

    private static final long serialVersionUID = -4871142170558316526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long numero_orden;

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
    private Double peso_inicial;

    @Column(nullable = false)
    private Double peso_final;

    @Column(nullable = false)
    private Integer frecuencia; //esto también lo haría con un enum, para que los valores posibles puedan
    //estar en un select de la página web después

    @Column (nullable = false, length = 5)
    private Integer password;   //falta generar la pswd

    @Column(nullable = false) //ver cómo usar este enum
    private String estado;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private Date fechaHoraRecepcion; //el Date incluye la hora?

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private Date fechaHoraPesoInicial; //recepción del peso inicial

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private Date fechaHoraTurno;

    @Column(nullable = false)
    private Long preset;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private Date fechaHoraInicioCarga;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private Date fechaHoraFinCarga;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private Date fechaHoraPesoFinal; //recepción del peso final

    @OneToOne(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    @JoinColumn(name = "id_datosCarga")
    private DatosCarga datosCargaProm;

    @OneToMany(fetch = FetchType.EAGER)
    private List<DatosCarga> datosCargaHistorico;

}
