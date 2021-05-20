package negocio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import negocio.inter.IntermedioXML;
import negocio.model.Libro;
import persistencia.dao.impl.BbiliotecaDAOImpl;

/**
 * Clase singleton que se encarga de la parte negocio de la aplicacion
 * 
 * @author kimia
 *
 */
public class Negocio implements NegocioInterface {

	// Las variables
	private static List<Libro> catalogo = new ArrayList<>();
	private static Negocio instance = null;
	BbiliotecaDAOImpl bibBD = new BbiliotecaDAOImpl();

	// Contructor privado
	private Negocio() {

	}

	/**
	 * metodo estatico que se encarga de crear un objeto negocio en el caso de que
	 * no se haya creado antes
	 * 
	 * @return
	 */
	public static Negocio getInstance() {
		if (instance == null) {
			instance = new Negocio();
		}
		return instance;
	}

	@Override
	public List<Libro> getCatalogoNegocio() {
		return catalogo;
	}

	@Override
	public boolean add(Libro libro) {
		return catalogo.add(libro);
	}

	@Override
	public boolean delete(Libro libro) {
		return bibBD.delete(libro.getIsbn()) && catalogo.remove(libro);
	}

	@Override
	public boolean update(Libro libro) {
		boolean devolver = false;
		// Un bucle para saber la posicion del libro editado en la tabla y poder guardar
		// el libro editado en esa podicion del catalogo
		for (Libro l : catalogo) {
			if (l.getIsbn().equals(libro.getIsbn())) {
				int pos = catalogo.indexOf(l);
				catalogo.set(pos, libro);
				if (bibBD.update(libro)) {
					devolver = true;
				}
				break;
			}
		}
		return devolver;
	}

	@Override
	public boolean save(String fichero) {
		try {
			IntermedioXML.salvar(fichero);
		} catch (IOException e) {
			System.out.println("Error");
		} catch (TransformerException e) {
			System.out.println("Error");
		} catch (ParserConfigurationException e) {
			System.out.println("Error");
		}
		return true;
	}

	@Override
	public boolean load(String fichero) {
		try {
			IntermedioXML.cargar(fichero);
		} catch (ParserConfigurationException e) {
			System.out.println("Error");
		} catch (SAXException e) {
			System.out.println("Error");
		} catch (IOException e) {
			System.out.println("Error");
		}
		return true;
	}

	public boolean salvarBD() {
		return bibBD.salvarBD();
	}

	public boolean cargarBD() {
		return bibBD.cargarBD();
	}

	public boolean login(String username, String password) {
		return bibBD.login(username, password);
	}

}
