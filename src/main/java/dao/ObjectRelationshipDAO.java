package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.*;

public class ObjectRelationshipDAO {

	
	
	/**
	 * Creates a new object relationship in the database.
	 * @param relationshipName The name describing the relationship.
	 * @param relationshipType The type of the relationship (e.g., ONE_TO_ONE, MANY_TO_ONE).
	 * @param description Detailed information about the relationship's purpose.
	 * @param objectTypeId ID of the primary object involved in the relationship.
	 * @param relatedObjectTypeId ID of the secondary object involved in the relationship.
	 * @return boolean indicating whether the creation was successful.
	 */
	public boolean createObjectRelationship(String relationshipName, ObjectRelationship.RelationshipType relationshipType, String description, int objectTypeId, int relatedObjectTypeId) {
	    Connection connection = null;
	    PreparedStatement pstmt = null;
	    boolean success = false;
	    try {
	        connection = DatabaseUtility.connect();
	        String sql = "INSERT INTO object_relationships (relationship_name, relationship_type, description, fk_object_type_id, fk_related_object_type_id) VALUES (?, ?, ?, ?, ?)";
	        pstmt = connection.prepareStatement(sql);
	        pstmt.setString(1, relationshipName);
	        pstmt.setString(2, relationshipType.name());
	        pstmt.setString(3, description);
	        pstmt.setInt(4, objectTypeId);
	        pstmt.setInt(5, relatedObjectTypeId);
	
	        int result = pstmt.executeUpdate();
	        success = result > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
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
	 * Deletes an existing object relationship from the database by its unique identifier.
	 * @param relationshipId The unique identifier of the object relationship to be deleted.
	 * @return boolean indicating whether the deletion was successful.
	 */
	public boolean deleteObjectRelationshipById(int relationshipId) {
	    Connection connection = null;
	    PreparedStatement pstmt = null;
	    boolean success = false;
	
	    try {
	        connection = DatabaseUtility.connect();
	        String sql = "DELETE FROM object_relationships WHERE id = ?";
	        pstmt = connection.prepareStatement(sql);
	        pstmt.setInt(1, relationshipId);
	
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            success = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error deleting object relationship", e);
	    } finally {
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException logOrIgnore) {}
	        DatabaseUtility.disconnect(connection);
	    }
	    return success;
	}
	
	
	/**
	 * Updates an existing object relationship in the database.
	 * @param id The unique identifier of the object relationship.
	 * @param relationshipName Describes the nature and purpose of the relationship.
	 * @param relationshipType Enum value describing the type of relationship.
	 * @param description Detailed explanation of the relationship's purpose.
	 * @param objectTypeId The ID of the primary object type involved in the relationship.
	 * @param relatedObjectTypeId The ID of the secondary object type involved in the relationship.
	 * @return true if the update is successful, false otherwise.
	 */
	public boolean updateObjectRelationship(int id, String relationshipName, ObjectRelationship.RelationshipType relationshipType, String description, int objectTypeId, int relatedObjectTypeId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "UPDATE object_relationships SET relationship_name = ?, relationship_type = ?::relationship_type, description = ?, fk_object_type_id = ?, fk_related_object_type_id = ? WHERE id = ?;";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, relationshipName);
	        pstmt.setString(2, relationshipType.name());
	        pstmt.setString(3, description);
	        pstmt.setInt(4, objectTypeId);
	        pstmt.setInt(5, relatedObjectTypeId);
	        pstmt.setInt(6, id);
	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        return false;
	    } finally {
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
	        DatabaseUtility.disconnect(conn);
	    }
	}
	
	/**
	 * Retrieves an ObjectRelationship from the database by its ID.
	 * Used in Object Management Dashboard and Object Type Editing to retrieve details of a specific object relationship for viewing or editing.
	 * @param id The unique identifier of the object relationship to be retrieved.
	 * @return ObjectRelationship if found, otherwise null.
	 */
	public ObjectRelationship fetchObjectRelationshipById(int id) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    ObjectRelationship relationship = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "SELECT * FROM object_relationships WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, id);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            relationship = new ObjectRelationship();
	            relationship.setId(rs.getInt("id"));
	            relationship.setRelationshipName(rs.getString("relationship_name"));
	            relationship.setRelationshipType(ObjectRelationship.RelationshipType.valueOf(rs.getString("relationship_type")));
	            relationship.setDescription(rs.getString("description"));
	            // Assuming foreign keys refer to ids of ObjectType
	            ObjectType objectType = new ObjectType();
	            objectType.setId(rs.getInt("fk_object_type_id"));
	            relationship.setObjectType(objectType);
	
	            ObjectType relatedObjectType = new ObjectType();
	            relatedObjectType.setId(rs.getInt("fk_related_object_type_id"));
	            relationship.setRelatedObjectType(relatedObjectType);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error fetching ObjectRelationship by ID", e);
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
	    return relationship;
	}
}
