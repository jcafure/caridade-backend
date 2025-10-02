package dev.caridadems.repository;

import dev.caridadems.domain.StatusCampaign;
import dev.caridadems.model.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaingRepository extends JpaRepository<Campaign, Integer> {

    Page<Campaign> findAllByStatus(StatusCampaign status, Pageable pageable);
}
