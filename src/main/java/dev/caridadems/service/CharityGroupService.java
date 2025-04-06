package dev.caridadems.service;

import dev.caridadems.dto.CharityGroupDTO;
import dev.caridadems.mapper.AddressMapper;
import dev.caridadems.mapper.CharityGroupMapper;
import dev.caridadems.repository.CharityGroupRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CharityGroupService {

    private CharityGroupMapper charityGroupMapper;
    private AddressMapper addressMapper;
    private CharityGroupRepository charityGroupRepository;
    private CityService cityService;

    @Transactional
    public CharityGroupDTO createCharityGroup(CharityGroupDTO charityGroupDTO) {
        var group = charityGroupMapper.converterDtoToEntity(charityGroupDTO);

        var addresses = charityGroupDTO.getAddress().stream()
                .map(dto -> {
                    var city = cityService.findCityByName(dto.getCity().getName());
                    var address = addressMapper.convertDtoToEntity(dto, city);
                    address.setCharityGroup(group);
                    return address;
                })
                .toList();

        group.setAddress(addresses);
        return charityGroupMapper.toDTO(charityGroupRepository.save(group));
    }
}
