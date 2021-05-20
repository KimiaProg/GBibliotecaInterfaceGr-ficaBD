package persistencia.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La clase general para las conexiones
 * @author alumno
 *
 */
public abstract class GenericDAOImpl {
	
	//Las variables estáticas
	private static Connection conexion = null;
	static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		
	/**
	 * Si no se ha creado ninguna conexion antes , crea una nueva, sino devuelve la que ya existe
	 * @return
	 */
	protected static final Connection getSharedConnection(){
		if(conexion==null)
			try {
				conexion = DriverManager.getConnection("jdbc:mysql://localhost:6033/biblioteca", "developer",
						"programaciondaw");
				//Nos aseguramos de que el Autocommit es true siempre en la conexion compartida
				if(!conexion.getAutoCommit())
					conexion.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("Error conectando a BD");
				e.printStackTrace();
			}
		
		return conexion;
	}
	
	/**
	 * Crea una nueva conexion 
	 * @return
	 */
	protected static final Connection getIndividualConnection(){
		Connection conexion=null;
			try {
				conexion = DriverManager.getConnection("jdbc:mysql://localhost:6033/biblioteca", "developer",
						"programaciondaw");
			} catch (SQLException e) {
				System.out.println("Error conectando a BD");
				e.printStackTrace();
			}
		return conexion;
	}


}
