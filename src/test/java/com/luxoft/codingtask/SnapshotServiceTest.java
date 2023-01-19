package com.luxoft.codingtask;

import static com.luxoft.codingtask.service.SnapshotServiceImpl.SPLIT_BY;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.luxoft.codingtask.entity.Snapshot;
import com.luxoft.codingtask.service.SnapshotService;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.yml")
class SnapshotServiceTest {

  @Autowired
  private SnapshotService snapshotService;

  @Test
  void testService() {
    // creating snapshot test record
    String[] dataIn = "Test,Wroclaw, Poland,+2022-02-22T01:02:03Z".split(
        String.valueOf(SPLIT_BY));
    snapshotService.addSnapshot(dataIn);

    // get persisted data by PRIMARY_KEY
    Snapshot snapshot = snapshotService.getSnapshot("Test");
    assertNotNull(snapshot);

    // remove record from storage by PRIMARY_KEY
    snapshotService.deleteSnapshot("Test");

    // wrong key response
    Executable executable = () -> snapshotService.getSnapshot("Test");
    assertThrows(ResponseStatusException.class, executable);
  }

  @Test
  void testUpload() throws IOException {
    MultipartFile multipartFile = new MockMultipartFile("sourceFile.tmp", "Hello World".getBytes());
    File file = new File("snapshot-input-data.csv");
    multipartFile.transferTo(file);

    snapshotService.uploadSnapshots(multipartFile);

    assertTrue(snapshotService.getAllSnapshots().size() > 0);
  }
}
