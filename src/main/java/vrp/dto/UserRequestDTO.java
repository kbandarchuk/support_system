package vrp.dto;

import org.apache.commons.lang.StringUtils;
import vrp.exceptions.CreateInvalidObjectException;

public class UserRequestDTO {

    /////////////////////////////
    // Calculated fields
    ///

    private final String requestType;
    private final String theme;
    private final String description;
    private final String importance;
    private final String comment;


    /////////////////////////////
    // Constructors
    ///

    public UserRequestDTO( final String requestType
                         , final String theme
                         , final String description
                         , final String importance
                         , final String comment) {
        this.requestType = requestType;
        this.theme = theme;
        this.description = description;
        this.importance = importance;
        this.comment = comment;
        validateObject();
    }


    /////////////////////////////
    // Accessors
    ///

    public String getRequestType() {
        return requestType;
    }

    public String getTheme() {
        return theme;
    }

    public String getDescription() {
        return description;
    }

    public String getImportance() {
        return importance;
    }

    public String getComment() {
        return comment;
    }


    /////////////////////////////
    // Validation
    ///

    protected void validateObject(){
        validateRequestType();
        validateTheme();
        validateDescription();
        validateImportance();
    }

    protected void validateRequestType(){
        if(StringUtils.isEmpty(requestType)){
            throw new CreateInvalidObjectException("Request type is empty");
        }
    }

    public void validateTheme(){
        if(StringUtils.isEmpty(theme)){
            throw new CreateInvalidObjectException("Theme is empty");
        }
    }

    public void validateDescription(){
        if(StringUtils.isEmpty(description)){
            throw new CreateInvalidObjectException("Description is empty");
        }
    }

    public void validateImportance(){
        if(StringUtils.isEmpty(importance)){
            throw new CreateInvalidObjectException("Importance is empty");
        }
    }
}
