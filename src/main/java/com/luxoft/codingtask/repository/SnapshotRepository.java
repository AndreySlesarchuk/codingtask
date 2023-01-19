package com.luxoft.codingtask.repository;

import com.luxoft.codingtask.entity.Snapshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnapshotRepository extends JpaRepository<Snapshot, String> {

}
