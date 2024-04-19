package dao;


import model.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DatabaseUtility;import java.sql.Timestamp;


public class ObjectTypeDAO {

	
	/**
	 * Fetches a paginated list of object types from the database with sorting.
	 * This method is used in the 'Object Management Dashboard' within the 'Object Types Table' section
	 * for displaying a list of object types with dynamic searching, sorting, and pagination.
	 *
	 * @param limit The maximum number of object types to return.
	 * @param offset The number of object types to skip for pagination.
	 * @param sortBy The field to sort the object types by.
	 * @param sortDirection The direction of sorting, either 'asc' or 'desc'.
	 * @return A list of ObjectType instances representing the object types.
	 */
	public List<ObjectType> fetchAllObjectTypes(int limit, int offset, String sortBy, String sortDirection) {
	    List<ObjectType> objectTypes = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "SELECT * FROM object_types ORDER BY " + sortBy + " " + sortDirection + " LIMIT ? OFFSET ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, limit);
	        pstmt.setInt(2, offset);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            ObjectType objectType = new ObjectType();
	            objectType.setId(rs.getInt("id"));
	            objectType.setName(rs.getString("name"));
	            objectType.setDescription(rs.getString("description"));
	            objectType.setCreatedAt(rs.getTimestamp("created_at"));
	            objectType.setUpdatedAt(rs.getTimestamp("updated_at"));
	            objectType.setStatusOption(ObjectType.StatusOption.valueOf(rs.getString("status_options")));
	            objectTypes.add(objectType);
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            DatabaseUtility.disconnect(conn);
	        } catch (SQLException ex) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	    return objectTypes;
	}
	
	
	/**
	 * Retrieves a single ObjectType based on its ID from the database.
	 * This method is used in the 'Object Management Dashboard' within the 'Object Types Table'
	 * section for operations like viewing or editing specific object types.
	 *
	 * @param id The unique identifier for the object type.
	 * @return ObjectType The object type found, or null if not found.
	 */
	public ObjectType fetchObjectTypeById(int id) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    ObjectType objectType = null;
	
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "SELECT * FROM object_types WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, id);
	        rs = pstmt.executeQuery();
	
	        if (rs.next()) {
	            objectType = new ObjectType();
	            objectType.setId(rs.getInt("id"));
	            objectType.setName(rs.getString("name"));
	            objectType.setDescription(rs.getString("description"));
	            objectType.setCreatedAt(rs.getTimestamp("created_at"));
	            objectType.setUpdatedAt(rs.getTimestamp("updated_at"));
	            objectType.setStatusOption(ObjectType.StatusOption.valueOf(rs.getString("status_options")));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error fetching object type by ID", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error closing PreparedStatement", e);
	            }
	        }
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error closing ResultSet", e);
	            }
	        }
	    }
	
	    return objectType;
	}
	
	
	/**
	 * Updates the name, description, and status of an existing object type identified by 'id'.
	 * This method is used in both the 'Object Management Dashboard' for updating object types directly from the table
	 * and in the 'Object Type Editing' for the submission of updated data.
	 *
	 * @param id The ID of the object type to be updated.
	 * @param name The new name of the object type.
	 * @param description The new description of the object type.
	 * @param statusOption The new status of the object type.
	 * @return boolean True if the update was successful, otherwise false.
	 */
	public boolean updateObjectType(int id, String name, String description, ObjectType.StatusOption statusOption) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean updateSuccess = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "UPDATE object_types SET name = ?, description = ?, status_options = ?::status_options WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	
	        pstmt.setString(1, name);
	        pstmt.setString(2, description);
	        pstmt.setString(3, statusOption.toString());
	        pstmt.setInt(4, id);
	
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            updateSuccess = true;
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	            }
	        }
	    }
	    return updateSuccess;
	}
	
	/**
	 * Deletes an object type from the database using its ID.
	 * @param id The identifier of the object type to be deleted.
	 * @return boolean indicating if the deletion was successful.
	 */
	public boolean deleteObjectTypeById(int id) {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    String sql = "DELETE FROM object_types WHERE id = ?";
	    try {
	        connection = DatabaseUtility.connect();
	        statement = connection.prepareStatement(sql);
	        statement.setInt(1, id);
	        int rowsAffected = statement.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error deleting object type with ID: " + id, e);
	        return false;
	    } finally {
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to close statement", e);
	            }
	        }
	        DatabaseUtility.disconnect(connection);
	    }
	}
}
