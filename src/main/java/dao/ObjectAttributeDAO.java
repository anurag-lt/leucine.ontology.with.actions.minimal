package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.*;

public class ObjectAttributeDAO {

	
	/**
	 * Creates a new attribute linked to a specific object type, allowing users to define new attributes as part of their object type configuration.
	 * This method is used in 'Object Type Creation' and 'Object Type Editing' sections.
	 *
	 * @param objectTypeId The identifier of the object type to which the attribute is linked.
	 * @param attributeName The name of the attribute being created.
	 * @param attributeType The data type of the attribute from the DataTypes enum.
	 * @param isMandatory Indicates whether the attribute is mandatory or optional.
	 * @param defaultValue The default value for the attribute, if any.
	 * @param description A brief description of the attribute.
	 * @param attributeLength The maximum length of the attribute value.
	 * @return boolean True if the attribute is successfully created, false otherwise.
	 */
	public boolean createAttribute(int objectTypeId, String attributeName, ObjectAttribute.DataTypes attributeType, boolean isMandatory, String defaultValue, String description, int attributeLength) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    try {
	        connection = DatabaseUtility.connect();
	        String sql = "INSERT INTO object_attributes (fk_object_type_id, attribute_name, data_type, is_mandatory, default_value, description, attribute_length) VALUES (?, ?, ?, ?, ?, ?, ?)";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, objectTypeId);
	        preparedStatement.setString(2, attributeName);
	        preparedStatement.setString(3, attributeType.name());
	        preparedStatement.setBoolean(4, isMandatory);
	        preparedStatement.setString(5, defaultValue);
	        preparedStatement.setString(6, description);
	        preparedStatement.setInt(7, attributeLength);
	        int affectedRows = preparedStatement.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error creating attribute", e);
	        return false;
	    } finally {
	        if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
	        DatabaseUtility.disconnect(connection);
	    }
	}
	
	/**
	 * Updates an existing attribute in the database. This method adapts changes such as name, type, mandatory status, and other properties.
	 *
	 * @param attributeId The identifier of the attribute to be updated.
	 * @param objectTypeId The identifier of the object type to which the attribute is linked.
	 * @param attributeName The new name of the attribute.
	 * @param attributeType The new data type for the attribute from the DataTypes enum.
	 * @param isMandatory Indicates whether the attribute is mandatory or optional.
	 * @param defaultValue The new default value for the attribute, if any.
	 * @param description A new brief description of the attribute.
	 * @param attributeLength The new maximum length of the attribute value.
	 * @return boolean True if the update was successful, false otherwise.
	 */
	public boolean updateAttribute(int attributeId, int objectTypeId, String attributeName, ObjectAttribute.DataTypes attributeType, boolean isMandatory, String defaultValue, String description, int attributeLength) {
	    Connection connection = null;
	    PreparedStatement pstmt = null;
	    boolean updateSuccess = false;
	    try {
	        connection = DatabaseUtility.connect();
	        String sql = "UPDATE object_attributes SET attribute_name = ?, data_type = ?, is_mandatory = ?, default_value = ?, description = ?, attribute_length = ?, fk_object_type_id = ? WHERE id = ?";
	        pstmt = connection.prepareStatement(sql);
	        pstmt.setString(1, attributeName);
	        pstmt.setString(2, attributeType.toString());
	        pstmt.setBoolean(3, isMandatory);
	        pstmt.setString(4, defaultValue);
	        pstmt.setString(5, description);
	        pstmt.setInt(6, attributeLength);
	        pstmt.setInt(7, objectTypeId);
	        pstmt.setInt(8, attributeId);
	
	        int rowsAffected = pstmt.executeUpdate();
	        updateSuccess = rowsAffected > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	            }
	        }
	        DatabaseUtility.disconnect(connection);
	    }
	    return updateSuccess;
	}
	
	/*
	 * Deletes an attribute from the database based on its identifier.
	 * @param attributeId The identifier of the attribute to be deleted.
	 * @return true if the delete operation was successful, false otherwise.
	 */
	public boolean deleteAttribute(int attributeId) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    boolean isDeleted = false;
	    try {
	        connection = DatabaseUtility.connect();
	        String sql = "DELETE FROM object_attributes WHERE id = ?;";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, attributeId);
	        int rowsAffected = preparedStatement.executeUpdate();
	        if (rowsAffected > 0) {
	            isDeleted = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error deleting attribute", e);
	    } finally {
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException se) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error closing PreparedStatement", se);
	            }
	        }
	        DatabaseUtility.disconnect(connection);
	    }
	    return isDeleted;
	}
	
	/**
	 * Retrieves all attributes associated with a specific object type to enable editing and management of those attributes.
	 * @param objectTypeId The identifier of the object type for which attributes need to be fetched.
	 * @return List<ObjectAttribute> A list of ObjectAttribute instances associated with the specified object type.
	 */
	public List<ObjectAttribute> getAttributeByObjectTypeId(int objectTypeId) {
	    List<ObjectAttribute> attributes = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        connection = DatabaseUtility.connect();
	        String query = "SELECT * FROM object_attributes WHERE fk_object_type_id = ?";
	        pstmt = connection.prepareStatement(query);
	        pstmt.setInt(1, objectTypeId);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            ObjectAttribute attribute = new ObjectAttribute();
	            attribute.setId(rs.getInt("id"));
	            attribute.setAttributeName(rs.getString("attribute_name"));
	            attribute.setDataType(ObjectAttribute.DataTypes.valueOf(rs.getString("data_type")));
	            attribute.setMandatory(rs.getBoolean("is_mandatory"));
	            attribute.setDefaultValue(rs.getString("default_value"));
	            attribute.setDescription(rs.getString("description"));
	            attribute.setAttributeLength(rs.getInt("attribute_length"));
	            attributes.add(attribute);
	        }
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
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	            }
	        }
	    }
	    return attributes;
	}
}
