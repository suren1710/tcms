package com.tcms.tenant.service;

import com.tcms.tenant.model.Tenant;

import java.util.List;

public interface TenantService {

    Tenant createTenant(final Tenant tenant);

    Tenant updateTenant(final Tenant tenant);

    Tenant deleteTenant(final Tenant tenant);

    Tenant getTenantById(final Long id);

    List<Long> getAllTenantIds();
}
