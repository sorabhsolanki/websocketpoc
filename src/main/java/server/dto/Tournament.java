package server.dto;

import java.util.Date;
import java.util.UUID;

/**
 */
public class Tournament {

  private String id;
  private String name;
  private String adminName;
  private Date createdAt;
  private boolean isActive;

  public Tournament(String name, String adminName, boolean isActive) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.adminName = adminName;
    this.isActive = isActive;
    this.createdAt = new Date();
  }

  public Tournament() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAdminName() {
    return adminName;
  }

  public void setAdminName(String adminName) {
    this.adminName = adminName;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }
}
