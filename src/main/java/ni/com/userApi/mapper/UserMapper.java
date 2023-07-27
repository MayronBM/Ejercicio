package ni.com.userApi.mapper;

import ni.com.userApi.dto.Token;
import ni.com.userApi.dto.UserDto;
import ni.com.userApi.persistencia.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(
    componentModel = "spring",
    uses = {PhoneMapper.class},
    unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper {
  @Mapping(target = "id", source = "usuario.id")
  @Mapping(target = "name", source = "usuario.nombre", ignore = true)
  @Mapping(target = "email", source = "usuario.email", ignore = true)
  @Mapping(target = "password", source = "usuario.clave", ignore = true)
  @Mapping(target = "phones", source = "usuario.telefonos", ignore = true)
  @Mapping(target = "created", source = "usuario.creado")
  @Mapping(target = "modified", source = "usuario.modificado")
  @Mapping(target = "isactive", source = "usuario.activo")
  @Mapping(target = "token", source = "token.token")
  UserDto convertirGuardar(Usuario usuario, Token token);

  //    @InheritInverseConfiguration
  @Mapping(source = "name", target = "nombre")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "password", target = "clave")
  @Mapping(source = "created", target = "creado")
  @Mapping(source = "modified", target = "modificado")
  @Mapping(source = "isactive", target = "activo")
  @Mapping(source = "phones", target = "telefonos")
  Usuario convertir(UserDto usuario);
}
