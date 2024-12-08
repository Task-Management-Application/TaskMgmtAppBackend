package com.backend.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Task {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int taskId;
    private String title;
    private String descrString;
    private String status;
    @ManyToOne
    @JoinColumn(name="org_id")
    private Organisation organisation;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;
    private Date createdAt;
    private Date lastUpdatedAt;
    private int deadline;
    private int priority;
    @OneToMany(mappedBy = "task",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> comments;
}
