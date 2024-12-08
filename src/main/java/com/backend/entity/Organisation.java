package com.backend.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
    @OneToMany(mappedBy = "organisation",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Task> tasks;
}
