package dev.caridadems.mapper;

import dev.caridadems.dto.DonorRegisterDTO;
import dev.caridadems.model.Donor;
import org.springframework.stereotype.Component;

@Component
public class DonorMapper {

    public Donor dtoToEntity(DonorRegisterDTO dto) {
        final var donor = new Donor();
        donor.setEmail(dto.getEmail());
        donor.setName(dto.getName());
        donor.setPhone(dto.getPhone());
        donor.setExternalId(dto.getExternalId());
        return donor;
    }
}
