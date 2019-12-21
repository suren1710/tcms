package com.tcms.tenant.model;

import com.tcms.tenant.model.audit.UserAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "tenant", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id"}),
        @UniqueConstraint(columnNames = {"domain_name"})
})
public class Tenant extends UserAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "domain_name")
    private String domainName;

    @NotBlank
    @Column(name = "subscrption_id")
    private Long subscriptionId;

    @NotBlank
    @Column(name = "subscription_end_on")
    private Date subscriptionEndsOn;

    public Long getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public String getDomainName() {

        return domainName;
    }

    public Long getSubscriptionId() {

        return subscriptionId;
    }

    public Date getSubscriptionEndsOn() {
        return subscriptionEndsOn;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setDomainName(String domainName) {

        this.domainName = domainName;
    }

    public void setSubscriptionId(Long subscriptionId) {

        this.subscriptionId = subscriptionId;
    }

    public void setSubscriptionEndsOn(Date subscriptionEndsOn) {
        this.subscriptionEndsOn = subscriptionEndsOn;
    }
}
