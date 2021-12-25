package com.recruitme.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Video class hold various properties of video
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01 2021 11:58:00 AM
 */
@Entity
@Setter
@Getter
@Table(name="video")
@NoArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false,length=2048)
    private String videoExternalId;

    @Column(nullable = false,length = 100)
    private String title;

    @Column(nullable = false,length = 100)
    private String fileName;

    @Column(nullable = true,length = 20)
    private String fileFormat;

    @Column(nullable = false)
    private LocalDateTime uploadDate;

    @ManyToOne
    private PlacementPreparationFaculty uploadBy;

    @ManyToOne(cascade = CascadeType.ALL)
    private Topic topic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return getId().equals(video.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", videoExternalId='" + videoExternalId + '\'' +
                ", title='" + title + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileFormat='" + fileFormat + '\'' +
                ", uploadDate=" + uploadDate +
                ", uploadBy=" + uploadBy +
                ", topic=" + topic +
                '}';
    }
}
