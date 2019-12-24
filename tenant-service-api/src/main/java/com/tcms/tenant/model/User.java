package com.tcms.tenant.model;

import com.tcms.tenant.model.audit.UserAudit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id"}),
        @UniqueConstraint(columnNames = {"email_address"})
})
public class User extends UserAudit {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @NotBlank
        @Column(name = "email_address")
        private String emailAddress;

        @NotBlank
        @Column(name = "first_name")
        private String firstName;

        @NotBlank
        @Column(name = "last_name")
        private String lastName;

        @NotBlank
        @Column(name = "password_sha_256")
        private String password;

        @NotBlank
        @Column(name = "tenant_id")
        private Long tenantId;

        @NotBlank
        @Column(name = "status")
        private String status;

        @NotBlank
        @Column(name = "type_id")
        private Integer loginTypeId;

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "jt_user_role",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
        private Set<Role> roles = new HashSet<>();

        public User() {
        }

        public User(@NotBlank String emailAddress,
                    @NotBlank String firstName,
                    @NotBlank String lastName,
                    @NotBlank String password,
                    @NotBlank Long tenantId,
                    @NotBlank String status,
                    @NotBlank Integer loginTypeId) {

                this.emailAddress = emailAddress;
                this.firstName = firstName;
                this.lastName = lastName;
                this.password = password;
                this.tenantId = tenantId;
                this.status = status;
                this.loginTypeId = loginTypeId;
        }

        public Long getId() {
                return id;
        }

        public String getEmailAddress() {
                return emailAddress;
        }

        public String getFirstName() {

                return firstName;
        }

        public String getLastName() {

                return lastName;
        }

        public String getPassword() {

                return password;
        }

        public Long getTenantId() {

                return tenantId;
        }

        public String getStatus() {
                return status;
        }

        public Integer getLoginTypeId() {
                return loginTypeId;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public void setEmailAddress(String emailAddress) {
                this.emailAddress = emailAddress;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public void setTenantId(Long tenantId) {
                this.tenantId = tenantId;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public void setLoginTypeId(Integer loginTypeId) {
                this.loginTypeId = loginTypeId;
        }

        public Set<Role> getRoles() {
                return roles;
        }

        public void setRoles(Set<Role> roles) {
                this.roles = roles;
        }
}
