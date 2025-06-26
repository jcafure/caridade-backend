package dev.caridadems.mapper;

import dev.caridadems.domain.StatusDonationItemMenuCampaign;
import dev.caridadems.dto.DonationItemDTO;
import dev.caridadems.model.DonationItem;
import dev.caridadems.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DonationItemMapper {

    private final ProductMapper productMapper;

    public DonationItem dtoToEntity(DonationItemDTO dto, Product product) {
        var donationItem = new DonationItem();
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
            dto.setStatusItem(entity.getStatusItem().getValue());
            return dto;
        }).toList();
    }
}
