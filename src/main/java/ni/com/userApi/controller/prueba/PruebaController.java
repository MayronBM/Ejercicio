package ni.com.userApi.controller.prueba;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pruebas")
@Tag(name = "Prueba", description = "Recursos de prueba")
public class PruebaController {
  @GetMapping(value = "/noSeguro", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public String getMessage() {
    return "Mensaje sin seguridad..";
  }

  @GetMapping(value = "/seguro", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public String getRestrictedMessage() {
    return "Mensaje aplicando seguridad";
  }
}
