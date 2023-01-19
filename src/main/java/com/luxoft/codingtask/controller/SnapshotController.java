package com.luxoft.codingtask.controller;

import static com.luxoft.codingtask.service.SnapshotServiceImpl.SPLIT_BY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

import com.luxoft.codingtask.entity.Snapshot;
import com.luxoft.codingtask.service.SnapshotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Snapshot", description = "API for Snapshot controller")
@ApiResponse(responseCode = "500", description = "Internal error occurred")
@RestController
@RequestMapping(SnapshotController.SNAPSHOTS_URI)
public class SnapshotController {

  public static final String SNAPSHOTS_URI = "/snapshots";

  private final SnapshotService snapshotService;

  public SnapshotController(SnapshotService snapshotService) {
    this.snapshotService = snapshotService;
  }

  @Operation(summary = "Upload data from a .csv file",
      responses = {
          @ApiResponse(responseCode = "200", description = "success"),
          @ApiResponse(responseCode = "400", description = "Wrong data to upload")
      })
  @PostMapping(path = "/upload")
  public ResponseEntity<Void> uploadData(
      @RequestParam("file") MultipartFile file) {
    snapshotService.uploadSnapshots(file);
    return ok().build();
  }

  @Operation(summary = "Create a snapshot",
      responses = {
          @ApiResponse(responseCode = "200", description = "Snapshot created",
              content = @Content(schema = @Schema(implementation = Snapshot.class))),
          @ApiResponse(responseCode = "400", description = "Snapshot not added")
      })
  @PostMapping(path = "/", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> addSnapshot(@RequestParam String csvCommaSeperatedLine) {
    snapshotService.addSnapshot(csvCommaSeperatedLine.split(String.valueOf(SPLIT_BY)));
    return ok().build();
  }

  @Operation(summary = "Get a snapshot by primary key",
      responses = {
          @ApiResponse(responseCode = "200", description = "success",
              content = @Content(schema = @Schema(implementation = Snapshot.class))),
          @ApiResponse(responseCode = "404", description = "Snapshot not found")
      })
  @GetMapping(path = "/{primaryKey}", produces = APPLICATION_JSON_VALUE)
  public Snapshot getSnapshot(@PathVariable String primaryKey) {
    return snapshotService.getSnapshot(primaryKey);
  }

  @Operation(summary = "Delete a snapshot by primary key",
      responses = {
          @ApiResponse(responseCode = "200", description = "Snapshot deleted"),
          @ApiResponse(responseCode = "404", description = "Snapshot not found")
      })
  @DeleteMapping(path = "/{primaryKey}")
  public ResponseEntity<Void> delete(@PathVariable String primaryKey) {
    snapshotService.deleteSnapshot(primaryKey);
    return ok().build();
  }
}