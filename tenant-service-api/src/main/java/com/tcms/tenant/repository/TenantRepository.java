package com.tcms.tenant.repository;

import com.tcms.tenant.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Optional<Tenant> findByDomainName(String domainName);
    List<Tenant> findByIdIn(List<Long> userIds);
    Boolean existsByDomainName(String domainName);
}
