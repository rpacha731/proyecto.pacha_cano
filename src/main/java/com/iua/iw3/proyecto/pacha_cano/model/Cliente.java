package com.iua.iw3.proyecto.pacha_cano.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name="clientes")
public class Cliente implements Serializable {

    private static final long serialVersionUID = -4871142170558316526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) //No sé si la razón social tiene que ser unique
    private String razonSocial;

    private String contacto;

    @Column(unique = true)
    private String codigoExterno;
}
