package persistencia.dao;

import negocio.model.Libro;

public interface BibliotecaDAO {

	/**
	 * Carga los datos de la base de datos en la tabla
	 * 
	 * @return
	 */
	boolean cargarBD();

	/**
	 * Salva los datos de la tabla en la base de datos
	 * 
	 * @return
	 */
	boolean salvarBD();

	/**
	 * Comprueba si el libro existe en la base de datos
	 * 
	 * @param l
	 * @return
	 */
	boolean libroExists(Libro l);

	/**
	 * Comprueba el usuario y la contraseña del usuario
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	boolean login(String username, String password);

	/**
	 * Actualiza el libro
	 * 
	 * @param libro
	 * @return
	 */
	boolean update(Libro libro);

	/**
	 * Borra el libro de la base de datos
	 * @param isbn
	 * @return
	 */
	boolean delete(String isbn);

}
