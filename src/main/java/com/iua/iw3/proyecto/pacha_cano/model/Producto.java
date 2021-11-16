package com.iua.iw3.proyecto.pacha_cano.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name="productos")
public class Producto {

    private static final long serialVersionUID = -4871142170558316526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nombre;

    @Column (length = 100)
    private String descripcion;

}
