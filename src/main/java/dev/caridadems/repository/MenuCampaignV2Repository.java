package dev.caridadems.repository;

import dev.caridadems.model.MenuCampaignV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuCampaignV2Repository extends JpaRepository<MenuCampaignV2, Integer> {
}
