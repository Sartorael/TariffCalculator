package ru.fastdelivery.presentation.api.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.fastdelivery.presentation.exception.ApiError;
import ru.fastdelivery.presentation.exception.GlobalExceptionHandler;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

  @Mock private IllegalArgumentException illegalArgumentException;

  @InjectMocks private GlobalExceptionHandler globalExceptionHandler;

  @BeforeEach
  void setUp() {
    when(illegalArgumentException.getMessage()).thenReturn("Invalid input");
  }

  @Test
  void testHandleIllegalArgument() {
    ResponseEntity<ApiError> responseEntity =
        globalExceptionHandler.handleIllegalArgument(illegalArgumentException);
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(responseEntity.getBody().status()).isEqualTo("error");
    assertThat(responseEntity.getBody().message()).isEqualTo("Invalid input");
  }
}
