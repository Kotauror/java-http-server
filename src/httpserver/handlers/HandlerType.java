package httpserver.handlers;

public enum HandlerType {
    BASIC_AUTH_HANDLER,
    GET_HANDLER,
    HEAD_HANDLER,
    POST_HANDLER,
    INTERNAL_ERROR_HANDLER,
    DIRECTORY_LISTING_HANDLER,
    PUT_HANDLER,
    DELETE_HANDLER,
    NOT_ALLOWED_HANDLER,
    OPTION_HANDLER,
    COOKIE_HANDLER,
    TEAPOT_HANDLER,
}
