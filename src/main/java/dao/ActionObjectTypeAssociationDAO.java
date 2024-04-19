package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.*;

public class ActionObjectTypeAssociationDAO {

	
	
	/**
	 * Retrieves all action-object type associations from the database. Used in 'Object Type Creation' and 'Object Type Editing' sections for populating forms and validating actions applicable to the specific object types.
	 * @return List of ActionObjectTypeAssociation
	 */
	public List<ActionObjectTypeAssociation> fetchActionObjectTypeAssociations() {
	    List<ActionObjectTypeAssociation> associations = new ArrayList<>();
	    Connection connection = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    try {
	        connection = DatabaseUtility.connect();
	        stmt = connection.createStatement();
	        String sql = "SELECT * FROM action_object_type_associations";
	        rs = stmt.executeQuery(sql);
	        while(rs.next()) {
	            ActionObjectTypeAssociation association = new ActionObjectTypeAssociation();
	            association.setId(rs.getInt("id"));
	            association.setAssociationName(rs.getString("association_name"));
	            association.setAssociationType(rs.getString("association_type"));
	            association.setIsActive(rs.getBoolean("is_active"));
	            association.setFkAction(new Action());
	            association.getFkAction().setId(rs.getInt("fk_action_id"));
	            association.setFkObjectType(new ObjectType());
	            association.getFkObjectType().setId(rs.getInt("fk_object_type_id"));
	            associations.add(association);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignored */ }
	        if (stmt != null) try { stmt.close(); } catch (SQLException e) { /* ignored */ }
	        DatabaseUtility.disconnect(connection);
	    }
	    return associations;
	}
	
	
	    /**
	     * Creates a new action-object type association in the database. Used in the 'Object Type Creation' section to define new associations as part of object type setup.
	     * @param associationName Descriptive name for the association.
	     * @param associationType Type of the association (create, update, or view).
	     * @param isActive Indicates whether the association is active.
	     * @param fkActionId Foreign key id linking to the Action.
	     * @param fkObjectTypeId Foreign key id linking to the Object Type.
	     * @return boolean indicating whether the insertion was successful.
	     */
	    public boolean createActionObjectTypeAssociation(String associationName, String associationType, boolean isActive, int fkActionId, int fkObjectTypeId) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        try {
	            conn = DatabaseUtility.connect();
	            String sql = "INSERT INTO action_object_type_associations (association_name, association_type, is_active, fk_action_id, fk_object_type_id) VALUES (?, ?, ?, ?, ?)";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, associationName);
	            pstmt.setString(2, associationType);
	            pstmt.setBoolean(3, isActive);
	            pstmt.setInt(4, fkActionId);
	            pstmt.setInt(5, fkObjectTypeId);
	            int affectedRows = pstmt.executeUpdate();
	            return affectedRows > 0;
	        } catch (SQLException e) {
	            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error inserting action-object type association", e);
	            return false;
	        } finally {
	            if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
	            DatabaseUtility.disconnect(conn);
	        }
	    }
	
	/**
	 * Updates an existing action-object type association in the database.
	 * @param associationId Identifier of the association to be updated.
	 * @param associationName New descriptive name for the association.
	 * @param associationType New type of the association (create, update, or view).
	 * @param isActive Indicates whether the association should be active.
	 * @param fkActionId Updated foreign key id linking to the Action.
	 * @param fkObjectTypeId Updated foreign key id linking to the Object Type.
	 * @return boolean indicating if the update was successful.
	 */
	 public boolean updateActionObjectTypeAssociation(int associationId, String associationName, String associationType, boolean isActive, int fkActionId, int fkObjectTypeId) {
	     Connection conn = null;
	     PreparedStatement pstmt = null;
	     boolean updateSuccess = false;
	     String sql = "UPDATE action_object_type_associations SET association_name = ?, association_type = ?, is_active = ?, fk_action_id = ?, fk_object_type_id = ? WHERE id = ?";
	     try {
	         conn = DatabaseUtility.connect();
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, associationName);
	         pstmt.setString(2, associationType);
	         pstmt.setBoolean(3, isActive);
	         pstmt.setInt(4, fkActionId);
	         pstmt.setInt(5, fkObjectTypeId);
	         pstmt.setInt(6, associationId);
	         int affectedRows = pstmt.executeUpdate();
	         if (affectedRows > 0) {
	             updateSuccess = true;
	         }
	     } catch (SQLException e) {
	         Logger.getLogger(ActionObjectTypeAssociationDAO.class.getName()).log(Level.SEVERE, null, e);
	     } finally {
	         try {
	             if (pstmt != null) pstmt.close();
	             DatabaseUtility.disconnect(conn);
	         } catch (SQLException e) {
	             Logger.getLogger(ActionObjectTypeAssociationDAO.class.getName()).log(Level.SEVERE, null, e);
	         }
	     }
	     return updateSuccess;
	}
	
	
	    /**
	     * Deletes an existing action-object type association from the database.
	     * Used in the 'Object Type Editing' section to remove associations that are no longer valid or needed.
	     *
	     * @param associationId Identifier of the association to be deleted.
	     * @return true if the association is successfully deleted, false otherwise.
	     */
	    public boolean deleteActionObjectTypeAssociation(int associationId) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        boolean isDeleted = false;
	        try {
	            conn = DatabaseUtility.connect();
	            String sql = "DELETE FROM action_object_type_associations WHERE id = ?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, associationId);
	            int affectedRows = pstmt.executeUpdate();
	            isDeleted = (affectedRows > 0);
	        } catch (SQLException e) {
	            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Failed to delete action-object type association", e);
	        } finally {
	            try {
	                if (pstmt != null) pstmt.close();
	                DatabaseUtility.disconnect(conn);
	            } catch (SQLException e) {
	                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Failed to close resources", e);
	            }
	        }
	        return isDeleted;
	    }
	
}
