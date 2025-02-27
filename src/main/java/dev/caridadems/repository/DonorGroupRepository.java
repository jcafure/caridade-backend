package dev.caridadems.repository;

import dev.caridadems.model.DonorGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonorGroupRepository extends JpaRepository<DonorGroup, Integer> {
}
