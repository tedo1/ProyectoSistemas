package srccine.modelo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Clase Pelicula
 * @author Jesús
 */
@Entity(name="Pelicula")
public class Pelicula implements Serializable{
    //Entero long identificador de la pelicula
    @Id
    private long _id;
    
    //Titulo de la pelicula
    private String _titulo;
      
    //Suma y media de la pelicula
    private long _suma;
    private double _media;  
        
    //Valoraciones realizadas sobre la pelicula
    @OneToMany
    private Map<String,Valoracion> _valoraciones;
    
    //Detalles y atributos de la pelicula
    @Column(columnDefinition = "LONGBLOB") 
    private DetallesPelicula _detalles;
    
    /**
     * Constructor por defecto
     */
    public Pelicula() { 
        _id = 0;
        _detalles = null;
        _suma = 0;
        _media = 0.0;
        _valoraciones = new HashMap();
    }

    /**
     * Constructor de Pelicula
     * @param id Long Identificador unico de pelicula
     * @param titulo String titulo de la pelicula
     * @param detalles Atributos de la pelicula
     */
    public Pelicula(long id,String titulo, Map detalles ) {
        _id = id;
        _titulo = titulo;
        _suma = 0;
        _media = 0.0;
        _valoraciones = new HashMap();
        _detalles = new DetallesPelicula(detalles);        
    }    
    
    /**
     * Devuelve los atributos de una pelicula
     * @return DetallesPelicula Los detalles de la pelicula
     */
    public DetallesPelicula obtieneDetalles(){
        return _detalles;
    }
    
    /**
     * Devuelve una copia de los atributos de una pelicula
     * @return Map con los detalles de la pelicula
     */
    public Map obtieneCopiaDetalles(){
        
        Map mapa = new HashMap();
        
        for (Object o: _detalles.obtieneDetalles().keySet()) {

            String nombre = (String) o;
            
            mapa.put(nombre, _detalles.obtieneDetalle(nombre));
        }
        
        return mapa;
    }
    
    /**
     * Modifica los detalles de la pelicula
     * @param detalles Nuevos detalles que tendra la pelicula
     */
    public void modifica(Map detalles){
        _detalles = new DetallesPelicula(detalles);
    }
    
    /**
     * Devuelve el identificador de la pelicula
     * @return Identificador de la pelicula
     */
    public Long obtieneID(){
        return _id;
    }
     
    /**
     * Añade una valoracion nueva sobre la peli
     * @param id Id del usuario que realiza la valoracion
     * @param v Valoracion que se añade a la pelicula
     */
    public void anadeValoracion(String id, Valoracion v){
        //comprobamos si el usuario valoro previamente la pelicula
        if (_valoraciones.containsKey(id)){
            //Cogemos la valoracion antigua y la restamos de la media
            Valoracion ant = _valoraciones.get(id);
            _suma -= ant.getPuntuacion();
            _media = (double) _suma / _valoraciones.size(); 
            //actualizamos la nueva valoracion y la media
            _valoraciones.put(id, v);
            _suma += v.getPuntuacion();
            _media = (double) _suma / _valoraciones.size(); 
        }else{
            _valoraciones.put(id, v);
            _suma += v.getPuntuacion();
            _media = (double) _suma / _valoraciones.size();                
        }             
    } 
    
    /**
     * Devuelve las valoraciones realizadas sobre la pelicula
     * @return Map<String,Valoracion> valoraciones sobre la peli
     */
    public Map<String,Valoracion> obtieneValoraciones( ){
        return _valoraciones;
    }    

    /**
     * Devuelve la nota media de la pelicula
     * @return Media de la pelicula
     */
    public double obtieneMedia() {
        return _media;
    }

    /**
     * Devuelve el titulo de la pelicula
     * @return Titulo de la pelicula
     */
    public String obtieneTitulo() {
        return _titulo;
    }

}
