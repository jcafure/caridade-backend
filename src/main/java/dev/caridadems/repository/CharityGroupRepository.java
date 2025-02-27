package dev.caridadems.repository;

import dev.caridadems.model.CharityGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharityGroupRepository extends JpaRepository<CharityGroup, Integer> {
}
