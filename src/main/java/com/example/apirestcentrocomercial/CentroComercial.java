package com.example.apirestcentrocomercial;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Clase que representa un centro comercial.
 */
@Data
@Entity
@Table(name = "centrocomercial")
public class CentroComercial {

    /**
     * ID único del centro comercial.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del centro comercial.
     */
    private String nombre;

    /**
     * Puntuación del centro comercial.
     */
    private Integer puntuacion;

    /**
     * Ubicación del centro comercial.
     */
    private String ubicacion;

    /**
     * Indica si el centro comercial tiene restaurantes o no.
     */
    private Boolean restaurantes;

}
