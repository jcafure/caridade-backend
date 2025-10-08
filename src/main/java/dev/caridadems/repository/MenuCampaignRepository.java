package dev.caridadems.repository;

import dev.caridadems.model.MenuCampaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuCampaignRepository extends JpaRepository<MenuCampaign, Integer> {

    Page<MenuCampaign> findByMealTypeContainingIgnoreCase(String mealType, Pageable page);
}
