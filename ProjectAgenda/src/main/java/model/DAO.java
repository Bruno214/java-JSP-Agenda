package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {

	/** The driver. */
	private static String DRIVER = "com.mysql.cj.jdbc.Driver";
	
	/** The url. */
	private static String URL = "jdbc:mysql://localhost:3306/agenda?useTimezone=true&serverTimezone=UTC";
	
	/** The user. */
	private static String USER = "root";
	
	/** The pass. */
	private static String PASS = "";

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public Connection getConnection() {
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL, USER, PASS);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException("Erro na conexão banco da dados!" + e);
		}

	}

	/**
	 * Close connection.
	 *
	 * @param connection the connection
	 */
	public void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException("Erro ao fechar a conexão com o banco de dados!" + e);
			}
		}

	}

	/**
	 * Close connection.
	 *
	 * @param connection the connection
	 * @param statement the statement
	 */
	public void closeConnection(Connection connection, PreparedStatement statement) {
		try {
			if (connection != null) {
				connection.close();
			}

			if (statement != null) {
				statement.close();
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao fechar conexões!" + e);
		}

	}

	/**
	 * Close connection.
	 *
	 * @param connection the connection
	 * @param statement the statement
	 * @param resultSet the result set
	 */
	public void closeConnection(Connection connection, PreparedStatement statement, ResultSet resultSet) {
		try {
			if (connection != null) {
				connection.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (resultSet != null) {
				resultSet.close();
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao fechar conexões!" + e);
		}

	}

	// CRUD

	/**
	 * Inserir contato.
	 *
	 * @param contato the contato
	 */
	public void inserirContato(JavaBeans contato) {
		String sql = "INSERT INTO contatos (nome, telefone, email) Values " + "(?,?,?)";

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = this.getConnection();
			statement = conn.prepareStatement(sql);

			statement.setString(1, contato.getNome());
			statement.setString(2, contato.getTelefone());
			statement.setString(3, contato.getEmail());
			statement.execute();

		} catch (Exception e) {
			throw new RuntimeException("Erro ao salvar contato no banco de dados!" + e);

		} finally {
			this.closeConnection(conn, statement);
		}

	}

	/**
	 * Listar contatos.
	 *
	 * @return the list
	 */
	public List<JavaBeans> listarContatos() {
		List<JavaBeans> contatos = new ArrayList<>();

		String sql = "SELECT * FROM contatos order by nome";

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			conn = this.getConnection();
			statement = conn.prepareStatement(sql);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				JavaBeans cont = new JavaBeans();
				cont.setId(resultSet.getInt("id"));
				cont.setNome(resultSet.getString("nome"));
				cont.setTelefone(resultSet.getString("telefone"));
				cont.setEmail(resultSet.getString("email"));

				contatos.add(cont);
			}

			return contatos;

		} catch (Exception e) {
			throw new RuntimeException("Erro ao listar os contatos!" + e);
		} finally {
			this.closeConnection(conn, statement, resultSet);

		}
	}

	/**
	 * Gets the contato by id.
	 *
	 * @param idContato the id contato
	 * @return the contato by id
	 */
	public JavaBeans getContatoById(String idContato) {
		// metodo responsavel por buscar no banco de dados um contato para ser alterado
		String sql = "SELECT * FROM contatos WHERE id=?";
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			conn = this.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setString(1, idContato);
			resultSet = statement.executeQuery();

			JavaBeans contato = new JavaBeans();

			while (resultSet.next()) {
				contato.setId(Integer.parseInt(resultSet.getString("id")));
				contato.setNome(resultSet.getString("nome"));
				contato.setTelefone(resultSet.getString("telefone"));
				contato.setEmail(resultSet.getString("email"));

			}

			return contato;

		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar pessoa no Banco de dados!" + e);
		} finally {
			this.closeConnection(conn, statement, resultSet);
		}

	}

	/**
	 * Atualizar contato.
	 *
	 * @param contato the contato
	 */
	public void atualizarContato(JavaBeans contato) {
		String sql = "UPDATE contatos SET nome=?, telefone=?, email=? WHERE id=?";

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = this.getConnection();
			statement = conn.prepareStatement(sql);

			statement.setString(1, contato.getNome());
			statement.setString(2, contato.getTelefone());
			statement.setString(3, contato.getEmail());
			statement.setInt(4, contato.getId());

			statement.execute();

		} catch (Exception e) {

			throw new RuntimeException("Erro ao atualizar contato no banco de dados" + e);
		} finally {
			this.closeConnection(conn, statement);

		}

	}

	/**
	 * Delete contato by id.
	 *
	 * @param idContato the id contato
	 */
	public void deleteContatoById(int idContato) {
		// metodo responsavel por deletar um contato recebenco um id
		String sql = "DELETE FROM contatos WHERE id=?";

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = this.getConnection();
			statement = conn.prepareStatement(sql);

			statement.setInt(1, idContato);
			statement.execute();

		} catch (Exception e) {
			throw new RuntimeException("Erro ao deletar contato no banco de dados!" + e);
		} finally {
			this.closeConnection(conn, statement);

		}

	}

}
