package com.user.managament.model.classroom;

import com.user.managament.model.user.User;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.UUID;

@Entity
public class Classroom {

    public Classroom(ClassroomType classroomType, User user, LocalTime startTime) {
        this.startTime = startTime;
        this.endTime = startTime.plusHours(1);
        this.user = user;
        this.classroomType = classroomType;
    }

    public Classroom() {}

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ClassroomType classroomType;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ClassroomType getClassroomType() {
        return classroomType;
    }

    public void setClassroomType(ClassroomType classroomType) {
        this.classroomType = classroomType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
}
