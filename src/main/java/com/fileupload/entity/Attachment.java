package com.fileupload.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data@NoArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;
    private String fileType;

    @Lob
    private byte[] data;

    @Column(name="CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation_date;


    public Attachment(String fileName, String fileType, byte[] data, Date date) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.creation_date = date;
    }
}
