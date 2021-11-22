package com.iua.iw3.proyecto.pacha_cano.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@Table(name="camiones")
public class Camion implements Serializable {

    private static final long serialVersionUID = -4871142170558316526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String patente;

    @Column (length = 100)
    private String descripcion;

    @Column (nullable = false)
    private Long cisternado;

    @Column(unique = true)
    private String codigoExterno;

}
