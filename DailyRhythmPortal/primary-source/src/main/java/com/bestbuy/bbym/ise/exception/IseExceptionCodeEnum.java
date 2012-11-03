/**
 * 
 */
package com.bestbuy.bbym.ise.exception;

/**
 * @author a186288
 * 
 */
public enum IseExceptionCodeEnum {

    GeneralSystem(0, "General system error"), //
    DataAccess(1, "Error accessing the database"), //
    UnexpectedNull(2, "System encountered a null object when it was not expecting one"), //
    UnsuccessfulWorkflowCompletion(3, "A workflow completed unsuccessfully"), //
    InvalidState(4, "The state was being set illegally"), //
    LoginError(5, "User Login Exception"), //
    DataConversionError(6, "There was an unexpected error while converting data in the workflow"), //
    ReportError(7, "There was an unexpected exception while rendering a report"), //
    AccountNotFoundException(8, "Customer Account Not Found Exception"), //
    BusinessCustomerException(9, "Business Customer Exception"), //
    AuthenticationFailureException(10, "Authentication Failure Exception"), //
    InvalidAccountPasswordException(11, "Invalid Account Password Exception"), //
    CapException(12, "Cap Error has occured"), //
    UnknownProperty(13, "Unknown system property"), //
    PropertyFileAccessException(14, "Unable to access the properties file"), //
    BusinessException(15, "Business exception"), //
    DataItemAlreadyExists(16, "Data item already exists"),
    ExternalServiceException(17, "External System Service Error"),
    DuplicateLoanerPhone(18, "Duplicate loaner phone"), //
    DeactivatedCheckedOutLoanerPhone(19, "Checked out phones cannot be deactivated"), //
    LoanerPhoneAlreadyCheckedOut(
	    20,
	    "This loaner phone was already checked out for a different customer.  Please Cancel and select a different loaner phone."), //
    PDFCreationError(21, "There was an unexpected error while rendering a PDF"),
    // Added for Restful webservice Exceptions
    BADREQUEST(400, "DataSharing - Bad requestException"), UNAUTHORIZED(401, "DataSharing  - Unauthorized Exception"),
    FORBIDDEN(403, "DataSharing -  Forbidden Exception"), NOTFOUND(404, "DataSharing  - Not found Exception"),
    METHODNOTALLOWED(405, "DataSharing  - Method not allowed Exception"), NOTACCEPTABLE(406,
	    "DataSharing - Not acceptable Exception"), CONFLICT(409, "DataSharing - Conflict Exception"),
    PRECONDITIONFAILED(412, "DataSharing - Precondition failed"), UNSUPPORTEDMEDIATYPE(415,
	    "DataSharing - Unsupported media type exception"), INTERNALSERVERERROR(500,
	    "DataSharing - Internal server error");

    private int code;
    private String description;

    private IseExceptionCodeEnum(int code, String codeDescription) {
	this.code = code;
	this.description = codeDescription;
    }

    public int getCode() {
	return code;
    }

    public void setCode(int code) {
	this.code = code;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }
}
