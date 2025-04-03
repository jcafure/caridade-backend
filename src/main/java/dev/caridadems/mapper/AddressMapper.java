package dev.caridadems.mapper;

import dev.caridadems.domain.TypeAddress;
import dev.caridadems.dto.AddressDTO;
import dev.caridadems.dto.CityDTO;
import dev.caridadems.model.Address;
import dev.caridadems.model.City;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address convertDtoToEntity(AddressDTO dto, City city) {
        final var address = new Address();
        address.setCep(dto.getCep());
        address.setStreet(dto.getStreet());
        address.setNumber(dto.getNumber());
        address.setComplement(dto.getComplement());
        address.setCity(city);
        address.setTypeAddress(TypeAddress.toEnum(dto.getTypeAddress()));
        return address;
    }

    public static AddressDTO toDTO(Address entity) {
        final var dto = new AddressDTO();
        dto.setId(entity.getId());
        dto.setCep(entity.getCep());
        dto.setStreet(entity.getStreet());
        dto.setNumber(entity.getNumber());
        dto.setComplement(entity.getComplement());
        dto.setTypeAddress(entity.getTypeAddress().getValue());

        if (entity.getCity() != null) {
            dto.setCity(toCityDTO(entity.getCity()));
        }

        return dto;
    }

    private static CityDTO toCityDTO(City city) {
        final var dto = new CityDTO();
        dto.setName(city.getName());
        dto.setState(city.getState().getName());
        return dto;
    }
}
