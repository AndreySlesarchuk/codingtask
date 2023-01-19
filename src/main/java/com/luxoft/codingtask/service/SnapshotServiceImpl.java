package com.luxoft.codingtask.service;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.luxoft.codingtask.entity.Snapshot;
import com.luxoft.codingtask.repository.SnapshotRepository;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class SnapshotServiceImpl implements SnapshotService {

  private static final Logger logger = LoggerFactory.getLogger(SnapshotServiceImpl.class);
  public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
          "+yyyy-MM-dd'T'HH:mm:ss'Z'")
      .withZone(ZoneId.of("UTC"));
  public static final Character SPLIT_BY = ',';

  private final SnapshotRepository snapshotRepository;

  public SnapshotServiceImpl(SnapshotRepository snapshotRepository) {
    this.snapshotRepository = snapshotRepository;
  }

  @Override
  public void uploadSnapshots(MultipartFile file) {
    long startTime = System.nanoTime();
    try (CSVReader csvReader = new CSVReader(
        new InputStreamReader(file.getInputStream()), SPLIT_BY)) {

      ExecutorService service = Executors.newFixedThreadPool(5);
      csvReader.readNext();
      String[] line;

      while ((line = csvReader.readNext()) != null) {
        String[] finalLine = line;
        service.submit(() -> addSnapshot(finalLine));
      }

    } catch (IOException e) {
      throw new ResponseStatusException(BAD_REQUEST, "Data loading is not correct");
    }
    String result =
        "Uploaded file " + file.getName() + " in :" + (System.nanoTime() - startTime) / 1_000_000
            + " mc";
    logger.info(result);
  }

  @Override
  public void addSnapshot(String[] recordIn) {
    if (isNotEmpty(recordIn[0])) {
      Snapshot snapshot = new Snapshot(recordIn);
      try {
        if (isNotEmpty(recordIn[3])) {
          snapshot.setUpdatedTime(LocalDateTime.parse(recordIn[3].trim(), formatter));
        }
      } catch (Exception e) {
        logger.error("Failed date conversion");
      }
      snapshotRepository.save(snapshot);
    }
  }

  @Override
  public Snapshot getSnapshot(String primaryKey) {
    return fetchSnapshot(primaryKey);
  }

  @Override
  public List<Snapshot> getAllSnapshots() {
    return snapshotRepository.findAll();
  }

  @Override
  public void deleteSnapshot(String primaryKey) {
    snapshotRepository.delete(fetchSnapshot(primaryKey));
  }

  private Snapshot fetchSnapshot(String primaryKey) {
    return snapshotRepository.findById(primaryKey)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
            "Snapshot with key '" + primaryKey + "' not found"));
  }

}
