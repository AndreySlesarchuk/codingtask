
package com.luxoft.codingtask.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SNAPSHOTS")
public class Snapshot {

  @Id
  @Column(name = "PRIMARY_KEY", unique = true)
  private String primaryKey;

  private String name;
  private String description;
  private LocalDateTime updatedTime;

  public Snapshot(String[] data) {
    this.primaryKey = data[0];
    this.name = data[1];
    this.description = data[2];
  }

}
