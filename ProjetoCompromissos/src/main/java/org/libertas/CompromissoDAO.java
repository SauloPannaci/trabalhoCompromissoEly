package org.libertas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class CompromissoDAO {
	private static LinkedList<Compromisso> lista = new LinkedList<Compromisso>();
	Conexao con = new Conexao();
	Retorno retorno = new Retorno();
	
	public Retorno salvar(Compromisso p) {
		if(p.getId() > 0) {
			return alterar(p);
		}else {
			return inserir(p);
		}
	}
	
	public Retorno inserir(Compromisso p) {
//		lista.add(p);
		
		try {
			String sql = "INSERT INTO compromisso (descricao, datainicio, datafim, local, status) VALUES "
					+ "(?, ?, ?, ?, ?);";
			
			PreparedStatement prep = con.getCon().prepareStatement(sql);
			prep.setString(1, p.getDescricao());
			prep.setString(2, p.getDatainicio());
			prep.setString(3, p.getDatafim());
			prep.setString(4, p.getLocal());
			prep.setString(5, p.getStatus());
			
			prep.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			retorno.setSucesso(false);
		}finally {
			if(retorno.getSucesso() == true) {
				retorno.setMensagem("Inserido com sucesso");
			}else {
				retorno.setMensagem("Falha ao inserir");
			}
			con.desconecta();
		}
		return retorno;
	}
	
	public LinkedList<Compromisso> listar(String pesquisa) {
		lista = new LinkedList<Compromisso>();
		try {
			if(pesquisa == null) {
				pesquisa = "";
			}
			String sql = "SELECT * FROM compromisso WHERE descricao like ? ORDER BY descricao";
			PreparedStatement sta = con.getCon().prepareStatement(sql);
			sta.setString(1, "%"+pesquisa+"%");
			ResultSet res = sta.executeQuery();
			while(res.next()) {
				Compromisso compromisso = new Compromisso();
				compromisso.setId(res.getInt("id"));
				compromisso.setDescricao(res.getString("descricao"));
				compromisso.setDatainicio(res.getString("datainicio"));
				compromisso.setDatafim(res.getString("datafim"));
				compromisso.setLocal(res.getString("local"));
				compromisso.setStatus(res.getString("status"));
				lista.add(compromisso);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconecta();
		return lista;
	}
	
	public Retorno alterar(Compromisso p) {
		try {
			String sql = "UPDATE compromisso SET descricao = ?, datainicio = ?, datafim = ?, local = ?, status = ? WHERE id = ?";
			
			PreparedStatement prep = con.getCon().prepareStatement(sql);
			prep.setString(1, p.getDescricao());
			prep.setString(2, p.getDatainicio());
			prep.setString(3, p.getDatafim());
			prep.setString(4, p.getLocal());
			prep.setString(5, p.getStatus());
			prep.setInt(6, p.getId());
			
			prep.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			retorno.setSucesso(false);
		}finally {
			if(retorno.getSucesso() == true) {
				retorno.setMensagem("Atualizado com sucesso");
			}else {
				retorno.setMensagem("Falha ao atualizar");
			}
			con.desconecta();
		}
		return retorno;
	}
	
	public Retorno excluir(int id) {
		try {
			String sql = "DELETE FROM compromisso WHERE id = ?;";
			
			PreparedStatement prep = con.getCon().prepareStatement(sql);
			prep.setInt(1, id);
			
			prep.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			retorno.setSucesso(false);
		}finally {
			if(retorno.getSucesso() == true) {
				retorno.setMensagem("Excluido com sucesso");
			}else {
				retorno.setMensagem("Falha ao excluir");
			}
			con.desconecta();
		}
		return retorno;
	}
	
	public Compromisso consultar(int id) {
		Compromisso p = new Compromisso();
		try {
			String sql = "SELECT * FROM compromisso WHERE id = "+id+";";
			Statement sta = con.getCon().createStatement();
			ResultSet res = sta.executeQuery(sql);
			if(res.next()) {
				p.setId(res.getInt("id"));
				p.setDescricao(res.getString("descricao"));
				p.setDatainicio(res.getString("datainicio"));
				p.setDatafim(res.getString("datafim"));
				p.setLocal(res.getString("local"));
				p.setStatus(res.getString("status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con.desconecta();
		return p;
	}
}
