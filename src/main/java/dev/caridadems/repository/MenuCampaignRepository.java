package dev.caridadems.repository;

import dev.caridadems.model.MenuCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuCampaignRepository extends JpaRepository<MenuCampaign, Integer>{
}
