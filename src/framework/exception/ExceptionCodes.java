package framework.exception;

import framework.exception.ExceptionCode;

public class ExceptionCodes
{
	// Commanding
	public static final ExceptionCode COMMANDING_NO_COMMAND_IDENTIFIER_FOUND = new ExceptionCode("COMMANDING_NO_COMMAND_IDENTIFIER_FOUND", "No command identifier is found in the request");
	public static final ExceptionCode COMMANDING_UNKNOWN_COMMAND = new ExceptionCode("COMMANDING_UNKNOWN_COMMAND", "Unknown command has received. Command: {0}");
	public static final ExceptionCode COMMANDING_CANNOT_INSTANTIATE_COMMANDHANDLER = new ExceptionCode("COMMANDING_CANNOT_INSTANTIATE_COMMANDHANDLER", "Cannot instantiate defined CommandHandler. Command: {0}, CommandHandlerFactory: {1}");
	public static final ExceptionCode COMMANDING_CANNOT_INSTANTIATE_COMMANDVALIDATOR = new ExceptionCode("COMMANDING_CANNOT_INSTANTIATE_COMMANDVALIDATOR", "Cannot instantiate defined CommandValidator. Command: {0}, CommandValidatorFactory: {1}");
	public static final ExceptionCode COMMANDING_NO_COMMANDTRANSFORMERFACTORY_FOUND = new ExceptionCode("COMMANDING_NO_COMMANDTRANSFORMERFACTORY_FOUND", "No ICommandTransformerFactory is found for the specified request. RequestName: {0}");
	public static final ExceptionCode COMMANDING_NO_COMMANDTRANSFORMER_FOUND = new ExceptionCode("COMMANDING_NO_COMMANDTRANSFORMER_FOUND", "No ICommandTransformer is found for the specified request. RequestName: {0}");
	public static final ExceptionCode COMMANDING_REQUEST_CANNOT_BE_TRANSFORMED_TO_COMMAND = new ExceptionCode("COMMANDING_REQUEST_CANNOT_BE_TRANSFORMED_TO_COMMAND", "Request cannot be transformed to Command ({0}) by CommandTransformer ({1}). Exception: {2}");
	public static final ExceptionCode COMMANDING_UNSUPPORTED_COMMANDRESULT = new ExceptionCode("COMMANDING_UNSUPPORTED_COMMANDRESULT", "ICommandResult ({0}) type is not supported by ICommandTransformer ({1})");
	public static final ExceptionCode COMMANDING_CONTENT_TRANSFORMATION_NOT_IMPLEMENTED = new ExceptionCode("COMMANDING_CONTENT_TRANSFORMATION_NOT_IMPLEMENTED", "No transformation is implemented to transform CommandContentResult");

	// Server 
	public static final ExceptionCode SERVER_SOCKET_INITIALIZATION_ERROR = new ExceptionCode("SERVER_SOCKET_INITIALIZATION_ERROR", "Cannot bind ServerSocket to port {0}");
	public static final ExceptionCode SERVER_SOCKET_NEW_CONNECTION_ACCEPTANCE_ERROR = new ExceptionCode("SERVER_SOCKET_NEW_CONNECTION_ACCEPTANCE_ERROR", "Cannot accept client socket connection");
	public static final ExceptionCode SERVER_SOCKET_CLOSURE_ERROR = new ExceptionCode("SERVER_SOCKET_CLOSURE_ERROR", "Cannot close ServerSocket");
			
	// Logging
	public static final ExceptionCode LOGGING_CANNOT_FIND_LOGGING_CONFIGURATION_FILE = new ExceptionCode("LOGGING_CANNOT_FIND_LOGGING_CONFIGURATION_FILE", "No logging configuration file is found at {0}.");
	public static final ExceptionCode LOGGING_CANNOT_CONFIGURE_LOGGING_FRAMEWORK = new ExceptionCode("LOGGING_CANNOT_CONFIGURE_LOGGING_FRAMEWORK", "Cannot configure logging framework.");
}
