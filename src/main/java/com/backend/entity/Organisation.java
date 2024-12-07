package com.backend.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    //private List<User> users;
    //private List<Task> tasks;
}
