package ru.fastdelivery.presentation.api.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.fastdelivery.presentation.exception.ApiError;

public class ApiErrorTest {

  @Test
  void testRecordMethods() {
    HttpStatus testHttpStatus = HttpStatus.BAD_REQUEST;
    String testStatus = "error";
    String testMessage = "Some error message";
    ApiError apiError = new ApiError(testHttpStatus, testStatus, testMessage);
    assertThat(apiError.httpStatus()).isEqualTo(testHttpStatus);
    assertThat(apiError.status()).isEqualTo(testStatus);
    assertThat(apiError.message()).isEqualTo(testMessage);
    String errorMessage = "Bad request error";
    ApiError expectedError = new ApiError(HttpStatus.BAD_REQUEST, "error", errorMessage);
    ApiError actualError = ApiError.badRequest(errorMessage);
    assertThat(actualError).isEqualTo(expectedError);
    ApiError sameApiError = new ApiError(testHttpStatus, testStatus, testMessage);
    ApiError differentApiError =
        new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "error", "Different message");
    assertThat(apiError).isEqualTo(sameApiError);
    assertThat(apiError).isNotEqualTo(differentApiError);
    assertThat(apiError.toString())
        .isEqualTo(
            "ApiError[httpStatus=400 BAD_REQUEST, status=error, message=Some error message]");
  }
}
