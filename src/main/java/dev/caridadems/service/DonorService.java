package dev.caridadems.service;

import dev.caridadems.dto.DonorRegisterDTO;
import dev.caridadems.dto.DonorRegisterResponseDTO;
import dev.caridadems.mapper.AddressMapper;
import dev.caridadems.mapper.DonorMapper;
import dev.caridadems.model.Address;
import dev.caridadems.repository.DonorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DonorService {

    private final DonorRepository donorRepository;
    private final AddressMapper addressMapper;
    private final CityService cityService;
    private final DonorMapper donorMapper;

    @Transactional
    public DonorRegisterResponseDTO newDonor(DonorRegisterDTO donorRegisterDto) {
        var donor = donorMapper.dtoToEntity(donorRegisterDto);
        List<Address> addresses = buildAddress(donorRegisterDto);
        addresses.forEach(address -> address.setDonor(donor));
        donor.setAddress(addresses);
        var donorSaved = donorRepository.save(donor);
        return new DonorRegisterResponseDTO(true, donorSaved.getId(), donorSaved.getName());

    }

    private List<Address> buildAddress(DonorRegisterDTO donorRegisterDto) {
        return donorRegisterDto.getAddressDTOS().stream()
                .map(dto -> addressMapper.convertDtoToEntity(dto,
                        cityService.findCityByName(dto.getCity().getName())))
                .toList();
    }

}
