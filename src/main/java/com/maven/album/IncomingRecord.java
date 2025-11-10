package com.maven.album;

import java.util.List;

public class IncomingRecord {

    private String projectId;
    private String producerName;
    private String projectStartDate;
    private String dateReceived;
    private String album8;
    private String shuffle8;
    private String album9;
    private String shuffle9;
    private String meta;
    private List<String> songPages;

    public IncomingRecord(
            String projectId,
            String producerName,
            String projectStartDate,
            String dateReceived,
            String album8,
            String shuffle8,
            String album9,
            String shuffle9,
            String meta,
            List<String> songPages
    ) {
        this.projectId = projectId;
        this.producerName = producerName;
        this.projectStartDate = projectStartDate;
        this.dateReceived = dateReceived;
        this.album8 = album8;
        this.shuffle8 = shuffle8;
        this.album9 = album9;
        this.shuffle9 = shuffle9;
        this.meta = meta;
        this.songPages = songPages;
    }

    // Getters
    public String getProjectId() { return projectId; }
    public String getProducerName() { return producerName; }
    public String getProjectStartDate() { return projectStartDate; }
    public String getDateReceived() { return dateReceived; }
    public String getAlbum8() { return album8; }
    public String getShuffle8() { return shuffle8; }
    public String getAlbum9() { return album9; }
    public String getShuffle9() { return shuffle9; }
    public String getMeta() { return meta; }
    public List<String> getSongPages() { return songPages; }
}
