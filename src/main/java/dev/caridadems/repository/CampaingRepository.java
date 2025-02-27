package dev.caridadems.repository;

import dev.caridadems.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaingRepository extends JpaRepository<Campaign, Integer> {
}
