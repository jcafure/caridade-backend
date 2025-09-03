package dev.caridadems.mapper;

import dev.caridadems.domain.StatusDonationItemMenuCampaign;
import dev.caridadems.dto.DonationItemDTO;
import dev.caridadems.model.DonationItem;
import dev.caridadems.model.MenuCampaign;
import dev.caridadems.model.Product;
import dev.caridadems.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DonationItemMapper {

    private final ProductMapper productMapper;
    private final ProductService productService;

    public DonationItem dtoToEntity(DonationItemDTO dto, Product product) {
        var donationItem = new DonationItem();
        if (dto.getId() != null) {
            donationItem.setId(dto.getId());
        }
        donationItem.setProduct(product);
        donationItem.setStatusItem(StatusDonationItemMenuCampaign.toEnum(dto.getStatusItem()));
        donationItem.setQuantity(dto.getQuantity());
        return donationItem;
    }

    public List<DonationItemDTO> entityToDto(List<DonationItem> entities) {
        return entities.stream().map(entity -> {
            var dto = new DonationItemDTO();
            if (entity.getId() != null) {
                dto.setId(entity.getId());
            }
            dto.setProductDTO(productMapper.converterEntityToDto(entity.getProduct()));
            dto.setQuantity(entity.getQuantity());
            dto.setStatusItem(entity.getStatusItem() != null ? entity.getStatusItem().getValue()
                    : StatusDonationItemMenuCampaign.FOR_DONATED.getValue());
            return dto;
        }).toList();
    }

    public List<DonationItem> convertDtoListToEntity(List<DonationItemDTO> donationItemDTOS,
                                                     MenuCampaign menuCampaign) {
        if (donationItemDTOS == null){
            return List.of();
        }

        return donationItemDTOS.stream()
                .map(donationDto -> {
                    var product = productService.findById(donationDto.getProductDTO().getId());
                    var donationItem = dtoToEntity(donationDto, product);
                    donationItem.setMenuCampaign(menuCampaign);
                    donationItem.setStatusItem(StatusDonationItemMenuCampaign.FOR_DONATED);
                    return donationItem;
                }).toList();
    }
}
