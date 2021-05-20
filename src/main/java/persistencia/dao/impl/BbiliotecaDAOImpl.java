package persistencia.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import negocio.Negocio;
import negocio.model.Genero;
import negocio.model.Libro;
import persistencia.dao.BibliotecaDAO;

/**
 * La clase para gestionar las compras mediante la base de datos (El conector
 * con la base de datos)
 * 
 * @author alumno
 *
 */
public class BbiliotecaDAOImpl extends GenericDAOImpl implements BibliotecaDAO {
	// Las consultas
	private static final String LOGIN_QUERY = "select * from User where username= ? and password=?;";
	private static final String CARGAR = "select * from libro;";
	private static final String SALVAR = "insert into libro values(?,?,?,?,?);";
	private static final String LIBROEXISTS = "select * from libro where isbn = ?;";
	private static final String UPDATELIBRO = "update libro set titulo=? , genero= ? , autor=?, paginas=? where isbn= ?;";
	private static final String DELETELIBRO = "delete from libro where isbn=?;";

	@Override
	public boolean login(String username, String password) {
		Connection con = GenericDAOImpl.getSharedConnection();
		String sql = LOGIN_QUERY;
		boolean devolver = false;
		try {

			PreparedStatement sentencia = con.prepareStatement(sql);
			sentencia.setString(1, username);
			sentencia.setString(2, password);
			ResultSet rs = sentencia.executeQuery();
			if (rs.next()) {
				devolver = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return devolver;
	}

	@Override
	public boolean cargarBD() {
		Connection con = GenericDAOImpl.getSharedConnection();
		String sql = CARGAR;
		try {
			PreparedStatement sentencia = con.prepareStatement(sql);
			ResultSet rs = sentencia.executeQuery();
			while (rs.next()) {
				Libro l = new Libro(rs.getString(1), rs.getString(2), Genero.getGenero(rs.getString(3)),
						rs.getString(4), Integer.parseInt(rs.getString(5)));
				Negocio.getInstance().getCatalogoNegocio().add(l);
			}
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}
		return true;
	}

	@Override
	public boolean salvarBD() {
		Connection con = GenericDAOImpl.getSharedConnection();
		String sql = SALVAR;
		boolean devolver = false;
		try {
			PreparedStatement sentencia = con.prepareStatement(sql);
			for (Libro l : Negocio.getInstance().getCatalogoNegocio()) {
				if (!libroExists(l)) {
					sentencia.setString(1, l.getTitulo());
					sentencia.setString(2, l.getIsbn());
					sentencia.setString(3, l.getGenero() + "");
					sentencia.setString(4, l.getAutor());
					sentencia.setInt(5, l.getPaginas());
					if (sentencia.executeUpdate() > 0) {
						devolver = true;
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return devolver;
	}

	@Override
	public boolean libroExists(Libro l) {
		Connection con = GenericDAOImpl.getSharedConnection();
		String sql = LIBROEXISTS;
		boolean devolver = false;
		try {
			PreparedStatement sentencia = con.prepareStatement(sql);
			sentencia.setString(1, l.getIsbn());
			ResultSet rs = sentencia.executeQuery();
			if (rs.next()) {
				devolver = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return devolver;
	}

	@Override
	public boolean update(Libro libro) {
		boolean devolver = false;
		Connection con = GenericDAOImpl.getSharedConnection();
		String sql = UPDATELIBRO;
		try {
			PreparedStatement sentencia = con.prepareStatement(sql);
			sentencia.setString(1, libro.getTitulo());
			sentencia.setString(2, libro.getGenero() + "");
			sentencia.setString(3, libro.getAutor());
			sentencia.setString(4, libro.getPaginas() + "");
			sentencia.setString(5, libro.getIsbn());

			if (sentencia.executeUpdate() > 0) {
				devolver = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return devolver;

	}

	@Override
	public boolean delete(String isbn) {
		boolean devolver = false;
		Connection con = GenericDAOImpl.getSharedConnection();
		String sql = DELETELIBRO;
		try {
			PreparedStatement sentencia = con.prepareStatement(sql);
			sentencia.setString(1, isbn);
		
			if (sentencia.executeUpdate() > 0) {
				devolver = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return devolver;

	}

}
