package devlab.storage.dbStorage.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "files")
public class DbFile {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    private Date date;

    public DbFile(String fileName, String fileType, byte[] data, Date date) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.date = date;

    }
}
