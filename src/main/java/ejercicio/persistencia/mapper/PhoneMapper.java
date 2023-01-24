package ejercicio.persistencia.mapper;

import ejercicio.persistencia.dto.PhoneDto;
import ejercicio.persistencia.dto.UserDto;
import ejercicio.persistencia.entidad.Telefono;
import ejercicio.persistencia.entidad.Usuario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PhoneMapper {
    @Mapping(target = "number", source = "numero")
    @Mapping(target = "citycode", source = "codigoCiudad")
    @Mapping(target = "contrycode", source = "codigoPais")
    PhoneDto convertir(Telefono telefono);

    @InheritInverseConfiguration
    Telefono convertir(PhoneDto phoneDto);
}
