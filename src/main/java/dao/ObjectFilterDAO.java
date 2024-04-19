package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.*;

public class ObjectFilterDAO {

	
	/**
	 * Fetches all ObjectFilter entries related to a specific ObjectType.
	 * @param fk_object_type_id The foreign key ID associated with the ObjectType to which the filters are linked.
	 * @return A list of ObjectFilter relevant to the given ObjectType ID.
	 * @throws SQLException If an SQL error occurs during operation.
	 */
	public List<ObjectFilter> fetchObjectFilters(int fk_object_type_id) throws SQLException {
	    List<ObjectFilter> filters = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        connection = DatabaseUtility.connect();
	        String sql = "SELECT * FROM object_filters WHERE fk_object_type_id = ?";
	        pstmt = connection.prepareStatement(sql);
	        pstmt.setInt(1, fk_object_type_id);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            ObjectFilter filter = new ObjectFilter();
	            filter.setId(rs.getInt("id"));
	            filter.setFilterName(rs.getString("filter_name"));
	            filter.setFilterCriteria(rs.getString("filter_criteria"));
	            // Assuming ObjectType loading logic is handled elsewhere or ObjectType is lazy loaded.
	            filters.add(filter);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        throw e;
	    } finally {
	        if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignored */ }
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignored */ }
	        DatabaseUtility.disconnect(connection);
	    }
	    return filters;
	}
	
	/**
	 * Creates a new ObjectFilter within the system.
	 * Used in the 'Object Type Creation' page to allow users to set up new filters as they create or define new object types,
	 * which aids in customized and efficient data handling and representation on the dashboard.
	 *
	 * @param filterName The name assigned to the filter, making it recognizable and applicable on the dashboard.
	 * @param filterCriteria The criteria or conditions that define what the filter does, such as which attributes are filtered or specific values matched.
	 * @param fk_object_type_id The foreign key ID linking the filter to an ObjectType, allowing dynamic application of filters based on object type attributes.
	 * @return true if the filter was successfully created, false otherwise.
	 */
	public boolean createObjectFilter(String filterName, String filterCriteria, int fk_object_type_id) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean success = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "INSERT INTO object_filters (filter_name, filter_criteria, fk_object_type_id) VALUES (?, ?, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, filterName);
	        pstmt.setString(2, filterCriteria);
	        pstmt.setInt(3, fk_object_type_id);
	
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            success = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
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
	    return success;
	}
	
	/**
	 * Updates an existing ObjectFilter in the database based on the provided parameters.
	 * @param id The unique identifier of the ObjectFilter to be updated.
	 * @param filterName Updated name of the filter.
	 * @param filterCriteria Updated filter criteria describing the new conditions or rules.
	 * @param fk_object_type_id The updated foreign key ID of the ObjectType to which the filter is linked.
	 * @return boolean indicating the success or failure of the update operation.
	 */
	public boolean updateObjectFilter(int id, String filterName, String filterCriteria, int fk_object_type_id) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    boolean updateSuccess = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "UPDATE object_filters SET filter_name = ?, filter_criteria = ?, fk_object_type_id = ? WHERE id = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, filterName);
	        stmt.setString(2, filterCriteria);
	        stmt.setInt(3, fk_object_type_id);
	        stmt.setInt(4, id);
	
	        int affectedRows = stmt.executeUpdate();
	        if (affectedRows > 0) {
	            updateSuccess = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating ObjectFilter", e);
	    } finally {
	        if (stmt != null) { try { stmt.close(); } catch (SQLException e) { Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Failed to close statement", e); } }
	        DatabaseUtility.disconnect(conn);
	    }
	    return updateSuccess;
	}
	
	
	/**
	 * Deletes a specific ObjectFilter from the system based on its unique identifier.
	 * This method supports the 'Object Management Dashboard' where users can delete filters.
	 *
	 * @param id The unique identifier of the ObjectFilter to be deleted.
	 * @return true if the deletion was successful, false otherwise.
	 */
	public boolean deleteObjectFilter(int id) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    boolean result = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "DELETE FROM object_filters WHERE id = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, id);
	
	        int affectedRows = stmt.executeUpdate();
	        if (affectedRows > 0) {
	            result = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (stmt != null) {
	            try {
	                stmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
	            }
	        }
	    }
	    return result;
	}
	
}
