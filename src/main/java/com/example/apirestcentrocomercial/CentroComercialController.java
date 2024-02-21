package com.example.apirestcentrocomercial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para manejar operaciones relacionadas con centros comerciales.
 */

@RestController
@RequestMapping("/api/centrocomercial")
public class CentroComercialController {


    /**
     * Repositorio para acceder a los datos de los centros comerciales.
     */
    @Autowired
    private CentroComercialRepository repository;

    /**
     * Servicio de seguridad para validar tokens de autenticación.
     */

    @Autowired
    private SecurityService security;

    /**
     * Obtiene todos los centros comerciales.
     *
     * @return Una lista de todos los centros comerciales.
     */

    @GetMapping("/")
    public List<CentroComercial> getAll(){return repository.findAll();}

    /**
     * Obtiene un centro comercial por su ID.
     *
     * @param id El ID del centro comercial.
     * @return El centro comercial correspondiente al ID especificado.
     */

    @GetMapping("/id/{id}")
    public CentroComercial getCentroComercialPorId(@PathVariable Long id){return repository.getCentroComercialById(id);}

    /**
     * Obtiene un centro comercial por su nombre.
     *
     * @param nombre El nombre del centro comercial.
     * @return El centro comercial correspondiente al nombre especificado.
     */

    @GetMapping("/nombre/{nombre}")
    public CentroComercial getCentroComercialPorNombre(@PathVariable String nombre){
        return repository.getCentroComercialByNombre(nombre);
    }

    /**
     * Obtiene centros comerciales por su puntuación.
     *
     * @param puntuacion La puntuación deseada.
     * @return Una lista de centros comerciales que tienen la puntuación especificada.
     */

    @GetMapping("/puntuacion/{puntuacion}")
    public List<CentroComercial> getCentroComercialPorPuntuacion(@PathVariable Integer puntuacion){
        return repository.getCentroComercialByPuntuacion(puntuacion);
    }

    /**
     * Obtiene un centro comercial por su ubicación.
     *
     * @param ubicacion La ubicación del centro comercial.
     * @return El centro comercial correspondiente a la ubicación especificada.
     */

    @GetMapping("/ubicacion/{ubicacion}")
    public CentroComercial getCentroComercialPorUbicacion(@PathVariable String ubicacion) {
        return repository.getCentroComercialByUbicacion(ubicacion);
    }

    /**
     * Obtiene centros comerciales que tienen restaurantes.
     *
     * @param restaurantes Indica si el centro comercial tiene restaurantes o no.
     * @return Una lista de centros comerciales que tienen restaurantes, si el parámetro es verdadero.
     */

    @GetMapping("/restaurantes/{restaurantes}")
    public List<CentroComercial> getCentroComercialPorRestaurantes(@PathVariable Boolean restaurantes) {
        return repository.getCentroComercialByRestaurantes(restaurantes);
    }

    /**
     * Obtiene una lista de nombres de centros comerciales.
     *
     * @return Una lista de nombres de centros comerciales.
     */

    @GetMapping("/listaNombres")
    public List<String> getListaNombres() {
        return repository.nombreCentroComercial();
    }

    /**
     * Obtiene la cantidad total de centros comerciales.
     *
     * @return El número total de centros comerciales.
     */

    @GetMapping("/cantidad")
    public Integer getNumeroRestaurantes() {
        return repository.cantidadDeCentrosComerciales();
    }

    /**
     * Obtiene centros comerciales con una puntuación igual o mayor a la especificada.
     *
     * @param puntuacionMinima La puntuación mínima deseada.
     * @return Una lista de centros comerciales con puntuación igual o mayor a la especificada.
     */

    @GetMapping("/puntuacionMinima/{puntuacionMinima}")
    public List<CentroComercial> getRestaurantesPuntuacionMinima(@PathVariable Integer puntuacionMinima) {
        return repository.getCentroComercialConPuntuacionMayorOIgual(puntuacionMinima);
    }

    /**
     * Crea un nuevo centro comercial.
     *
     * @param centroComercial El nuevo centro comercial a crear.
     * @param token El token de autenticación.
     * @return El centro comercial creado, si la autenticación es válida.
     */

    @PostMapping("/post")
    public ResponseEntity<CentroComercial> nuevo(@RequestBody CentroComercial centroComercial, @RequestParam String token) {
        if (security.validateToken(token)) {
            return new ResponseEntity<>(repository.save(centroComercial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Actualiza un centro comercial existente.
     *
     * @param id El ID del centro comercial a actualizar.
     * @param centroComercial Los nuevos datos del centro comercial.
     * @param token El token de autenticación.
     * @return El centro comercial actualizado, si la autenticación es válida y el centro comercial existe.
     */

    @PutMapping("/{id}")
    public ResponseEntity<CentroComercial> put(@PathVariable Long id, @RequestBody CentroComercial centroComercial, @RequestParam String token) {
        if (!security.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            var restaurante = new CentroComercial();
            var restauranteSeleccionado = repository.findById(id);

            if (restauranteSeleccionado.isEmpty()) {
                restaurante = centroComercial;
            } else {
                restaurante = restauranteSeleccionado.get();
                restaurante.setNombre(centroComercial.getNombre());
                restaurante.setPuntuacion(centroComercial.getPuntuacion());
                restaurante.setUbicacion(centroComercial.getUbicacion());
                restaurante.setRestaurantes(centroComercial.getRestaurantes());
            }
            return new ResponseEntity<>(repository.save(restaurante), HttpStatus.OK);
        }
    }

    /**
     * Elimina un centro comercial existente.
     *
     * @param id El ID del centro comercial a eliminar.
     * @param token El token de autenticación.
     * @return El centro comercial eliminado, si la autenticación es válida y el centro comercial existe.
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<CentroComercial> delete(@PathVariable Long id, @RequestParam String token) {
        ResponseEntity<CentroComercial> response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if (security.validateToken(token)) {
            CentroComercial salida = new CentroComercial();
            if (repository.existsById(id)) {
                salida = repository.findById(id).get();
                repository.deleteById(id);
                response = new ResponseEntity<>(salida, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(salida, HttpStatus.NOT_FOUND);
            }
        }

        return response;
    }
}
