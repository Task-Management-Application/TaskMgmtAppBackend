package com.backend.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="organisation")
@NoArgsConstructor
@Getter
@Setter
public class Organisation {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int orgId;
    private String orgName;
    private int createdBy;
    private Date createdAt;
    //Todo: It will be many to many relationships
    @ManyToMany(mappedBy = "orgList",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<User> users;

    @ElementCollection
    @CollectionTable(name = "organisation_user_roles",
    joinColumns = @JoinColumn(name = "organisation_id"))
    @MapKeyJoinColumn(name = "user_id")
    @Column(name = "is_admin")
    private Map<User,Boolean> userRoles=new HashMap<>();
    @OneToMany(mappedBy = "organisation",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Task> tasks;
    public void makeAdmin(User user, Boolean makeValue)
    {
        userRoles.put(user, makeValue);
    }
    public Boolean isAdmin(User user)
    {
        return userRoles.get(user);
    }
}
