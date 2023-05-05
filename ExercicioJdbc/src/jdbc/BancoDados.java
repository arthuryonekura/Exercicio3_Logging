package jdbc;

import java.io.IOException;
import java.sql.*;

public class BancoDados implements InterfaceBancoDados {
public static Connection c;

@Override
public void conectar(String db_url, String db_user, String db_password) throws IOException {
Log log = new Log("Log.txt");
	try {
		c = DriverManager.getConnection(db_url, db_user, db_password);
		System.out.println("Conectado ao Banco de Dados!");
        log.logger.info("Conectado");
  } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Não foi possível conectar ao Banco de Dados: "+e);
        log.logger.warning("Não conectado.");
        }
	}

@Override
public void desconectar() throws IOException {
Log log = new Log("Log.txt");
	try {
		c.close();
		System.out.println("Você fez logout com sucesso!");
		log.logger.info("Você concluiu seu logout.");
  } catch (SQLException e) {
		System.out.println("Não foi possível desconectar do banco de dados.");
		log.logger.warning("Não foi possível descinectar.");
		e.printStackTrace();
		}
	}

@Override
public void consultar(String db_query) throws IOException {
Log log = new Log("Log.txt");
	try {
		Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery(db_query);
    while (rs.next()) {
        System.out.println(" Id: " + rs.getInt(1) + " Nome: " + rs.getString(2) + " Email: " + rs.getString(3)+ " Cargo: " + rs.getString(4));
        log.logger.info("Foi possível consultar.");
    }
        rs.close();
        statement.close();
  } catch (SQLException e) {
      e.printStackTrace();
	  log.logger.warning("Não foi possível consultar.");	}
	}

@Override
public int inserirAlterarExcluir(String db_query) throws IOException {
Log log = new Log("Log.txt"); 
	int numLinhasAfetadas = 0;
	try {
	    PreparedStatement ps = c.prepareStatement(db_query);
	    numLinhasAfetadas = ps.executeUpdate();
	    System.out.println("Sucesso! " + numLinhasAfetadas + " linhas foram afetadas.");
	    log.logger.info("Você concluiu com sucesso.");
	    ps.close();
 } catch (SQLException e) {
	    System.out.println("Erro ao realizar a operação: " + e.getMessage());
	    log.logger.warning("Erro na operação.");
	    }
	return numLinhasAfetadas;
	}
public static void main(String[] args) throws IOException {
final String db_url = "jdbc:mysql://localhost:3306/reuniao";
final String db_user = "root";
final String db_password = "";
		
BancoDados bd = new BancoDados();
bd.conectar(db_url, db_user, db_password);
bd.inserirAlterarExcluir("INSERT INTO pessoa (nome, email, cargo) VALUES ('Ana', 'ana@gmail.com', 'Aluno')");
bd.inserirAlterarExcluir("INSERT INTO pessoa (nome, email, cargo) VALUES ('Davi', 'davi@gmail.com', 'Professor')");
bd.inserirAlterarExcluir("INSERT INTO pessoa (nome, email, cargo) VALUES ('Rafael', 'rafael@gmail.com', 'Aluno')");
bd.consultar("SELECT * FROM pessoa");
bd.desconectar();
		
	}
}
