package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.*;

public class CompanyDAO {

	
	/**
	 * Creates a new company record in the database. Used in the Company Registration form for submitting new company details and in submission actions to trigger integration with other modules.
	 * @param companyName name of the company
	 * @param companySectors sector of the company, such as pharma, biotech
	 * @param companySize size of the company
	 * @param headquartersAddress headquarters address of the company
	 * @param contactName primary contact person's name
	 * @param email email address for communication
	 * @param phone phone number for immediate communication needs
	 * @return boolean indicating success or failure of the insertion
	 */
	public boolean createCompany(String companyName, Company.CompanySector companySectors, int companySize, String headquartersAddress, String contactName, String email, String phone) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    boolean isSuccess = false;
	    try {
	        connection = DatabaseUtility.connect();
	        String sql = "INSERT INTO companies (company_name, company_sectors, company_size, headquarters_address, contact_name, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, companyName);
	        preparedStatement.setString(2, companySectors.name());
	        preparedStatement.setInt(3, companySize);
	        preparedStatement.setString(4, headquartersAddress);
	        preparedStatement.setString(5, contactName);
	        preparedStatement.setString(6, email);
	        preparedStatement.setString(7, phone);
	        int affectedRows = preparedStatement.executeUpdate();
	        isSuccess = affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, null, e);
	            }
	        }
	        DatabaseUtility.disconnect(connection);
	    }
	    return isSuccess;
	}
	
	/*
	 * Checks if the provided company name is unique in the system to avoid duplicates.
	 * Used in the Company Registration form to validate the uniqueness of the company name before submission.
	 */
	public boolean checkCompanyNameUnique(String companyName) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String query = "SELECT COUNT(*) AS count FROM companies WHERE company_name = ?";
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, companyName);
	        rs = stmt.executeQuery();
	        if (rs.next() && rs.getInt("count") > 0) {
	            return false; // Company name is not unique
	        }
	        return true; // Company name is unique
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "SQL error while checking company name uniqueness", e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (stmt != null) {
	            try {
	                stmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to close statement", e);
	            }
	        }
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to close result set", e);
	            }
	        }
	    }
	}
	
	/**
	 * Deletes a company from the database using its ID.
	 * @param id The unique identifier of the company to be deleted.
	 * @return true if the company is successfully deleted, false otherwise.
	 */
	public boolean deleteCompanyById(int id) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean isSuccess = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "DELETE FROM companies WHERE id = ?;";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, id);
	        int affectedRows = pstmt.executeUpdate();
	        isSuccess = affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error deleting company with ID: " + id, e);
	    } finally {
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e); }
	        DatabaseUtility.disconnect(conn);
	    }
	    return isSuccess;
	}
	
	
	/**
	 * Updates an existing company's information in the database.
	 * Used in the Company and Facility Overview page to allow editing of company details through modals.
	 *
	 * @param id unique identifier of the company to update
	 * @param companyName new name of the company
	 * @param companySectors new sector of the company
	 * @param companySize new size of the company
	 * @param headquartersAddress new headquarters address of the company
	 * @param contactName new primary contact person's name
	 * @param email new email address for communication
	 * @param phone new phone number for immediate communication needs
	 * @return true if the update was successful, false otherwise
	 */
	public boolean updateCompany(int id, String companyName, Company.CompanySector companySectors, int companySize, String headquartersAddress, String contactName, String email, String phone) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "UPDATE companies SET company_name = ?, company_sectors = ::company_sectors, company_size = ?, headquarters_address = ?, contact_name = ?, email = ?, phone = ? WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, companyName);
	        pstmt.setString(2, companySectors.name());
	        pstmt.setInt(3, companySize);
	        pstmt.setString(4, headquartersAddress);
	        pstmt.setString(5, contactName);
	        pstmt.setString(6, email);
	        pstmt.setString(7, phone);
	        pstmt.setInt(8, id);
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            return true;
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException ex) {
	                Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    }
	    return false;
	}
}
