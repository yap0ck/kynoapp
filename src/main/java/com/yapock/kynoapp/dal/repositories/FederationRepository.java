package com.yapock.kynoapp.dal.repositories;

import com.yapock.kynoapp.dal.models.Federation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FederationRepository extends JpaRepository<Federation, UUID> {
}
