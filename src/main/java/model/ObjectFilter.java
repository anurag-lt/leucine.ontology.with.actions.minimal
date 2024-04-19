package model;

/**
 * Represents a filter setting associated with an object within the system.
 * It captures the filter's name, criteria, and the associated object type for dynamic application.
 */
public class ObjectFilter {
    
    private int id;
    private String filterName;
    private String filterCriteria;
    private ObjectType fkObjectType;
    
    /**
     * Gets the unique identifier of the ObjectFilter.
     * @return The unique identifier of the ObjectFilter.
     */
    public int getId() {
        return id;
    }
    
    /**
     * Sets the unique identifier of the ObjectFilter.
     * @param id The unique identifier to set for the ObjectFilter.
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Gets the name of the filter.
     * @return The name of the filter.
     */
    public String getFilterName() {
        return filterName;
    }
    
    /**
     * Sets the name of the filter.
     * @param filterName The name of the filter to set.
     */
    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }
    
    /**
     * Gets the filter criteria as a textual representation.
     * @return The filter criteria.
     */
    public String getFilterCriteria() {
        return filterCriteria;
    }
    
    /**
     * Sets the filter criteria as a textual representation.
     * @param filterCriteria The filter criteria to set.
     */
    public void setFilterCriteria(String filterCriteria) {
        this.filterCriteria = filterCriteria;
    }
    
    /**
     * Gets the ObjectType to which this filter is associated.
     * @return The associated ObjectType.
     */
    public ObjectType getFkObjectType() {
        return fkObjectType;
    }
    
    /**
     * Sets the ObjectType to which this filter is associated.
     * @param fkObjectType The ObjectType to associate with this filter.
     */
    public void setFkObjectType(ObjectType fkObjectType) {
        this.fkObjectType = fkObjectType;
    }
    
    @Override
    public String toString() {
        return "ObjectFilter{" +
                "id=" + id +
                ", filterName='" + filterName + '\'' +
                ", filterCriteria='" + filterCriteria + '\'' +
                ", fkObjectType=" + (fkObjectType != null ? fkObjectType.getName() : "null") +
                '}';
    }
}