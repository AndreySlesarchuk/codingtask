package com.luxoft.codingtask;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.luxoft.codingtask.controller.SnapshotController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.yml")
class SnapshotTest {

  @Autowired
  private SnapshotController snapshotController;

  @Test
  public void contextLoads() {
    assertThat(snapshotController).isNotNull();
  }
}
