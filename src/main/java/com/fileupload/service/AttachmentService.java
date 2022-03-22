package com.fileupload.service;

import com.fileupload.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    Attachment saveAttachment(MultipartFile file, String emailId) throws Exception;

    Attachment getAttachment(String fileId) throws Exception;
}
