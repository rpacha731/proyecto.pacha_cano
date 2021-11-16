package com.iua.iw3.proyecto.pacha_cano.model.accounts;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
public class Rol implements Serializable {

    private static final long serialVersionUID = 1139806825119468503L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String nombre;

    private String descripcion;
}
