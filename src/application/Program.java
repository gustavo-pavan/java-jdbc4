package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Connection conn = null;
		PreparedStatement st = null;
		
		try {
			
			conn = DB.getConnetion();
			
			// Query para inserir dados
			// Statement.RETURN_GENERATED_KEYS -> retorna o ID do ultimo registro
			
			st = conn.prepareStatement(
					 "INSERT INTO seller "
					+"(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+"VALUES "
					+"(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			// Paramteros para inserir			
			st.setString(1, "Carl Purple"); // o primeiro parametro refere-se ao interregoção da query
			st.setString(2, "carl@gmail.com");
			st.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime()));
			st.setDouble(4, 3000.0);
			st.setInt(5, 4);
			
			//executar query
			
			// pegar quantidade de linhas afetadas no banco
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys(); //pegar id de retorno
				while(rs.next()) {
					
					int id = rs.getInt(1); // pegar o dado na primeira posição
					System.out.println("Done! Id = " + id);
				}
			}
			else {
				System.out.println("No rown affected!");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnetion();
		}
		
	}

}
