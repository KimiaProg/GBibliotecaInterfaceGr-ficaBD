package negocio.imple;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import negocio.xml.ServiceXML;

public class ImpleXML {

	/**
	 * Llama al metodo save de ServiceXML y le pasa el fichero
	 * 
	 * @param fichero
	 * @throws IOException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 */
	public static void salvar(String fichero) throws IOException, TransformerException, ParserConfigurationException {
		ServiceXML.save(fichero);
	}

	/**
	 * Llama al metodo load de ServiceXML y le pasa el fichero
	 * 
	 * @param fichero
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void cargar(String fichero) throws ParserConfigurationException, SAXException, IOException {
		ServiceXML.load(fichero);

	}

}
