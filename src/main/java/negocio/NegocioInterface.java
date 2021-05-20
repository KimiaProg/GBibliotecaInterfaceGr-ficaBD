package negocio;

import java.util.List;

import negocio.model.Libro;

public interface NegocioInterface {

	/**
	 * Devuelve el catalogo
	 * 
	 * @return
	 */
	public List<Libro> getCatalogoNegocio();

	/**
	 * Añade un libro en el catalogo
	 * 
	 * @param libro
	 * @return
	 */
	public boolean add(Libro libro);

	/**
	 * Borra un libro del catalogo
	 * 
	 * @param libro
	 * @return
	 */
	public boolean delete(Libro libro);

	/**
	 * Actualiza un libro en el catalogo
	 * 
	 * @param libro
	 * @return
	 */
	public boolean update(Libro libro);

	/**
	 * Salva los elemtos de la tabla en un fichero
	 * 
	 * @param fichero para guardar los elementos
	 * @return
	 */
	public boolean save(String fichero);

	/**
	 * Carga los elementos del fichero en la tabla
	 * 
	 * @param fichero aa cargar
	 * @return
	 */
	public boolean load(String fichero);
}
