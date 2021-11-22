package com.iua.iw3.proyecto.pacha_cano.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "ordenes")
@ApiModel(value = "Orden de carga", description = "Clase que describe a la orden de carga")
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

    @ApiModelProperty(notes = "Precio del producto", example = "20.65", required = true, allowableValues = "1, 2, 5, 10, 15")
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

    @Column(unique = true)
    private String codigoExterno;

    public static Integer generateRandomPassword () {
        double aux = 10000 + Math.random() * 90000;
        return (int) aux;
    }

}
