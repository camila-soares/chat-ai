package com.github.chat_ai.controller;

import com.github.chat_ai.service.DocIngestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;


@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class UploadController {

    private final DocIngestionService docIngestionService;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<String>> tarefaComRetorno(@RequestPart("file") MultipartFile file) throws IOException {
        log.info("Uploading file [{}]", file);


//        file((h,j) ->
//                log.info("\"[{h}] -> \"[{j}]"));

        String fileName = file.toString();

        String uploadDirectory = "tmp";
        File newFile = new File(uploadDirectory + File.separator + fileName);


        CompletableFuture<Void> runnable = getVoidCompletableFuture(file, newFile);
        return CompletableFuture.completedFuture(ResponseEntity.ok("Upload completed: " + runnable.toString()));
    //return ResponseEntity.ok("PDF recebido");
    }

    @NotNull
    private CompletableFuture<Void> getVoidCompletableFuture(MultipartFile file, File newFile) {
        CompletableFuture<Void> runnable = new CompletableFuture<>();
        runnable.thenRun(() -> {
            try {
                docIngestionService.storeDocument(newFile.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        runnable.thenRunAsync(() -> {
            try {
                file.transferTo(newFile.getAbsoluteFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return runnable;
    }
}
