package dev.caridadems.repository;

import dev.caridadems.model.MenuCampaignDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuCampaignDonationRepository extends JpaRepository<MenuCampaignDonation, Integer> {
}
