package negocio.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import negocio.Negocio;
import negocio.model.Libro;

/**
 * Clase para gestionar los ficheros xml
 * 
 * @author kimia
 *
 */
public class ServiceXML {
	// La variable statica documento del fichero
	private static Document doc;

	/**
	 * Para cargar y leer del fichero recibido
	 * 
	 * @param fichero
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void load(String fichero) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory mySaxParserF = SAXParserFactory.newInstance();
		SAXParser mySaxPars = mySaxParserF.newSAXParser();
		File file = new File(fichero);
		mySaxPars.parse(file, new XMLHandler());

	}

	/**
	 * Para guardar los libros de la tabla en el fichero
	 * 
	 * @param fichero
	 * @throws IOException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 */
	public static void save(String fichero) throws IOException, TransformerException, ParserConfigurationException {
		generadorDoc();
		generadorXML(fichero);
	}

	/**
	 * Genera el documento y escribe los elemtos en el fichero
	 * 
	 * @throws ParserConfigurationException
	 */
	private static void generadorDoc() throws ParserConfigurationException {
		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		DocumentBuilder DB = fact.newDocumentBuilder();
		doc = DB.newDocument();

		Element libros = doc.createElement("libros");
		doc.appendChild(libros);
		// Bucle para repetir creando elemtos de libro hasta que no haya más libros en
		// el catalogo
		for (Libro l : Negocio.getInstance().getCatalogoNegocio()) {
			Element libro = doc.createElement("libro");
			libros.appendChild(libro);

			Element titulo = doc.createElement("titulo");
			crearEtiquetaPadreHijo(libro, titulo, l.getTitulo());

			Element isbn = doc.createElement("isbn");
			crearEtiquetaPadreHijo(libro, isbn, l.getIsbn());

			Element autor = doc.createElement("autor");
			crearEtiquetaPadreHijo(libro, autor, l.getAutor());

			Element genero = doc.createElement("genero");
			crearEtiquetaPadreHijo(libro, genero, l.getGenero().toString());

			Element paginas = doc.createElement("paginas");
			crearEtiquetaPadreHijo(libro, paginas, l.getPaginas().toString());
		}

	}

	/**
	 * Crea conexion entre etiqueta padre e hijo
	 * 
	 * @param etiquetaPadre
	 * @param etiquetaHijo
	 * @param text
	 */
	private static void crearEtiquetaPadreHijo(Element etiquetaPadre, Element etiquetaHijo, String text) {
		etiquetaPadre.appendChild(etiquetaHijo);
		etiquetaHijo.appendChild(doc.createTextNode(text));
	}

	/**
	 * Convierte el documento en un documento XML
	 * 
	 * @param fichero
	 * @throws IOException
	 * @throws TransformerException
	 */
	private static void generadorXML(String fichero) throws IOException, TransformerException {
		TransformerFactory fact = TransformerFactory.newInstance();
		Transformer trans = fact.newTransformer();

		Source fuente = new DOMSource(doc);
		File file = new File(fichero);

		FileWriter write = new FileWriter(file);
		PrintWriter print = new PrintWriter(write);
		Result res = new StreamResult(print);

		trans.transform(fuente, res);

	}

}
