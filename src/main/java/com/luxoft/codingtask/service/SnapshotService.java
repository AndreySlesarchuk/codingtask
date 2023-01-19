package com.luxoft.codingtask.service;

import com.luxoft.codingtask.entity.Snapshot;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;


public interface SnapshotService {

  void uploadSnapshots(MultipartFile file);

  void addSnapshot(String[] recordIn);

  Snapshot getSnapshot(String primaryKey);

  List<Snapshot> getAllSnapshots();

  void deleteSnapshot(String primaryKey);

}
