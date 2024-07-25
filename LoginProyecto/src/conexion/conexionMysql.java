/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import com.mysql.jdbc.PreparedStatement;
import com.sun.org.apache.bcel.internal.Constants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author pc
 */
public class conexionMysql {
    Connection cn;
    PreparedStatement ps = null;
    ResultSet rs;

    
    
    
    public Connection conectar(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/login_java_mysql","root","");
            System.out.println("Conectado BD");
        } catch (Exception e) {
            System.out.println("Error al conectar BD");
        }
        return cn;
    }
    
    public int obtenerEstadoId(String estadoNombre) {
        int estadoId = -1;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = conectar();
            String sql = "SELECT id FROM estados WHERE TRIM(LOWER(nombre)) = ?";
            ps = (PreparedStatement) cn.prepareStatement(sql);
            ps.setString(1, estadoNombre.trim().toLowerCase());
            rs = ps.executeQuery();

            System.out.println("Buscando estado: " + estadoNombre); // Línea para depuración

            if (rs.next()) {
                estadoId = rs.getInt("id");
                System.out.println("Estado encontrado: " + estadoNombre + " con ID: " + estadoId); // Línea para depuración
            } else {
                System.out.println("Estado no encontrado para: " + estadoNombre); // Línea para depuración
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener estado_id: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return estadoId;
    }
    

    /**
     *
     * @param estadoCivilNombre
     * @return
     */
    public int  obtenerEstadoCivilId(String estadoCivilNombre) {
        int estadoCivilId = -1;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = conectar();
            String sql = "SELECT id FROM estado_civil WHERE TRIM(LOWER(descripcion)) = ?";
            ps = (PreparedStatement) cn.prepareStatement(sql);
            ps.setString(1, estadoCivilNombre.trim().toLowerCase());
            rs = ps.executeQuery();

            System.out.println("Buscando estado civil: " + estadoCivilNombre); // Línea para depuración

            if (rs.next()) {
                estadoCivilId = rs.getInt("id");
                System.out.println("Estado civil encontrado: " + estadoCivilNombre + " con ID: " + estadoCivilId); // Línea para depuración
            } else {
                System.out.println("Estado civil no encontrado para: " + estadoCivilNombre); // Línea para depuración
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener estado_civil_id: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return estadoCivilId;
    }
    public int  obtenerdocumentosId(String documentoNombre) {
        int documentoId = -1;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = conectar();
            String sql = "SELECT id FROM documentos WHERE nombre_documento = ?";
            ps = (PreparedStatement) cn.prepareStatement(sql);
            ps.setString(1, documentoNombre);
            rs = ps.executeQuery();

            System.out.println("Buscando Documento: " + documentoNombre); // Línea para depuración

            if (rs.next()) {
                documentoId = rs.getInt("id");
                System.out.println("Estado encontrado: " + documentoNombre + " con ID: " + documentoId); // Línea para depuración
            } else {
                System.out.println("Estado no encontrado para: " + documentoNombre); // Línea para depuración
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener estado_id: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return documentoId;
    }
    
    
    
    public int Reusuario(String nombre, String apellido, String telefono, String estadoNombre, String estadoCivilNombre, String documentoNombre, String identificacion) {
        int res = 0;
        PreparedStatement ps = null;
        try {
            int estado_id = obtenerEstadoId(estadoNombre);
            if (estado_id == -1) {
                System.out.println("Estado no encontrado.");
                return 0; // O manejar el caso de estado no encontrado según lo necesites
            }
            int estado_civil_id = obtenerEstadoCivilId(estadoCivilNombre);
            if (estado_civil_id == -1) {
                System.out.println("Estado no encontrado.");
                return 0; // O manejar el caso de estado no encontrado según lo necesites
            }
            int documento_id = obtenerdocumentosId(documentoNombre);
            if (documento_id == -1) {
                System.out.println("Estado no encontrado.");
                return 0; // O manejar el caso de estado no encontrado según lo necesites
            }

            cn = conectar();
            String sql = "INSERT INTO estudiantes(nombre, apellido, telefono, estado_id, estado_civil_id, documento_id, identificacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = (PreparedStatement) cn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, telefono);
            ps.setInt(4, estado_id); // Usar el estado_id obtenido
            ps.setInt(5, estado_civil_id);
            ps.setInt(6, documento_id);
            ps.setString(7, identificacion);
            
            res = ps.executeUpdate();
            System.out.println("Estudiante registrado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al registrar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return res;
        
    }
    public int Acestudiante(String identificacion, String nombre, String apellido, String telefono, String estadoNombre, String estadoCivilNombre, String documentoNombre) {
        int res = 0;
        try {
            int estado_id = obtenerEstadoId(estadoNombre);
            if (estado_id == -1) {
                System.out.println("Estado no encontrado: " + estadoNombre);
                return 0;
            }

            int estado_civil_id = obtenerEstadoCivilId(estadoCivilNombre);
            if (estado_civil_id == -1) {
                System.out.println("Estado civil no encontrado: " + estadoCivilNombre);
                return 0;
            }

            int documento_id = obtenerdocumentosId(documentoNombre);
            if (documento_id == -1) {
                System.out.println("Documento no encontrado: " + documentoNombre);
                return 0;
            }

            cn = conectar();
            String sql = "UPDATE estudiantes SET nombre = ?, apellido = ?, telefono = ?, estado_id = ?, estado_civil_id = ?, documento_id = ? WHERE identificacion = ?";
            ps = (PreparedStatement) cn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, telefono);
            ps.setInt(4, estado_id);
            ps.setInt(5, estado_civil_id);
            ps.setInt(6, documento_id);
            ps.setString(7, identificacion);

            res = ps.executeUpdate();
            System.out.println("Estudiante actualizado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return res;
    }
    

    public int Reusuario(int id, String nombre, String apellido, String telefono, String estadoNombre, String estadoCivilNombre, String documentoNombre, String identificacion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public Estudiante buscarEstudiantePorIdentificacion(String identificacion) {
    Estudiante estudiante = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
        cn = conectar();
        String sql = "SELECT e.nombre, e.apellido, e.telefono, es.nombre AS estado, ec.descripcion AS estado_civil, d.nombre_documento AS documento " +
                     "FROM estudiantes e " +
                     "JOIN estados es ON e.estado_id = es.id " +
                     "JOIN estado_civil ec ON e.estado_civil_id = ec.id " +
                     "JOIN documentos d ON e.documento_id = d.id " +
                     "WHERE e.identificacion = ?";
        ps = (PreparedStatement) cn.prepareStatement(sql);
        ps.setString(1, identificacion);
        rs = ps.executeQuery();

        if (rs.next()) {
            estudiante = new Estudiante();
            estudiante.setNombre(rs.getString("nombre"));
            estudiante.setApellido(rs.getString("apellido"));
            estudiante.setTelefono(rs.getString("telefono"));
            estudiante.setEstado(rs.getString("estado"));
            estudiante.setEstadoCivil(rs.getString("estado_civil"));
            estudiante.setDocumento(rs.getString("documento"));
            estudiante.setIdentificacion(rs.getString("identificacion"));
        } else {
            System.out.println("Estudiante no encontrado para identificación: " + identificacion);
        }
    } catch (SQLException e) {
        System.out.println("Error al buscar estudiante: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (cn != null) cn.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    return estudiante;
}

    public java.sql.PreparedStatement prepareStatement(String consulta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public int Elusuario(String identificacion) {
            int res = 0;
            Connection cn = null;
            PreparedStatement ps = null;

            try {
                cn = conectar(); // Asegúrate de conectar antes de preparar la consulta
                String consulta = "DELETE FROM estudiantes WHERE identificacion = ?";
                ps = (PreparedStatement) cn.prepareStatement(consulta);
                ps.setString(1, identificacion);
                res = ps.executeUpdate();

                if (res > 0) {
                    System.out.println("Se ha eliminado correctamente");
                } else {
                    System.out.println("No se encontró el usuario con la identificación proporcionada");
                }
            } catch (SQLException e) {
                System.out.println("Error no se ha podido eliminar: " + e.getMessage());
            } finally {
                try {
                    if (ps != null) ps.close();
                    if (cn != null) cn.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar recursos: " + e.getMessage());
                }
            }
            return res;
    }
    
        
    public int buscarIdPorIdentificacion(String identificacion) {
        int id = -1; // Valor por defecto indicando que no se encontró el usuario
        try {
            String consulta = "SELECT id FROM estudiantes WHERE identificacion = ?";
            PreparedStatement ps = (PreparedStatement) cn.prepareStatement(consulta);
            ps.setString(1, identificacion);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id"); // Asegúrate de que 'id' es el nombre de la columna en tu tabla
            } else {
                System.out.println("Usuario no encontrado para la identificación proporcionada.");
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el ID del usuario: " + e.getMessage());
        }
        return id;
    }
    public int eliminarUsuarioPorIdentificacion(String identificacion) {
        int filasAfectadas = 0;
        try {
            int id = buscarIdPorIdentificacion(identificacion); // Obtén el ID usando la identificación

            if (id != -1) { // Solo elimina si se encontró el ID
                String consulta = "DELETE FROM estudiantes WHERE id = ?";
                PreparedStatement ps = (PreparedStatement) cn.prepareStatement(consulta);
                ps.setInt(1, id);
                filasAfectadas = ps.executeUpdate();

                if (filasAfectadas > 0) {
                    System.out.println("Usuario eliminado exitosamente.");
                } else {
                    System.out.println("No se pudo eliminar el usuario con ID: " + id);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
        }
        return filasAfectadas;
    }
}
