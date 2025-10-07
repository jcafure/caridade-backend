package dev.caridadems.repository;

import dev.caridadems.domain.StatusCampaign;
import dev.caridadems.model.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampaingRepository extends JpaRepository<Campaign, Integer> {

    Page<Campaign> findAllByStatusIn(List<StatusCampaign> statuses, Pageable pageable);
    Optional<Campaign> findByIdAndStatusIn(Integer id, List<StatusCampaign> statusCampaigns);
}
