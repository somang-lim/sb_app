package com.ll.exam.app10.app.fileUpload.repository;

import com.ll.exam.app10.app.fileUpload.entity.GenFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenFileRepository extends JpaRepository<GenFile, Long> {
    List<GenFile> findByRelTypeCodeAndRelIdOrderByTypeCodeAscType2CodeAscFileNoAsc(String article, Long id);

    Optional<GenFile> findByRelTypeCodeAndRelIdAndTypeCodeAndType2CodeAndFileNo(String relTypeCode, long relId, String typeCode, String type2Code, int fileNo);
}
