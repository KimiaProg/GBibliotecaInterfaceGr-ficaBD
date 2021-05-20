package negocio.inter;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import negocio.imple.ImpleXML;

public class IntermedioXML {

	/**
	 * Llama al metodo salvar de IMPLXML y le pasa el fichero
	 * 
	 * @param fichero
	 * @throws IOException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 */
	public static void salvar(String fichero) throws IOException, TransformerException, ParserConfigurationException {
		ImpleXML.salvar(fichero);
	}

	/**
	 * Llama al metodo cargar de ImpleXML y le pasa el fichero
	 * 
	 * @param fichero
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void cargar(String fichero) throws ParserConfigurationException, SAXException, IOException {
		ImpleXML.cargar(fichero);

	}
}
