package gui.viewsandcontrollers.views.form;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gui.Notifications;
import gui.viewsandcontrollers.viewmodel.LibroConverter;
import gui.viewsandcontrollers.viewmodel.LibroViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import negocio.model.Genero;
import negocio.model.Libro;

/**
 * Clase Controlador del form de libro
 * @author kimia
 *
 */
public class ControllerNuevo {

	// Las variables
	LibroViewModel viewModel = new LibroViewModel();
	@FXML
	ComboBox<Pair<String, String>> comboGen;
	@FXML
	TextField textTitulo;
	@FXML
	TextField textISBN;
	@FXML
	TextField textAutor;
	@FXML
	TextField textPaginas;
	boolean nuevo;

	/**
	 * El constructor por defecto( se le llama para crear un nuevo libro)
	 */
	public ControllerNuevo() {
		nuevo = true;
	}

	/**
	 * El constructor sobrecargado (se le llama para modificar un libro existente)
	 * 
	 * @param libro recibe el libro a editar
	 */
	public ControllerNuevo(Libro libro) {
		nuevo = false;
		viewModel = LibroConverter.toLibroViewModel(libro);

	}

	/**
	 * el primer método que se ejecuta al llamar al controller
	 */
	public void initialize() {

		// Establecer las opciones de ComboBox
		List<Pair<String, String>> opcionesCombo = new ArrayList<>();
		opcionesCombo.add(new Pair(Genero.FICCION.toString(), Genero.FICCION.toString()));
		opcionesCombo.add(new Pair(Genero.NOVELA.toString(), Genero.NOVELA.toString()));
		opcionesCombo.add(new Pair(Genero.POESIA.toString(), Genero.POESIA.toString()));

		// Establecer un converter de String para nuestro combo box
		comboGen.setConverter(new StringConverter<Pair<String, String>>() {

			@Override
			public String toString(Pair<String, String> pair) {
				return pair.getKey();
			}

			@Override
			public Pair<String, String> fromString(String string) {
				return null;
			}
		});

		// Añadir las opciones a nuestro combo
		comboGen.getItems().addAll(opcionesCombo);

		// Establecer conexión entre los textFields y el libro viewModel
		textTitulo.textProperty().bindBidirectional(viewModel.getTitulo());
		textISBN.textProperty().bindBidirectional(viewModel.getIsbn());
		textAutor.textProperty().bindBidirectional(viewModel.getAutor());
		comboGen.valueProperty().bindBidirectional(viewModel.getGenero());
		Bindings.bindBidirectional(textPaginas.textProperty(), viewModel.getPaginas(), new NumberStringConverter());

		// Si es en el caso de modificacion no se permite editar el isbn
		if (!nuevo) {
			textISBN.setEditable(false);
		}
	}

	/**
	 * Este metodo se ejecuta cuando se hace click en el boton aceptar
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void aceptarCerrarVentana(ActionEvent event) throws IOException {
		// Cogiendo la fuente de la ventana para poder cerrarla
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();

		boolean isDone;
		if (nuevo) {
			isDone = viewModel.create();
		} else {
			isDone = viewModel.update();
		}

		stage.close();
		// Si la operacion se ha hecho con exito entonces se actualiza la tabla
		if (isDone) {
			Notifications.publish(Notifications.CATALOGO_UPDATED);
		}
	}

	/**
	 * Cierra la ventana
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void cancelar(ActionEvent event) throws IOException {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

}
