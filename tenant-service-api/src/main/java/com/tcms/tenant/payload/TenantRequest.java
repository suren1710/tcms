package com.tcms.tenant.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TenantRequest {

    @NotBlank
    @Size(max = 140)
    private String name;

    @NotBlank
    @Size(max = 40)
    private String domainName;

    @NotBlank
    private int subscriptionId;

    public void setName(String name) {
        this.name = name;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getName() {
        return name;
    }

    public String getDomainName() {
        return domainName;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }
}

