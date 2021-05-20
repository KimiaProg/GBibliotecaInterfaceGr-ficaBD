package gui.viewsandcontrollers.views.main;

import java.io.IOException;
import java.util.Optional;

import gui.Notifications;
import gui.viewsandcontrollers.views.form.ControllerNuevo;
import negocio.Negocio;
import negocio.model.*;
import negocio.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * El controlador para la ventana principal
 * @author kimia
 *
 */
public class ControllerMain {

	// Las variables
	private static ObservableList<Libro> catalogo = FXCollections.observableArrayList();
	private Negocio negocio = Negocio.getInstance();
	@FXML
	TableView<Libro> table;
	@FXML
	Button botonEliminar;
	@FXML
	Button botonEditar;
	@FXML
	TableColumn<Libro, String> titulo;
	@FXML
	TableColumn<Libro, String> isbn;
	@FXML
	TableColumn<Libro, String> autor;
	@FXML
	TableColumn<Libro, Genero> genero;
	@FXML
	TableColumn<Libro, Integer> paginas;

	/**
	 * Inicializa el controlador
	 */
	@FXML
	public void initialize() {
		titulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
		isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		autor.setCellValueFactory(new PropertyValueFactory<>("autor"));
		genero.setCellValueFactory(new PropertyValueFactory<>("genero"));
		paginas.setCellValueFactory(new PropertyValueFactory<>("paginas"));

		botonEditar.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
		botonEliminar.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());

		Notifications.subscribe(Notifications.CATALOGO_UPDATED, this, this::update);
	}

	/**
	 * Crea un nuevo libro
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void nuevo(ActionEvent event) throws IOException {
		formLibro(event, null);
	}

	/**
	 * Abre el formulario libro
	 * 
	 * @param event
	 * @param libro
	 * @throws IOException
	 */
	private void formLibro(ActionEvent event, Libro libro) throws IOException {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../form/NuevoFXML.fxml"));

		ControllerNuevo nuevo;
		// Si es para nuevo libro se establece el constructor por defecto del
		// constrolador si es para editar se la pasa el libro a editar por parametro del
		// constructor
		if (libro == null) {
			nuevo = new ControllerNuevo();
		} else {
			nuevo = new ControllerNuevo(libro);
		}
		fxmlLoader.setController(nuevo);
		Parent root1 = fxmlLoader.load();
		stage.setScene(new Scene(root1));
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(((Node) event.getSource()).getScene().getWindow());
		stage.showAndWait();
	}

	/**
	 * Edita un libro existente en el catalogo
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void editar(ActionEvent event) throws IOException {
		Libro libroAEditar = table.getSelectionModel().getSelectedItem();
		formLibro(event, libroAEditar);
	}

	/**
	 * Elimina el elemento seleccionado
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void eliminar(ActionEvent event) throws IOException {
		Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
		warning.setTitle("Warning");
		warning.setContentText("Está seguro de eliminar este registro?");

		Optional<ButtonType> botonRes = warning.showAndWait();

		// Borrando el libro seleccionado del catalogo del negocio y actualizar la tabla
		if (botonRes.get() == ButtonType.OK) {
			negocio.delete(table.getSelectionModel().getSelectedItem());
			update(Notifications.CATALOGO_UPDATED);
		}
	}

	/**
	 * Guarda los elementos de la tabla en un fichero xml
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void salvar(ActionEvent event) throws IOException {
		TextInputDialog ventana = new TextInputDialog();
		ventana.setTitle("Salvar");
		ventana.setContentText("Porfavor introduce el fichero");
		Optional<String> resul = ventana.showAndWait();
		// Llamar al metodo save de negocio y pasarle el nombre del fichero despues
		// actualizar la tabla
		if (resul.isPresent()) {
			negocio.save(resul.get());
			update(Notifications.CATALOGO_UPDATED);
		}
	}

	/**
	 * Carga los elementos del fichero xml en la tabla
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void cargar(ActionEvent event) throws IOException {
		TextInputDialog ventana = new TextInputDialog();
		ventana.setTitle("Cargar");
		ventana.setContentText("Porfavor introduce el fichero");
		Optional<String> resul = ventana.showAndWait();
		// Llamar al metodo load de negocio y pasarle el nombre del fichero despues
		// actualizar la tabla
		if (resul.isPresent()) {
			negocio.load(resul.get());
			update(Notifications.CATALOGO_UPDATED);
		}

	}

	/**
	 * Carga los datos de la base de datos
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void cargarBD(ActionEvent event) throws IOException {
		Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
		warning.setTitle("Warning");
		warning.setContentText("Está seguro de cargar los libros de la base de datos en la tabla?");

		Optional<ButtonType> botonRes = warning.showAndWait();

		if (botonRes.get() == ButtonType.OK) {
			if(negocio.cargarBD()) {
				update(Notifications.CATALOGO_UPDATED);
			}else {
				System.out.println("No se ha podido cargar los datos");
			}
		}

	}
	
	/**
	 * Salva los datos de la tabla en base de datos
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void salvarBD(ActionEvent event) throws IOException {
		Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
		warning.setTitle("Warning");
		warning.setContentText("Está seguro de guardar los libros en la base de datos?");

		Optional<ButtonType> botonRes = warning.showAndWait();

		if (botonRes.get() == ButtonType.OK) {
			if(!negocio.salvarBD()) {
				System.out.println("No se ha podido salvar los datos");
			};
		}
	}
	/**
	 * Actualiza los elementos de la tabla con el catalogo
	 * 
	 * @param event
	 */
	private void update(String event) {
		catalogo = FXCollections.observableArrayList(negocio.getCatalogoNegocio());
		table.setItems(catalogo);
		table.refresh();
	}

	/**
	 * 
	 * @return Devuelve la lista observable
	 */
	public static ObservableList<Libro> getCatalogo() {
		return catalogo;
	}
}
