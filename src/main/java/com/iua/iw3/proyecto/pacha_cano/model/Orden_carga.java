package com.iua.iw3.proyecto.pacha_cano.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name="ordenes")
public class Orden_carga {

    private static final long serialVersionUID = -4871142170558316526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private long numero_orden;

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
    private long peso_inicial;

    @Column(nullable = false)
    private long peso_final;

    @Column(nullable = false)
    private int frecuencia; //esto también lo haría con un enum, para que los valores posibles puedan
    //estar en un select de la página web después

    @Column (nullable = false, length = 5)
    private int password;   //falta generar la pswd

    @Column(nullable = false) //ver cómo usar este enum
    private enum estado {
        E1, E2, E3, E4
    }

    @Column(nullable = false)
    private Date fecha_recepcion; //el Date incluye la hora?

    @Column(nullable = false)
    private Date fecha_peso_inicial; //recepción dle peso inicial

    @Column(nullable = false)
    private Date fecha_inicio_carga;

    @Column(nullable = false)
    private Date fecha_fin_carga;

    @Column(nullable = false)
    private Date fecha_peso_final; //recepción del peso final

    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "id_datosCarga")
    private Datos_carga datos_carga; //falta implementar esta clase


}
