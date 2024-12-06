package org.libertas;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	private Connection conexao;
	
	public Conexao() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conexao = DriverManager.getConnection(
				    "jdbc:mysql://localhost:3306/compromissobd?verifyServerCertificate=false&useSSL=false",
				    "saulo", 
				    "12345"
				);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getCon() {
		return conexao;
	}

	public void setCon(Connection con) {
		this.conexao = con;
	}
	
	public void desconecta() {
		try {
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
