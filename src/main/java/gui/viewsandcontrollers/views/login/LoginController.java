package gui.viewsandcontrollers.views.login;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import negocio.Negocio;

public class LoginController {

	private Negocio negocio = Negocio.getInstance();
	@FXML
	TextField username;
	@FXML
	TextField password;

	public void initialize() {

	}

	/**
	 * Este metodo se ejecuta cuando se hace click en el boton aceptar
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void aceptar(ActionEvent event) throws IOException {
		Node source = (Node) event.getSource();
		Stage parent = (Stage) source.getScene().getWindow();
		if (negocio.login(username.getText(), password.getText())) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../main/BasicFXML.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage dialog = new Stage();
			dialog.setScene(new Scene(root1));
			dialog.initOwner(parent);
			dialog.initModality(Modality.APPLICATION_MODAL);
			parent.close();
			dialog.showAndWait();
		}else {
			System.out.println("El usuario y la contraseña no coinciden");
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
