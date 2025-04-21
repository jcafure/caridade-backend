package dev.caridadems.service;

import dev.caridadems.dto.DonorRegisterDto;
import dev.caridadems.dto.DonorRegisterResponseDto;
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
    public DonorRegisterResponseDto newDonor(DonorRegisterDto donorRegisterDto) {
        var donor = donorMapper.dtoToEntity(donorRegisterDto);
        donor.setAddress(buildAddress(donorRegisterDto));
        var donorSaved = donorRepository.save(donor);
        return new DonorRegisterResponseDto(true, donorSaved.getId());

    }

    private List<Address> buildAddress(DonorRegisterDto donorRegisterDto) {
        return donorRegisterDto.getAddressDTOS().stream()
                .map(dto -> addressMapper.convertDtoToEntity(dto,
                        cityService.findCityByName(dto.getCity().getName())))
                .toList();
    }

}
