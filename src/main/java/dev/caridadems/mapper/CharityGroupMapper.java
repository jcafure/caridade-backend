package dev.caridadems.mapper;

import dev.caridadems.dto.AddressDTO;
import dev.caridadems.dto.CharityGroupDTO;
import dev.caridadems.model.CharityGroup;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CharityGroupMapper {

    public CharityGroup converterDtoToEntity(CharityGroupDTO dto) {
        final var charityGroup = new CharityGroup();
        charityGroup.setName(dto.getName());
        charityGroup.setEmail(dto.getEmail());
        charityGroup.setPhone(dto.getPhone());
        charityGroup.setDescription(dto.getDescription());
        return  charityGroup;
    }

    public CharityGroupDTO toDTO(CharityGroup entity) {
        final var dto = new CharityGroupDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());

        List<AddressDTO> addressDTOs = entity.getAddress().stream()
                .map(AddressMapper::toDTO)
                .toList();

        dto.setAddress(addressDTOs);
        return dto;
    }

}
