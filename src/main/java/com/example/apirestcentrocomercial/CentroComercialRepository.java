package com.example.apirestcentrocomercial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * Repositorio para acceder a los datos de los centros comerciales en la base de datos.
 */
public interface CentroComercialRepository extends JpaRepository<CentroComercial, Long> {

    /**
     * Obtiene un centro comercial por su ID.
     *
     * @param id El ID del centro comercial.
     * @return El centro comercial correspondiente al ID especificado.
     */
    CentroComercial getCentroComercialById(Long id);

    /**
     * Obtiene un centro comercial por su nombre.
     *
     * @param nombre El nombre del centro comercial.
     * @return El centro comercial correspondiente al nombre especificado.
     */
    CentroComercial getCentroComercialByNombre(String nombre);

    /**
     * Obtiene centros comerciales por su puntuación.
     *
     * @param puntuacion La puntuación deseada.
     * @return Una lista de centros comerciales que tienen la puntuación especificada.
     */
    List<CentroComercial> getCentroComercialByPuntuacion(Integer puntuacion);

    /**
     * Obtiene un centro comercial por su ubicación.
     *
     * @param ubicacion La ubicación del centro comercial.
     * @return El centro comercial correspondiente a la ubicación especificada.
     */
    CentroComercial getCentroComercialByUbicacion(String ubicacion);

    /**
     * Obtiene centros comerciales que tienen restaurantes.
     *
     * @param restaurantes Indica si el centro comercial tiene restaurantes o no.
     * @return Una lista de centros comerciales que tienen restaurantes, si el parámetro es verdadero.
     */
    List<CentroComercial> getCentroComercialByRestaurantes(Boolean restaurantes);

    /**
     * Obtiene una lista de nombres de centros comerciales.
     *
     * @return Una lista de nombres de centros comerciales.
     */
    @Query("SELECT r.nombre FROM CentroComercial r")
    List<String> nombreCentroComercial();

    /**
     * Obtiene la cantidad total de centros comerciales.
     *
     * @return El número total de centros comerciales.
     */
    @Query("SELECT count(r) FROM CentroComercial r")
    Integer cantidadDeCentrosComerciales();

    /**
     * Obtiene centros comerciales con una puntuación igual o mayor a la especificada.
     *
     * @param puntuacion La puntuación mínima deseada.
     * @return Una lista de centros comerciales con puntuación igual o mayor a la especificada.
     */
    @Query("SELECT r FROM CentroComercial r WHERE r.puntuacion >= :puntuacion")
    List<CentroComercial> getCentroComercialConPuntuacionMayorOIgual(Integer puntuacion);
}
