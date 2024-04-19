package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.*;

public class ActionsInputTypeDAO {

	
	
	/**
	 * This method is used in the 'Object Type Creation' section for adding new actions input types into the system database.
	 * It handles the insertion of new input types linked to actions, defining the data requirements for those actions.
	 *
	 * @param inputDataType Specifies the data type expected for the action input parameters.
	 * @param actionId Foreign key linking to the specific action this input type is associated with.
	 * @return boolean indicating whether the insert operation was successful.
	 */
	public boolean createActionsInputType(ActionsInputType.DataType inputDataType, int actionId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean isSuccess = false;
	    String sql = "INSERT INTO actions_input_types (input_data_type, fk_action_id) VALUES (?, ?)";
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, inputDataType.toString());
	        pstmt.setInt(2, actionId);
	        int affectedRows = pstmt.executeUpdate();
	        isSuccess = affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error inserting actions input type", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Failed to close PreparedStatement", e);
	            }
	        }
	    }
	    return isSuccess;
	}
	
	
	/**
	 * Retrieves all actions input types associated with a specific action ID.
	 * This method is useful in the 'Object Type Editing' section to allow for editing or viewing operations.
	 *
	 * @param actionId The ID of the action to retrieve input types for.
	 * @return List of ActionsInputType objects linked to the given action ID.
	 */
	public List<ActionsInputType> fetchActionsInputTypesByActionId(int actionId) {
	    List<ActionsInputType> inputTypes = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    try {
	        connection = DatabaseUtility.connect();
	        String query = "SELECT * FROM actions_input_types WHERE fk_action_id = ?";
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, actionId);
	        resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	            ActionsInputType inputType = new ActionsInputType();
	            inputType.setId(resultSet.getInt("id"));
	            inputType.setInputDataType(ActionsInputType.DataType.valueOf(resultSet.getString("input_data_type")));
	            Action action = new Action();
	            action.setId(resultSet.getInt("fk_action_id"));
	            inputType.setAction(action);
	            inputTypes.add(inputType);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error fetching actions input types by action ID", e);
	    } finally {
	        if (resultSet != null) try { resultSet.close(); } catch (SQLException e) { Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to close ResultSet", e); }
	        if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) { Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to close PreparedStatement", e); }
	        DatabaseUtility.disconnect(connection);
	    }
	    return inputTypes;
	}
	
	
	/**
	 * Updates existing actions input type records in the database according to new specifications or corrections made by users.
	 *
	 * @param inputTypeId Unique identifier of the actions input type to be updated.
	 * @param newInputDataType The new data type to set for the action input parameter.
	 * @param actionId Foreign key to link the input type with a specific action, potentially updated.
	 * @return boolean indicating if the update was successful.
	 */
	public boolean updateActionsInputType(int inputTypeId, ActionsInputType.DataType newInputDataType, int actionId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean updateSuccess = false;
	
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "UPDATE actions_input_types SET input_data_type = ?, fk_action_id = ? WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	
	        pstmt.setString(1, newInputDataType.toString());
	        pstmt.setInt(2, actionId);
	        pstmt.setInt(3, inputTypeId);
	
	        int affectedRows = pstmt.executeUpdate();
	        updateSuccess = affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        try {
	            if (pstmt != null) {
	                pstmt.close();
	            }
	            DatabaseUtility.disconnect(conn);
	        } catch (SQLException e) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        }
	    }
	    return updateSuccess;
	}
	
	
	/**
	 * Deletes a specific actions input type from the database.
	 * @param inputTypeId The ID of the actions input type record to delete.
	 * Used in the 'Object Type Editing' section, this method handles the deletion of specific actions input types from the system database,
	 * often as part of data cleansing or editing processes.
	 * @return boolean Indicates success or failure of the deletion operation.
	 */
	public boolean deleteActionsInputType(int inputTypeId) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    String sql = "DELETE FROM actions_input_types WHERE id = ?;";
	    try {
	        connection = DatabaseUtility.connect();
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, inputTypeId);
	        int affectedRows = preparedStatement.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error deleting ActionsInputType with ID: " + inputTypeId, e);
	        return false;
	    } finally {
	        // Clean up JDBC objects
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	        } catch (SQLException e) {
	            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error closing PreparedStatement", e);
	        }
	        DatabaseUtility.disconnect(connection);
	    }
	}
}
