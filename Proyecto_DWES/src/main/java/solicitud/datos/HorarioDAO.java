package solicitud.datos;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import solicitud.vo.HorarioVO;
import solicitud.vo.SolicitudVO;


public class HorarioDAO {

	public static Connection getConnection(){
		Connection con=null;
		try{
			
			// CARGAR EL FICHERO DE PROPIEDADES
			Properties properties = new Properties();
			InputStream input = HorarioDAO.class.getResourceAsStream("/configuracion.properties");
	        properties.load(input);

			String driver = properties.getProperty("driver");
			String urlConexion = properties.getProperty("urlConexion");
			String usuario = properties.getProperty("usuario");
			String password = properties.getProperty("password");
			
			Class.forName(driver);
			con=DriverManager.getConnection(urlConexion,usuario, password);
			
			
		}catch(Exception e){System.out.println(e);}
		return con;
	}
	
	
	public static int guardar(HorarioVO e,int idSolicitud){
		int status=0;
		try{
			Connection con=HorarioDAO.getConnection();
			PreparedStatement ps=con.prepareStatement("insert into HORAS_GRUPOS (Hora,Grupo,Aula,Tipo,Permiso) values (?,?,?,?,?)");
			ps.setInt(5,idSolicitud);
			ps.setString(2,e.getGrupo1());
			ps.setString(2,e.getGrupo2());
			ps.setString(2,e.getGrupo3());
			ps.setString(2,e.getGrupo4());
			ps.setString(2,e.getGrupo5());
			ps.setString(2,e.getGrupo6());
			ps.setString(3,e.getAula1());
			ps.setString(3,e.getAula2());
			ps.setString(3,e.getAula3());
			ps.setString(3,e.getAula4());
			ps.setString(3,e.getAula5());
			ps.setString(3,e.getAula6());
			ps.setString(4,e.getTipo());

			status=ps.executeUpdate();
			
		
		}catch(Exception ex){ex.printStackTrace();}
		
		return status;
	}
	
	
	public static int modificarHorario(HorarioVO e,int idSolicitud){
		int status=0;
		try{
			Connection con=HorarioDAO.getConnection();
			PreparedStatement ps=con.prepareStatement("insert into HORAS_GRUPOS (Hora,Grupo,Aula,Tipo,Permiso) values (?,?,?,?,?)");
			ps.setInt(5,idSolicitud);
			ps.setString(2,e.getGrupo1());
			ps.setString(2,e.getGrupo2());
			ps.setString(2,e.getGrupo3());
			ps.setString(2,e.getGrupo4());
			ps.setString(2,e.getGrupo5());
			ps.setString(2,e.getGrupo6());
			ps.setString(3,e.getAula1());
			ps.setString(3,e.getAula2());
			ps.setString(3,e.getAula3());
			ps.setString(3,e.getAula4());
			ps.setString(3,e.getAula5());
			ps.setString(3,e.getAula6());
			ps.setString(4,e.getTipo());

			status=ps.executeUpdate();
			
		
		}catch(Exception ex){ex.printStackTrace();}
		
		return status;
	}
	public static int eliminarHorario(int id){
		int status=0;
		try{
			Connection con=HorarioDAO.getConnection();
			PreparedStatement ps=con.prepareStatement("delete from HORAS_GRUPOS where Id=?");
			ps.setInt(1,id);
			status=ps.executeUpdate();
			
			con.close();
		}catch(Exception e){e.printStackTrace();}
		
		return status;
	}
	
	
	
	
	
	
	public static int eliminar(HorarioVO HORAS_GRUPOS){
		int status=0;
		try{
			Connection con=SolicitudDAO.getConnection();
			PreparedStatement ps=con.prepareStatement("delete from HORAS_GRUPOS where Id=?");
			ps.setInt(1,HORAS_GRUPOS.getId());
			status=ps.executeUpdate();
			
			con.close();
		}catch(Exception e){e.printStackTrace();}
		
		return status;
	}
	public static HorarioVO obtenerHorarioPorId(int id){
		HorarioVO e=new HorarioVO();
		
		try{
			Connection con=HorarioDAO.getConnection();
			PreparedStatement ps=con.prepareStatement("select * from HORAS_GRUPOS where Id=?");
			ps.setInt(1,id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				ps.setInt(5,id);
				ps.setString(2,e.getGrupo1());
				ps.setString(2,e.getGrupo2());
				ps.setString(2,e.getGrupo3());
				ps.setString(2,e.getGrupo4());
				ps.setString(2,e.getGrupo5());
				ps.setString(2,e.getGrupo6());
				ps.setString(3,e.getAula1());
				ps.setString(3,e.getAula2());
				ps.setString(3,e.getAula3());
				ps.setString(3,e.getAula4());
				ps.setString(3,e.getAula5());
				ps.setString(3,e.getAula6());
				ps.setString(4,e.getTipo());

			}
			con.close();
		}catch(Exception ex){ex.printStackTrace();}
		
		return e;
		
	}
	public static List<HorarioVO> obtenerHorario(){
		List<HorarioVO> list=new ArrayList<HorarioVO>();
		
		
		try{
			Connection con=HorarioDAO.getConnection();
			PreparedStatement ps=con.prepareStatement("select  id,Hora,Grupo,Aula,Tipo from HORAS_GRUPOS");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				HorarioVO e=new HorarioVO();
				e.setId(rs.getInt(1));
				e.setAula1(rs.getString(3));
				e.setAula2(rs.getString(3));
				e.setAula3(rs.getString(3));
				e.setAula4(rs.getString(3));
				e.setAula5(rs.getString(3));
				e.setAula6(rs.getString(3));
				e.setGrupo1(rs.getString(2));
				e.setGrupo2(rs.getString(2));
				e.setGrupo3(rs.getString(2));
				e.setGrupo4(rs.getString(2));
				e.setGrupo5(rs.getString(2));
				e.setGrupo6(rs.getString(2));
				e.setTipo(rs.getString(4));
				list.add(e);
			}
			con.close();
		}catch(Exception e){e.printStackTrace();}
		
		return list;
	}
}
