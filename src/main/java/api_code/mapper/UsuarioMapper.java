package api_code.mapper;

import org.mapstruct.Mapper;

import api_code.dto.UsuarioRequestDTO;
import api_code.dto.UsuarioResponseDTO;
import api_code.entity.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioRequestDTO dto);

    UsuarioResponseDTO toDTO(Usuario usuario);
}