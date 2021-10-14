package solicitud.datos;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import solicitud.vo.HorarioVO;
import solicitud.vo.SolicitudVO;


public class SolicitudDAO {

	public static Connection getConnection(){
		Connection con=null;
		
		try{
			
			// CARGAR EL FICHERO DE PROPIEDADES
			Properties properties = new Properties();
			InputStream input = SolicitudDAO.class.getResourceAsStream("/configuracion.properties");
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
	public static int guardar(SolicitudVO e){
		int status=0;
		int idGeneradoSolicitud=0;
		try{
			Connection con=SolicitudDAO.getConnection();
			PreparedStatement ps=con.prepareStatement("insert into Solicitudes(Nombre,DNI,Tlf,Materia,FechaPermiso,Motivo,FechaEntrega,DiaCompleto,DiaNoCompleto,Denegado,Concedido) values (?,?,?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1,e.getNombre());
			ps.setString(2,e.getDni());
			ps.setInt(3,e.getTelefono());
			ps.setString(4,e.getMateria());
			ps.setString(5,e.getFechaPermiso());
			ps.setString(6,e.getMotivo());
			ps.setString(7,e.getFechaEntrega());
			ps.setBoolean(8,e.isDiaCompleto());
			ps.setBoolean(9,e.isDiaNoCompleto());
			ps.setBoolean(11,e.isConcedido());
			ps.setBoolean(10,e.isDenegado());
	
	
			status=ps.executeUpdate();
			
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
			         idGeneradoSolicitud = generatedKeys.getInt(1);
			}
			
			HorarioDAO.guardar(e.getHorario(),idGeneradoSolicitud);
		
			
			con.close();
		}catch(Exception ex){ex.printStackTrace();}
		
		return status;
	}
	
	
	public static int modificar(SolicitudVO e){
		int status=0;
		try{
			Connection con=SolicitudDAO.getConnection();
			PreparedStatement ps=con.prepareStatement("update Solicitudes set Nombre=?,DNI=?,Tlf=?,Materia=?,FechaPermiso=?,Motivo=?,FechaEntrega=?,DiaCompleto=?,DiaNocompleto=?,Concedido=?,Denegado=? where Id=?");
			ps.setString(1,e.getNombre());
			ps.setString(2,e.getDni());
			ps.setInt(3,e.getTelefono());
			ps.setString(4,e.getMateria());
			ps.setString(5,e.getFechaPermiso());
			ps.setString(6,e.getMotivo());
			ps.setString(7,e.getFechaEntrega());
			ps.setBoolean(8,e.isDiaCompleto());
			ps.setBoolean(9,e.isDiaNoCompleto());
			ps.setBoolean(10,e.isConcedido());
			ps.setBoolean(11,e.isDenegado());
		
			ps.setInt(12, e.getId());
			status=ps.executeUpdate();
			
			con.close();
		}catch(Exception ex){ex.printStackTrace();}
		
		return status;
	}
//	public static int eliminar(int id){
//		int status=0;
//		try{
//			Connection con=SolicitudDAO.getConnection();
//			PreparedStatement ps=con.prepareStatement("delete from Solicitudes where Id=?");
//			ps.setInt(1,id);
//			status=ps.executeUpdate();
//			
//			con.close();
//		}catch(Exception e){e.printStackTrace();}
//		
//		return status;
//	}
	
	public static int eliminar(SolicitudVO Solicitudes){
		int status=0;
		try{
			Connection con=SolicitudDAO.getConnection();
			PreparedStatement ps=con.prepareStatement("delete from Solicitudes where Id=?");
			ps.setInt(1,Solicitudes.getId());
			status=ps.executeUpdate();
			
			con.close();
		}catch(Exception e){e.printStackTrace();}
		
		return status;
	}
	public static SolicitudVO obtenerSolicitudPorId(int Id){
		SolicitudVO e=new SolicitudVO();
		
		try{
			Connection con=SolicitudDAO.getConnection();
			PreparedStatement ps=con.prepareStatement("select * from Solicitudes where Id=?");
			ps.setInt(1,Id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				e.setId(rs.getInt(1));
				e.setNombre(rs.getString(2));
				e.setDni(rs.getString(3));
				e.setTelefono(rs.getInt(4));
				e.setMateria(rs.getString(5));
				e.setFechaPermiso(rs.getString(6));
				e.setMotivo(rs.getString(7));
				e.setFechaEntrega(rs.getString(8));
				e.setDiaCompleto(rs.getBoolean(9));
				e.setDiaNoCompleto(rs.getBoolean(10));
				e.setFirma(rs.getString(11));
				e.setDenegado(rs.getBoolean(12));
				e.setConcedido(rs.getBoolean(13));
				e.setConstancias(rs.getString(14));
			
				
				
			}
			con.close();
		}catch(Exception ex){ex.printStackTrace();}
		
		return e;
		
	}
	public static List<SolicitudVO> obtenerSolicitud(){
		List<SolicitudVO> list=new ArrayList<SolicitudVO>();
		
		
		try{
			Connection con=SolicitudDAO.getConnection();
			PreparedStatement ps=con.prepareStatement("select  Nombre, DNI, Materia, FechaPermiso, Motivo, Id from Solicitudes");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				SolicitudVO e=new SolicitudVO();
				e.setNombre(rs.getString(1));
				e.setDni(rs.getString(2));
				e.setMateria(rs.getString(3));
				e.setFechaPermiso(rs.getString(4));
				e.setMotivo(rs.getString(5));
				e.setId(rs.getInt(6));
				list.add(e);
			}
			con.close();
		}catch(Exception e){e.printStackTrace();}
		
		return list;
	}
}
