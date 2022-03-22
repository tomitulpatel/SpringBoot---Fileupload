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

   /* @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email_id", referencedColumnName = "email_id")
    private User user;*/
    @Column(name="EMAIL_ID")
    private String emailId;


    public Attachment(String fileName, String fileType, byte[] data, Date date, String emailId) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.creation_date = date;
        this.emailId = emailId;
    }
}
