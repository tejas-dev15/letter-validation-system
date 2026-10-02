package com.mrsac.lettervalidation.repository;

import com.mrsac.lettervalidation.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document,Long> {
}
