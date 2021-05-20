package negocio.model;


import java.util.InputMismatchException;

import javafx.util.Pair;

public enum Genero {
	NOVELA, POESIA, FICCION;

	public static Genero getGenero(Pair<String, String> pair) {
		Genero seleccionado = null;

		switch (pair.getValue()) {
		case "NOVELA":
			seleccionado = Genero.NOVELA;
			break;
		case "POESIA":
			seleccionado = Genero.POESIA;
			break;
		case "FICCION":
			seleccionado = Genero.FICCION;
			break;
		default:
			throw new InputMismatchException("¡No existe el género introducido en esta libreria!");
		}
		return seleccionado;
	}

	public static Genero getGenero(String string) {
		Genero seleccionado = null;

		switch (string.toUpperCase()) {
		case "NOVELA":
			seleccionado = Genero.NOVELA;
			break;
		case "POESIA":
			seleccionado = Genero.POESIA;
			break;
		case "FICCION":
			seleccionado = Genero.FICCION;
			break;
		default:
			throw new InputMismatchException("¡No existe el género introducido en esta libreria!");
		}
		return seleccionado;
	}
}
